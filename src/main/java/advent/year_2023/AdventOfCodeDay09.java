package advent.year_2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class AdventOfCodeDay09 {
    static int extrapolateLast(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        List<Integer> result = new ArrayList<>();

        try (reader) {
            String line = reader.readLine();
            while (line != null) {

                List<Integer> data = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
                result.add(calcLastExtrapolation(data) + data.get(data.size() - 1));

                // read next line
                line = reader.readLine();
            }
        }

        return result.stream().mapToInt(e -> e).sum();
    }

    private static int calcLastExtrapolation(List<Integer> data) {
        if (data.stream().allMatch(e -> e.equals(0))) {
            return 0;
        }

        List<Integer> diffs = IntStream.range(0, data.size() - 1).map(i -> data.get(i + 1) - data.get(i)).boxed().toList();

        var result = calcLastExtrapolation(diffs);
        return diffs.get(diffs.size() - 1) + result;
    }

    public static int extrapolateFirst(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        List<Integer> result = new ArrayList<>();

        try (reader) {
            String line = reader.readLine();
            while (line != null) {

                List<Integer> data = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
                result.add(data.get(0) - calcFirstExtrapolation(data));

                // read next line
                line = reader.readLine();
            }
        }

        return result.stream().mapToInt(e -> e).sum();
    }

    private static int calcFirstExtrapolation(List<Integer> data) {
        if (data.stream().allMatch(e -> e.equals(0))) {
            return 0;
        }

        List<Integer> diffs = IntStream.range(0, data.size() - 1).map(i -> data.get(i + 1) - data.get(i)).boxed().toList();

        var result = calcFirstExtrapolation(diffs);
        return diffs.get(0) - result;
    }
}
