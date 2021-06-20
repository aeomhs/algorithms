package com.aeomhs.codekata.beakjoon;

import java.io.*;

public class P14501 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int N = Integer.parseInt(br.readLine());
            int[] T = new int[N+1];
            int[] P = new int[N+1];
            int[][] memo = new int[N+1][N+1];

            for (int i = 1; i <= N; i++) {
                String[] buffer = br.readLine().split(" ");
                T[i] = Integer.parseInt(buffer[0]);
                P[i] = Integer.parseInt(buffer[1]);
                if (i+T[i]-1 <= N)
                    memo[i][i+T[i]-1] = P[i];
            }

            //memo[i][j] = i일부터 j일까지 최대 값
            for (int len = 2; len <= N; len++) {
                for (int i = 1; i <= N-len+1; i++) {
                    for (int j = i+len-1; j <= N; j++) {
                        for (int k = i; k < j; k++) {
                            memo[i][j] = Math.max(memo[i][j], memo[i][k] + memo[k+1][j]);
                        }
                    }
                }
            }

            int max = memo[1][N];
            for (int i = 1; i < N; i++) {
                int sum = memo[1][i] + memo[i+1][N];
                if (sum > max)
                    max = sum;
            }

            bw.write(max+"\n");
            bw.flush();
        }
    }
}
