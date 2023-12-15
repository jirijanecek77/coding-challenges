package advent.year_2023;

import domain.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class AdventOfCodeDay13 {

    static long countMirror(String inputFileName, boolean withFix) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        long sum = 0;
        char[][] data = new char[][]{};
        int rowIndex = 0;
        try (reader) {
            String line = reader.readLine();
            while (line != null) {
                if (line.isBlank()) {
                    sum += calcMirror(data, withFix);
                    data = new char[][]{};
                    rowIndex = 0;
                } else {
                    data = Arrays.copyOf(data, rowIndex + 1);
                    data[rowIndex] = line.toCharArray();
                    rowIndex++;
                }

                // read next line
                line = reader.readLine();
            }
        }
        sum += calcMirror(data, withFix);
        return sum;
    }

    private static int calcMirror(char[][] data, boolean withFix) {
        int rowCount = withFix ? calcRowsWithFix(data) : calcRows(data);
        int colCount = withFix ? calcColsWithFix(data) : calcColumns(data);

        return rowCount * 100 + colCount;
    }

    private static int calcRows(char[][] data) {
        int[] rows = new int[data.length];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = String.valueOf(data[i]).hashCode();
        }

        return calculate(rows);
    }

    private static int calcColumns(char[][] data) {
        int[] cols = new int[data[0].length];
        for (int col = 0; col < cols.length; col++) {
            StringBuilder sb = new StringBuilder();
            for (char[] row : data) {
                sb.append(row[col]);
            }
            cols[col] = sb.toString().hashCode();
        }
        return calculate(cols);
    }

    private static int calculate(int[] data) {
        int result = 0;
        for (int i = 1; i < data.length; i++) {
            int high = Math.min(2 * i, data.length) - 1;
            int low = i - (high - i) - 1;

            while (low < high) {
                if (data[low] != data[high]) {
                    break;
                }

                if (low == high - 1) {
                    result += i;
                    System.out.println("found result for " + i);
                }
                low++;
                high--;
            }
        }
        return result;
    }

    private static int calcRowsWithFix(char[][] data) {
        for (int i = 1; i < data.length; i++) {
            int high = Math.min(2 * i, data.length) - 1;
            int low = i - (high - i) - 1;

            Pair<Integer, Integer> smudge = null;
            while (low < high) {
                int dist = 0;
                int colIdx = -1;
                for (int col = 0; col < data[low].length; col++) {
                    char ch = smudge != null && smudge.first() == low && smudge.second() == col
                            ? revert(data[low][col]) : data[low][col];
                    if (ch != data[high][col]) {
                        colIdx = col;
                        dist++;
                    }
                }
                if (dist == 1 && smudge == null) {
                    smudge = Pair.of(low, colIdx);
                } else if (dist != 0) {
                    break;
                }

                if (low == high - 1 && smudge != null) {
                    System.out.println("found result for row " + i);
                    return i;
                }
                low++;
                high--;
            }
        }
        return 0;
    }

    private static int calcColsWithFix(char[][] data) {
        for (int i = 1; i < data[0].length; i++) {
            int high = Math.min(2 * i, data[0].length) - 1;
            int low = i - (high - i) - 1;

            Pair<Integer, Integer> smudge = null;
            while (low < high) {
                int dist = 0;
                int rowIdx = -1;
                for (int row = 0; row < data.length; row++) {
                    char ch = smudge != null && smudge.first() == row && smudge.second() == low
                            ? revert(data[row][low]) : data[row][low];
                    if (ch != data[row][high]) {
                        rowIdx = row;
                        dist++;
                    }
                }
                if (dist == 1 && smudge == null) {
                    smudge = Pair.of(rowIdx, low);
                } else if (dist != 0) {
                    break;
                }

                if (low == high - 1 && smudge != null) {
                    System.out.println("found result for column " + i);
                    return i;
                }
                low++;
                high--;
            }
        }
        return 0;
    }

    private static char revert(char c) {
        return c == '.' ? '#' : '.';
    }
}
