package advent.year_2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdventOfCodeDay11 {

    static long shortestPaths(String inputFileName, int expansion) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        List<Point> positions = new ArrayList<>();

        try (reader) {
            int rowIndex = 0;

            String line = reader.readLine();
            Set<Integer> emptyColumns = IntStream.range(0, line.length()).boxed().collect(Collectors.toSet());

            while (line != null) {
                char[] rowData = line.toCharArray();

                boolean hasGalaxy = false;
                for (int i = 0; i < rowData.length; i++) {
                    if (rowData[i] == '#') {
                        hasGalaxy = true;
                        emptyColumns.remove(i);
                        positions.add(new Point(rowIndex, i));
                    }
                }

                if (!hasGalaxy) {
                    rowIndex += expansion - 1;
                }
                rowIndex++;

                // read next line
                line = reader.readLine();
            }

            int columnDiff = 0;
            for (int column : emptyColumns) {
                for (int i = 0; i < positions.size(); i++) {
                    Point p = positions.get(i);
                    if (p.col > (column + columnDiff)) {
                        positions.set(i, new Point(p.row, p.col + expansion - 1));
                    }
                }
                columnDiff += expansion - 1;
            }
        }

        long sum = 0;
        for (int i = 0; i < positions.size(); i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                Point from = positions.get(i);
                Point to = positions.get(j);
                sum += Math.abs(from.row - to.row) + Math.abs(from.col - to.col);
            }
        }
        return sum;
    }

    private record Point(int row, int col) {
    }
}
