package ScratchPad;

import java.util.*;

public class GraphDemoAdjSet {
    public static void main(String[] args) {
        Set<Edge> adj = new HashSet<>();
        List<List<Edge>> adjList = new ArrayList<>();
        int V = 5; // max edges;

        adj.add(new Edge(1, 2));
        adj.add(new Edge(4, 2));
        adj.add(new Edge(2, 3));

        // Init sublist
        for (int i = 0; i < V; i++) {
            adjList.add(new ArrayList<>());
        }

        // adding edges
        addEdge(adjList, new Edge(2, 0), 1);
        addEdge(adjList, new Edge(3, 0), 1);


//        printGraphSet(adj);
        printGraphList(adjList);
    }

    static void addEdge(List<List<Edge>> adjList, Edge e, int i) {
        // we're use auto index of N = adjList.size();
        if(!(adjList.get(i).contains(e))) adjList.get(i).add(e);
    }

    static void printGraphSet(Set<Edge> adj) {
        for (Edge e: adj) {
            System.out.println("v= " + e.vertex  + " w= " + e.weight);
        }
    }

    static void printGraphList(List<List<Edge>> adjList) {
        for (int i = 0; i < adjList.size(); i++) {
            System.out.print(i + ": ");
            for (Edge e: adjList.get(i)) {
                System.out.print("(" + e.vertex + "," + e.weight + ") ");
            }
            System.out.println();
        }
    }

    static class Edge {
        int weight;
        int vertex;

        Edge (int v, int w) {
            this.vertex = v;
            this.weight = w;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Edge)) return false;

            Edge e = (Edge) o;
            return this.vertex == e.vertex && this.weight == e.weight;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.vertex, this.weight);
        }
    }
}
