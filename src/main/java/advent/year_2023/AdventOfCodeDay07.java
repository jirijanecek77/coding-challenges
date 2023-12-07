package advent.year_2023;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdventOfCodeDay07 {

    private static final Map<Character, Integer> CARD_RANKS = new HashMap<>() {{
        put('2', 2);
        put('3', 3);
        put('4', 4);
        put('5', 5);
        put('6', 6);
        put('7', 7);
        put('8', 8);
        put('9', 9);
        put('T', 10);
        put('J', 11);
        put('Q', 12);
        put('K', 13);
        put('A', 14);
    }};

    private static final Map<Character, Integer> CARD_RANKS_WITH_JOKER = new HashMap<>() {{
        put('2', 2);
        put('3', 3);
        put('4', 4);
        put('5', 5);
        put('6', 6);
        put('7', 7);
        put('8', 8);
        put('9', 9);
        put('T', 10);
        put('J', 1);
        put('Q', 12);
        put('K', 13);
        put('A', 14);
    }};

    public static long calculateCards(String inputData) {
        return calculate(inputData, CARD_RANKS, Map::values);
    }

    public static int calculateCardsWithJokers(String inputData) {
        return calculate(inputData, CARD_RANKS_WITH_JOKER, AdventOfCodeDay07::jokerMapper);
    }

    private static int calculate(String inputData, Map<Character, Integer> cardRanks, Function<Map<Character, Long>, Collection<Long>> cardinalityMapToCardinalityMapper) {
        List<Integer> bids = Arrays.stream(inputData.split("\n"))
                .map(row -> {
                    String[] items = row.split(" ");
                    return new Hand(items[0], calculateRank(items[0], cardinalityMapToCardinalityMapper), Integer.parseInt(items[1]));
                })
                .sorted(Comparator.comparing(Hand::rank)
                        .thenComparing((h1, h2) -> compareHandsNoSameCards(h1.cards, h2.cards, cardRanks)))
                .peek(System.out::println)
                .map(Hand::bid)
                .toList();

        return IntStream.range(0, bids.size())
                .map(i -> bids.get(i) * (i + 1))
                .sum();
    }

    private static int calculateRank(String cards, Function<Map<Character, Long>, Collection<Long>> jokerMapper) {
        Map<Character, Long> cardinalities = cards.chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return cardinalitiesToRank(jokerMapper.apply(cardinalities));
    }

    private static int cardinalitiesToRank(Collection<Long> cardinalities) {
        int n = cardinalities.size();
        int topCardinality = cardinalities.stream().mapToInt(Long::intValue).max().orElse(0);

        if (n == 1) {
            // all same
            return 7;
        }
        if (n == 2) {
            // 4 or full house
            return topCardinality == 4 ? 6 : 5;
        }
        if (n == 3) {
            // 3 or 2 pairs
            return topCardinality == 3 ? 4 : 3;
        }
        if (n == 4) {
            // 1 pair
            return 2;
        }
        return 1;
    }

    private static int compareHandsNoSameCards(String s1, String s2, Map<Character, Integer> cardRanks) {
        int i = 0;
        while (i < s1.length()) {
            int comparison = cardRanks.get(s1.charAt(i)).compareTo(cardRanks.get(s2.charAt(i)));
            if (comparison != 0) {
                return comparison;
            }
            i++;
        }
        return 0;
    }

    private static Collection<Long> jokerMapper(Map<Character, Long> cardinalityMap) {
        Map<Character, Long> withoutJokersMap = new HashMap<>(cardinalityMap);
        int jokers = Optional.ofNullable(withoutJokersMap.remove('J')).orElse(0L).intValue();

        if (withoutJokersMap.isEmpty()) {
            return cardinalityMap.values();
        }

        List<Long> result = withoutJokersMap.values().stream().sorted().collect(Collectors.toList());
        long highestCardinality = result.remove(result.size() - 1);
        result.add(highestCardinality + jokers);

        return result;
    }

    private record Hand(String cards, Integer rank, Integer bid) {
        @Override
        public String toString() {
            return "(" + cards + ", " + rank + ", " + bid + ')';
        }
    }
}
