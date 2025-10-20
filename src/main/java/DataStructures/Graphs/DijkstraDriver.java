package DataStructures.Graphs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class DijkstraDriver {
    public static void main(String[] args) {
        System.out.println("DijkstraDriver initialised");
        Map<Integer, List<Pair<Integer, Integer>>> adj = new HashMap<>();

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    }

    // implement addMethod

    // implement printGraphMethod

    // use of generics?
    static class Pair<K, V> {
        int vertex;
        int weight;

        Pair(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }
}
