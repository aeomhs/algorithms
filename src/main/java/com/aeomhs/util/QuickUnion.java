package com.aeomhs.util;

public class QuickUnion extends UnionFind {

    public QuickUnion(int N) {
        super(N);
    }

    @Override
    public int find(int p) {
        while (id[p] != p)
            p = id[p];

        return p;
    }

    @Override
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);

        if (i == j)
            return;

        id[i] = j;

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
        QuickUnion quickUnion = new QuickUnion(N);
        for (int[] link : linked)
            quickUnion.union(link[0], link[1]);

        System.out.println(quickUnion);
    }
}
