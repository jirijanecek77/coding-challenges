package advent.year_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay21Test {

    @Test
    void task1() {
        assertEquals(16, AdventOfCodeDay21.task1("advent/2023_21/input_test.in", 6));
        assertEquals(3615, AdventOfCodeDay21.task1("advent/2023_21/input.in", 64));
    }

    @Test
    void task2() {
        assertEquals(167004, AdventOfCodeDay21.task1("advent/2023_21/input_test.in", 500));
//        assertEquals(228060006554227L, AdventOfCodeDay21.task1("advent/2023_21/input.in", 26501365));
    }
}