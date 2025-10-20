package ScratchPad;

import java.util.ArrayList;
import java.util.List;

public class GraphDemoAdj {
    public static void main(String[] args) {
        System.out.println("GraphDemo01 initialised");

        List<List<Integer>> adj = new ArrayList<>();

        int V = 5; // max edges

        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        addEdge(adj, 2, 3);
        addEdge(adj, 3, 4);
        addEdge(adj, 0, 2);
        addEdge(adj, 1, 2);
        addEdge(adj, 4, 2);

        printGraph(adj);
    }

    static void addEdge(List<List<Integer>> adj, int u, int v) {
        if (!(adj.get(u).contains(v))) adj.get(u).add(v);
        if (!(adj.get(v).contains(u))) adj.get(v).add(u);
    }

    static void printGraph(List<List<Integer>> adj) {
        for (int i = 0; i < adj.size(); i++) {
            System.out.print(i + ": ");
            for (int x: adj.get(i)) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }
}
