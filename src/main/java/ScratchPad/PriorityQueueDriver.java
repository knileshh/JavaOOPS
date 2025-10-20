package ScratchPad;

import java.util.PriorityQueue;

public class PriorityQueueDriver {
    public static void main(String[] args) {
        System.out.println("Priority Queue Driver Class Initialised");

        // What is priority queue?
        // A queue where we can reorder the elements based on the priority
        // Element with smallest or largest priority comes first

        // In java the ordering is min Heap by default meaning that the smallest element get's out first
        // Uses binary heap behind the scenes

        // Now, if you don't want to sort by min, you want max, then you can pass a custom comparator
        // it's just rule that is used to sort the elements.

        // We're still comparing only two elements
        // Comparator think of it as just a psudo compare function that java
        // uses, like if  < 0 a before b, 0, a and b equal, > 0, a after b, it's compares
        // multiple times internally.
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[1] - a[1]);

        // To avoid overflow, we should do Comparator.comparingInt(arr -> arr[i]);
        // or (a, b) -> Integer.compare(a[1], b[1]);

        maxHeap.add(new int[]{1, 30});
        maxHeap.add(new int[]{2, 10});
        maxHeap.add(new int[]{3, 20});

        while (!maxHeap.isEmpty()) {
            int[] task = maxHeap.poll();
            System.out.println("Task: " + task[0] + " Priority: " + task[1]);
        }
    }
}
