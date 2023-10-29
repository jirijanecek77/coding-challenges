package challenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecursionChallengesTest {

    @Test
    void testStepPerm() {
        int result = RecursionChallenges.stepPerms(6);
        assertEquals(24, result);
    }

    @Test
    void testCrosswordPuzzle() {
        String[] crossword = {
                "XXXXXX-XXX",
                "XX------XX",
                "XXXXXX-XXX",
                "XXXXXX-XXX",
                "XXX------X",
                "XXXXXX-X-X",
                "XXXXXX-X-X",
                "XXXXXXXX-X",
                "XXXXXXXX-X",
                "XXXXXXXX-X"};
        String words = "ICELAND;MEXICO;PANAMA;ALMATY";
        String[] result = RecursionChallenges.crosswordPuzzle(crossword, words);

        String[] expected = {
                "XXXXXXIXXX",
                "XXMEXICOXX",
                "XXXXXXEXXX",
                "XXXXXXLXXX",
                "XXXPANAMAX",
                "XXXXXXNXLX",
                "XXXXXXDXMX",
                "XXXXXXXXAX",
                "XXXXXXXXTX",
                "XXXXXXXXYX"
        };
        assertArrayEquals(expected, result);
    }

    @Test
    void testSuperDigit() {
        int result = RecursionChallenges.superDigit("123", 3);
        assertEquals(9, result);
    }

    @Test
    void testSolveSudoku() {
        int[][] board = {
                {7, 0, 2, 0, 5, 0, 6, 0, 0},
                {0, 0, 0, 0, 0, 3, 0, 0, 0},
                {1, 0, 0, 0, 0, 9, 5, 0, 0},
                {8, 0, 0, 0, 0, 0, 0, 9, 0},
                {0, 4, 3, 0, 0, 0, 7, 5, 0},
                {0, 9, 0, 0, 0, 0, 0, 0, 8},
                {0, 0, 9, 7, 0, 0, 0, 0, 5},
                {0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 7, 0, 4, 0, 2, 0, 3},
        };

        assertTrue(RecursionChallenges.solveSudoku(board));
    }
}
