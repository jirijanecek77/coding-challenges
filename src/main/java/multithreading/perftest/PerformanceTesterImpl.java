package multithreading.perftest;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PerformanceTesterImpl implements PerformanceTester {
    @Override
    public PerformanceTestResult runPerformanceTest(Runnable task, int executionCount, int threadPoolSize) throws InterruptedException {
        long[] executionResults = new long[executionCount];

        long startTime, endTime;
        try (ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize)) {

            startTime = System.currentTimeMillis();

            // do all the executions
            for (int i = 0; i < executionCount; i++) {
                TaskTimeMeasurementWrapper taskTimeMeasurementWrapper = new TaskTimeMeasurementWrapper(i, executionResults, task);
                executor.submit(taskTimeMeasurementWrapper);
            }

            // close the executor and wait for all task to finish
            executor.shutdown();
            boolean terminationResult = executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            if (!terminationResult) {
                System.err.println("Executor termination timed out");
            }
            endTime = System.currentTimeMillis();
        }

        long totalTime = endTime - startTime;
        long minTime = Arrays.stream(executionResults).min().orElse(-1);
        long maxTime = Arrays.stream(executionResults).max().orElse(-1);
        return new PerformanceTestResult(totalTime, minTime, maxTime);
    }

    /***
     * Wrapper class to measure execution time of a given task
     * @param id
     * @param executionTimes
     * @param task to measure
     */
    private record TaskTimeMeasurementWrapper(int id, long[] executionTimes, Runnable task) implements Runnable {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            task.run();
            long endTime = System.currentTimeMillis();
            executionTimes[id] = endTime - startTime;
        }
    }
}
