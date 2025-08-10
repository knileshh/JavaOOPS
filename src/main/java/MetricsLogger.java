import java.util.function.Supplier;

public class MetricsLogger {
    private final long startTime;
    private final Runtime runtime;

    public MetricsLogger() {
        this.runtime = Runtime.getRuntime();
        runtime.gc();
        this.startTime = System.nanoTime();
    }

    public void logTime() {
        long endTime = System.nanoTime();
        double elapsedMs = (endTime - startTime) / 1_000_000.0;
        System.out.println("Time taken: " + String.format("%.2f", elapsedMs) + " ms");
    }

    public void logMemory() {
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        double usedMB = usedMemory / (1024 * 1024.0);
        System.out.println("Used Memory: " + String.format("%.2f", usedMB) + " MB");
    }

    public void logAll() {
        logTime();
        logMemory();
    }

    public static void measure(String label, Runnable task) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();

        long start = System.nanoTime();
        task.run();
        long end = System.nanoTime();

        long used = runtime.totalMemory() - runtime.freeMemory();
        double timeMs = (end - start) / 1_000_000.0;
        double memMB = used / (1024.0 * 1024.0);

        System.out.printf("[%s] Time: %.2f ms | Memory: %.2f MB%n", label, timeMs, memMB);
    }

    public static <T> T measure(String label, Supplier<T> task) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();

        long start = System.nanoTime();
        T result = task.get();
        long end = System.nanoTime();

        long used = runtime.totalMemory() - runtime.freeMemory();
        double timeMs = (end - start) / 1_000_000.0;
        double memMB = used / (1024.0 * 1024.0);

        System.out.printf("[%s] Time: %.2f ms | Memory: %.2f MB%n", label, timeMs, memMB);
        return result;
    }
}
