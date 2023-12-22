package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay12Test {

    @Test
    void combinationsCount() throws IOException {
        assertEquals(21, AdventOfCodeDay12.combinationsCount("src/test/resources/advent/2023_12/input_test.in", 1));
        assertEquals(8180, AdventOfCodeDay12.combinationsCount("src/test/resources/advent/2023_12/input.in", 1));
    }

    @Test
    void combinationsCount5() throws IOException {
//        assertEquals(525152, AdventOfCodeDay12.combinationsCount("src/test/resources/advent/2023_12/input_test.in", 5));
        // assertEquals(742305960572L, AdventOfCodeDay12.combinationsCount("src/test/resources/advent/2023_12/input.in", 5));
    }
}