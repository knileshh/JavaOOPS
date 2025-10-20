package DataStructures.Graphs;

import java.util.*;

public class DijkstraDriver2 {
    public static void main(String[] args) {
        Dijkstra2 dijkstra = new Dijkstra2();
        dijkstra.buildGraph();
        dijkstra.printGraph();
        dijkstra.run(0);
    }
}

class Dijkstra2 {
    Map<Integer, List<int[]>> graph = new HashMap<>();

    public void buildGraph() {
        int n = 5; // max nodes

        // assign key as i's and ArrayList<>() as values
        for (int i = 0; i < n; i ++) {
            graph.put(i, new ArrayList<>());
        }

        addEdge(0, 1, 2);
        addEdge(0, 2, 4);
        addEdge(1, 2, 1);
        addEdge(1, 3, 7);
        addEdge(2, 4, 3);
        addEdge(3, 4, 1);
    }

    private void addEdge(int u, int v, int w) {

        // get (key) -> ArrayList<>(), add int[] array to array List
        // 1: [[v,w], [2, 4]]

        graph.get(u).add(new int[]{v, w});
    }

    public void printGraph() {
        System.out.println("Adjacency List: ");
        for (int node: graph.keySet()) {
            System.out.print(node + " -> ");
            for (int[] edge: graph.get(node)) {
                System.out.print("(" + edge[0] + ", " + edge[1] + ") ");
            }
            System.out.println();
        }
    }

    public void run(int source) {
        int n = graph.size();
        int[] dist = new int[n]; // Arr of size n (vertex)
        int[] parent = new int[n]; // same (parent vertex)
        boolean[] visited = new boolean[n]; // same vertex

        // pq, auto move smaller elem on top
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        // Initialize distances
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        dist[source] = 0; // source is param of method, set src as 0 rest, MAX_VALUE

        pq.offer(new int[] {0, source});

        while (!pq.isEmpty()) {
            int[] current = pq.poll(); // get smallest from pq, but why in array[]
            int d = current[0];
            int u = current[1];

            if (visited[u]) continue;
            visited[u] = true;

            for (int[] edge : graph.get(u)) {
                int v = edge[0];
                int w = edge[1];

                // Relaxation
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                    pq.offer(new int[]{dist[v], v});
                }
            }
        }

        System.out.println("Shortest distance from source " + source + ":");
        for (int i = 0; i < n; i++) {
            System.out.println("Node " + i + "-> " + dist[i]);
        }
    }
}