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
             MeasureResult<BigInteger> result = MetricsLogger.measure("DP Fib", () -> fib((7100), memo));
             writeToFile(result);

        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public static void writeToFile(MeasureResult<BigInteger> result) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/Algorithm/MyWeirdAlgos/log.log", true));
        bw.write(String.format("%s - Result: %d, Time: %.4fms, Memory %.4fKB", Instant.now(), result.result(), result.timeMs(), result.memoryKb()));
        bw.newLine();
        bw.close();

        System.out.println(result.result() + " - w2l " + result.result().toString().length());
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
}