package advent.year_2022;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class AdventTests {

    @Test
    void testCheckPasswords() throws IOException {
        Assertions.assertEquals(396, new CheckPassword().checkPasswords("src/test/resources/advent/2/input_big.in"));
    }

    @Test
    void testTreeCounter() throws IOException {
        Assertions.assertEquals(336L, new TreeCounter().countTrees("src/test/resources/advent/3/input.in"));
    }

    @Test
    void testPassportReader() throws IOException {
        Assertions.assertEquals(114, new PassportReader().readData("src/test/resources/advent/4/input.in"));
    }

    @Test
    void testSeatDecoder() throws IOException {
        Assertions.assertEquals(894, new SeatDecoder().decodeSeat("src/test/resources/advent/5/input.in"));
    }

}