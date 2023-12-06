package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay05Test {


    @Test
    void mapNumbers() throws IOException {
        assertEquals(251346198, AdventOfCodeDay05.mapNumbers("src/test/resources/advent/2023_5/input.in"));
    }

    @Test
    void mapNumbersRanges() throws IOException {
        assertEquals(46, AdventOfCodeDay05.mapNumbersRanges("src/test/resources/advent/2023_5/input_test.in"));
        assertEquals(184903631, AdventOfCodeDay05.mapNumbersRanges("src/test/resources/advent/2023_5/input.in"));
    }
}