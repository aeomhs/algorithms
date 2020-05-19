package com.aeomhs.codekata.beakjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class P2630 {

    private static final int WHITE_LABEL = 0;
    private static final int BLUE_LABEL = 1;

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String buffer = br.readLine();

            int N = Integer.parseInt(buffer);
            int[][] map = new int[N][N];

            String[] tokens;
            for (int row = 0; row < N; row++) {
                tokens = br.readLine().split(" ");
                for (int i = 0; i < N; i++) {
                    map[row][i] = Integer.parseInt(tokens[i]);
                }
            }

            HashMap<Integer, Integer> counter = new HashMap<>();
            counter.put(WHITE_LABEL, 0);
            counter.put(BLUE_LABEL, 0);
            divideAndConquer(map, N/2, 0, 0, counter);

            System.out.println(counter.get(WHITE_LABEL));
            System.out.println(counter.get(BLUE_LABEL));
        }
    }

    private static void divideAndConquer(int[][] map, int N, int row, int col, Map<Integer, Integer> counter) {

        if (N == 0) {
            return;
        }

        int[][] leftTop = new int[N][N];
        boolean isLTWhite = map[row][col] == WHITE_LABEL;
        boolean isLTSquare = true;

        int[][] leftBot = new int[N][N];
        boolean isLBWhite = map[row+N][col] == WHITE_LABEL;
        boolean isLBSquare = true;

        int[][] rightTop = new int[N][N];
        boolean isRTWhite = map[row][col+N] == WHITE_LABEL;
        boolean isRTSquare = true;

        int[][] rightBot = new int[N][N];
        boolean isRBWhite = map[row+N][col+N] == WHITE_LABEL;
        boolean isRBSquare = true;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                leftTop[i][j] = map[row+i][col+j];
                if (isLTSquare && ((leftTop[i][j] == 0) != isLTWhite))
                    isLTSquare = false;
                leftBot[i][j] = map[row+i+N][col+j];
                if (isLBSquare && ((leftBot[i][j] == 0) != isLBWhite))
                    isLBSquare = false;
                rightTop[i][j] = map[row+i][col+j+N];
                if (isRTSquare && ((rightTop[i][j] == 0) != isRTWhite))
                    isRTSquare = false;
                rightBot[i][j] = map[row+i+N][col+j+N];
                if (isRBSquare && ((rightBot[i][j] == 0) != isRBWhite))
                    isRBSquare = false;
            }
        }

        if ((isLTSquare && isLBSquare && isRTSquare && isRBSquare) &&
            (isLTWhite == isLBWhite) && (isRTWhite == isRBWhite) && (isLTWhite == isRTWhite)) {
            System.out.println("Size: " + N + "("+row+","+col+")");
            if (isLTWhite)
                counter.put(WHITE_LABEL, counter.get(WHITE_LABEL) + 1);
            else
                counter.put(BLUE_LABEL, counter.get(BLUE_LABEL) + 1);

            return;
        }

        if (isLTSquare) {
            if (isLTWhite)
                counter.put(WHITE_LABEL, counter.get(WHITE_LABEL) + 1);
            else
                counter.put(BLUE_LABEL, counter.get(BLUE_LABEL) + 1);
        } else {
            divideAndConquer(map, N/2, row, col, counter);
        }

        if (isLBSquare) {
            if (isLBWhite)
                counter.put(WHITE_LABEL, counter.get(WHITE_LABEL) + 1);
            else
                counter.put(BLUE_LABEL, counter.get(BLUE_LABEL) + 1);
        } else {
            divideAndConquer(map, N/2, row+N, col, counter);
        }

        if (isRTSquare) {
            if (isRTWhite)
                counter.put(WHITE_LABEL, counter.get(WHITE_LABEL) + 1);
            else
                counter.put(BLUE_LABEL, counter.get(BLUE_LABEL) + 1);
        } else {
            divideAndConquer(map, N/2, row, col+N, counter);
        }

        if (isRBSquare) {
            if (isRBWhite)
                counter.put(WHITE_LABEL, counter.get(WHITE_LABEL) + 1);
            else
                counter.put(BLUE_LABEL, counter.get(BLUE_LABEL) + 1);
        } else {
            divideAndConquer(map, N/2, row+N, col+N, counter);
        }
    }
}
