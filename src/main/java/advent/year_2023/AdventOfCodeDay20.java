package advent.year_2023;

import utils.FileLineReader;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static utils.MathUtils.lcm;
import static utils.RegexUtils.parseByPattern;


public class AdventOfCodeDay20 {

    private static final Pattern LINE_PATTERN = Pattern.compile("^((%|&)?)(\\w+) -> (((\\w+)(, )?)+)$");
    public static final String TARGET_MODULE = "rx";
    private static int a;

    static int task1(String inputFileName) {
        Map<String, Module> modules = readFile(inputFileName);

        Module start = modules.get("broadcaster");

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
        Map<String, Module> modules = readFile(inputFileName);
        Module start = modules.get("broadcaster");
        Map<String, Long> result = new HashMap<>();

        long i = 1;
        while (!result.containsKey(TARGET_MODULE)) {
            Queue<State> queue = new LinkedList<>();
            queue.add(new State(null, start, false));
            while (!queue.isEmpty()) {
                State state = queue.poll();
                queue.addAll(calcNextStates(state));

                Module to = state.to;
                Module from = state.from;
                if (to.type == Type.CON && from.type == Type.CON) {
                    Set<String> parents = to.memory.keySet().stream().map(e -> e.name).collect(Collectors.toSet());
                    if (parents.stream().allMatch(result::containsKey)) {
                        result.put(to.name, lcm(result.entrySet().stream()
                                .filter(e -> parents.contains(e.getKey()))
                                .map(Map.Entry::getValue)
                                .toList())
                        );
                    }
                } else if (to.type == Type.CON && to.memory.values().stream().reduce(true, (a, b) -> a && b)) {
                    result.put(to.name, i);
                } else if (to.type == Type.NO_ACTION) {
                    if (result.containsKey(from.name)) {
                        result.put(to.name, result.get(from.name));
                    }
                }
            }

            i++;
        }
        return result.get(TARGET_MODULE);
    }

    private static Map<String, Module> readFile(String inputFileName) {
        Map<String, Module> modules = new HashMap<>();
        for (String line : FileLineReader.of(inputFileName)) {
            String[] lineParts = parseByPattern(line, LINE_PATTERN, 1, 3, 4);

            String name = lineParts[1];
            Module module = modules.containsKey(name) ? modules.get(name) : new Module(name);
            module.type = Type.getType(name, lineParts[0]);

            modules.put(name, module);
            Arrays.stream(lineParts[2].split(", ")).forEach(childName -> {
                if (modules.containsKey(childName)) {
                    Module child = modules.get(childName);
                    module.children.add(child);
                    child.memory.put(module, false);
                } else {
                    Module child = new Module(childName);
                    modules.put(childName, child);
                    module.children.add(child);
                    child.memory.put(module, false);
                }
            });

        }
        return modules;
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
