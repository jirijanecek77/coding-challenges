package advent.year_2023;

import domain.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
        int colCount = withFix ? 0 : calcColumns(data);

        return rowCount * 100 + colCount;
    }

    private static int calcRowsWithFix(char[][] data) {
        Queue<char[][]> queue = new LinkedList<>();
        List<Pair<Integer, Integer>> positions = calcFixPositions(data);

        if (positions.isEmpty()) {
            queue.add(data);
        } else {
            positions.forEach(e -> queue.add(copyOf(data, e.first(), e.second())));
        }

        while (!queue.isEmpty()) {
            int result = calc(queue.poll());
            if (result > 0) {
                return result;
            }
        }
        return 0;
    }

    private static int calc(char[][] data) {
        boolean isMirror;
        for (int i = 1; i < data.length; i++) {
            isMirror = true;
            int j = 0;
            while (j < i && i + j < data.length) {
                isMirror = isMirror && Arrays.compare(data[i - j - 1], data[i + j]) == 0;
                j++;

                if (isMirror) {
                    System.out.println("found result for line " + i);
                    return i;
                }
            }
        }
        return 0;
    }

    private static List<Pair<Integer, Integer>> calcFixPositions(char[][] data) {
        List<Pair<Integer, Integer>> smudges = new ArrayList<>();

        for (int i = 1; i < data.length; i++) {
            int j = 0;
            boolean matches = true;
            while (j < i && i + j < data.length) {
                int colIdx = -1;
                int rowIndex1 = i - j - 1;
                int rowIndex2 = i + j;
                int dist = 0;
                for (int col = 0; col < data[rowIndex1].length; col++) {
                    if (data[rowIndex1][col] != data[rowIndex2][col]) {
                        colIdx = col;
                        dist++;
                    }
                }
                System.out.println("[" + rowIndex1 + ", " + rowIndex2 + "] dist " + dist);
                if (dist == 1 && matches) {
                    smudges.add(Pair.of(rowIndex1, colIdx));
                    System.out.println("add data with change on [" + rowIndex1 + ", " + colIdx + "]");
                } else {
                    matches = matches && dist == 0;
                }
                j++;
            }
        }

        if (smudges.isEmpty()) {
            return List.of();
        }

        Comparator<Pair<Integer, Integer>> comparator = Comparator.comparing(Pair::first);
        return smudges.stream().sorted(comparator.thenComparing(Pair::second)).toList();
    }

    private static char[][] copyOf(char[][] matrix, int x, int y) {
        char[][] matrixCopy = new char[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            char[] aMatrix = matrix[i];
            int aLength = aMatrix.length;
            matrixCopy[i] = new char[aLength];
            System.arraycopy(aMatrix, 0, matrixCopy[i], 0, aLength);
        }

        matrixCopy[x][y] = matrix[x][y] == '.' ? '#' : '.';
        return matrixCopy;
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
        boolean isMirror;
        for (int i = 1; i < data.length; i++) {
            isMirror = true;
            int j = 0;
            while (j < i && i + j < data.length) {
                isMirror = isMirror && (data[i - j - 1] == data[i + j]);
                j++;
            }
            if (isMirror) {
                result += i;
            }
        }
        return result;
    }
}
