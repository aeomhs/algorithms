package com.aeomhs.util;

import java.util.Deque;
import java.util.LinkedList;

// Robert Sedgewick. Algorithms. 4th Edition. Chapter 4. Graph
public class BreadthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public BreadthFirstPaths(Graph G, int s) {
        marked = new boolean[G.getV()];
        edgeTo = new int[G.getV()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        Deque<Integer> queue = new LinkedList<>();
        marked[s] = true;
        queue.addLast(s);
        while (!queue.isEmpty()) {
            int v = queue.pollFirst();
            for (int w : G.adj(v)){
                if (!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    queue.addLast(w);
                }
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

class BreadthFirstPathsTest {
    public static void main(String[] args) {
        Graph graph = new Graph(10);
        graph.addEdge(0 , 1);
        graph.addEdge(0 , 2);
        graph.addEdge(0 , 3);
        graph.addEdge(2 , 5);
        graph.addEdge(3 , 6);
        graph.addEdge(4 , 5);
        graph.addEdge(6 , 7);
        graph.addEdge(7 , 1);
        graph.addEdge(8 , 7);
        graph.addEdge(8 , 9);

        BreadthFirstPaths breadthFirstPaths = new BreadthFirstPaths(graph, 0);
        System.out.println("0 to 1 : " + breadthFirstPaths.pathTo(1));
        System.out.println("0 to 2 : " + breadthFirstPaths.pathTo(2));
        System.out.println("0 to 3 : " + breadthFirstPaths.pathTo(3));
        System.out.println("0 to 4 : " + breadthFirstPaths.pathTo(4));
        System.out.println("0 to 5 : " + breadthFirstPaths.pathTo(5));
        System.out.println("0 to 6 : " + breadthFirstPaths.pathTo(6));
        System.out.println("0 to 7 : " + breadthFirstPaths.pathTo(7));
        System.out.println("0 to 8 : " + breadthFirstPaths.pathTo(8));
        System.out.println("0 to 9 : " + breadthFirstPaths.pathTo(9));
    }
}
