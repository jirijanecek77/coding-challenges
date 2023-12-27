package advent.year_2023;

import utils.FileLineReader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public class AdventOfCodeDay21 {

    static int task1(String inputFileName, int steps) {
        char[][] data = new char[][]{};
        Position start = null;

        int rowIndex = 0;
        for (String line : FileLineReader.of(inputFileName)) {
            data = Arrays.copyOf(data, rowIndex + 1);
            data[rowIndex] = line.toCharArray();
            for (int col = 0; col < line.length(); col++) {
                if (line.charAt(col) == 'S') {
                    start = new Position(rowIndex, col);
                }
            }
            rowIndex++;
        }

        return calcFromPosition(data, start, steps, rowIndex);
    }

    private static int calcFromPosition(char[][] data, Position start, int steps, int n) {
        Set<Position> positions = new HashSet<>();
        positions.add(start);

        while (steps-- > 0) {
            Set<Position> currentPos = new HashSet<>();
            positions.stream()
                    .flatMap(p -> nextSteps(p, data, n).stream())
                    .forEach(currentPos::add);

            positions = currentPos;
        }

        return positions.size();
    }

    private static List<Position> nextSteps(Position pos, char[][] data, int n) {
        int row = pos.row();
        int col = pos.col();

        return Stream.of(
                        new Position(row - 1, col),
                        new Position(row + 1, col),
                        new Position(row, col - 1),
                        new Position(row, col + 1)
                )
                .filter(p -> Set.of('.', 'S').contains(data[(p.row + 1000 * n) % n][(p.col + 1000 * n) % n]))
                .toList();
    }

    private record Position(int row, int col) {
    }

}
