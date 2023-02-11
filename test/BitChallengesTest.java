import org.junit.jupiter.api.Test;

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

}