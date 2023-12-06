package advent.year_2023;

import domain.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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

    private static Long transform(List<Transformation> trans, Long newSeed) {
        return trans.stream()
                .filter(e -> newSeed >= e.start() && newSeed <= e.end)
                .findFirst()
                .map(e -> newSeed + e.delta)
                .orElse(newSeed);
    }

    public static long mapNumbersRanges(String inputFileName) throws IOException {
        List<Long> seeds = new ArrayList<>();
        List<List<Transformation>> transformations = new ArrayList<>();

        parseInputFile(inputFileName, seeds, transformations);

        long minValue = Long.MAX_VALUE;
        for (int i = 0; i < seeds.size(); i += 2) {
            Long start = seeds.get(i);
            Long delta = seeds.get(i + 1);
            Stream<Pair<Long, Long>> intervals = Stream.of(Pair.of(start, start + delta - 1));

            for (List<Transformation> trans : transformations) {
                intervals = intervals
                        .flatMap(interval -> trans.stream()
                                .filter(e -> !(e.end() < interval.first() || e.start() > interval.second()))
                                .map(e -> new Transformation(Math.max(interval.first(), e.start), Math.min(interval.second(), e.end), e.delta))
                                .map(e -> Pair.of(e.start + e.delta, e.end + e.delta))
                        );

            }
            minValue = Math.min(minValue, intervals.mapToLong(Pair::first).min().orElse(Long.MAX_VALUE));
        }
        return minValue;
    }

    private static void parseInputFile(String inputFileName, List<Long> seeds, List<List<Transformation>> transformations) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        try (reader) {
            List<Transformation> transformationGroup = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                Matcher matcher = SEEDS_PATTERN.matcher(line);
                if (matcher.find()) {
                    seeds.addAll(Arrays.stream(matcher.group(2).split(" ")).map(Long::parseLong).toList());
                    reader.readLine();
                }

                if (line.isBlank()) {
                    transformations.add(prepareTransformation(transformationGroup));
                } else if (Character.isDigit(line.charAt(0))) {
                    List<Long> intervalData = Arrays.stream(line.split(" "))
                            .map(Long::parseLong).toList();
                    transformationGroup.add(
                            new Transformation(
                                    intervalData.get(1),
                                    intervalData.get(1) + intervalData.get(2) - 1,
                                    intervalData.get(0) - intervalData.get(1)
                            )
                    );
                } else {
                    transformationGroup = new ArrayList<>();
                }

                // read next line
                line = reader.readLine();
            }
            transformations.add(prepareTransformation(transformationGroup));
        }
    }

    private static List<Transformation> prepareTransformation(List<Transformation> transformations) {
        var result = new ArrayList<>(transformations.stream().sorted(Comparator.comparing(e -> e.start)).toList());
        result.add(0, new Transformation(Long.MIN_VALUE, result.get(0).start - 1, 0L));
        result.add(new Transformation(result.get(result.size() - 1).end + 1, Long.MAX_VALUE, 0L));
        return result;
    }

    private record Transformation(Long start, Long end, Long delta) {
    }
}
