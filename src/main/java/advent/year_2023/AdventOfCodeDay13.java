package advent.year_2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class AdventOfCodeDay13 {

    static long countMirror(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        long sum = 0;
        char[][] data = new char[][]{};
        int rowIndex = 0;
        try (reader) {
            String line = reader.readLine();
            while (line != null) {
                if (line.isBlank()) {
                    sum += calcMirror(data);
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
        sum += calcMirror(data);
        return sum;
    }

    private static int calcMirror(char[][] data) {
        int rowCount = calcRows(data);
        int colCount = calcColumns(data);

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
