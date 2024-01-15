package advent.year_2022;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class AdventTests {

    @Test
    void testCheckPasswords() throws IOException {
        Assertions.assertEquals(396, new AoC2020Day02().solve("src/test/resources/advent/2020_02/input.in"));
    }

    @Test
    void testTreeCounter() throws IOException {
        Assertions.assertEquals(336L, new Aoc2020Day03().solve("src/test/resources/advent/2020_03/input.in"));
    }

    @Test
    void testPassportReader() throws IOException {
        Assertions.assertEquals(114, new AoC2020Day04().solve("src/test/resources/advent/2020_04/input.in"));
    }

    @Test
    void testSeatDecoder() throws IOException {
        Assertions.assertEquals(894, new Aoc2020Day05().solve1("advent/2020_05/input.in"));
        Assertions.assertEquals(579, new Aoc2020Day05().solve2("advent/2020_05/input.in"));
    }

}