package Algorithm;

import java.util.HashMap;

public class FibCheck {
    public static void main(String[] args) {
          MetricsLogger3 logger = new MetricsLogger3();

//        HashMap<Integer, Long> hmap = new HashMap<>();

//        System.out.println(fib(1, new HashMap<>()));
//        System.out.println(fib(10, new HashMap<>()));
//        System.out.println(fib(20, new HashMap<>()));
//        System.out.println(fib(30, new HashMap<>()));
//        System.out.println(fib(40, new HashMap<>()));
//        System.out.println(fib(50, new HashMap<>()));
//        System.out.println(fib(60, new HashMap<>()));
//        System.out.println(fib(70, new HashMap<>()));
//        System.out.println(fib(80, new HashMap<>()));
//        System.out.println(fib(90, new HashMap<>()));
//        System.out.println(fib(100, new HashMap<>()));
//        System.out.println(fib(500, new HashMap<>()));
//        System.out.println(fib(1000, new HashMap<>()));
//        System.out.println(fib(10000, new HashMap<>()));

//        logger.logAll();

        MetricsLogger3.measure("Test1", () -> fib(10000, new HashMap<>()));
        long res = MetricsLogger3.measure("Test 2", () -> fib(4000, new HashMap<>()));
        System.out.println("Log of res: " + res);
    }

    public static long fib(int n, HashMap<Integer, Long> hmap) {

        if (hmap.containsKey(n)) {
            return hmap.get(n);
        }

        if (n <= 1) {
            return n;
        }

        long result = fib(n-1, hmap) + fib(n-2, hmap);
        hmap.put(n, result);

        return hmap.get(n);
    }
}
