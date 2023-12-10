package advent.year_2023;

import domain.Coordinate;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class AdventOfCodeDay10 {
    static int cycleLength(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        char[][] data = new char[][]{};
        Coordinate start = null;
        int rowIndex = 0;
        try (reader) {
            String line = reader.readLine();
            while (line != null) {
                data = Arrays.copyOf(data, rowIndex + 1);
                data[rowIndex] = line.toCharArray();

                int colIndex = line.indexOf('S');
                if (colIndex >= 0) {
                    start = new Coordinate(rowIndex, colIndex);
                }

                rowIndex++;

                // read next line
                line = reader.readLine();
            }
        }
        return searchPath(data, start).size() / 2;
    }

    static int cycleArea(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        char[][] data = new char[][]{};
        Coordinate start = null;
        int rows = 0;
        try (reader) {
            String line = reader.readLine();
            while (line != null) {
                data = Arrays.copyOf(data, rows + 1);
                data[rows] = line.toCharArray();

                int colIndex = line.indexOf('S');
                if (colIndex >= 0) {
                    start = new Coordinate(rows, colIndex);
                }

                rows++;

                // read next line
                line = reader.readLine();
            }
        }
        Set<Coordinate> path = searchPath(data, start);
        int columns = data[0].length;
        return (rows * columns) - path.size() - outerArea(path, rows, columns, data);
    }

    private static int outerArea(Set<Coordinate> path, int rows, int columns, char[][] chars) {
        boolean[][] data = new boolean[rows + 2][columns + 2];
        path.forEach(coordinate -> data[coordinate.x() + 1][coordinate.y() + 1] = true);

        Set<Coordinate> visited = new HashSet<>();
        Stack<Coordinate> stack = new Stack<>();
        stack.push(new Coordinate(0, 0));
        int areaSize = 0;

        while (!stack.isEmpty()) {
            Coordinate current = stack.pop();

            List<Coordinate> nextSteps = new ArrayList<>();
            if (!data[current.x()][current.y()]) {
                if (!visited.contains(current) && current.x() > 0 && current.x() < data.length - 1 && current.y() > 0 && current.y() < data[current.x()].length - 1) {
                    System.out.println((current.x() - 1) + ", " + (current.y() - 1));
                    areaSize++;
                }

                if (current.x() + 1 < data.length) {
                    Coordinate nextStep = new Coordinate(current.x() + 1, current.y());
                    nextSteps.add(nextStep);
                }
                if (current.x() - 1 >= 0) {
                    Coordinate nextStep = new Coordinate(current.x() - 1, current.y());
                    nextSteps.add(nextStep);
                }
                if (current.y() - 1 >= 0) {
                    Coordinate nextStep = new Coordinate(current.x(), current.y() - 1);
                    nextSteps.add(nextStep);
                }
                if (current.y() + 1 < data[current.x()].length) {
                    Coordinate nextStep = new Coordinate(current.x(), current.y() + 1);
                    nextSteps.add(nextStep);
                }
            } else {

            }
            visited.add(current);
            nextSteps.stream().filter(step -> !visited.contains(step)).forEach(stack::push);
        }

//        for (int x = 1; x < data.length -1 ; x++) {
//            for (int y = 1; y < data[x].length -1 ; y++) {
//                System.out.print(chars[x-1][y-1]);
//            }
//            System.out.println();
//        }

        return areaSize;
    }

    private static Set<Coordinate> searchPath(char[][] data, Coordinate start) {
        Set<Coordinate> visited = new HashSet<>();

        List<Coordinate> startNeighbors = getNextStepsFromStart(data, start);
        Coordinate left = startNeighbors.get(0), right = startNeighbors.get(1);
        visited.add(start);
        while (!left.equals(right)) {
            visited.add(left);
            visited.add(right);
            left = getNextSteps(data, left).stream().filter(e -> !visited.contains(e)).findFirst().orElseThrow();
            right = getNextSteps(data, right).stream().filter(e -> !visited.contains(e)).findFirst().orElseThrow();
        }

        visited.add(left);
        return visited;
    }

    private static List<Coordinate> getNextSteps(char[][] data, Coordinate current) {
        return switch (data[current.x()][current.y()]) {
            case 'F' -> List.of(new Coordinate(current.x() + 1, current.y()), new Coordinate(current.x(), current.y() + 1));
            case 'J' -> List.of(new Coordinate(current.x() - 1, current.y()), new Coordinate(current.x(), current.y() - 1));
            case 'L' -> List.of(new Coordinate(current.x() - 1, current.y()), new Coordinate(current.x(), current.y() + 1));
            case '7' -> List.of(new Coordinate(current.x() + 1, current.y()), new Coordinate(current.x(), current.y() - 1));
            case '|' -> List.of(new Coordinate(current.x() - 1, current.y()), new Coordinate(current.x() + 1, current.y()));
            case '-' -> List.of(new Coordinate(current.x(), current.y() - 1), new Coordinate(current.x(), current.y() + 1));
            default -> throw new RuntimeException("unknown pipe " + data[current.x()][current.y()]);
        };
    }


    private static List<Coordinate> getNextStepsFromStart(char[][] data, Coordinate start) {
        List<Coordinate> nextSteps = new ArrayList<>();

        if (start.x() + 1 < data.length) {
            Coordinate nextStep = new Coordinate(start.x() + 1, start.y());
            if (contains(data, nextStep, '|', 'J', 'L')) {
                nextSteps.add(nextStep);
            }
        }
        if (start.x() - 1 >= 0) {
            Coordinate nextStep = new Coordinate(start.x() - 1, start.y());
            if (contains(data, nextStep, '|', 'F', '7')) {
                nextSteps.add(nextStep);
            }
        }
        if (start.y() - 1 >= 0) {
            Coordinate nextStep = new Coordinate(start.x(), start.y() - 1);
            if (contains(data, nextStep, '-', 'L', 'F')) {
                nextSteps.add(nextStep);
            }
        }
        if (start.y() + 1 < data[start.x()].length) {
            Coordinate nextStep = new Coordinate(start.x(), start.y() + 1);
            if (contains(data, nextStep, '-', '7', 'J', 'S')) {
                nextSteps.add(nextStep);
            }
        }
        return nextSteps;
    }

    private static boolean contains(char[][] data, Coordinate coordinate, Character... targets) {
        return Stream.of(targets).anyMatch(ch -> data[coordinate.x()][coordinate.y()] == ch);
    }
}
