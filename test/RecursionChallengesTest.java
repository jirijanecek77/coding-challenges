import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
