package advent.year_2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class AdventOfCodeDay17 {

    private static final Map<Character, Character> RESTRICTED_DIRECTIONS = Map.of(
            '<', '>',
            '>', '<',
            'v', '^',
            '^', 'v'
    );


    static int calcMinHeatLoss(String inputFileName, boolean isUltra) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        int[][] data = new int[][]{};
        int n = 0;
        try (reader) {
            String line = reader.readLine();
            while (line != null) {
                data = Arrays.copyOf(data, n + 1);
                data[n] = line.chars().map(ch -> ch - '0').toArray();
                n++;

                // read next line
                line = reader.readLine();
            }
        }

        Map<Node, Integer> distances = new HashMap<>();
        Comparator<Node> comparator = Comparator.comparing(distances::get);
        Queue<Node> queue = new PriorityQueue<>(comparator);
        Set<Node> visited = new HashSet<>();

        Node startNode = new Node(0, 0, 'o', 0);
        distances.put(startNode, 0);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (visited.contains(node)) {
                continue;
            }

            visited.add(node);

            // moving up
            char direction = '^';
            if (isValid(node.row - 1, node.col, n) && isValidDirection(node, direction, n, isUltra)) {
                int edgeDistance = data[node.row - 1][node.col];
                int newDistance = distances.get(node) + edgeDistance;
                Node newNode = new Node(node.row - 1, node.col, direction, calcStepsInDirection(node, direction));
                if (newDistance < Optional.ofNullable(distances.get(newNode)).orElse(Integer.MAX_VALUE)) {
                    distances.put(newNode, newDistance);
                }
                if (!visited.contains(newNode)) {
                    queue.add(newNode);
                }
            }

            // moving down
            direction = 'v';
            if (isValid(node.row + 1, node.col, n) && isValidDirection(node, direction, n, isUltra)) {
                int edgeDistance = data[node.row + 1][node.col];
                int newDistance = distances.get(node) + edgeDistance;
                Node newNode = new Node(node.row + 1, node.col, direction, calcStepsInDirection(node, direction));
                if (newDistance < Optional.ofNullable(distances.get(newNode)).orElse(Integer.MAX_VALUE)) {
                    distances.put(newNode, newDistance);
                }
                if (!visited.contains(newNode)) {
                    queue.add(newNode);
                }
            }

            // moving left
            direction = '<';
            if (isValid(node.row, node.col - 1, n) && isValidDirection(node, direction, n, isUltra)) {
                int edgeDistance = data[node.row][node.col - 1];
                int newDistance = distances.get(node) + edgeDistance;
                Node newNode = new Node(node.row, node.col - 1, direction, calcStepsInDirection(node, direction));
                if (newDistance < Optional.ofNullable(distances.get(newNode)).orElse(Integer.MAX_VALUE)) {
                    distances.put(newNode, newDistance);
                }
                if (!visited.contains(newNode)) {
                    queue.add(newNode);
                }
            }

            // moving right
            direction = '>';
            if (isValid(node.row, node.col + 1, n) && isValidDirection(node, direction, n, isUltra)) {
                int edgeDistance = data[node.row][node.col + 1];
                int newDistance = distances.get(node) + edgeDistance;
                Node newNode = new Node(node.row, node.col + 1, direction, calcStepsInDirection(node, direction));
                if (newDistance < Optional.ofNullable(distances.get(newNode)).orElse(Integer.MAX_VALUE)) {
                    distances.put(newNode, newDistance);
                }
                if (!visited.contains(newNode)) {
                    queue.add(newNode);
                }
            }
        }

        distances.entrySet().stream()
                .sorted(Comparator.<Map.Entry<Node, Integer>, Integer>comparing(e1 -> e1.getKey().row)
                        .thenComparing(e -> e.getKey().col)
                        .thenComparing(e -> e.getKey().direction)
                        .thenComparing(e -> e.getKey().stepsInDirection)
                )
                .forEach(System.out::println);

        int target = n - 1;
        return distances.entrySet().stream().filter(e -> e.getKey().row == target && e.getKey().col == target)
                .mapToInt(Map.Entry::getValue).min().orElseThrow();
    }

    private static boolean isValidDirection(Node node, char direction, int n, boolean isUltra) {
        if (node.direction == 'o') {
            return true;
        }
        if (RESTRICTED_DIRECTIONS.get(node.direction) == direction) {
            return false;
        }
        if (isUltra) {
            if (node.direction == direction) {
                return node.stepsInDirection < 10;
            } else {
                if (node.stepsInDirection < 4) {
                    return false;
                } else {
                    switch (direction) {
                        case '^':
                            return node.row > 4;
                        case 'v':
                            return node.row < n - 4;
                        case '>':
                            return node.col < n - 4;
                        case '<':
                            return node.col > 4;
                    }

                }
            }
        }
        return node.direction != direction || node.stepsInDirection < 3;
    }

    private static int calcStepsInDirection(Node node, char direction) {
        return node.direction == direction ? node.stepsInDirection + 1 : 1;
    }

    private static boolean isValid(int x, int y, int n) {
        return x >= 0 && y >= 0 && x < n && y < n;
    }

    private record Node(int row, int col, char direction, int stepsInDirection) {
        @Override
        public String toString() {
            return "[" + row + ", " + col + "] [" + stepsInDirection + direction + "]";
        }
    }
}
