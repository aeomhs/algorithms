package com.aeomhs.util;

// Robert Sedgewick. Algorithms. 4th Edition. Chapter 4. Graph
public class DepthFirstSearch {
    private boolean[] marked;
    private int count;

    public DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.getV()];
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        count++;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }

    public boolean marked(int w) {
        return marked[w];
    }

    public int getCount() {
        return count;
    }
}