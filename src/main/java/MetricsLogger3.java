import java.util.function.Supplier;

public class MetricsLogger3 {
    private final long start;
    private final Runtime runtime;

    public MetricsLogger3() {
        start = System.nanoTime();
        runtime = Runtime.getRuntime();
        runtime.gc();
    }

    public void logTime() {
        long end = System.nanoTime();
        double timeElapsed = (end - start) / 1_000_000.0;
        System.out.printf("Time elapsed: %.2f", timeElapsed);
    }

    public void logMemory() {
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        double memoryUsed = (totalMemory - freeMemory) / (1024.0 * 1024.0);
        System.out.printf("Memory Used: %.2f", memoryUsed);
    }

    public void getAll() {
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

        double timeElapsed = (end - start)/1_000_000.0;
        double memoryUsed = (totalMemory - freeMemory) / (1024.0 * 1024.0);

        System.out.printf(" %s Time Elapsed: %.2f ms Memory used: %.2f", label, timeElapsed, memoryUsed);
    }

    public static <T> T measure(String label, Supplier<T> task) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long totalMemory = runtime.totalMemory();
        long start = System.nanoTime();
        T result = task.get();
        long end = System.nanoTime();
        long freeMemory = runtime.freeMemory();

        double timeElapsed = (end - start) / 1_000_000.0;
        double memoryUsed = (totalMemory - freeMemory) / (1024.0 * 1024.0);

        System.out.printf("\n%s Time Elapsed: %.4f ms Memory Used: %.4f MB", label, timeElapsed, memoryUsed);
        return result;
    }

}
