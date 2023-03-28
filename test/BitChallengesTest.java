import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class BitChallengesTest {

    @Test
    void testSwapBits() {

        //110001 -> 100011
        long result = BitChallenges.swapBits(49, 1, 4);
        assertEquals(result, 35);
    }

    @Test
    void testClosestPowerOf() {
        int result = BitChallenges.closestPowerOf(15);
        assertEquals(3, result);
    }

    @Test
    void testSortBy1s() {
        int[] result = BitChallenges.sortBy1s(new int[]{0, 2, 1, 3, 5, 7, 4, 6, 8});
        assertArrayEquals(new int[]{0, 1, 2, 4, 8, 3, 5, 6, 7}, result);
    }

    @Test
    void testUnique2N() {
        assertEquals(3, BitChallenges.unique2N(new int[]{1, 1, 5, 4, 3, 6, 6, 5, 4}));
    }

    @Test
    void testUnique3N() {
        assertEquals(3, BitChallenges.unique3N(new int[]{1, 1, 1, 5, 4, 5, 3, 4, 6, 6, 5, 6, 4}));
    }

    @Test
    void testPow() {
        assertEquals(BigInteger.valueOf(8), BitChallenges.pow(2, 3));
        assertEquals(BigInteger.valueOf(45325).pow(100), BitChallenges.pow(45325, 100));
    }
}