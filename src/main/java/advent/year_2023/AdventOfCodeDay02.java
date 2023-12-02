package advent.year_2023;

import domain.Pair;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toMap;

public class AdventOfCodeDay02 {

    private static final Map<String, Integer> MAX_CUBES = Map.of(
            "red", 12,
            "green", 13,
            "blue", 14
    );

    private static final Pattern GAME_LINE_PATTERN = Pattern.compile("^(Game )(\\d+): ((\\d+ (blue|red|green)(, |; )?)+)$");

    public static int countGames(String data) {
        return Arrays.stream(data.split("\n"))
                .mapToInt(AdventOfCodeDay02::validGameNumber)
                .sum();
    }

    //Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    private static int validGameNumber(String data) {
        Matcher matcher = GAME_LINE_PATTERN.matcher(data);
        if (matcher.find()) {
            int gameNr = Integer.parseInt(matcher.group(2));
            String[] rounds = matcher.group(3).split("; ");
            if (Arrays.stream(rounds).allMatch(AdventOfCodeDay02::checkGame)) {
                return gameNr;
            }
        }
        return 0;
    }

    private static boolean checkGame(String data) {
        String[] games = data.split(", ");
        return Arrays.stream(games)
                .map(AdventOfCodeDay02::mapToColorNumberPair)
                .allMatch(pair -> MAX_CUBES.get(pair.first()) >= pair.second());
    }

    public static int countPowerOfSets(String data) {
        return Arrays.stream(data.split("\n"))
                .mapToInt(AdventOfCodeDay02::countPowerSet)
                .sum();
    }

    private static int countPowerSet(String data) {
        Matcher matcher = GAME_LINE_PATTERN.matcher(data);
        if (matcher.find()) {
            return Arrays.stream(matcher.group(3).split("; |, "))
                    .map(AdventOfCodeDay02::mapToColorNumberPair)
                    .collect(toMap(
                            Pair::first,  // Color
                            Pair::second,  // Number
                            Integer::max))
                    .values().stream()
                    .reduce(1, (a, b) -> a * b);
        }
        return 0;
    }

    private static Pair<String, Integer> mapToColorNumberPair(String game) {
        String[] parts = game.split(" ", 2);
        int number = Integer.parseInt(parts[0]);
        String color = parts[1];
        return Pair.of(color, number);
    }
}
