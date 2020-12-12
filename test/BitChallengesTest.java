import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BitChallengesTest {

    @Test
    void testSwapBits() {

        //110001 -> 100011
        long result = BitChallenges.swapBits(49, 1, 4);
        assertEquals(result, 35);
    }

}