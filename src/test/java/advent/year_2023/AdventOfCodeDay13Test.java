package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay13Test {

    @Test
    void countMirror() throws IOException {
        assertEquals(405, AdventOfCodeDay13.countMirror("src/test/resources/advent/2023_13/input_test.in", false));
        assertEquals(34772, AdventOfCodeDay13.countMirror("src/test/resources/advent/2023_13/input.in", false));
    }

    @Test
    void countMirrorWithFix() throws IOException {
        assertEquals(400, AdventOfCodeDay13.countMirror("src/test/resources/advent/2023_13/input_test.in", true));
//        assertEquals(40700, AdventOfCodeDay13.countMirror("src/test/resources/advent/2023_13/input.in", true));
    }
}