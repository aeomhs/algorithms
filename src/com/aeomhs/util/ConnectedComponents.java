package com.aeomhs.util;

import java.util.Deque;
import java.util.LinkedList;

// Robert Sedgewick. Algorithms. 4th Edition. Chapter 4. Graph
public class ConnectedComponents {
    private boolean[] marked;
    private int[] id;
    private int count;

    public ConnectedComponents(Graph G) {
        marked = new boolean[G.getV()];
        id = new int[G.getV()];
        for (int s = 0; s < G.getV(); s++) {
            if (!marked[s]) {
                dfs(G, s);
                count++;
            }
        }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }

    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    public int count() {
        return count;
    }
}

class ConnectedComponentsTest {
    public static void main(String[] args) {
        Graph graph = new Graph(10);
        graph.addEdge(0 , 1);
        graph.addEdge(0 , 2);
        graph.addEdge(0 , 3);
        graph.addEdge(2 , 5);
        graph.addEdge(4 , 7);
        graph.addEdge(8 , 7);
        graph.addEdge(8 , 9);

        ConnectedComponents connectedComponents = new ConnectedComponents(graph);
        int componentNum = connectedComponents.count();
        Deque<Integer>[] componentQueue = new Deque[componentNum];
        for (int i = 0; i < componentNum; i++) {
            componentQueue[i] = new LinkedList<>();
        }
        for (int v = 0; v < graph.getV(); v++) {
            componentQueue[connectedComponents.id(v)].addLast(v);
        }
        for (int i = 0; i < componentNum; i++) {
            for (int v : componentQueue[i])
                System.out.print(v + ", ");
            System.out.println();
        }
    }
}
