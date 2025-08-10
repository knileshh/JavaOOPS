package Algorithm;

import java.util.function.Supplier;

public class MetricsLogger2 {
    private final Runtime runtime;
    private final long start;

    public MetricsLogger2() {
        runtime = Runtime.getRuntime();
        runtime.gc();
        start = System.nanoTime();
        System.out.println("\u001B[32mMetricsLogger 2 by https://knileshh.com initialized successfully!\u001B[0m");
    }

    public void logMemory() {
        long memUsed = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("Memory Used: %.2f Mb", (memUsed/(1024.0 * 1024)));
    }

    public void logTime() {
        long end = System.nanoTime();
        System.out.printf("Time elapsed: %.2f", ((end - start)/(1_000_000.0)));
    }

    public void logAll() {
        logMemory();
        logTime();
    }

    public static void measure(String label, Runnable task) {
        long start = System.nanoTime();
        Runtime.getRuntime().gc();
        long totalMemory = Runtime.getRuntime().totalMemory();
        task.run();
        double totalTime = ((System.nanoTime() - start)/1_000_000.0);
        long memoryUsed = totalMemory - Runtime.getRuntime().freeMemory();

        System.out.printf("\n%s Time elapsed: %.2f ms, Memory Used: %.4f MB",
                            label, totalTime, (memoryUsed/(1024*1024.0)));

    }

    public static <T> T measure(String label, Supplier<T> task) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();

        long start = System.nanoTime();
        T result = task.get();
        long end = System.nanoTime();

        long used = runtime.totalMemory() - runtime.freeMemory();

        double timeMs = (end - start)/1_000_000.0;
        double memoryMB = used / (1024.0 * 1024.0);

        System.out.printf("%s : Time Elapsed: %.2f ms, Memory Used: %.2f MB", label, timeMs, memoryMB);
        return result;
    }
}
