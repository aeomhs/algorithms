package com.aeomhs.codekata.beakjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class P2178 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String buffer = br.readLine();
            String[] tokens = buffer.split(" ");
            int N = Integer.parseInt(tokens[0]);
            int M = Integer.parseInt(tokens[1]);
            int[][] map = new int[N][M];
            for (int row = 0; row < N; row++) {
                buffer = br.readLine();
                for (int i = 0; i < M; i++) {
                    map[row][i] = buffer.charAt(i) - '0';
                }
            }

            System.out.println(bfs(map, N, M));
        }
    }

    static int bfs(int[][] map, int N, int M) {
        boolean[][] visit = new boolean[N][M];

        LinkedList<int[]> queue = new LinkedList<>();
        queue.offerFirst(new int[]{0,0});
        visit[0][0] = true;

        while (!queue.isEmpty()) {
            int[] v = queue.pollLast();

            if (v[0] == N-1 && v[1] == M-1)
                break;

            for (int[] w : getAdj(map, v, N, M, visit)) {
                if (!visit[w[0]][w[1]]) {
                    queue.offerFirst(w);
                    visit[w[0]][w[1]] = true;
                    map[w[0]][w[1]] = map[v[0]][v[1]] + 1;
                }
            }
        }

        return map[N-1][M-1];
    }

    static List<int[]> getAdj(int[][] map, int[] point, int N, int M, boolean[][] visit) {
        int row = point[0];
        int col = point[1];

        boolean isEndOfLeft = col == 0;
        boolean isEndOfRight = col == M-1;
        boolean isEndOfTop = row == 0;
        boolean isEndOfBottom = row == N-1;

        LinkedList<int[]> adjList = new LinkedList<>();

        if (!isEndOfLeft && !visit[row][col-1] && map[row][col-1] > 0) {
            adjList.add(new int[]{row, col-1});
        }
        if (!isEndOfRight && !visit[row][col+1] && map[row][col+1] > 0) {
            adjList.add(new int[]{row, col+1});
        }
        if (!isEndOfTop && !visit[row-1][col] && map[row-1][col] > 0) {
            adjList.add(new int[]{row-1, col});
        }
        if (!isEndOfBottom && !visit[row+1][col] && map[row+1][col] > 0) {
            adjList.add(new int[]{row+1, col});
        }

        return adjList;
    }

    static int bfsMemoryFailed(boolean[][] map, int N, int M) {
        boolean[][] visit = new boolean[N][M];

        Point root = new Point(0, 0);
        root.minOrder = 1;

        LinkedList<Point> queue = new LinkedList<>();
        queue.offerFirst(root);

        Point target = null;
        while (!queue.isEmpty()) {
            Point v = queue.pollLast();
            visit[v.row][v.col] = true;
            if (v.row == N-1 && v.col == M-1) {
                target = v;
                break;
            }
            Point[] adjPoints = v.getAdjPoints(map, N, M);
            for (Point w : adjPoints) {
                if (!visit[w.row][w.col]) {
                    queue.offerFirst(w);
                    w.minOrder = v.minOrder + 1;
                }
            }
        }

        return target == null ? -1 : target.minOrder;
    }

    static class Point {
        int row, col;
        int minOrder;

        Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        Point[] getAdjPoints(boolean[][] map, int N, int M) {
            boolean isEndOfLeft = col == 0;
            boolean isEndOfRight = col == M-1;
            boolean isEndOfTop = row == 0;
            boolean isEndOfBottom = row == N-1;

            LinkedList<Point> adjList = new LinkedList<>();

            if (!isEndOfLeft && map[row][col-1]) {
                adjList.add(new Point(row, col-1));
            }
            if (!isEndOfRight && map[row][col+1]) {
                adjList.add(new Point(row, col+1));
            }
            if (!isEndOfTop && map[row-1][col]) {
                adjList.add(new Point(row-1, col));
            }
            if (!isEndOfBottom && map[row+1][col]) {
                adjList.add(new Point(row+1, col));
            }

            Point[] adjPoints = new Point[adjList.size()];
            for (int i = 0; i < adjList.size(); i++)
                adjPoints[i] = adjList.get(i);

            return adjPoints;
        }
    }
}
