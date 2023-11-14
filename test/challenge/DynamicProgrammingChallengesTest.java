package challenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DynamicProgrammingChallengesTest {

    @Test
    void test_abbreviation() {

        assertTrue(DynamicProgrammingChallenges.abbreviation("AbcDE", "ABDE"));
        assertFalse(DynamicProgrammingChallenges.abbreviation("KXzQ", "K"));
        assertTrue(DynamicProgrammingChallenges.abbreviation("Pi", "P"));
        assertFalse(DynamicProgrammingChallenges.abbreviation("AfPZN", "APZNC"));
        assertTrue(DynamicProgrammingChallenges.abbreviation("ABC", "ABC"));
        assertFalse(DynamicProgrammingChallenges.abbreviation("ABCDxxx", "ABC"));

    }

}