package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay17Test {

    @Test
    void calcMinHeatLoss() throws IOException {
        assertEquals(102, AdventOfCodeDay17.calcMinHeatLoss("src/test/resources/advent/2023_17/input_test.in", false));
        assertEquals(10, AdventOfCodeDay17.calcMinHeatLoss("src/test/resources/advent/2023_17/input.in", false));
    }
}