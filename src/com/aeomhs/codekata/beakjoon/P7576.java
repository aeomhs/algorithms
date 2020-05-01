package com.aeomhs.codekata.beakjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;

public class P7576 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String[] row = br.readLine().split(" ");
            int M = Integer.parseInt(row[0]);
            int N = Integer.parseInt(row[1]);

            int[][] box = new int[N][M];

            for (int i = 0; i < N; i++) {
                row = br.readLine().split(" ");
                for (int j = 0; j < M; j++) {
                    box[i][j] = Integer.parseInt(row[j]);
                }
            }

            System.out.println(solution(box, N, M));

        }
    }

    private static int solution(int[][] box, int N, int M) {

        LinkedList<Point> queue = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (box[i][j] == 1)
                    queue.addFirst(new Point(j, i));
            }
        }

        int day = 0;
        int count = queue.size();
        while (!queue.isEmpty()) {
            if (count == 0) {
                count = queue.size();
                day++;
            }
            Collection<Point> spreadPoints = queue.removeLast().spread(box, N, M);
            for (Point point : spreadPoints) {
                box[point.y][point.x] = 1;
                queue.addFirst(point);
            }
            count--;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (box[i][j] == 0)
                    return -1;
            }
        }

        return day;
    }

    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Collection<Point> spread(final int[][] box, final int N, final int M) {
            boolean endOfLeft = x == 0;
            boolean endOfRight = x == M-1;
            boolean isTop = y == 0;
            boolean isBottom = y == N-1;

            LinkedList<Point> spreadPoints = new LinkedList<>();

            if (!endOfLeft && box[y][x-1] != -1 && box[y][x-1] != 1)
                spreadPoints.add(new Point(x-1, y));
            if (!endOfRight && box[y][x+1] != -1 && box[y][x+1] != 1)
                spreadPoints.add(new Point(x+1, y));
            if (!isTop && box[y-1][x] != -1 && box[y-1][x] != 1)
                spreadPoints.add(new Point(x, y-1));
            if (!isBottom && box[y+1][x] != -1 && box[y+1][x] != 1)
                spreadPoints.add(new Point(x, y+1));

            return spreadPoints;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
