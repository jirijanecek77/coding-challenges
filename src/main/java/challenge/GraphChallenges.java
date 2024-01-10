package challenge;

import java.util.*;

public class GraphChallenges {

    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // https://leetcode.com/problems/evaluate-division/description/?envType=study-plan-v2&envId=top-interview-150

        Map<String, Map<String, Double>> vertices = new HashMap<>();
        for (int i = 0; i < equations.size(); i++) {
            var from = equations.get(i).get(0);
            var to = equations.get(i).get(1);
            var value = values[i];

            addVertexEdge(vertices, from, to, value);
            addVertexEdge(vertices, to, from, 1 / value);
        }

        return queries.stream()
                .mapToDouble(query -> dfs(query.get(0), query.get(1), vertices, new HashSet<>(), 1.0))
                .toArray();
    }

    private static void addVertexEdge(Map<String, Map<String, Double>> vertices, String from, String to, double value) {
        if (vertices.containsKey(from)) {
            vertices.get(from).put(to, value);
        } else {
            Map<String, Double> edge = new HashMap<>();
            edge.put(to, value);
            vertices.put(from, edge);
        }
    }

    private static double dfs(String from, String to, Map<String, Map<String, Double>> vertices, Set<String> visited, double acc) {
        if (!vertices.containsKey(from) || !vertices.containsKey(to) || visited.contains(from)) {
            return -1.0;
        }
        visited.add(from);

        var edges = vertices.get(from);
        if (edges.containsKey(to)) {
            return edges.get(to) * acc;
        }

        return edges.entrySet().stream()
                .map(e -> dfs(e.getKey(), to, vertices, visited, acc * e.getValue()))
                .filter(e -> e > 0)
                .findFirst()
                .orElse(-1.0);
    }

    public static boolean canVisitAllRooms(List<List<Integer>> rooms) {
        if (rooms.isEmpty()) {
            return true;
        }
        Set<Integer> visited = new HashSet<>();
        dfsRooms(rooms, 0, visited);
        return visited.size() == rooms.size();
    }

    private static void dfsRooms(List<List<Integer>> rooms, int room, Set<Integer> visited) {
        if (visited.contains(room)) {
            return;
        }
        visited.add(room);

        rooms.get(room).forEach(e -> dfsRooms(rooms, e, visited));
    }
}