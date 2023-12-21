package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay18Test {

    @Test
    void calcArea() throws IOException {
        assertEquals(62, AdventOfCodeDay18.calcArea("src/test/resources/advent/2023_18/input_test.in", true));
        assertEquals(108909, AdventOfCodeDay18.calcArea("src/test/resources/advent/2023_18/input.in", true));
    }

    @Test
    void calcArea2() throws IOException {
        assertEquals(952408144115L, AdventOfCodeDay18.calcArea("src/test/resources/advent/2023_18/input_test.in", false));
        assertEquals(133125706867777L, AdventOfCodeDay18.calcArea("src/test/resources/advent/2023_18/input.in", false));
    }
}