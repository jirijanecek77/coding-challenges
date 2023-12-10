package advent.year_2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AdventOfCodeDay10 {

    private static final char EMPTY = 'X';
    private static final char LEFT = 'L';
    private static final char RIGHT = 'R';
    private static final char PATH = '.';

    static int loopLength(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        char[][] data = new char[][]{};
        Step start = null;
        int rowIndex = 0;
        try (reader) {
            String line = reader.readLine();
            while (line != null) {
                data = Arrays.copyOf(data, rowIndex + 1);
                data[rowIndex] = line.toCharArray();

                int colIndex = line.indexOf('S');
                if (colIndex >= 0) {
                    start = new Step(rowIndex, colIndex, 'S');
                }

                rowIndex++;

                // read next line
                line = reader.readLine();
            }
        }
        return searchPath(data, start).size() / 2;
    }

    static int loopAreaSize(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        char[][] data = new char[][]{};
        Step start = null;
        int rows = 0;
        try (reader) {
            String line = reader.readLine();
            while (line != null) {
                data = Arrays.copyOf(data, rows + 1);
                data[rows] = line.toCharArray();

                int colIndex = line.indexOf('S');
                if (colIndex >= 0) {
                    start = new Step(rows, colIndex, 'S');
                }

                rows++;

                // read next line
                line = reader.readLine();
            }
        }
        List<Step> path = searchPath(data, start);
        int columns = data[rows - 1].length;
        char[][] area = new char[rows][columns];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                area[x][y] = EMPTY;
            }
        }

        path.forEach(e -> area[e.x()][e.y] = PATH);

        for (Step current : path) {
            int x = current.x;
            int y = current.y;
            switch (current.direction()) {
                case 'L':
                    mark(area, LEFT, x + 1, y);
                    mark(area, RIGHT, x - 1, y);
                    break;
                case 'R':
                    mark(area, LEFT, x - 1, y);
                    mark(area, RIGHT, x + 1, y);
                    break;
                case 'U':
                    mark(area, LEFT, x, y - 1);
                    mark(area, RIGHT, x, y + 1);
                    break;
                case 'D':
                    mark(area, LEFT, x, y + 1);
                    mark(area, RIGHT, x, y - 1);
                    if (data[x][y] == 'J') {
                        mark(area, LEFT, x + 1, y);
                    }
                    if (data[x][y] == 'L') {
                        mark(area, RIGHT, x + 1, y);
                    }
                    break;
            }
        }

        int areaSize = 0;
        char outerChar = area[0][0] == 'L' ? 'R' : 'L';
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                if (area[x][y] == outerChar) {
                    areaSize++;
                }
                System.out.print(area[x][y]);
            }
            System.out.println();
        }
        System.out.println();

        return areaSize;
    }

    private static void mark(char[][] area, char ch, int x, int y) {
        if (x >= 0 && x < area.length && y >= 0 && y < area[x].length && area[x][y] == EMPTY) {
            area[x][y] = ch;
            mark(area, ch, x + 1, y);
            mark(area, ch, x - 1, y);
            mark(area, ch, x, y - 1);
            mark(area, ch, x, y + 1);
        }
    }

    private static List<Step> searchPath(char[][] data, Step start) {
        List<Step> path = new ArrayList<>();
        Step current = getNextStepsFromStart(data, start);
        if (current.x > start.x) {
            start = new Step(start.x, start.y, 'D');
        } else if (current.x < start.x) {
            start = new Step(start.x, start.y, 'U');
        } else if (current.y < start.y) {
            start = new Step(start.x, start.y, 'L');
        } else if (current.y > start.y) {
            start = new Step(start.x, start.y, 'R');
        }

        path.add(start);
        while (current.x != start.x || current.y != start.y) {
            path.add(current);
            current = getNextStep(data, current);
        }

        return path;
    }

    private static Step getNextStep(char[][] data, Step currentStep) {
        int x = currentStep.x;
        int y = currentStep.y;
        return switch (data[x][y]) {
            case 'F' -> currentStep.direction == 'L' ? new Step(x + 1, y, 'D') : new Step(x, y + 1, 'R');
            case 'J' -> currentStep.direction == 'R' ? new Step(x - 1, y, 'U') : new Step(x, y - 1, 'L');
            case 'L' -> currentStep.direction == 'L' ? new Step(x - 1, y, 'U') : new Step(x, y + 1, 'R');
            case '7' -> currentStep.direction == 'R' ? new Step(x + 1, y, 'D') : new Step(x, y - 1, 'L');
            case '|' -> currentStep.direction == 'U' ? new Step(x - 1, y, 'U') : new Step(x + 1, y, 'D');
            case '-' -> currentStep.direction == 'L' ? new Step(x, y - 1, 'L') : new Step(x, y + 1, 'R');
            default -> throw new RuntimeException("unknown pipe " + data[x][y]);
        };
    }


    private static Step getNextStepsFromStart(char[][] data, Step start) {
        int x = start.x;
        int y = start.y;
        if (x + 1 < data.length) {
            Step nextStep = new Step(x + 1, y, 'D');
            if (findByStep(data, nextStep, '|', 'J', 'L')) {
                return nextStep;
            }
        }
        if (x - 1 >= 0) {
            Step nextStep = new Step(x - 1, y, 'U');
            if (findByStep(data, nextStep, '|', 'F', '7')) {
                return nextStep;
            }
        }
        if (y - 1 >= 0) {
            Step nextStep = new Step(x, y - 1, 'L');
            if (findByStep(data, nextStep, '-', 'L', 'F')) {
                return nextStep;
            }
        }
        if (y + 1 < data[x].length) {
            Step nextStep = new Step(x, y + 1, 'R');
            if (findByStep(data, nextStep, '-', '7', 'J', 'S')) {
                return nextStep;
            }
        }
        return null;
    }

    private static boolean findByStep(char[][] data, Step step, Character... targets) {
        return Stream.of(targets).anyMatch(ch -> data[step.x][step.y] == ch);
    }

    private record Step(int x, int y, char direction) {
        @Override
        public String toString() {
            return "(" + x + ", " + y + ", dir: " + direction + ")";
        }
    }
}
