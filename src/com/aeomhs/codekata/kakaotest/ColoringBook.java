package com.aeomhs.codekata.kakaotest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class ColoringBookTest {

    public int[] solution(int m, int n, int[][] picture) {

        ColoringBook coloringBook = new ColoringBook(m, n, picture);

        return new int[] {
                coloringBook.count(), coloringBook.maxSize()
        };
    }

    @Test
    public void coloringBookTest() {
        int m, n;
        int[][] picture;
        int[] result, expected;

        ColoringBookTest test = new ColoringBookTest();
        m = 6;
        n = 4;
        picture = new int[][] {
                {1, 1, 1, 0},
                {1, 2, 2, 0},
                {1, 0, 0, 1},
                {0, 0, 0, 1},
                {0, 0, 0, 3},
                {0, 0, 0, 3}
        };
        expected = new int[]{4,5};
        result = test.solution(m, n, picture);
        Assertions.assertArrayEquals(expected, result);

 		m = 13;
 		n = 16;
 		picture = new int[][] {
 			{ 0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0, },
 			{ 0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0, },
 			{ 0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0, },
 			{ 0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0, },
 			{ 0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0, },
 			{ 0,1,1,1,1,2,1,1,1,1,2,1,1,1,1,0, },
 			{ 0,1,1,1,2,1,2,1,1,2,1,2,1,1,1,0, },
 			{ 0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0, },
 			{ 0,1,3,3,3,1,1,1,1,1,1,3,3,3,1,0, },
 			{ 0,1,1,1,1,1,2,1,1,2,1,1,1,1,1,0, },
 			{ 0,0,1,1,1,1,1,2,2,1,1,1,1,1,0,0, },
 			{ 0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0, },
 			{ 0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0, },
 		};
 		expected = new int[]{12, 120};
 		result = test.solution(m, n, picture);
 		Assertions.assertArrayEquals(expected, result);
    }
}

public class ColoringBook {

    private boolean[][] visited;

    private int N;

    private int M;

    private int[][] picture;

    private UnionFind uf;

    ColoringBook(int m, int n, int[][] picture) {
        this.N = n;
        this.M = m;
        this.picture = picture;
        visited = new boolean[m][n];

        uf = new UnionFind(m*n);

        for (int i = 0; i < picture.length; i++) {
            for (int j = 0; j < picture[0].length; j++) {
                if (!visited[i][j])
                    spread(i, j);
            }
        }

        int[] ids = uf.getIds();
        for (int i = 0; i < M; i++){
            for (int j = 0; j < N; j++){
                System.out.print(String.format("%4d ", ids[i*n+j]));
            }
            System.out.println();
        }
    }

    private void spread(int i, int j) {

        visited[i][j] = true;

        if (picture[i][j] == 0) {
            uf.unionBlank(i*N+j);
            return;
        }

        boolean isEdgeOfTop   = i == 0;
        boolean isEdgeOfBot   = i == M-1;
        boolean isEdgeOfLeft  = j == 0;
        boolean isEdgeOfRight = j == N-1;

        if (!isEdgeOfTop && !visited[i-1][j] && picture[i][j] == picture[i-1][j]) {
            uf.union((i*N)+j, ((i-1)*N)+j);
            spread(i-1, j);
        }

        if (!isEdgeOfBot && !visited[i+1][j] && picture[i][j] == picture[i+1][j]) {
            uf.union((i*N)+j, ((i+1)*N)+j);
            spread(i+1, j);
        }

        if (!isEdgeOfLeft && !visited[i][j-1] && picture[i][j] == picture[i][j-1]) {
            uf.union((i*N)+j, ((i)*N)+(j-1));
            spread(i, j-1);
        }

        if (!isEdgeOfRight && !visited[i][j+1] && picture[i][j] == picture[i][j+1]) {
            uf.union((i*N)+j, ((i)*N)+(j+1));
            spread(i, j+1);
        }
    }

    public int count() {
        return uf.getCount();
    }

    public int maxSize() {
        return uf.getMaxSize();
    }
}


class UnionFind {

    private int[] id;

    private int[] sz;

    private int count;

    UnionFind(int n) {
        id = new int[n];
        sz = new int[n];

        for (int i = 0; i < id.length; i++)
            id[i] = i;

        Arrays.fill(sz, 1);

        count = n;
    }

    public int find(int p) {
        while (p != id[p])
            p = id[p];
        return p;
    }

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);

        if (i == j)
            return;

        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        }
        else {
            id[j] = i;
            sz[i] += sz[j];
        }

        count--;
    }

    public void unionBlank(int p) {
        id[p] = -1;
        sz[p] = 0;
        count--;
    }

    public int getCount() {
        return count;
    }

    public int getMaxSize() {
        int maxSize = 0;
        for (int size : sz) {
            if (size > maxSize)
                maxSize = size;
        }
        return maxSize;
    }

    public int[] getIds() {
        return id;
    }
}