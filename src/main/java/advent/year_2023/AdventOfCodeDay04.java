package advent.year_2023;

import domain.Pair;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AdventOfCodeDay04 {

    private static final Pattern CARD_LINE_PATTERN = Pattern.compile("^(Card\\s*)(\\d+):((\\s*\\d+)+) \\|((\\s*\\d+)+)$");

    public static int checkSublist(String data) {
        return Arrays.stream(data.split("\n"))
                .mapToInt(AdventOfCodeDay04::checkCard)
                .sum();
    }

    private static int checkCard(String data) {
        Matcher matcher = CARD_LINE_PATTERN.matcher(data);
        if (matcher.find()) {
            int winningCount = getWinningCardsCount(matcher);
            if (winningCount == 0) {
                return 0;
            }
            return 1 << winningCount - 1; // 2 power size - 1
        }
        return 0;
    }

    public static int checkSublist2(String data) {
        Map<Integer, Integer> cards = Arrays.stream(data.split("\n"))
                .map(AdventOfCodeDay04::parseCards)
                .collect(Collectors.toMap(Pair::first, Pair::second));

        int[] arr = new int[cards.size()];
        Arrays.fill(arr, 1);
        int sum = 0;

        for (int i = 0; i < cards.size(); i++) {
            sum += arr[i];
            for (int j = i + 1; j < i + 1 + cards.get(i + 1); j++) {
                arr[j] += arr[i];
            }
        }

        return sum;
    }

    private static Pair<Integer, Integer> parseCards(String data) {
        Matcher matcher = CARD_LINE_PATTERN.matcher(data);
        if (!matcher.find()) {
            throw new RuntimeException(data + " has unknown format");
        }
        int gameNumber = Integer.parseInt(matcher.group(2));
        int winningCount = getWinningCardsCount(matcher);

        return Pair.of(gameNumber, winningCount);
    }

    private static int getWinningCardsCount(Matcher matcher) {
        Set<String> winning = Arrays.stream(matcher.group(3).split(" ")).filter(e -> !e.isBlank()).collect(Collectors.toSet());
        Set<String> current = Arrays.stream(matcher.group(5).split(" ")).filter(e -> !e.isBlank()).collect(Collectors.toSet());
        current.retainAll(winning);
        return current.size();
    }
}
