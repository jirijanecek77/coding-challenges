package advent.year_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay17Test {

    @Test
    void task1() {
        assertEquals(102, AdventOfCodeDay17.calcMinHeatLoss("advent/2023_17/input_test.in", false));
        assertEquals(767, AdventOfCodeDay17.calcMinHeatLoss("advent/2023_17/input.in", false));
    }

    @Test
    void task2() {
        assertEquals(94, AdventOfCodeDay17.calcMinHeatLoss("advent/2023_17/input_test.in", true));
        assertEquals(904, AdventOfCodeDay17.calcMinHeatLoss("advent/2023_17/input.in", true));
    }
}