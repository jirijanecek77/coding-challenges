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


public class AdventOfCodeDay19 {

    private static final Pattern WORKFLOW_PATTERN = Pattern.compile("^(\\w+)\\{(.+)\\}$");
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^(x|m|a|s)(<|>)(\\d+):(\\w+)$");

    public static final String ACCEPTED_STATE = "A";
    private static final Set<String> TERMINALS = Set.of(ACCEPTED_STATE, "R");

    static long task1(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        Map<String, List<Instruction>> workflows = new HashMap<>();
        List<Map<Character, Integer>> parts = new ArrayList<>();

        try (reader) {
            String line = reader.readLine();
            while (!line.isBlank()) {
                Matcher workflowMatcher = WORKFLOW_PATTERN.matcher(line);
                if (workflowMatcher.find()) {
                    addWorkflow(workflowMatcher, workflows);
                }

                // read next line
                line = reader.readLine();
            }

            // skip blank line
            line = reader.readLine();

            while (line != null) {
                Map<Character, Integer> part = Arrays.stream(line.substring(1, line.length() - 1).split(","))
                        .map(e -> {
                            String[] strArr = e.split("=");
                            return Pair.of(strArr[0].charAt(0), Integer.parseInt(strArr[1]));
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

    static long task2(String inputFileName) throws IOException {
        final BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName));

        Map<String, List<Instruction>> workflows = new HashMap<>();

        try (reader) {
            String line = reader.readLine();
            while (!line.isBlank()) {
                Matcher workflowMatcher = WORKFLOW_PATTERN.matcher(line);
                if (workflowMatcher.find()) {

                    addWorkflow(workflowMatcher, workflows);
                }

                // read next line
                line = reader.readLine();
            }
        }

        Interval interval = new Interval(1, 4000);
        return dfs("in", Map.of('x', interval, 'm', interval, 'a', interval, 's', interval), workflows);
    }

    private static void addWorkflow(Matcher workflowMatcher, Map<String, List<Instruction>> workflows) {
        String name = workflowMatcher.group(1);
        List<Instruction> instructions = Arrays.stream(workflowMatcher.group(2).split(","))
                .map(str -> {
                    Matcher iMatcher = INSTRUCTION_PATTERN.matcher(str);
                    if (iMatcher.find()) {
                        char sign = iMatcher.group(2).charAt(0);
                        int number = Integer.parseInt(iMatcher.group(3));

                        return new Instruction(
                                iMatcher.group(1).charAt(0),
                                sign == '>',
                                number,
                                iMatcher.group(4)
                        );
                    }
                    return new Instruction('x', false, -1, str);
                })
                .toList();

        workflows.put(name, instructions);
    }

    private static boolean isAccepted(Map<Character, Integer> part, Map<String, List<Instruction>> workflows) {
        String state = "in";

        while (true) {
            for (Instruction instruction : workflows.get(state)) {
                int value = part.get(instruction.key);
                if (instruction.isValid(value)) {
                    if (TERMINALS.contains(instruction.nextState)) {
                        return instruction.nextState.equals(ACCEPTED_STATE);
                    } else {
                        state = instruction.nextState;
                        break;
                    }
                }
            }
        }
    }

    private static long dfs(String state, Map<Character, Interval> intervals, Map<String, List<Instruction>> workflows) {
        if (TERMINALS.contains(state)) {
            return state.equals(ACCEPTED_STATE) ?
                    intervals.values().stream().mapToLong(e -> e.high - e.low + 1).reduce(1, (a, b) -> a * b) : 0;
        }

        Map<Character, Interval> elseIntervals = new HashMap<>(intervals);
        return workflows.get(state).stream()
                .mapToLong(instruction -> {
                    if (instruction.value < 0) {
                        // terminal state
                        return dfs(instruction.nextState, elseIntervals, workflows);
                    } else {
                        // next state
                        Interval elseInterval = elseIntervals.get(instruction.key);
                        Map<Character, Interval> newIntervals = new HashMap<>(elseIntervals);
                        newIntervals.put(instruction.key, instruction.isHigher ? new Interval(instruction.value + 1, elseInterval.high) : new Interval(elseInterval.low, instruction.value - 1));
                        elseIntervals.put(instruction.key, instruction.isHigher ? new Interval(elseInterval.low, instruction.value) : new Interval(instruction.value, elseInterval.high));

                        return dfs(instruction.nextState, newIntervals, workflows);
                    }
                })
                .sum();
    }

    private record Interval(int low, int high) {
        @Override
        public String toString() {
            return "[" + low + "," + high + ']';
        }
    }

    private record Instruction(
            Character key,

            Boolean isHigher,

            Integer value,

            String nextState) {

        public boolean isValid(int valueToValidate) {
            if (value < 0) {
                // terminal state
                return true;
            }
            return isHigher ? valueToValidate > value : valueToValidate < value;
        }
    }
}
