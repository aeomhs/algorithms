package com.aeomhs.util;

import java.util.Deque;
import java.util.LinkedList;

// Robert Sedgewick. Algorithms. 4th Edition. Chapter 4. Graph
public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public DepthFirstPaths(Graph G, int s) {
        marked = new boolean[G.getV()];
        edgeTo = new int[G.getV()];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Deque<Integer> stackPath = new LinkedList<>();
        for (int x = v; x != s; x = edgeTo[x])
            stackPath.addFirst(x);
        stackPath.addFirst(s);
        return stackPath;
    }
}

class DepthFirstPathsTest {
    public static void main(String[] args) {
        Graph graph = new Graph(10);
        graph.addEdge(0 , 1);
        graph.addEdge(0 , 3);
        graph.addEdge(0 , 5);
        graph.addEdge(0 , 7);
        graph.addEdge(0 , 9);
        graph.addEdge(2 , 3);
        graph.addEdge(4 , 5);
        graph.addEdge(6 , 7);
        graph.addEdge(8 , 9);

        DepthFirstPaths depthFirstPaths = new DepthFirstPaths(graph, 0);

        for (int x : depthFirstPaths.pathTo(4)) {
            System.out.print(x + "-");
        }
        System.out.println();

    }
}
