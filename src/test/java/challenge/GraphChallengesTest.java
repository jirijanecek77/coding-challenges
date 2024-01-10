package challenge;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphChallengesTest {

    @Test
    void calcEquation() {
        assertArrayEquals(new double[]{6.0, 0.5, -1.0, 1.0, -1.0}, GraphChallenges.calcEquation(
                        List.of(List.of("a", "b"), List.of("b", "c")),
                        new double[]{2.0, 3.0},
                        List.of(List.of("a", "c"), List.of("b", "a"), List.of("a", "e"), List.of("a", "a"), List.of("x", "x"))
                )
        );

        assertArrayEquals(new double[]{360.0, 0.008333333333333333, 20.0, 1.0, -1.0, -1.0}, GraphChallenges.calcEquation(
                        List.of(List.of("x1", "x2"), List.of("x2", "x3"), List.of("x3", "x4"), List.of("x4", "x5")),
                        new double[]{3.0, 4.0, 5.0, 6.0},
                        List.of(List.of("x1", "x5"), List.of("x5", "x2"), List.of("x2", "x4"), List.of("x2", "x2"), List.of("x2", "x9"), List.of("x9", "x9"))
                )
        );
    }

    @Test
    void canVisitAllRooms() {
        assertTrue(GraphChallenges.canVisitAllRooms(List.of(List.of(1), List.of(2), List.of(3), List.of(2))));
        assertFalse(GraphChallenges.canVisitAllRooms(List.of(List.of(1, 3), List.of(3, 0, 1), List.of(2), List.of(0))));
    }
}