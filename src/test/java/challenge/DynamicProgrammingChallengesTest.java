package challenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicProgrammingChallengesTest {

    @Test
    void test_abbreviation() {
        assertTrue(DynamicProgrammingChallenges.abbreviation("AbcDE", "ABDE"));
        assertFalse(DynamicProgrammingChallenges.abbreviation("KXzQ", "K"));
        assertTrue(DynamicProgrammingChallenges.abbreviation("Pi", "P"));
        assertFalse(DynamicProgrammingChallenges.abbreviation("AfPZN", "APZNC"));
        assertTrue(DynamicProgrammingChallenges.abbreviation("ABC", "ABC"));
        assertFalse(DynamicProgrammingChallenges.abbreviation("ABCDxxx", "ABC"));
    }

    @Test
    void test_targetSum() {
        assertEquals(5, DynamicProgrammingChallenges.targetSum(new int[]{1, 1, 1, 1, 1}, 3));
    }

    @Test
    void minDifficulty() {
        assertEquals(9, DynamicProgrammingChallenges.minDifficulty(new int[]{6, 5, 4, 3, 2, 1}, 3));
    }

    @Test
    void minOperations() {
        assertEquals(7, DynamicProgrammingChallenges.minOperations(new int[]{14, 12, 14, 14, 12, 14, 14, 12, 12, 12, 12, 14, 14, 12, 14, 14, 14, 12, 12}));
        assertEquals(-1, DynamicProgrammingChallenges.minOperations(new int[]{2, 1, 2, 2, 3, 3}));
    }

    @Test
    void lengthOfLIS() {
        assertEquals(3, DynamicProgrammingChallenges.lengthOfLIS(new int[]{4, 10, 4, 3, 8, 9}));
        assertEquals(1, DynamicProgrammingChallenges.lengthOfLIS(new int[]{4, 4, 4, 4, 4, 4}));
        assertEquals(4, DynamicProgrammingChallenges.lengthOfLIS(new int[]{-2, 1, 3, -4, 5}));
    }

    @Test
    void jobScheduling() {
        assertEquals(18, DynamicProgrammingChallenges.jobScheduling(new int[]{4, 2, 4, 8, 2}, new int[]{5, 5, 5, 10, 8}, new int[]{1, 2, 8, 10, 4}));
    }

    @Test
    void minFallingPathSum() {
        assertEquals(13, DynamicProgrammingChallenges.minFallingPathSum(new int[][]{{2, 1, 3}, {6, 5, 4}, {7, 8, 9}}));
        assertEquals(-36, DynamicProgrammingChallenges.minFallingPathSum(new int[][]{{100, -42, -46, -41}, {31, 97, 10, -10}, {-58, -51, 82, 89}, {51, 81, 69, -51}}));
    }
}