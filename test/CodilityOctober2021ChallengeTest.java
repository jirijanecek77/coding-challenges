import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodilityOctober2021ChallengeTest {

    final CodilityOctober2021Challenge codilityOctober2021Challenge = new CodilityOctober2021Challenge();

    @Test
    void basicTest() {
        assertEquals(5, codilityOctober2021Challenge.solution(new int[]{2, 3, 1, 3}));
    }

    @Test
    void onesTest() {
        assertEquals(1, codilityOctober2021Challenge.solution(new int[]{1, 1, 1, 1, 1}));
    }
}