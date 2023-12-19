package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay14Test {

    @Test
    void countRocks() throws IOException {
        assertEquals(136, new AdventOfCodeDay14().countRocks("src/test/resources/advent/2023_14/input_test.in", false));
        assertEquals(110128, new AdventOfCodeDay14().countRocks("src/test/resources/advent/2023_14/input.in", false));
    }

    @Test
    void countRocksAllDirections() throws IOException {
        assertEquals(64, new AdventOfCodeDay14().countRocks("src/test/resources/advent/2023_14/input_test.in", true));
        assertEquals(103861, new AdventOfCodeDay14().countRocks("src/test/resources/advent/2023_14/input.in", true));
    }
}