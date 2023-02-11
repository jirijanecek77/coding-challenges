import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArraysChallengesTest {

    @Test
    void testMinimumBribes() {
        int[] q = {1, 2, 5, 3, 7, 8, 6, 4};
        int result = ArraysChallenges.minimumBribes(q);

        assertEquals(7, result);
    }

    @Test
    void testMinimumSwaps() {
        int[] q = {2, 3, 4, 1, 5};
        int result = ArraysChallenges.minimumSwaps(q);

        assertEquals(3, result);
    }

    @Test
    void testArrayManipulation() {
        int[][] queries = {{2, 6, 8}, {3, 5, 7}, {1, 8, 1}, {5, 9, 15}};
        long result = ArraysChallenges.arrayManipulation(10, queries);

        assertEquals(31, result);
    }

    @Test
    void maximumSubarraySum() {
        int[] arr = {2, 3, -10, -1, 5, 1, 1, -2, 15, -2, 3, 0, -1};
        int result = ArraysChallenges.maximumSubarraySum(arr);

        assertEquals(21, result);
    }

    @Test
    void zeroSubarraySum() {
        int[] arr = {800, 2, 3, -10, -1, 5, 1, 1, -2, 15, -2, 3, 0, -1};
        boolean result = ArraysChallenges.zeroSubarraySum(arr);

        assertTrue(result);
    }

    @Test
    void zeroSubarraySum_notSum() {
        int[] arr = {2, 3, 5, 1};
        boolean result = ArraysChallenges.zeroSubarraySum(arr);

        assertFalse(result);
    }

    @Test
    void maximumNonAdjacentSum() {
        int[] arr = {-2, 1, 3, -4, 5};
        int result = ArraysChallenges.maximumNonAdjacentSum(arr);

        assertEquals(8, result);
    }

    @Test
    void longestDecreasingSubarray() {
        int[] arr = {-2, 1, 3, -4, 5};

        assertEquals(2, ArraysChallenges.longestDecreasingSubarray(arr));
    }

    @Test
    void findLocalMaximum() {
        int[] arr = {1, 3, 4, 5, 6, 5, 4, 3};
        assertEquals(6, ArraysChallenges.findLocalMaximum(arr));
    }

    @Test
    void secondLargest() {
        assertEquals(5, ArraysChallenges.secondLargest(new int[]{1, 3, 4, 5, 6, 5, 4, 3}));

        assertEquals(1, ArraysChallenges.secondLargest(new int[]{1, 2, 1}));

        assertEquals(-1, ArraysChallenges.secondLargest(new int[]{1, 1, 1}));
    }
}
