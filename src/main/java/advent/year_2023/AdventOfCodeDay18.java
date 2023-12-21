package advent.year_2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AdventOfCodeDay18 {

    private static final Pattern LINE_PATTERN = Pattern.compile("^(R|L|U|D) (\\d+) \\(#(.+)\\)");

    static long calcArea(String inputFileName, boolean task1) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        List<Edge> edges = new ArrayList<>();

        try (reader) {

            String line = reader.readLine();
            int row = 1, col = 1;
            while (line != null) {
                Matcher matcher = LINE_PATTERN.matcher(line);
                if (matcher.find()) {

                    String code = matcher.group(3);
                    int steps = task1 ? Integer.parseInt(matcher.group(2)) : Integer.parseInt(code.substring(0, code.length() - 1), 16);
                    char direction = task1 ? matcher.group(1).charAt(0) : mapToDirection(code.charAt(5));

                    switch (direction) {
                        case 'R':
                            edges.add(new Edge(row, col, row, col + steps));
                            col += steps;
                            break;
                        case 'L':
                            edges.add(new Edge(row, col - steps, row, col));
                            col -= steps;
                            break;
                        case 'U':
                            edges.add(new Edge(row - steps, col, row, col));
                            row -= steps;
                            break;
                        case 'D':
                            edges.add(new Edge(row, col, row + steps, col));
                            row += steps;
                            break;
                    }
                }

                // read next line
                line = reader.readLine();
            }
        }
        int minRow = edges.stream().mapToInt(e -> e.startRow).min().orElseThrow();
        int maxRow = edges.stream().mapToInt(e -> e.endRow).max().orElseThrow();
        long size = 0;

        for (int i = minRow; i <= maxRow; i++) {
            int row = i;
            List<Edge> intersecting = edges.stream()
                    .filter(e -> (e.startRow <= row && e.endRow > row) || (e.startRow == row && e.endRow == row))
                    .sorted(Comparator.<Edge, Integer>comparing(e -> e.startCol).thenComparing(e -> e.endCol))
                    .toList();

            boolean in = false;
            int lastCol = 0;

            for (Edge edge : intersecting) {
                if (in) {
                    size += Math.max(0, edge.startCol - lastCol - 1);
                }

                if (edge.startRow != edge.endRow) {
                    in = !in;
                }
                lastCol = edge.endCol;
            }
        }

        return size + edges.stream().mapToInt(e -> e.endRow - e.startRow + e.endCol - e.startCol).sum();
    }

    private static char mapToDirection(char ch) {
        return switch (ch) {
            case '0' -> 'R';
            case '1' -> 'D';
            case '2' -> 'L';
            case '3' -> 'U';
            default -> throw new RuntimeException();
        };
    }

    private record Edge(int startRow, int startCol, int endRow, int endCol) {
    }
}
