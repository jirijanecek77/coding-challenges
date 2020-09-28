import java.util.*;

public class RecursionChallenges {

    static Map<Integer, Integer> map = new HashMap<>();

    static int stepPerms(int n) {

        int result = calculatePerms(n);

        return (int) (result % 10000000007L);
    }

    private static int calculatePerms(int n) {
        if (n < 0) return 0;
        if (n <= 1) return 1;

        if (!map.containsKey(n)) {
            map.put(n, calculatePerms(n - 1) + calculatePerms(n - 2) + calculatePerms(n - 3));
        }

        return map.get(n);
    }


    static String[] crosswordPuzzle(String[] crossword, String words) {
        List<String> wordsArray = Arrays.asList(words.split(";"));

        solveCrosswordPuzzle(crossword, wordsArray);
        return crossword;
    }

    private static boolean solveCrosswordPuzzle(String[] data, List<String> words) {
        boolean isEmpty = true;
        int row = -1;
        int colStart = -1;
        String emptySpace = "";
        int N = 10;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                char ch = data[i].charAt(j);
                if (ch == '-' && colStart == -1) {
                    colStart = j;
                }
                if (ch != '-' || j + 1 == N) {
                    if (colStart > 0 && colStart < j - 1) {
                        row = i;
                        emptySpace = data[row].substring(colStart, j);
                        isEmpty = false;
                        break;
                    } else {
                        colStart = -1;
                    }
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        if (isEmpty) {
            return true;
        }

        for (String word : words) {
            if (isSafe(data, row, emptySpace, word)) {
                data[row] = data[row].replace(emptySpace, word);
                List<String> newWords = new ArrayList<>(words);
                newWords.remove(word);
                if (solveCrosswordPuzzle(data, newWords)) {
                    return true;
                } else {
                    // replace it
                    data[row] = emptySpace;
                }
            }
        }

        return false;
    }

    private static boolean isSafe(String[] data, int row, String emptySpace, String word) {
        if (emptySpace.length() != word.length()) {
            return false;
        }
        return true;
    }


    //https://www.hackerrank.com/challenges/recursive-digit-sum/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=recursion-backtracking
    static int superDigit(String n, int k) {
        long result = n.chars()
                .map(ch -> Integer.parseInt(String.valueOf((char) ch)))
                .sum();


        return calcSuperDigit(String.valueOf(result * k));
    }

    private static int calcSuperDigit(String n) {
        if (n.length() == 1) return Integer.parseInt(n);

        return calcSuperDigit(String.valueOf(n.chars()
                .map(ch -> Integer.parseInt(String.valueOf((char) ch)))
                .sum()));
    }

}
