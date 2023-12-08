package advent.year_2023;

import domain.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.MathUtils.lcm;

public class AdventOfCodeDay08 {
    private static final Pattern INSTRUCTIONS_PATTERN = Pattern.compile("^(L|R)+$");
    private static final Pattern STEPS_PATTERN = Pattern.compile("^([A-Z|0-9]{3}) = \\(([A-Z|0-9]{3}), ([A-Z|0-9]{3})\\)$");

    static long calcSteps(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));
        char[] instructions = new char[]{};
        Map<String, Pair<String, String>> steps = new HashMap<>();

        try (reader) {
            Matcher instructionMatcher = INSTRUCTIONS_PATTERN.matcher(reader.readLine());
            if (instructionMatcher.find()) {
                instructions = instructionMatcher.group(0).toCharArray();
                reader.readLine();
            }

            String line = reader.readLine();
            while (line != null) {
                Matcher stepMatcher = STEPS_PATTERN.matcher(line);
                if (stepMatcher.find()) {
                    steps.put(stepMatcher.group(1), Pair.of(stepMatcher.group(2), stepMatcher.group(3)));
                } else {
                    throw new RuntimeException();
                }

                // read next line
                line = reader.readLine();
            }
        }

        return countSteps("AAA", s -> s.equals("ZZZ"), instructions, steps);
    }

    static long calcStepsSimultaneously(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));
        char[] input = new char[]{};
        Map<String, Pair<String, String>> steps = new HashMap<>();

        try (reader) {
            Matcher instructionMatcher = INSTRUCTIONS_PATTERN.matcher(reader.readLine());
            if (instructionMatcher.find()) {
                input = instructionMatcher.group(0).toCharArray();
                reader.readLine();
            }

            String line = reader.readLine();
            while (line != null) {
                Matcher stepMatcher = STEPS_PATTERN.matcher(line);
                if (stepMatcher.find()) {
                    steps.put(stepMatcher.group(1), Pair.of(stepMatcher.group(2), stepMatcher.group(3)));
                }

                // read next line
                line = reader.readLine();
            }
        }

        char[] instructions = input;
        List<String> data = steps.keySet().stream().filter(e -> e.endsWith("A")).toList();
        List<Long> iterationCounts = data.stream()
                .map(step -> countSteps(step, s -> s.endsWith("Z"), instructions, steps))
                .toList();

        return lcm(iterationCounts);
    }

    private static long countSteps(String start, Predicate<String> stopPredicate, char[] instructions, Map<String, Pair<String, String>> steps) {
        long count = 0;
        String nextStep = start;
        while (!stopPredicate.test(nextStep)) {
            for (char instruction : instructions) {
                nextStep = instruction == 'L' ? steps.get(nextStep).first() : steps.get(nextStep).second();
            }
            count += instructions.length;
        }
        return count;
    }
}
