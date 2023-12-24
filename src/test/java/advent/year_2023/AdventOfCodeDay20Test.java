package advent.year_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay20Test {

    @Test
    void task1() {
        assertEquals(32000000, AdventOfCodeDay20.task1("advent/2023_20/input_test.in"));
        assertEquals(11687500, AdventOfCodeDay20.task1("advent/2023_20/input_test2.in"));
        assertEquals(806332748, AdventOfCodeDay20.task1("advent/2023_20/input.in"));
    }

    @Test
    void task2() {
//        assertEquals(124693661917133L, AdventOfCodeDay20.task1("advent/2023_20/input.in"));
    }
}