package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay17Test {

    @Test
    void calcMinHeatLoss() throws IOException {
        assertEquals(102, AdventOfCodeDay17.calcMinHeatLoss("src/test/resources/advent/2023_17/input_test.in", false));
        assertEquals(767, AdventOfCodeDay17.calcMinHeatLoss("src/test/resources/advent/2023_17/input.in", false));
    }

    @Test
    void calcMinHeatLossUltra() throws IOException {
        assertEquals(94, AdventOfCodeDay17.calcMinHeatLoss("src/test/resources/advent/2023_17/input_test.in", true));
        assertEquals(904, AdventOfCodeDay17.calcMinHeatLoss("src/test/resources/advent/2023_17/input.in", true));
    }
}