package advent.year_2023;

import domain.Pair;
import utils.FileLineReader;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static utils.RegexUtils.parseByPattern;


public class AdventOfCodeDay20 {

    private static final Pattern LINE_PATTERN = Pattern.compile("^((%|&)?)(\\w+) -> (((\\w+)(, )?)+)$");
    private static int a;

    static int task1(String inputFileName) {
        Map<String, Vertex> vertexMap = new HashMap<>();
        for (String line : FileLineReader.of(inputFileName)) {
            String[] lineParts = parseByPattern(line, LINE_PATTERN, 1, 3, 4);

            String name = lineParts[1];
            Vertex vertex = vertexMap.containsKey(name) ? vertexMap.get(name) : new Vertex(name);
            vertex.type = Type.getType(name, lineParts[0]);

            vertexMap.put(name, vertex);
            Arrays.stream(lineParts[2].split(", ")).forEach(childName -> {
                if (vertexMap.containsKey(childName)) {
                    Vertex child = vertexMap.get(childName);
                    vertex.children.add(child);
                    child.memory.put(vertex, false);
                } else {
                    Vertex child = new Vertex(childName);
                    vertexMap.put(childName, child);
                    vertex.children.add(child);
                    child.memory.put(vertex, false);
                }
            });

        }

        Vertex start = vertexMap.get("broadcaster");
        var result = IntStream.range(0, 1000)
                .mapToObj(e -> solve(start))
                .reduce(Pair.of(0, 0), (a, b) -> Pair.of(a.first() + b.first(), a.second() + b.second()));

        return result.first() * result.second();
    }

    private static Pair<Integer, Integer> solve(Vertex start) {
        int low = 0, high = 0;

        Queue<State> queue = new LinkedList<>();
        queue.add(new State(null, start, false));
        while (!queue.isEmpty()) {
            State state = queue.poll();

            Vertex vertex = state.to;
            Boolean isHigh = state.isHigh;

            if (isHigh) {
                high++;
            } else {
                low++;
            }

            switch (vertex.type) {
                case SEND -> vertex.children.forEach(v -> queue.add(new State(vertex, v, isHigh)));
                case FLIP -> {
                    if (!isHigh) {
                        vertex.isOn = !vertex.isOn;
                        vertex.children.forEach(v -> queue.add(new State(vertex, v, vertex.isOn)));
                    }
                }
                case CON -> {
                    vertex.memory.put(state.from, isHigh);
                    vertex.children.forEach(v -> queue.add(new State(vertex, v, !vertex.memory.values().stream().allMatch(e -> e))));
                }
            }
        }

        return Pair.of(low, high);
    }

    private record State(Vertex from, Vertex to, Boolean isHigh) {
    }

    private static class Vertex {

        String name;
        Type type;

        boolean isOn;

        Map<Vertex, Boolean> memory;

        List<Vertex> children;

        public Vertex(String name) {
            this.name = name;
            this.type = Type.NO_ACTION;
            this.isOn = false;
            this.children = new ArrayList<>();
            this.memory = new HashMap<>();
        }

        @Override
        public String toString() {
            return this.name + "(" + type +
                    switch (type) {
                        case FLIP -> isOn ? ": on" : ": off";
                        case CON -> ": " + memory.entrySet().stream().map(e -> e.getKey().name + ":" + e.getValue()).collect(Collectors.joining(", "));
                        default -> "";
                    } + ")";
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
