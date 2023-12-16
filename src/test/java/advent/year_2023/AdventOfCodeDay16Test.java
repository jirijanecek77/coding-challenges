package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay16Test {

    @Test
    void calcEnergizing() throws IOException {
        assertEquals(46, AdventOfCodeDay16.calcEnergizing("src/test/resources/advent/2023_16/input_test.in", false));
        assertEquals(6994, AdventOfCodeDay16.calcEnergizing("src/test/resources/advent/2023_16/input.in", false));
    }

    @Test
    void calcEnergizingAnyStart() throws IOException {
        assertEquals(51, AdventOfCodeDay16.calcEnergizing("src/test/resources/advent/2023_16/input_test.in", true));
        assertEquals(7488, AdventOfCodeDay16.calcEnergizing("src/test/resources/advent/2023_16/input.in", true));
    }
}