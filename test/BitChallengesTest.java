import org.junit.jupiter.api.Test;

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
}