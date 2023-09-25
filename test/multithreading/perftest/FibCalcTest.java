package multithreading.perftest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FibCalcTest {

    @Test
    void testFib() {
        assertEquals(0, new FibCalcImpl().fib(0));
        assertEquals(1, new FibCalcImpl().fib(1));
        assertEquals(1, new FibCalcImpl().fib(2));
        assertEquals(2, new FibCalcImpl().fib(3));
        assertEquals(3, new FibCalcImpl().fib(4));
        assertEquals(102334155, new FibCalcImpl().fib(60));
    }
}