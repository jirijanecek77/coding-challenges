package advent.year_2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class AdventOfCodeDay17 {

    static int calcMinHeatLoss(String inputFileName, boolean checkAll) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        int[][] data = new int[][]{};
        int rowIndex = 0;
        try (reader) {
            String line = reader.readLine();
            while (line != null) {
                data = Arrays.copyOf(data, rowIndex + 1);
                data[rowIndex] = line.chars().map(ch -> ch - '0').toArray();
                rowIndex++;

                // read next line
                line = reader.readLine();
            }
        }


        return calculateShortestPath(data, new Node(0, 0), new Node(rowIndex - 1, rowIndex - 1));
    }

    private static int calculateShortestPath(int[][] data, Node start, Node target) {
        Map<Node, Integer> visited = new HashMap<>();
        visited.put(start, 0);
        shortestPath(data, visited, 0, new Node(start.row, start.col + 1, Direction.RIGHT, 0));
        shortestPath(data, visited, 0, new Node(start.row + 1, start.col, Direction.DOWN, 0));

        printVisited(visited, data.length);
        return visited.get(target);
    }

    private static void printVisited(Map<Node, Integer> visited, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%3d,", visited.get(new Node(i, j)));
            }
            System.out.println();
        }
    }

    private static void shortestPath(int[][] data, Map<Node, Integer> visited, int distance, Node node) {
        int newDistance = distance + data[node.row][node.col];
        if (visited.containsKey(node)) {
            Integer oldDistance = visited.get(node);
            distance = Math.min(newDistance, oldDistance);
            visited.put(node, distance);

            if (newDistance < oldDistance) {
                calcNextSteps(node, data.length).forEach(step -> shortestPath(data, visited, newDistance, step));
            }
        } else {
            visited.put(node, newDistance);
            calcNextSteps(node, data.length).forEach(step -> shortestPath(data, visited, newDistance, step));
        }
    }

    private static List<Node> calcNextSteps(Node node, int n) {
        List<Node> steps = new ArrayList<>();

        switch (node.direction) {
            case DOWN: {
                if (node.stepsInDirection < 2) {
                    steps.add(new Node(node.row + 1, node.col, Direction.DOWN, node.stepsInDirection + 1));
                }
                steps.add(new Node(node.row, node.col + 1, Direction.RIGHT, 0));
                steps.add(new Node(node.row, node.col - 1, Direction.LEFT, 0));
                break;
            }
            case RIGHT: {
                if (node.stepsInDirection < 2) {
                    steps.add(new Node(node.row, node.col + 1, Direction.RIGHT, node.stepsInDirection + 1));
                }
                steps.add(new Node(node.row + 1, node.col, Direction.DOWN, 0));
                steps.add(new Node(node.row - 1, node.col, Direction.UP, 0));
                break;
            }
            case LEFT: {
                steps.add(new Node(node.row + 1, node.col, Direction.DOWN, 0));
                if (node.stepsInDirection < 2) {
                    steps.add(new Node(node.row, node.col - 1, Direction.LEFT, node.stepsInDirection + 1));
                }
                steps.add(new Node(node.row - 1, node.col, Direction.UP, 0));
                break;
            }
            case UP: {
                steps.add(new Node(node.row, node.col + 1, Direction.RIGHT, 0));
                if (node.stepsInDirection < 2) {
                    steps.add(new Node(node.row - 1, node.col, Direction.UP, node.stepsInDirection + 1));
                }
                steps.add(new Node(node.row, node.col - 1, Direction.LEFT, 0));
                break;
            }
        }

        return steps.stream()
                .filter(step -> step.row >= 0 && step.row < n && step.col >= 0 && step.col < n)
                .toList();
    }

    private record Node(int row, int col, Direction direction, int stepsInDirection) {
        public Node(int row, int col) {
            this(row, col, Direction.RIGHT, 0);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return row == node.row && col == node.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    private enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN;
    }
}
