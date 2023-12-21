package advent.year_2023;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventOfCodeDay19Test {

    @Test
    void calcAccepted() throws IOException {
        assertEquals(19114, AdventOfCodeDay19.calcAccepted("src/test/resources/advent/2023_19/input_test.in"));
        assertEquals(346230, AdventOfCodeDay19.calcAccepted("src/test/resources/advent/2023_19/input.in"));
    }

//    @Test
//    void calcAccepted2() throws IOException {
//        assertEquals(167409079868000L, AdventOfCodeDay19.calcAccepted("src/test/resources/advent/2023_19/input_test.in"));
//        assertEquals(133125706867777L, AdventOfCodeDay19.calcAccepted("src/test/resources/advent/2023_19/input.in"));
//    }
}