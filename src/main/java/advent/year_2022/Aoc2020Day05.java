package advent.year_2022;

import utils.FileLineReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Aoc2020Day05 {

    public int solve1(String inputFileName) {

        int maxId = 0;

        for (String line : FileLineReader.of(inputFileName)) {
            int row = textToInt(line.substring(0, 7), 'B');
            int col = textToInt(line.substring(7), 'R');
            maxId = Math.max(maxId, row * 8 + col);
        }

        return maxId;
    }

    public int solve2(String inputFileName) {

        List<Integer> ids = new ArrayList<>();
        for (String line : FileLineReader.of(inputFileName)) {
            int row = textToInt(line.substring(0, 7), 'B');
            int col = textToInt(line.substring(7), 'R');
            ids.add(row * 8 + col);
        }

        Collections.sort(ids);

        for (int i = 1; i < ids.size(); i++) {
            if (ids.get(i) - ids.get(i - 1) > 1) {
                return ids.get(i) - 1;
            }
        }
        throw new RuntimeException();
    }

    private int textToInt(String text, char highChar) {
        int result = 0;
        char[] chars = text.toCharArray();

        for (int i = chars.length - 1, exp = 0; i >= 0; i--, exp++) {
            if (chars[i] == highChar) {
                result += 1 << exp;
            }
        }
        return result;
    }
}
