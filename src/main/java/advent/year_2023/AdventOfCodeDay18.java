package advent.year_2023;

import utils.FileLineReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static utils.RegexUtils.parseByPattern;


public class AdventOfCodeDay18 {

    private static final Pattern LINE_PATTERN = Pattern.compile("^(R|L|U|D) (\\d+) \\(#(.+)\\)");

    static long calcArea(String inputFileName, boolean task1) {
        List<Vertex> vertices = new ArrayList<>();

        long x = 0, y = 0;
        for (String line : FileLineReader.of(inputFileName)) {

            String[] parts = parseByPattern(line, LINE_PATTERN, 1, 2, 3);
            String code = parts[2];
            int steps = task1 ? Integer.parseInt(parts[1]) : Integer.parseInt(code.substring(0, code.length() - 1), 16);
            char direction = task1 ? parts[0].charAt(0) : mapToDirection(code.charAt(5));

            vertices.add(
                    switch (direction) {
                        case 'R' -> new Vertex(x += steps, y);
                        case 'L' -> new Vertex(x -= steps, y);
                        case 'U' -> new Vertex(x, y += steps);
                        case 'D' -> new Vertex(x, y -= steps);
                        default -> throw new IllegalStateException("Unexpected value: " + direction);
                    });
        }

        return shoelaceFormula(vertices);
    }

    private static long shoelaceFormula(List<Vertex> vertices) {
        long area = 0;
        long perimeter = 0;
        int n = vertices.size();
        for (int i = 0; i <= n - 1; i++) {
            long x1 = vertices.get(i).x;
            long y1 = vertices.get(i).y;
            long x2 = vertices.get((i + 1) % n).x;
            long y2 = vertices.get((i + 1) % n).y;

            area += x1 * y2 - x2 * y1;
            perimeter += Math.abs(x2 - x1) + Math.abs(y2 - y1);
        }

        return (Math.abs(area) + perimeter) / 2 + 1;
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

    private record Vertex(long x, long y) {
    }
}
