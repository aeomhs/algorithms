package com.aeomhs.codekata.beakjoon;

import java.io.*;

public class P1717 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            String[] buffer = br.readLine().split(" ");
            int N = Integer.parseInt(buffer[0]);
            int M = Integer.parseInt(buffer[1]);

            UnionFind unionFind = new UnionFind(N+1);

            for (int i = 0; i < M; i++) {
                buffer = br.readLine().split(" ");
                if ("0".equals(buffer[0])) {
                    unionFind.union(Integer.parseInt(buffer[1]), Integer.parseInt(buffer[2]));
                } else {
                    bw.write(unionFind.isConnected(Integer.parseInt(buffer[1]), Integer.parseInt(buffer[2])) ? "YES\n" : "NO\n");
                }
            }

            bw.flush();
        }
    }
}

class UnionFind {

    private int[] id;

    private int[] size;

    UnionFind(int N) {
        id = new int[N+1];
        size = new int[N+1];

        for (int i = 0; i < N; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    public boolean isConnected(int v, int w) {
        if (v == w)
            return true;

        return find(v) == find(w);
    }

    // 경로 압축
    public int find(int v) {
        if (v == id[v]) {
            return v;
        }

        return (id[v] = find(id[v]));
    }

    public void union(int v, int w) {
        int p = find(v);
        int q = find(w);

        if (p == q)
            return;

        // weighted
        if (size[p] > size[q]) {
            size[p] += size[q];
            id[q] = id[p];
        }
        else {
            size[p] += size[q];
            id[p] = id[q];
        }
    }
}