package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay16Test {

    @Test
    void task1() throws IOException {
        assertEquals(46, AdventOfCodeDay16.calcEnergizing("advent/2023_16/input_test.in", false));
        assertEquals(6994, AdventOfCodeDay16.calcEnergizing("advent/2023_16/input.in", false));
    }

    @Test
    void task2() throws IOException {
        assertEquals(51, AdventOfCodeDay16.calcEnergizing("advent/2023_16/input_test.in", true));
        assertEquals(7488, AdventOfCodeDay16.calcEnergizing("advent/2023_16/input.in", true));
    }
}