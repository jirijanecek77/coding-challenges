package advent.year_2023;

import domain.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdventOfCodeDay03 {

    private static final Predicate<Character> ALL_SYMBOLS = ch -> !Character.isDigit(ch);
    private static final Predicate<Character> ASTRIX_SYMBOL = ch -> ch == '*';

    public static int sumAdjacentToSymbol(String data) {
        int result = 0;
        String[] rows = data.split("\n");

        if (rows.length == 0) {
            return result;
        }

        Set<Integer> previous = Set.of();
        Set<Integer> current = getSymbolIndexes(rows[0], ALL_SYMBOLS);
        for (int row = 0; row < rows.length; row++) {
            Set<Integer> next = row + 1 < rows.length ? getSymbolIndexes(rows[row + 1], ALL_SYMBOLS) : Set.of();
            Stack<Character> digitStack = new Stack<>();
            boolean hasAdjacentSymbol = false;

            // check adjacent to symbol
            int i = 0;
            for (char ch : rows[row].toCharArray()) {
                if (Character.isDigit(ch)) {
                    digitStack.add(ch);
                    hasAdjacentSymbol = hasAdjacentSymbol
                            || previous.contains(i - 1) || previous.contains(i) || previous.contains(i + 1)
                            || current.contains(i - 1) || current.contains(i + 1)
                            || next.contains(i - 1) || next.contains(i) || next.contains(i + 1);
                } else if (hasAdjacentSymbol) {
                    result += charStackToNumber(digitStack);
                    hasAdjacentSymbol = false;
                } else {
                    // empty stack
                    charStackToNumber(digitStack);
                }
                i++;
            }

            if (hasAdjacentSymbol) {
                result += charStackToNumber(digitStack);
            }
            previous = current;
            current = next;
        }
        return result;
    }

    public static int sumAdjacentToAstrixSymbol(String data) {
        int result = 0;
        String[] rows = data.split("\n");

        if (rows.length == 0) {
            return result;
        }
        Map<Pair<Integer, Integer>, Integer> previousAstrixPositions = new HashMap<>();

        Set<Integer> previous = Set.of();
        Set<Integer> current = getSymbolIndexes(rows[0], ASTRIX_SYMBOL);
        for (int row = 0; row < rows.length; row++) {
            Set<Integer> next = row + 1 < rows.length ? getSymbolIndexes(rows[row + 1], ASTRIX_SYMBOL) : Set.of();
            Stack<Character> digitStack = new Stack<>();
            Pair<Integer, Integer> astrixPosition = null;

            int col = 0;
            for (char ch : rows[row].toCharArray()) {
                if (Character.isDigit(ch)) {
                    digitStack.add(ch);
                    if (astrixPosition == null) {
                        astrixPosition = getAstrixPosition(col, row, previous, current, next);
                    }
                } else if (astrixPosition != null) {
                    result += calculateProduct(charStackToNumber(digitStack), astrixPosition, previousAstrixPositions);
                    astrixPosition = null;
                } else {
                    // empty stack, no astrix around this number found
                    charStackToNumber(digitStack);
                }
                col++;
            }

            if (astrixPosition != null) {
                result += calculateProduct(charStackToNumber(digitStack), astrixPosition, previousAstrixPositions);
            }
            previous = current;
            current = next;
        }
        return result;
    }

    private static Set<Integer> getSymbolIndexes(String row, Predicate<Character> symbolPredicate) {
        return IntStream.range(0, row.length())
                .filter(i -> symbolPredicate.and(ch -> ch != '.').test(row.charAt(i)))
                .boxed()
                .collect(Collectors.toSet());
    }

    private static int charStackToNumber(Stack<Character> stack) {
        int result = 0;
        int powerTen = 1;
        while (!stack.isEmpty()) {
            result += (stack.pop() - '0') * powerTen;
            powerTen *= 10;
        }
        return result;
    }

    private static Pair<Integer, Integer> getAstrixPosition(int col, int row, Set<Integer> previous, Set<Integer> current, Set<Integer> next) {
        if (previous.contains(col - 1)) {
            return Pair.of(row - 1, col - 1);
        }
        if (previous.contains(col)) {
            return Pair.of(row - 1, col);
        }
        if (previous.contains(col + 1)) {
            return Pair.of(row - 1, col + 1);
        }
        if (current.contains(col - 1)) {
            return Pair.of(row, col - 1);
        }
        if (current.contains(col + 1)) {
            return Pair.of(row, col + 1);
        }
        if (next.contains(col - 1)) {
            return Pair.of(row + 1, col - 1);
        }
        if (next.contains(col)) {
            return Pair.of(row + 1, col);
        }
        if (next.contains(col + 1)) {
            return Pair.of(row + 1, col + 1);
        }
        return null;
    }

    private static int calculateProduct(int number, Pair<Integer, Integer> astrixPosition, Map<Pair<Integer, Integer>, Integer> previousAstrixPositions) {
        return previousAstrixPositions.entrySet().stream()
                .filter(e -> e.getKey().equals(astrixPosition))
                .findFirst()
                .map(e -> {
                    previousAstrixPositions.remove(e.getKey());
                    return number * e.getValue();
                })
                .orElseGet(() -> {
                    previousAstrixPositions.put(astrixPosition, number);
                    return 0;
                });
    }
}
