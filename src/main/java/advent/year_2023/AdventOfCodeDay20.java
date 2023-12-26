package advent.year_2023;

import utils.FileLineReader;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static utils.MathUtils.lcm;
import static utils.RegexUtils.parseByPattern;


public class AdventOfCodeDay20 {

    private static final Pattern LINE_PATTERN = Pattern.compile("^((%|&)?)(\\w+) -> (((\\w+)(, )?)+)$");
    private static int a;

    static int task1(String inputFileName) {
        Map<String, Module> vertexMap = readFile(inputFileName);

        Module start = vertexMap.get("broadcaster");

        int low = 0, high = 0;
        for (int i = 0; i < 1000; i++) {
            Queue<State> queue = new LinkedList<>();
            queue.add(new State(null, start, false));
            while (!queue.isEmpty()) {
                State state = queue.poll();

                if (state.isHigh) {
                    high++;
                } else {
                    low++;
                }

                queue.addAll(calcNextStates(state));
            }
        }
        return low * high;
    }

    public static long task2(String inputFileName) {
        Map<String, Module> vertexMap = readFile(inputFileName);
        Module start = vertexMap.get("broadcaster");
        Map<String, Long> result = new HashMap<>();

        long i = 1;
        while (!result.containsKey("rx")) {
            Queue<State> queue = new LinkedList<>();
            queue.add(new State(null, start, false));
            while (!queue.isEmpty()) {
                State state = queue.poll();
                queue.addAll(calcNextStates(state));

                Module module = state.to;
                if (module.type == Type.CON && state.from.type == Type.CON) {
                    Set<String> parents = module.memory.keySet().stream().map(e -> e.name).collect(Collectors.toSet());
                    if (parents.stream().allMatch(result::containsKey)) {
                        result.put(module.name, lcm(result.entrySet().stream()
                                .filter(e -> parents.contains(e.getKey()))
                                .map(Map.Entry::getValue)
                                .toList())
                        );
                    }
                } else if (module.type == Type.CON && module.memory.values().stream().allMatch(e -> e)) {
                    result.put(module.name, i);
                } else if (module.type == Type.NO_ACTION) {
                    if (result.containsKey(state.from.name)) {
                        result.put(module.name, result.get(state.from.name));
                    }

                }
            }

            i++;
        }
        return result.get("rx");
    }

    private static Map<String, Module> readFile(String inputFileName) {
        Map<String, Module> vertexMap = new HashMap<>();
        for (String line : FileLineReader.of(inputFileName)) {
            String[] lineParts = parseByPattern(line, LINE_PATTERN, 1, 3, 4);

            String name = lineParts[1];
            Module module = vertexMap.containsKey(name) ? vertexMap.get(name) : new Module(name);
            module.type = Type.getType(name, lineParts[0]);

            vertexMap.put(name, module);
            Arrays.stream(lineParts[2].split(", ")).forEach(childName -> {
                if (vertexMap.containsKey(childName)) {
                    Module child = vertexMap.get(childName);
                    module.children.add(child);
                    child.memory.put(module, false);
                } else {
                    Module child = new Module(childName);
                    vertexMap.put(childName, child);
                    module.children.add(child);
                    child.memory.put(module, false);
                }
            });

        }
        return vertexMap;
    }

    private static List<State> calcNextStates(State state) {
        Module module = state.to;
        Boolean isHigh = state.isHigh;
        Module from = state.from;

        return switch (module.type) {
            case SEND -> module.children.stream().map(v -> new State(module, v, isHigh)).toList();
            case FLIP -> {
                if (!isHigh) {
                    module.isOn = !module.isOn;
                    yield module.children.stream().map(v -> new State(module, v, module.isOn)).toList();
                }
                yield List.of();
            }
            case CON -> {
                module.memory.put(from, isHigh);
                yield module.children.stream().map(v -> new State(module, v, !module.memory.values().stream().allMatch(e -> e))).toList();
            }
            default -> List.of();
        };
    }

    private record State(Module from, Module to, Boolean isHigh) {
    }

    private static class Module {

        String name;
        Type type;

        boolean isOn;

        Map<Module, Boolean> memory;

        List<Module> children;

        public Module(String name) {
            this.name = name;
            this.type = Type.NO_ACTION;
            this.isOn = false;
            this.children = new ArrayList<>();
            this.memory = new HashMap<>();
        }
    }

    private enum Type {
        SEND(""),
        NO_ACTION(""),
        FLIP("%"),
        CON("&");

        private final String sign;

        Type(String sign) {
            this.sign = sign;
        }

        static Type getType(String name, String sign) {
            if (name.equals("broadcaster")) {
                return Type.SEND;
            }
            return Arrays.stream(Type.values()).filter(e -> e.sign.equals(sign)).findFirst().orElse(NO_ACTION);
        }
    }

}
