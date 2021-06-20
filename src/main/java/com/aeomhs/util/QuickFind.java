package com.aeomhs.util;

/**
 * <h1>Quick Find</h1>
 *
 */
public class QuickFind extends UnionFind {

    public QuickFind(int N) {
        super(N);
    }

    @Override
    public int find(int p) {
        return id[p];
    }

    @Override
    public void union(int p, int q) {
        int pId = find(p);
        int qId = find(q);

        if (pId == qId)
            return;

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pId) {
                id[i] = qId;
            }
        }

        count--;
    }

    public static void main(String[] args) {
        int N = 10;
        int[][] linked = new int[][] {
                {4, 3},
                {3, 8},
                {6, 5},
                {9, 4},
                {2, 1},
                {5, 0},
                {7, 2},
                {6, 1}
        };
        QuickFind quickFind = new QuickFind(N);
        for (int[] link : linked)
            quickFind.union(link[0], link[1]);

        System.out.println(quickFind);
    }
}
