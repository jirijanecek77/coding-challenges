package advent.year_2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdventOfCodeDay12 {

    private static final Pattern ROW_PATTERN = Pattern.compile("^((.|\\?|#)+) ((\\d(,)*)+)$");

    static long combinationsCount(String inputFileName, int repetitions) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        long sum = 0;

        try (reader) {
            String line = reader.readLine();

            while (line != null) {
                Matcher matcher = ROW_PATTERN.matcher(line);
                if (!matcher.find()) {
                    throw new RuntimeException("unknown row");
                }

                List<Integer> counts = Collections.nCopies(
                                repetitions,
                                Arrays.stream(matcher.group(3).split(",")).map(Integer::parseInt).toList())
                        .stream().flatMap(Collection::stream).toList();

                String data = IntStream.range(0, repetitions).mapToObj(e -> matcher.group(1)).collect(Collectors.joining("?"));
                String input = cleanData(data);
                String target = counts.stream().map("#"::repeat).collect(Collectors.joining("."));

                System.out.println(input + " -> " + target);
                long count = solve(input, counts, target);
                System.out.println(count);
                sum += count;

                // read next line
                line = reader.readLine();
            }

            return sum;
        }
    }

    private static long solveDP(String input, String target) {
        long[] dp = new long[input.length()];
        dp[0] = input.charAt(0) == '?' ? 1L : 0;
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == '?') {
                dp[i] = dp[i - 1] + 2;
            } else {
                dp[i] = dp[i - 1];
            }
        }
        return dp[input.length() - 1];
    }

    private static long solve(String data, List<Integer> counts, String target) {
//        System.out.println(data + counts);
        int first = data.indexOf('?');
        if (first < 0) {
            int result = cleanData(data).equals(target) ? 1 : 0;
//            System.out.println(result);
            return result;
        } else if (!(target + ".").startsWith(data.substring(0, first))) {
//            System.out.println("cancel " + data + " not like " + target);
            return 0;
        }
        return solve(cleanData(data.replaceFirst("\\?", ".")), counts, target)
                + solve(cleanData(data.replaceFirst("\\?", "#")), counts, target);
    }

    private static String cleanData(String data) {
        return removeTrailingDots(removeLeadingDots(data)).replaceAll("\\.{2,}", ".");
    }

    private static String removeLeadingDots(String s) {
        return s.replaceAll("^(\\.)+", "");
    }

    private static String removeTrailingDots(String s) {
        return s.replaceAll("(\\.)+$", "");
    }
}
