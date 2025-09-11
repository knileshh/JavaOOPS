package Algorithm;

import Utility.MetricsLogger;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GridTraveller {
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    public static final String GREEN_BG = "\u001B[42m";


    public static void main(String[] args) {
        System.out.println("Welcome to grid traveller");

        Map<String, Long> resultMap = new LinkedHashMap<>();

        resultMap.put("Grid Recursion", MetricsLogger.measure("Grid Norm: ", () -> gridTraveller(18, 18)).result());
        resultMap.put("Grid Memo", MetricsLogger.measure("Grid Memo: ", () -> gridTravellerMemoized(18, 18, new HashMap<>())).result());

        System.out.println("\n\nResults : ");

        resultMap.forEach(
                (label, value) -> System.out.printf("%s: %s%d%s%n", label, GREEN_BG, value, RESET)
        );
    }

    public static long gridTravellerMemoized(int m, int n, HashMap<String, Long> hmap) {
        String key = m + "," + n;
        if (hmap.containsKey(key)) return hmap.get(key);
        if ( m == 1 && n == 1) return 1;
        if (m == 0 || n == 0)  return 0;

        long res = gridTravellerMemoized(m - 1, n, hmap) + gridTravellerMemoized(m, n - 1, hmap);
        hmap.put(key, res);
        return res;
    }

    public static long gridTraveller(int m, int n) {
        if (m == 1 && n == 1) return 1;
        if (m == 0 || n == 0) return 0;

        return gridTraveller(m - 1, n) + gridTraveller(m, n - 1);
    }
}
