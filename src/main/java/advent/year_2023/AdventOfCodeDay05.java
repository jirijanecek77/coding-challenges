package advent.year_2023;

import domain.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AdventOfCodeDay05 {

    private static final Pattern SEEDS_PATTERN = Pattern.compile("^(seeds: )((\\s*\\d+)+)$");

    public static long mapNumbers(String inputFileName) throws IOException {
        List<Long> seeds = new ArrayList<>();
        List<List<Transformation>> transformations = new ArrayList<>();

        parseInputFile(inputFileName, seeds, transformations);

        return seeds.stream()
                .mapToLong(seed -> {
                            Long newSeed = seed;
                            for (List<Transformation> trans : transformations) {
                                newSeed = transform(trans, newSeed);
                            }
                            return newSeed;
                        }
                )
                .min()
                .orElse(0);
    }

    private static Long transform(List<Transformation> intervals, Long seed) {
        return intervals.stream()
                .filter(e -> seed >= e.start() && seed <= e.end)
                .findFirst()
                .map(e -> seed + e.delta)
                .orElse(seed);
    }

    public static long mapNumbersRanges(String inputFileName) throws IOException {
        List<Long> seeds = new ArrayList<>();
        List<List<Transformation>> transformations = new ArrayList<>();

        parseInputFile(inputFileName, seeds, transformations);

        Set<Pair<Long, Long>> intervals = new HashSet<>();
        for (int i = 0; i < seeds.size(); i += 2) {
            Long start = seeds.get(i);
            Long delta = seeds.get(i + 1);
            intervals.add(Pair.of(start, start + delta - 1));
        }

        for (List<Transformation> trans : transformations) {
            System.out.println(intervals);
            System.out.println(trans);
            intervals = intervals.stream()
                    .flatMap(interval -> trans.stream()
                            .filter(e -> !(e.end() < interval.first() || e.start() > interval.second()))
                            .map(e -> new Transformation(Math.max(interval.first(), e.start), Math.min(interval.second(), e.end), e.delta))
                            .map(e -> Pair.of(e.start + e.delta, e.end + e.delta))
                    )
                    .collect(Collectors.toSet());
            System.out.println("New seed intervals: " + intervals + "\n");
        }
        var minValue = intervals.stream().mapToLong(Pair::first).min().orElseThrow();
        System.out.println("Result : " + minValue + "\n\n");
        return minValue;
    }

    private static void parseInputFile(String inputFileName, List<Long> seeds, List<List<Transformation>> transformations) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        try (reader) {
            List<Transformation> transformationGroup = new ArrayList<>();

            Matcher matcher = SEEDS_PATTERN.matcher(reader.readLine());
            if (matcher.find()) {
                seeds.addAll(Arrays.stream(matcher.group(2).split(" ")).map(Long::parseLong).toList());
                reader.readLine();
            }

            String line = reader.readLine();
            while (line != null) {
                if (line.isBlank()) {
                    transformations.add(fillTransformationGaps(transformationGroup));
                } else if (Character.isDigit(line.charAt(0))) {
                    List<Long> transData = Arrays.stream(line.split(" ")).map(Long::parseLong).toList();
                    transformationGroup.add(
                            new Transformation(
                                    transData.get(1),
                                    transData.get(1) + transData.get(2) - 1,
                                    transData.get(0) - transData.get(1)
                            )
                    );
                } else {
                    transformationGroup = new ArrayList<>();
                }

                // read next line
                line = reader.readLine();
            }
            transformations.add(fillTransformationGaps(transformationGroup));
        }
    }

    private static List<Transformation> fillTransformationGaps(List<Transformation> transformations) {
        var result = new ArrayList<>(transformations.stream()
                .sorted(Comparator.comparing(e -> e.start))
                .toList());

        if (!result.isEmpty() && result.get(0).start > 0) {
            result.add(0, new Transformation(0L, result.get(0).start - 1, 0L));
        }

        for (int i = 1; i < result.size(); i++) {
            long start = result.get(i).start;
            long end = result.get(i - 1).end;

            if (end + 1 < start) {
                result.add(i, new Transformation(end + 1, start - 1, 0L));
                i++;
            }
        }

        result.add(new Transformation(result.get(result.size() - 1).end + 1, Long.MAX_VALUE, 0L));
        return result;
    }

    private record Transformation(Long start, Long end, Long delta) {
        @Override
        public String toString() {
            return "(" + start + ", " + end + ", delta: " + delta + ')';
        }
    }
}
