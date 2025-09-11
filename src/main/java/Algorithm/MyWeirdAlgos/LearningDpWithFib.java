package Algorithm.MyWeirdAlgos;

import java.io.*;
import java.util.*;
import java.time.*;
import Utility.MetricsLogger;
import Utility.MetricsLogger.MeasureResult;
import java.math.*;

public class LearningDpWithFib {
    public static void main(String[] args) {
        System.out.println("WeirdSystems Fib operational!");

        HashMap<Integer, BigInteger> memo = new HashMap<>();

        try {
            long[] arr = new long[32];
            Arrays.fill(arr, -1);

            List<MeasureResult<?>> results = new ArrayList<>();
            results.add(MetricsLogger.measure("DP FibMemo", () -> fib((30), memo)));
            results.add(MetricsLogger.measure("DP FibTab", () -> fibTab(30, arr), true));

            // Now using streams API find the min and max for base
            double maxTime = results.stream().mapToDouble(MeasureResult::timeMs).max().orElse(1);
            double maxMemory = results.stream().mapToDouble(MeasureResult::memoryKb).max().orElse(1);

            results.sort(Comparator.comparingDouble(MeasureResult::timeMs));

            System.out.printf("%-15s | %-10s | %-10s | %-10s | %-10s%n", "Algorithm", "Time(ms)", "Time %", "Memory(KB)", "Memory %");

            for (MeasureResult<?> r : results) {
                double timePct = (r.timeMs() / maxTime) * 100;
                double memPct = (r.memoryKb() / maxMemory) * 100;

                System.out.printf("%-15s | %-10.4f | %-9.2f%% | %-10.4f | %-9.2f%%%n", r.label(), r.timeMs(), timePct, r.memoryKb(), memPct);
                writeToFile(r);
            }

        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    // Remember to declare generic types before method return type, and print with %s which calls .toString() on objects
    // because the compiler doesn't exactly know what T is exactly.
    public static <T> void writeToFile(MeasureResult<T> r) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/Algorithm/MyWeirdAlgos/log.log", true));
        bw.write(String.format("%s - Label: '%s' Result: %s, Time: %.4fms, Memory %.4fKB", Instant.now(), r.label(), r.result(), r.timeMs(), r.memoryKb()));
        bw.newLine();
        bw.close();
    }

    public static BigInteger fib(int a, HashMap<Integer, BigInteger> memo) {

        if (memo.containsKey(a)) {
            return memo.get(a);
        }

        if (a <= 1) {
            return new BigInteger(String.valueOf(a));
        }

        BigInteger result = fib(a - 1, memo).add(fib(a - 2, memo));
        memo.put(a, result);
        return result;
    }

    public static long fibTab(int n, long[] arr) {
        if (arr[n] != -1) {
            return arr[n];
        }

        if (n <= 1) {
            return n;
        }

        long result = fibTab(n-1, arr) + fibTab(n-2, arr);
        arr[n] = result;
        return result;
    }
}