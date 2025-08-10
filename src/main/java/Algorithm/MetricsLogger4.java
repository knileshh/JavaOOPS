package Algorithm;

import java.util.function.Supplier;

public class MetricsLogger4 {
    public final long start;
    public final Runtime runtime;

    public MetricsLogger4() {
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
        double memoryUsed = (totalMemory - freeMemory) / (1024.0 * 1024.0);

        System.out.printf("%s Time Elapsed: %.4fms, Memory Used: %.4fMB%n",label, timeElapsed, memoryUsed);
    }

    public static <T> T measure(String label, Supplier<T> task) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        long start = System.nanoTime();
        T res = task.get();
        long end = System.nanoTime();
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();

        double timeElapsed = (end - start) / 1_000_000.0;
        double memoryUsed = (usedMemoryAfter - usedMemoryBefore) / (1024.0  * 1024.0);

        System.out.printf("%s Time Elapsed: %.4fms, Memory Used: %.4fMB%n", label, timeElapsed, memoryUsed);

        return res;
    }
}
