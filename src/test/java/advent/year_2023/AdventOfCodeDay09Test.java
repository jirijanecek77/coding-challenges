package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay09Test {

    @Test
    void extrapolateLast() throws IOException {
        assertEquals(114, AdventOfCodeDay09.extrapolateLast("src/test/resources/advent/2023_9/input_test.in"));
        assertEquals(2008960228, AdventOfCodeDay09.extrapolateLast("src/test/resources/advent/2023_9/input.in"));
    }

    @Test
    void extrapolateFirst() throws IOException {
        assertEquals(2, AdventOfCodeDay09.extrapolateFirst("src/test/resources/advent/2023_9/input_test.in"));
        assertEquals(1097, AdventOfCodeDay09.extrapolateFirst("src/test/resources/advent/2023_9/input.in"));
    }
}