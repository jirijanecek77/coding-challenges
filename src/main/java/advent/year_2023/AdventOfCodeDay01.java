package advent.year_2023;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class AdventOfCodeDay01 {

    private static final Map<String, Character> DIGITS = Map.of(
            "one", '1',
            "two", '2',
            "three", '3',
            "four", '4',
            "five", '5',
            "six", '6',
            "seven", '7',
            "eight", '8',
            "nine", '9'
    );

    public static int findLeftMostAndRightMostNumbers(String data) {
        return Arrays.stream(data.split("\n"))
                .mapToInt(str -> findLeftMostInt(str) * 10 + findRightMostInt(str))
                .sum();
    }

    private static int findLeftMostInt(String str) {
        for (int i = 0; i < str.length(); i++) {
            char ch = mapToDigit(str.substring(i)).orElse(str.charAt(i));
            if (Character.isDigit(ch)) {
                return ch - '0';
            }
        }
        return 0;
    }

    private static int findRightMostInt(String str) {
        for (int i = str.length() - 1; i >= 0; i--) {
            char ch = mapToDigit(str.substring(i)).orElse(str.charAt(i));
            if (Character.isDigit(ch)) {
                return ch - '0';
            }
        }
        return 0;
    }

    private static Optional<Character> mapToDigit(String data) {
        return DIGITS.entrySet().stream()
                .filter(e -> data.startsWith(e.getKey()))
                .findFirst()
                .map(Map.Entry::getValue);
    }
}
