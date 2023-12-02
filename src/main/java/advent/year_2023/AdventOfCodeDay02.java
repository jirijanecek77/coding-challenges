package advent.year_2023;

import domain.Pair;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class AdventOfCodeDay02 {

    private static final Map<String, Integer> MAX_CUBES = Map.of(
            "red", 12,
            "green", 13,
            "blue", 14
    );

    public static int countGames(String data) {
        return Arrays.stream(data.split("\n"))
                .mapToInt(AdventOfCodeDay02::validGameNumber)
                .sum();
    }

    //Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    private static int validGameNumber(String str) {
        var gameNrEndPosition = str.indexOf(':');
        int gameNr = Integer.parseInt(str.substring(5, gameNrEndPosition));
        String[] rounds = str.substring(gameNrEndPosition + 2).split("; ");
        if (Arrays.stream(rounds).allMatch(AdventOfCodeDay02::checkGame)) {
            return gameNr;
        }
        return 0;
    }

    private static boolean checkGame(String data) {
        String[] games = data.split(", ");
        return Arrays.stream(games)
                .allMatch(game -> {
                    var separator = game.indexOf(" ");
                    int count = Integer.parseInt(game.substring(0, separator));
                    String color = game.substring(separator + 1);
                    return MAX_CUBES.get(color) >= count;
                });
    }

    public static int countPowerOfSets(String data) {
        return Arrays.stream(data.split("\n"))
                .mapToInt(AdventOfCodeDay02::countPowerSet)
                .sum();
    }

    private static int countPowerSet(String str) {
        var gameNrEndPosition = str.indexOf(':');
        return Arrays.stream(str.substring(gameNrEndPosition + 2).split("; |, "))
                .map(AdventOfCodeDay02::mapToColorNumberPair)
                .collect(toMap(
                        Pair::first,  // Color
                        Pair::second,  // Number
                        Integer::max))
                .values().stream()
                .reduce(1, (a, b) -> a * b);
    }

    private static Pair<String, Integer> mapToColorNumberPair(String game) {
        var separator = game.indexOf(" ");
        int count = Integer.parseInt(game.substring(0, separator));
        String color = game.substring(separator + 1);
        return new Pair<>(color, count);
    }
}
