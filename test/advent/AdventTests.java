package advent;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdventTests {

    @Test
    void testCheckPasswords() throws IOException {
        assertEquals(396, new CheckPassword().checkPasswords("resources/advent/2/input_big.in"));
    }

    @Test
    void testTreeCounter() throws IOException {
        assertEquals(336L, new TreeCounter().countTrees("resources/advent/3/input.in"));
    }

    @Test
    void testPassportReader() throws IOException {
        assertEquals(114, new PassportReader().readData("resources/advent/4/input.in"));
    }

    @Test
    void testSeatDecoder() throws IOException {
        assertEquals(894, new SeatDecoder().decodeSeat("resources/advent/5/input.in"));
    }

}