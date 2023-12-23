package advent.year_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay18Test {

    @Test
    void task1() {
        assertEquals(62, AdventOfCodeDay18.calcArea("advent/2023_18/input_test.in", true));
        assertEquals(108909, AdventOfCodeDay18.calcArea("advent/2023_18/input.in", true));
    }

    @Test
    void task2() {
        assertEquals(952408144115L, AdventOfCodeDay18.calcArea("advent/2023_18/input_test.in", false));
        assertEquals(133125706867777L, AdventOfCodeDay18.calcArea("advent/2023_18/input.in", false));
    }
}