package Utility;

import java.util.function.Supplier;


public final class MetricsLogger {
    public record MeasureResult<T> (String label, T result, double timeMs, double memoryKb) {}

    public final long start;
    public final Runtime runtime;

    public MetricsLogger() {
        start = System.nanoTime();
        runtime = Runtime.getRuntime();
        runtime.gc();
    }

    public void logTime() {
        long end = System.nanoTime();
        double timeElapsed = (end - start) / 1_000_000.0;
        System.out.printf("Time Elapsed: %.4fms%n", timeElapsed);
    }

    public void logMemory() {
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        double memoryUsed = (totalMemory - freeMemory) / (1024.0 * 1024.0);
        System.out.printf("Memory Used: %.4fMB%n", memoryUsed);
    }

    public void logAll() {
        logTime();
        logMemory();
    }

    public static void measure(String label, Runnable task) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();

        long totalMemory = runtime.totalMemory();
        long start = System.nanoTime();
        task.run();
        long end = System.nanoTime();
        long freeMemory = runtime.freeMemory();


        double timeElapsed = (end - start) / 1_000_000.0;
        double memoryUsed = (totalMemory - freeMemory) / 1024.0;

        // TODO: Make it auto MB or KB or B based on the free memory size;

        System.out.printf("%s Time Elapsed: %.4fms, Memory Used: %.4fKB%n",label, timeElapsed, memoryUsed);
    }

    public static <T> MeasureResult<T> measure(String label, Supplier<T> task) {
        return measure(label, task, false);
    }

    public static <T> MeasureResult<T> measure(String label, Supplier<T> task, boolean debug) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        long start = System.nanoTime();
        T res = task.get();
        long end = System.nanoTime();
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();

        double timeElapsed = (end - start) / 1_000_000.0;
        double memoryUsed = (usedMemoryAfter - usedMemoryBefore) /  1024.0;

        // TODO: Make it auto MB or KB or B based on the free memory size (wrote the method on paper);

        if (debug) {
            System.out.printf("%s Time Elapsed: %.4fms, Memory Used: %.4fKB%n", label, timeElapsed, memoryUsed);
        }
        // Value returned by the lambda
        return new MeasureResult<>(label, res, timeElapsed, memoryUsed);
    }

    // TODO: MAJOR!!! The instance-based logger (logTime, logMemory) isn’t thread-safe if reused between threads, but that’s expected.
}
