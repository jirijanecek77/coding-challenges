import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
