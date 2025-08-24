package Algorithm.Templates.dfs;

import java.util.*;

public class DFSug001 {


    public static void main(String[] args) {
        int nodes = 5;
        List<List<Integer>> graph = new ArrayList<>();

        int[][] edges = {{0, 1}, {1, 2}, {3, 4}};

        boolean[] visited = new boolean[nodes];
        Arrays.fill(visited, false);

        createGraph(nodes, edges, graph);
        printGraph(graph);
        dfs(visited, 0, graph);
        printVisited(visited);
    }

    static void createGraph(int nodes, int[][] edges, List<List<Integer>> graph) {

        for (int i = 0; i < nodes; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }
    }

    static void printGraph(List<List<Integer>> graph) {
        int i = 0;
        for (List<Integer> g : graph) {
            System.out.print(i++ + " : ");
            for (Integer e : g) {
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }

//    static void dfs(boolean[] visited, int node, List<List<Integer>> graph) {
//
//        List<Integer> op = new ArrayList<>();
//        List<Integer> ngbr = new ArrayList<>();
//
//        ngbr.add(node);
//
//        while (!ngbr.isEmpty()) {
//            int n = ngbr.remove(0);
//
//            if (visited[n]) {
//                continue;
//            }
//
//            visited[n] = true;
//            op.add(n);
//
//            for (Integer neigh : graph.get(n)) {
//                if (!visited[neigh]) {
//                    ngbr.add(neigh);
//                }
//            }
//        }
//    }

    static void dfs(boolean[] visited, int node, List<List<Integer>> graph) {

        List<Integer> op = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();

        stack.push(node);

        while (!stack.isEmpty()) {

            int n = stack.pop();

            if (visited[n]) {
                continue;
            }

            visited[n] = true;
            op.add(n);

            for (Integer neighbors : graph.get(n)) {
                if (!visited[neighbors]) {
                    stack.push(neighbors);
                }
            }
        }

        System.out.println(op);
    }

    static void printVisited(boolean[] visited) {
        for (int i = 0; i < visited.length; i++) {
            System.out.print(i + " " + visited[i]);
            System.out.println();
        }
    }


}
