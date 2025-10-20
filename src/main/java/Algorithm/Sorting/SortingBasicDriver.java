package Algorithm.Sorting;

import java.util.Arrays;

public class SortingBasicDriver {
    public static void main(String[] args) {
        System.out.println("Bubble sort driver class initialized");
        BubbleSort bs = new BubbleSort();
        bs.sort();
    }
}

class BubbleSort {
    int[] sampleNums = {2, 5, 22, 55, 99, 8, 88, 1, 0};
//    int[] sampleNums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    // Doesn't handle duplicates
    void sort() {
        // Print the array once to console.
        System.out.println(Arrays.toString(sampleNums));

        for (int i = 0; i < sampleNums.length; i++) {
            for (int j = i; j < sampleNums.length; j++) {
                if (sampleNums[i] > sampleNums[j]) {
                    int temp = sampleNums[i];
                    sampleNums[i] = sampleNums[j];
                    sampleNums[j] = temp;
                }
            }
        }

        // Results after sorting
        System.out.println(Arrays.toString(sampleNums));
    }
}
