package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay11Test {

    @Test
    void shortestPaths() throws IOException {
        assertEquals(374, AdventOfCodeDay11.shortestPaths("src/test/resources/advent/2023_11/input_test.in", 2));
        assertEquals(9445168, AdventOfCodeDay11.shortestPaths("src/test/resources/advent/2023_11/input.in", 2));
    }

    @Test
    void shortestPathsMil() throws IOException {
        assertEquals(82000210L, AdventOfCodeDay11.shortestPaths("src/test/resources/advent/2023_11/input_test.in", 1000000));
        assertEquals(742305960572L, AdventOfCodeDay11.shortestPaths("src/test/resources/advent/2023_11/input.in", 1000000));
    }
}