package multithreading.perftest;

/**
 * Multi-threaded performance test executor of Fibonacci sequence calculators
 * <p>
 * Application accepts 3 parameters:
 * n - which fibonacci number to calculate
 * calculationCount = how many fibonacci calculations to run in total during the test
 * threadPoolSize = how many threads should be used to run the calculations
 */
public class PerformanceTestApp {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("""
                    Three arguments expected: n - which fibonacci number to calculate
                    calculationCount = how many fibonacci calculations to run in total during the test
                    threadPoolSize = how many threads should be used to run the calculations""");
        }
        int n = Integer.parseInt(args[0]);
        int executionCount = Integer.parseInt(args[1]);
        int threadPoolSize = Integer.parseInt(args[2]);

        try {
            PerformanceTestResult result = new PerformanceTesterImpl()
                    .runPerformanceTest(
                            () -> new FibCalcImpl().fib(n),
                            executionCount,
                            threadPoolSize
                    );

            System.out.printf("totalTime=%dms, minTime=%dms, maxTime=%dms%n", result.getTotalTime(), result.getMinTime(), result.getMaxTime());
        } catch (InterruptedException e) {
            System.out.println("Test was interrupted.");
        }
    }
}
