package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay10Test {

    @Test
    void cycleLength() throws IOException {
        assertEquals(8, AdventOfCodeDay10.loopLength("src/test/resources/advent/2023_10/input_test.in"));
        assertEquals(6942, AdventOfCodeDay10.loopLength("src/test/resources/advent/2023_10/input.in"));
    }

    @Test
    void cycleArea() throws IOException {
        assertEquals(1, AdventOfCodeDay10.loopAreaSize("src/test/resources/advent/2023_10/input_test.in"));
        assertEquals(4, AdventOfCodeDay10.loopAreaSize("src/test/resources/advent/2023_10/input_test2.in"));
        assertEquals(297, AdventOfCodeDay10.loopAreaSize("src/test/resources/advent/2023_10/input.in"));
    }
}