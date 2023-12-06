package advent.year_2023;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        Queue<Integer> queue = new PriorityQueue<>();
        return Arrays.stream(data.split("\n"))
                .mapToInt(row -> countCards(row, queue))
                .sum();
    }

    private static int getWinningCardsCount(Matcher matcher) {
        Set<String> winning = Arrays.stream(matcher.group(3).split(" ")).filter(e -> !e.isBlank()).collect(Collectors.toSet());
        Set<String> current = Arrays.stream(matcher.group(5).split(" ")).filter(e -> !e.isBlank()).collect(Collectors.toSet());
        current.retainAll(winning);
        return current.size();
    }

    private static int countCards(String data, Queue<Integer> queue) {
        Matcher matcher = CARD_LINE_PATTERN.matcher(data);
        if (!matcher.find()) {
            throw new RuntimeException(data + " has unknown format");
        }
        int result = 0;
        int gameNumber = Integer.parseInt(matcher.group(2));
        int winningCount = getWinningCardsCount(matcher);
        List<Integer> nextCards = IntStream.range(gameNumber + 1, gameNumber + 1 + winningCount).boxed().toList();

        queue.add(gameNumber);
        while (Objects.equals(queue.peek(), gameNumber)) {
            queue.remove();
            queue.addAll(nextCards);
            result++;
        }

        return result;
    }
}
