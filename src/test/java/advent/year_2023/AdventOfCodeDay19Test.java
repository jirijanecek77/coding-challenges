package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay19Test {

    @Test
    void task1() throws IOException {
        assertEquals(19114, AdventOfCodeDay19.task1("src/test/resources/advent/2023_19/input_test.in"));
        assertEquals(346230, AdventOfCodeDay19.task1("src/test/resources/advent/2023_19/input.in"));
    }

    @Test
    void task2() throws IOException {
        assertEquals(167409079868000L, AdventOfCodeDay19.task2("src/test/resources/advent/2023_19/input_test.in"));
        assertEquals(124693661917133L, AdventOfCodeDay19.task2("src/test/resources/advent/2023_19/input.in"));
    }
}