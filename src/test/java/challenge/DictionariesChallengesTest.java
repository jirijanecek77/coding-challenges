package challenge;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DictionariesChallengesTest {

    @Test
    void testCheckMagazine() {
        String[] magazine = {"give", "me", "one", "grand", "today", "night"};
        String[] note = {"give", "one", "grand", "today"};
        boolean result = DictionariesChallenges.checkMagazine(magazine, note);

        assertTrue(result);
    }

    @Test
    void testTwoStrings() {
        String result = DictionariesChallenges.twoStrings("hello", "world");

        assertEquals("YES", result);
    }

    @Test
    void testSherlockAndAnagrams() {
        int result = DictionariesChallenges.sherlockAndAnagrams("cdcd");

        assertEquals(5, result);
    }

    @Test
    void testCountTriples() {
        long result = DictionariesChallenges.countTriplets(Arrays.asList(1L, 3L, 9L, 9L, 27L, 81L), 3L);

        assertEquals(6, result);
    }

    @Test
    void testFreqQuery() {
        List<List<Integer>> queries = Arrays.asList(
                Arrays.asList(1, 3),
                Arrays.asList(2, 3),
                Arrays.asList(3, 2),
                Arrays.asList(1, 4),
                Arrays.asList(1, 5),
                Arrays.asList(1, 5),
                Arrays.asList(1, 4),
                Arrays.asList(3, 2),
                Arrays.asList(2, 4),
                Arrays.asList(3, 2)
        );

        List<Integer> result = DictionariesChallenges.freqQuery(queries);

        assertEquals(Arrays.asList(0, 1, 1), result);
    }

}
