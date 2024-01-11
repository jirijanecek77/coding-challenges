package challenge;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Test
    void kThLargest() {
        assertEquals(4, ArraysChallenges.kThLargest(new int[]{1, -3, 4, 5, 6, 5, 4, 23}, 3));
    }

    @Test
    void oneInteger() {
        assertEquals(33, ArraysChallenges.oneInteger(new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    void maxScoreRemovingStones() {
        assertEquals(6, ArraysChallenges.maxScoreRemovingStones(2, 4, 6));
    }

    @Test
    void findKthClosest() {
        assertArrayEquals(new int[]{1, 2, 3, 4}, ArraysChallenges.findKthClosest(new int[]{1, 2, 3, 4, 5, 7}, 4, 3));
        assertArrayEquals(new int[]{2, 4, 4, 5}, ArraysChallenges.findKthClosest(new int[]{1, 2, 4, 4, 5, 7}, 4, 3));
    }

    @Test
    void minCost() {
        assertEquals(2, ArraysChallenges.minCost("aabcaa", new int[]{1, 2, 3, 5, 4, 1}));
    }

    @Test
    void makeEqual() {
        assertTrue(ArraysChallenges.makeEqual(new String[]{"abc", "aabc", "bc"}));
        assertFalse(ArraysChallenges.makeEqual(new String[]{"abc", "aabc", "bcc"}));
    }

    @Test
    void minOperations() {
        assertEquals(1, ArraysChallenges.minOperations("0100"));
    }

    @Test
    void maxLengthBetweenEqualCharacters() {
        assertEquals(18, ArraysChallenges.maxLengthBetweenEqualCharacters("mgntdygtxrvxjnwksqhxuxtrv"));
    }

    @Test
    void findContentChildren() {
        assertEquals(2, ArraysChallenges.findContentChildren(new int[]{10, 9, 8, 7}, new int[]{5, 6, 7, 8}));
    }

    @Test
    void findMatrix() {
        List<List<Integer>> matrix = ArraysChallenges.findMatrix(new int[]{1, 3, 4, 1, 2, 3, 1});
        assertTrue(matrix.size() == 3
                && Set.of(1, 3, 4, 2).equals(new HashSet<>(matrix.get(0)))
                && Set.of(1, 3).equals(new HashSet<>(matrix.get(1)))
                && Set.of(1).equals(new HashSet<>(matrix.get(2))));
    }

    @Test
    void numberOfBeams() {
        assertEquals(8, ArraysChallenges.numberOfBeams(new String[]{"011001", "000000", "010100", "001000"}));
    }

    @Test
    void increasingTriplet() {
        assertTrue(ArraysChallenges.increasingTriplet(new int[]{2, 1, 5, 0, 4, 6}));
        assertFalse(ArraysChallenges.increasingTriplet(new int[]{1, 1, 1, 1, 1, 1}));
        assertFalse(ArraysChallenges.increasingTriplet(new int[]{1, 1, -6, 9}));
    }

    @Test
    void kthFactor() {
        assertEquals(3, ArraysChallenges.kthFactor(12, 3));
    }
}