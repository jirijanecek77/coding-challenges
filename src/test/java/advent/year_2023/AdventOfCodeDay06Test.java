package advent.year_2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay06Test {

    @Test
    void calculate1() {
        assertEquals(293046, AdventOfCodeDay06.calculate1(
                "Time:        61     70     90     66\n" +
                        "Distance:   643   1184   1362   1041"));
    }

    @Test
    void calculate2() {
        assertEquals(35150181, AdventOfCodeDay06.calculate2(
                "Time:        61     70     90     66\n" +
                        "Distance:   643   1184   1362   1041"));
    }
}