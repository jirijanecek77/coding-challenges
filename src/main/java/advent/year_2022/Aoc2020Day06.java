package advent.year_2022;

import utils.FileLineReader;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Aoc2020Day06 {

    public int solve1(String inputFileName) {

        int sum = 0;
        Set<Character> group = new HashSet<>();
        for (String line : FileLineReader.of(inputFileName)) {
            if (line.isBlank()) {
                sum += group.size();
                group.clear();
            } else {
                group.addAll(line.chars().mapToObj(e -> (char) e).toList());
            }
        }

        return sum + group.size();
    }

    public int solve2(String inputFileName) {

        int sum = 0;
        boolean insert = true;
        Set<Character> group = new HashSet<>();

        for (String line : FileLineReader.of(inputFileName)) {
            if (line.isBlank()) {
                sum += group.size();
                group.clear();
                insert = true;
            } else {
                Set<Character> lineChars = line.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
                if (insert) {
                    group.addAll(lineChars);
                    insert = false;
                } else {
                    group.retainAll(lineChars);
                }
            }
        }

        return sum + group.size();
    }
}
