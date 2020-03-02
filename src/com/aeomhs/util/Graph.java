package com.aeomhs.util;

import java.util.Deque;
import java.util.LinkedList;

// Robert Sedgewick. Algorithms. 4th Edition. Chapter 4. Graph
public class Graph {
    private final int V;            // 정점 개수
    private int E;                  // 간선 개수
    private Deque<Integer>[] adj;   // 각 정점별 인접 리스트

    public Graph(int V) {
        this.V = V;
        this.E = E;
        adj = new Deque[V];
        for (int v = 0; v < V; v++)
            adj[v] = new LinkedList<>();
    }

    public int getV() {
        return this.V;
    }

    public int getE() {
        return this.E;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
}

class GraphTest {
    public static void main(String[] args) {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(4, 3);

        graph.adj(0).forEach(linked -> System.out.print(linked + ", "));
        System.out.println();
        graph.adj(1).forEach(linked -> System.out.print(linked + ", "));
        System.out.println();
        graph.adj(2).forEach(linked -> System.out.print(linked + ", "));
        System.out.println();
        graph.adj(3).forEach(linked -> System.out.print(linked + ", "));
        System.out.println();
        graph.adj(4).forEach(linked -> System.out.print(linked + ", "));
        System.out.println();
    }
}
