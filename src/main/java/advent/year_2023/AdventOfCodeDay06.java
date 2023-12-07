package advent.year_2023;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class AdventOfCodeDay06 {

    private static final Pattern ROW_PATTERN = Pattern.compile("^(Time:\\s*|Distance:\\s*)((\\s*\\d+)+)$");

    public static long calculate1(String data) {
        String[] rows = data.split("\n");

        List<Long> times = parseData1(rows[0]);
        List<Long> distances = parseData1(rows[1]);

        return IntStream.range(0, times.size())
                .mapToLong(i -> calculateBest(times.get(i), distances.get(i)))
                .reduce(1, (a, b) -> a * b);
    }

    public static long calculate2(String data) {
        String[] rows = data.split("\n");

        Long time = parseData2(rows[0]);
        Long distance = parseData2(rows[1]);

        return calculateBest(time, distance);

    }

    private static List<Long> parseData1(String row) {
        Matcher matcher = ROW_PATTERN.matcher(row);
        if (matcher.find()) {
            return Arrays.stream(matcher.group(2).split(" "))
                    .filter(e -> !e.isBlank())
                    .map(Long::parseLong)
                    .toList();
        }
        return List.of();
    }

    private static Long parseData2(String row) {
        Matcher matcher = ROW_PATTERN.matcher(row);
        if (matcher.find()) {
            return Long.parseLong(matcher.group(2).replaceAll(" ", ""));
        }
        return 0L;
    }

    private static long calculateBest(long time, long distance) {
        long half = time / 2;
        long value = binarySearch(time, distance);
        boolean idEven = time % 2 == 0;

        return (half - (value + 1)) * 2 + (idEven ? 1 : 2);
    }

    private static long binarySearch(long n, long key) {
        long left = 1, right = n / 2;
        while (left <= right) {
            long mid = (left + right) >>> 1;
            long value = (n - mid) * mid;

            if (value < key)
                left = mid + 1;
            else if (value > key)
                right = mid - 1;
            else {
                return mid;
            }
        }

        return left - 1;
    }
}
