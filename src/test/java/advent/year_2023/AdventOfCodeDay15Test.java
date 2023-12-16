package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay15Test {

    @Test
    void calcHash() throws IOException {
        assertEquals(1320, AdventOfCodeDay15.calcHash("src/test/resources/advent/2023_15/input_test.in"));
        assertEquals(520500, AdventOfCodeDay15.calcHash("src/test/resources/advent/2023_15/input.in"));
    }

    @Test
    void calcFocusingPower() throws IOException {
        assertEquals(145, AdventOfCodeDay15.calcFocusingPower("src/test/resources/advent/2023_15/input_test.in"));
        assertEquals(213097, AdventOfCodeDay15.calcFocusingPower("src/test/resources/advent/2023_15/input.in"));
    }
}