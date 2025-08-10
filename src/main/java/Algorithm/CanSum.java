package Algorithm;

import java.util.HashMap;

public class CanSum {
    public static void main(String[] args) {
        if (canSum(4,
                new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20})
        ) {
            System.out.println("Sum is possible");
        } else {
            System.out.println("Sum is not possible");
        }

        MetricsLogger4 m4 = new MetricsLogger4();

        MetricsLogger4.measure("NORM: canSum",
                () -> canSum(450000,
                        new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20})
        );

        MetricsLogger4.measure("MEMO: canSum",
                () -> canSumMemoized(450000,
                        new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20},
                        new HashMap<>())
        );

    }

    public static boolean canSum(int targetSum, int[] arr) {
        if (targetSum == 0) return true;

        if (targetSum < 0) return false;

        for (int x : arr) {
            if (canSum(targetSum - x, arr)) return true;
        }

        return false;
    }

    public static boolean canSumMemoized(int targetSum, int[] arr, HashMap<String, Boolean> hmap) {
        if (hmap.containsKey(String.valueOf(targetSum))) return true;

        if (targetSum == 0) return true;

        if (targetSum < 0) return false;

        for (int x : arr) {
            if (canSumMemoized(targetSum - x, arr, hmap)) {
                hmap.put(String.valueOf(targetSum), true);
                return true;
            }
        }

        hmap.put(String.valueOf(targetSum), false);
        return false;
    }
}
