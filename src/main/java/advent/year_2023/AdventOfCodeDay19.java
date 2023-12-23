package advent.year_2023;

import domain.Interval;
import domain.Pair;
import utils.FileLineReader;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static utils.RegexUtils.parseByPattern;


public class AdventOfCodeDay19 {

    private static final Pattern WORKFLOW_PATTERN = Pattern.compile("^(\\w+)\\{(.+)\\}$");
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("^(x|m|a|s)(<|>)(\\d+):(\\w+)$");

    public static final String ACCEPTED_STATE = "A";
    private static final Set<String> TERMINALS = Set.of(ACCEPTED_STATE, "R");

    static long task1(String inputFileName) {
        Map<String, List<Instruction>> workflows = new HashMap<>();
        List<Map<Character, Integer>> parts = new ArrayList<>();
        boolean readWorkflow = true;

        for (String line : FileLineReader.of(inputFileName)) {
            if (readWorkflow) {
                if (line.isBlank()) {
                    readWorkflow = false;
                    continue;
                }
                addWorkflow(parseByPattern(line, WORKFLOW_PATTERN, 1, 2), workflows);

            } else {
                Map<Character, Integer> part = Arrays.stream(line.substring(1, line.length() - 1).split(","))
                        .map(e -> {
                            String[] strArr = e.split("=");
                            return Pair.of(strArr[0].charAt(0), Integer.parseInt(strArr[1]));
                        })
                        .collect(Collectors.toMap(Pair::first, Pair::second));
                parts.add(part);
            }
        }

        return parts.stream()
                .mapToLong(part -> isAccepted("in", part, workflows) ? part.values().stream().mapToLong(e -> e).sum() : 0)
                .sum();
    }

    static long task2(String inputFileName) {

        Map<String, List<Instruction>> workflows = new HashMap<>();

        for (String line : FileLineReader.of(inputFileName)) {
            if (line.isBlank()) {
                break;
            }
            addWorkflow(parseByPattern(line, WORKFLOW_PATTERN, 1, 2), workflows);
        }

        Interval interval = new Interval(1, 4000);
        return dfs("in", Map.of('x', interval, 'm', interval, 'a', interval, 's', interval), workflows);
    }

    private static void addWorkflow(String[] data, Map<String, List<Instruction>> workflows) {
        String name = data[0];
        List<Instruction> instructions = Arrays.stream(data[1].split(","))
                .map(str -> {
                    if (str.contains(":")) {
                        String[] instruction = parseByPattern(str, INSTRUCTION_PATTERN, 1, 2, 3, 4);
                        char sign = instruction[1].charAt(0);
                        int number = Integer.parseInt(instruction[2]);

                        return new Instruction(
                                instruction[0].charAt(0),
                                sign == '>',
                                number,
                                instruction[3]
                        );
                    }
                    return new Instruction('x', false, -1, str);
                })
                .toList();

        workflows.put(name, instructions);
    }

    private static boolean isAccepted(String state, Map<Character, Integer> part, Map<String, List<Instruction>> workflows) {
        if (TERMINALS.contains(state)) {
            return state.equals(ACCEPTED_STATE);
        }

        for (Instruction instruction : workflows.get(state)) {
            if (instruction.isValid(part.get(instruction.key))) {
                return isAccepted(instruction.nextState, part, workflows);
            }
        }
        throw new IllegalStateException();
    }

    private static long dfs(String state, Map<Character, Interval> intervals, Map<String, List<Instruction>> workflows) {
        if (TERMINALS.contains(state)) {
            return state.equals(ACCEPTED_STATE) ?
                    intervals.values().stream().mapToLong(e -> e.high() - e.low() + 1).reduce(1, (a, b) -> a * b) : 0;
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
                        newIntervals.put(instruction.key, instruction.isHigher ? new Interval(instruction.value + 1, elseInterval.high()) : new Interval(elseInterval.low(), instruction.value - 1));
                        elseIntervals.put(instruction.key, instruction.isHigher ? new Interval(elseInterval.low(), instruction.value) : new Interval(instruction.value, elseInterval.high()));

                        return dfs(instruction.nextState, newIntervals, workflows);
                    }
                })
                .sum();
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
