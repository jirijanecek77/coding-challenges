package advent.year_2023;

import domain.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class AdventOfCodeDay19 {

    private static final Pattern WORKFLOW_PATTERN = Pattern.compile("^(\\w+)\\{(.+)\\}$");
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^(x|m|a|s)(<|>)(\\d+):(\\w+)$");

    public static final String ACCEPTED_STATE = "A";
    private static final Set<String> TERMINALS = Set.of(ACCEPTED_STATE, "R");

    static long calcAccepted(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        Map<String, List<Instruction>> workflows = new HashMap<>();
        List<Map<Character, Integer>> parts = new ArrayList<>();

        try (reader) {
            String line = reader.readLine();
            while (!line.isBlank()) {
                Matcher workflowMatcher = WORKFLOW_PATTERN.matcher(line);
                if (workflowMatcher.find()) {

                    String name = workflowMatcher.group(1);
                    List<Instruction> instructions = Arrays.stream(workflowMatcher.group(2).split(","))
                            .map(str -> {
                                Matcher iMatcher = INSTRUCTION_PATTERN.matcher(str);
                                if (iMatcher.find()) {
                                    char sign = iMatcher.group(2).charAt(0);
                                    int number = Integer.parseInt(iMatcher.group(3));

                                    return new Instruction(
                                            iMatcher.group(1).charAt(0),
                                            sign == '>' ? i -> i > number : i -> i < number,
                                            iMatcher.group(4),
                                            iMatcher.group(4).length() == 1 && TERMINALS.contains(iMatcher.group(4))
                                    );
                                }
                                return new Instruction('t', e -> true, str, str.length() == 1 && TERMINALS.contains(str));
                            })
                            .toList();

                    workflows.put(name, instructions);
                }

                // read next line
                line = reader.readLine();
            }

            // skip blank line
            line = reader.readLine();

            while (line != null) {
                Map<Character, Integer> part = Arrays.stream(line.substring(1, line.length() - 1).split(","))
                        .map(e -> {
                            String[] data = e.split("=");
                            return Pair.of(data[0].charAt(0), Integer.parseInt(data[1]));
                        })
                        .collect(Collectors.toMap(Pair::first, Pair::second));
                parts.add(part);

                // read next line
                line = reader.readLine();
            }
        }

        return parts.stream()
                .mapToLong(part -> isAccepted(part, workflows) ? part.values().stream().mapToLong(e -> e).sum() : 0)
                .sum();
    }

    private static boolean isAccepted(Map<Character, Integer> part, Map<String, List<Instruction>> workflows) {
        String state = "in";

        while (true) {
            for (Instruction instruction : workflows.get(state)) {
                int value = instruction.category == 't' ? 0 : part.get(instruction.category);
                if (instruction.predicate.test(value)) {
                    if (instruction.isTerminal()) {
                        return instruction.nextState.equals(ACCEPTED_STATE);
                    } else {
                        state = instruction.nextState;
                        break;
                    }
                }
            }
        }
    }

    private record Instruction(
            Character category,

            Predicate<Integer> predicate,

            String nextState,

            Boolean isTerminal) {
    }
}
