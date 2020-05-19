package com.aeomhs.codekata.beakjoon;

import java.io.*;

public class P1018 {

    private static final String START_W = "WBWBWBWB";

    private static final String START_B = "BWBWBWBW";

    private static final int LENGTH = 8;

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            String[] NM = br.readLine().split(" ");
            int N = Integer.parseInt(NM[0]);
            int M = Integer.parseInt(NM[1]);

            char[][] board = new char[N][M];

            for (int i = 0; i < N; i++) {
                String buffer = br.readLine();
                for (int j = 0; j < M; j++) {
                    board[i][j] = buffer.charAt(j);
                }
            }
            int min = Integer.MAX_VALUE;

            for (int i = 0; i < N-LENGTH+1; i++) {
                for (int j = 0; j < M-LENGTH+1; j++) {
                    int count = Math.min(countRecoloring(board, i, j, true),
                                         countRecoloring(board, i, j, false));
                    if (count < min)
                        min = count;
                }
            }

            bw.write(min + "\n");
            bw.flush();
        }
    }

    private static int countRecoloring(char[][] board, int row, int col, boolean flag) {
        int count = 0;

        for (int i = row; i < row+LENGTH; i++) {
            for (int j = col, index = 0; j < col+LENGTH; j++, index++) {
                if (flag && (board[i][j] != START_B.charAt(index))) {
                    count++;
                } else if (!flag && (board[i][j] != START_W.charAt(index))) {
                    count++;
                }
            }

            flag = !flag;
        }

        return count;
    }
}
