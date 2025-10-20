package DataStructures.Graphs;

import java.util.ArrayList;
import java.util.List;

public class GraphDriver {
    public static void main(String[] args) {
        System.out.println("Graph Driver class initialised");

        int V = 5;

        List<List<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        addEdge(adj, 2, 4);
        addEdge(adj, 4, 1);
        addEdge(adj, 4, 0);
        addEdge(adj, 1, 1);
        addEdge(adj, 2, 4);

        printGraph(adj);
    }

    static void addEdge(List<List<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    static void printGraph(List<List<Integer>> adj) {
        for (int i = 0; i < adj.size(); i++) {
            System.out.print(i + ": ");
            for (int x : adj.get(i)) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }
}

//// Create separation of logic
//class Graph {
//
//    // A graph stores the vertices of their neighbors
//    List<Vertex> vertices;
//
//    // A nested static class because vertex doesn't exist outside of graph
//    static class Vertex {
//
//        // I'm storing only 32 bit integer values right now.
//        int value;
//
//        // Another field to store a list of Vertices, but why? A vertex shouldn't store s list of vertices or should it?
//        // Well 1 : [], here 1 is a vertex and therefore, it has a list, so by the definition the implementation can
//        // have a list in vertex right?
////        List<Vertex> neighbours;
//        // A list cannot contain unknow types, therefore we need to create a List for integers and not vertex.
//        List<List<Integer>> neighbours;
//
//        Vertex(int value) {
//            this.value = value;
//        }
//    }
//
//    void createSampleGraph() {
//        int[] nums = {2, 33, 4, 55, 6, 77, 8, 99};
//
//        for (int x: nums) {
//
//        }
//    }
//}
