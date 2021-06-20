package com.aeomhs.util;

public class WeightedQuickUnionUF extends QuickUnion {

    private int[] sz;

    public WeightedQuickUnionUF(int N) {
        super(N);

        sz = new int[N];
        for (int i = 0; i < N; i++)
            sz[i] = 1;
    }

    @Override
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);

        if (i == j)
            return;

        if (sz[i] > sz[j]) {
            id[j] = i;
            sz[i] += sz[j];
        }
        else {
            id[i] = j;
            sz[j] += sz[i];
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
        WeightedQuickUnionUF weightedQuickUnionUF = new WeightedQuickUnionUF(N);
        for (int[] link : linked)
            weightedQuickUnionUF.union(link[0], link[1]);

        System.out.println(weightedQuickUnionUF);
    }
}
