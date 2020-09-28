import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarmUpChallengesTest {

    @Test
    void testSockMerchant() {
        int[] data = {10, 20, 20, 10, 10, 30, 50, 10, 20};
        int result = WarmUpChallenges.sockMerchant(9, data);
        assertEquals(3, result);
    }

    @Test
    void testCountingValleys() {
        int result = WarmUpChallenges.countingValleys(10, "UDDDUDUUDU");

        assertEquals(2, result);
    }

    @Test
    void testJumpingClouds() {
        int[] clouds = {0, 0, 0, 0, 1, 0};
        int result = WarmUpChallenges.jumpingOnClouds(clouds);
        assertEquals(3, result);
    }

    @Test
    void testRepeatedString() {
        long result = WarmUpChallenges.repeatedString("aba", 10L);
        assertEquals(7, result);
    }

}
