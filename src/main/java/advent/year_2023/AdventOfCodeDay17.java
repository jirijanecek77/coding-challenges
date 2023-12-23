package advent.year_2023;

import utils.FileLineReader;

import java.util.*;
import java.util.stream.Stream;


public class AdventOfCodeDay17 {

    private static final Map<Character, Character> RESTRICTED_DIRECTIONS = Map.of(
            '<', '>',
            '>', '<',
            'v', '^',
            '^', 'v'
    );
    private static final int MIN_STEPS_IN_DIRECTION = 4;
    private static final int MAX_STEPS_IN_DIRECTION = 10;

    static int calcMinHeatLoss(String inputFileName, boolean isUltra) {

        int[][] data = new int[][]{};
        int n = 0;

        for (String line : FileLineReader.of(inputFileName)) {
            data = Arrays.copyOf(data, n + 1);
            data[n] = line.chars().map(ch -> ch - '0').toArray();
            n++;
        }

        Map<Vertex, Integer> distances = new HashMap<>();
        dijkstraShortestPath(distances, data, isUltra);

        distances.entrySet().stream()
                .sorted(Comparator.<Map.Entry<Vertex, Integer>, Integer>comparing(e -> e.getKey().row)
                        .thenComparing(e -> e.getKey().col)
                        .thenComparing(e -> e.getKey().direction)
                        .thenComparing(e -> e.getKey().stepsInDirection)
                )
                .forEach(System.out::println);

        int target = n - 1;
        return distances.entrySet().stream()
                .filter(e -> e.getKey().row == target && e.getKey().col == target)
                .mapToInt(Map.Entry::getValue)
                .min().orElseThrow();
    }

    private static void dijkstraShortestPath(Map<Vertex, Integer> distances, int[][] data, boolean isUltra) {
        Queue<Vertex> queue = new PriorityQueue<>(Comparator.comparing(distances::get));

        Vertex startVertex = new Vertex(0, 0, 'o', 0);
        distances.put(startVertex, 0);
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();

            for (Vertex adj : getAdjacent(vertex, data.length, isUltra)) {
                int edgeDistance = data[adj.row][adj.col];
                int newDistance = distances.get(vertex) + edgeDistance;
                if (distances.containsKey(adj)) {
                    distances.put(adj, Math.min(newDistance, distances.get(adj)));
                } else {
                    distances.put(adj, newDistance);
                    queue.add(adj);
                }
            }
        }
    }

    private static List<Vertex> getAdjacent(Vertex vertex, int n, boolean isUltra) {
        return Stream.of(
                        new Vertex(vertex.row - 1, vertex.col, '^', nextStepsInDirection(vertex, '^')),
                        new Vertex(vertex.row + 1, vertex.col, 'v', nextStepsInDirection(vertex, 'v')),
                        new Vertex(vertex.row, vertex.col - 1, '<', nextStepsInDirection(vertex, '<')),
                        new Vertex(vertex.row, vertex.col + 1, '>', nextStepsInDirection(vertex, '>'))
                )
                .filter(adj -> isValidVertex(adj, vertex.direction, vertex.stepsInDirection, n, isUltra))
                .toList();
    }

    private static boolean isValidVertex(Vertex vertex, char currentDirection, int currentSteps, int n, boolean isUltra) {
        if (vertex.row < 0
                || vertex.col < 0
                || vertex.row >= n
                || vertex.col >= n
                || RESTRICTED_DIRECTIONS.get(vertex.direction) == currentDirection
        ) {
            return false;
        }

        if (!isUltra) {
            return vertex.direction != currentDirection || vertex.stepsInDirection <= 3;
        } else {
            if (currentDirection == 'o') {
                return true;
            }
            if (vertex.direction == currentDirection) {
                return vertex.stepsInDirection <= MAX_STEPS_IN_DIRECTION;
            } else {
                if (currentSteps < MIN_STEPS_IN_DIRECTION) {
                    return false;
                } else {
                    return switch (vertex.direction) {
                        case '^' -> vertex.row >= (MIN_STEPS_IN_DIRECTION - 1);
                        case 'v' -> vertex.row <= n - MIN_STEPS_IN_DIRECTION;
                        case '<' -> vertex.col >= (MIN_STEPS_IN_DIRECTION - 1);
                        case '>' -> vertex.col <= n - MIN_STEPS_IN_DIRECTION;
                        default -> false;
                    };
                }
            }
        }
    }

    private static int nextStepsInDirection(Vertex vertex, char direction) {
        return vertex.direction == direction ? vertex.stepsInDirection + 1 : 1;
    }

    private record Vertex(int row, int col, char direction, int stepsInDirection) {
        @Override
        public String toString() {
            return "[" + row + ", " + col + "] [" + stepsInDirection + direction + "]";
        }
    }
}
