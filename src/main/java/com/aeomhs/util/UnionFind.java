package com.aeomhs.util;

import java.util.Arrays;

/**
 * Robert Sedgewick. Algorithms. 4th Edition. Chapter 1. Union-Find
 */
public abstract class UnionFind {

    protected int[] id;

    protected int count;

    public UnionFind(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * p가 속한 컴포넌트의 id를 return
     */
    public abstract int find(int p);

    /**
     * p와 q를 같은 컴포넌트로 귀속시킨다.
     */
    public abstract void union(int p, int q);

    @Override
    public String toString() {
        return "UnionFind{" +
                "id=" + Arrays.toString(id) +
                ", count=" + count +
                '}';
    }
}
