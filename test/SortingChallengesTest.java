import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortingChallengesTest {

    @Test
    void testMaximumToys() {
        int[] prices = {1, 12, 5, 111, 200, 1000, 10};
        int result = SortingChallenges.maximumToys(prices, 50);

        assertEquals(4, result);
    }
}
