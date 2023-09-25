package multithreading.perftest;

public class FibCalcImpl implements FibCalc {
    @Override
    public long fib(int n) {
        if (n < 0) throw new IllegalArgumentException("Argument must be greater than 0");
        if (n >= 1476) throw new IllegalArgumentException("Argument must be smaller than 1476");

        long result = fibRecursively(n);
        System.out.printf("Calculation of fib(%d)=%d done in %s%n", n, result, Thread.currentThread().getName());
        return result;
    }

    /**
     * Recurrent implementation of Fibonacci sequence calculation
     * @param n
     * @return n-th Fibonacci number
     */
    private long fibRecursively(int n) {
        if (n <= 1) {
            return n;
        }
        return fibRecursively(n - 1) + fibRecursively(n - 2);
    }

    /**
     * Iterative implementation of Fibonacci sequence calculation
     * Just for the purposes of playing with the performance :)
     * @param n
     * @return n-th Fibonacci number
     */
    private long fibIteratively(int n) {
        if (n <= 1) return n;
        long last = 1;
        long beforeLast = 0;
        long current = -1;

        for (int i = 1; i < n; i++) {
            current = last + beforeLast;
            beforeLast = last;
            last = current;
        }
        return current;
    }
}
