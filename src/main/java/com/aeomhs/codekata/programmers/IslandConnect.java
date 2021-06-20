package com.aeomhs.codekata.programmers;

import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class IslandConnect {
    public static void main(String[] args) {
        IslandConnect test = new IslandConnect();
        int n = 4;
        int[][] costs = new int[][] {
                {0,1,1},
                {0,2,2},
                {1,2,5},
                {1,3,1},
                {2,3,8}
        };
        int expected = 4;
        int result = test.solution(n, costs);
        System.out.println(expected + ", " + result);
    }

    public int solution(int n, int[][] costs) {
        Graph graph = new Graph(n);

        for (int[] edge : costs) {
            graph.addEdge(new Edge(edge[0], edge[1], edge[2]));
        }

        MST mst = new MST(n, graph);

        return mst.totalCost();
    }

}

class MST {

    private boolean[] marked;

    private PriorityQueue<Edge> pq;

    private LinkedList<Edge> mst;

    MST(int n, Graph graph) {
        marked = new boolean[n];

        mst = new LinkedList<>();

        pq = new PriorityQueue<>();

        visit(graph, 0);
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int v = edge.v();
            int w = edge.w(v);

            if (marked[v] && marked[w])
                continue;
            mst.add(edge);
            if (!marked[v])
                visit(graph, v);
            if (!marked[w])
                visit(graph, w);
        }
    }

    private void visit(Graph graph, int v) {
        marked[v] = true;

        for (Edge edge : graph.adj(v)) {
            if (!marked[edge.w(v)])
                pq.add(edge);
        }
    }

    public int totalCost() {
        int costSum = 0;
        for (Edge edge : mst) {
            costSum += edge.cost();
        }
        return costSum;
    }
}

class Graph {
    private int V;

    private int E;

    private Deque<Edge>[] adj;

    Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = new Deque[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(Edge edge) {
        int v = edge.v();
        int w = edge.w(v);
        adj[v].add(edge);
        adj[w].add(edge);
        E++;
    }

    public Deque<Edge> adj(int v) {
        return adj[v];
    }
}

class Edge implements Comparable<Edge> {
    private int v;
    private int w;
    private int cost;

    Edge(int v, int w, int cost) {
        this.v = v;
        this.w = w;
        this.cost = cost;
    }

    public int cost() {
        return cost;
    }

    public int v() {
        return v;
    }

    public int w(int v) {
        return v == w ? this.v : w;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(this.cost, o.cost);
    }
}