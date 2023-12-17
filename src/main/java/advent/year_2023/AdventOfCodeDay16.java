package advent.year_2023;

import domain.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;


public class AdventOfCodeDay16 {

    static int calcEnergizing(String inputFileName, boolean checkAll) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        char[][] data = new char[][]{};
        int rowIndex = 0;
        try (reader) {
            String line = reader.readLine();
            while (line != null) {
                data = Arrays.copyOf(data, rowIndex + 1);
                data[rowIndex] = line.toCharArray();
                rowIndex++;

                // read next line
                line = reader.readLine();
            }
        }
        if (!checkAll) {
            return calcFromPosition(data, new Position(0, 0, Direction.RIGHT));
        }

        char[][] matrix = data;
        int n = rowIndex;
        return IntStream.range(0, n)
                .flatMap(i -> IntStream.of(
                        calcFromPosition(matrix, new Position(i, 0, Direction.RIGHT)),
                        calcFromPosition(matrix, new Position(i, n - 1, Direction.LEFT)),
                        calcFromPosition(matrix, new Position(0, i, Direction.DOWN)),
                        calcFromPosition(matrix, new Position(n - 1, i, Direction.UP))
                )).max().orElseThrow();
    }

    private static int calcFromPosition(char[][] data, Position start) {
        Set<Position> visited = new HashSet<>();
        dfs(start, data, visited);
        return (int) visited.stream().map(e -> Pair.of(e.row, e.col)).distinct().count();
    }

    private static void dfs(Position pos, char[][] data, Set<Position> visited) {
        int row = pos.row();
        int col = pos.col();

        if (row >= 0 && row < data.length && col >= 0 && col < data[row].length && !visited.contains(pos)) {
            visited.add(pos);
            calcNextPositions(pos, data[row][col]).forEach(nextPos -> dfs(nextPos, data, visited));
        }
    }

    private static List<Position> calcNextPositions(Position pos, char ch) {
        return switch (ch) {
            case '|' -> {
                if (EnumSet.of(Direction.UP, Direction.DOWN).contains(pos.direction)) {
                    yield List.of(continueInDirection(pos));
                } else {
                    yield List.of(continueUp(pos), continueDown(pos));
                }
            }
            case '-' -> {
                if (EnumSet.of(Direction.LEFT, Direction.RIGHT).contains(pos.direction)) {
                    yield List.of(continueInDirection(pos));
                } else {
                    yield List.of(continueLeft(pos), continueRight(pos));
                }
            }
            case '/' -> switch (pos.direction) {
                case UP -> List.of(continueRight(pos));
                case DOWN -> List.of(continueLeft(pos));
                case LEFT -> List.of(continueDown(pos));
                case RIGHT -> List.of(continueUp(pos));
            };
            case '\\' -> switch (pos.direction) {
                case UP -> List.of(continueLeft(pos));
                case DOWN -> List.of(continueRight(pos));
                case LEFT -> List.of(continueUp(pos));
                case RIGHT -> List.of(continueDown(pos));
            };
            default -> List.of(continueInDirection(pos));
        };
    }

    private static Position continueLeft(Position pos) {
        return new Position(pos.row, pos.col - 1, Direction.LEFT);
    }

    private static Position continueRight(Position pos) {
        return new Position(pos.row, pos.col + 1, Direction.RIGHT);
    }

    private static Position continueUp(Position pos) {
        return new Position(pos.row - 1, pos.col, Direction.UP);
    }

    private static Position continueDown(Position pos) {
        return new Position(pos.row + 1, pos.col, Direction.DOWN);
    }

    private static Position continueInDirection(Position pos) {
        return switch (pos.direction) {
            case UP -> continueUp(pos);
            case DOWN -> continueDown(pos);
            case LEFT -> continueLeft(pos);
            case RIGHT -> continueRight(pos);
        };
    }

    private record Position(int row, int col, Direction direction) {
    }

    private enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN;
    }

}
