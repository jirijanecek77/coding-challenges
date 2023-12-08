package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay08Test {

    @Test
    void calcSteps() throws IOException {
        assertEquals(6, AdventOfCodeDay08.calcSteps("src/test/resources/advent/2023_8/input_test.in"));
        assertEquals(15871, AdventOfCodeDay08.calcSteps("src/test/resources/advent/2023_8/input.in"));
    }

    @Test
    void calcStepsSimultaneously() throws IOException {
        assertEquals(6, AdventOfCodeDay08.calcStepsSimultaneously("src/test/resources/advent/2023_8/input_test2.in"));
        assertEquals(11283670395017L, AdventOfCodeDay08.calcStepsSimultaneously("src/test/resources/advent/2023_8/input.in"));
    }
}