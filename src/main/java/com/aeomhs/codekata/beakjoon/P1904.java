package com.aeomhs.codekata.beakjoon;

import java.io.*;

public class P1904 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int N = Integer.parseInt(br.readLine());

            bw.write(solution(N)+"\n");
            bw.flush();
        }
    }

    /**
     * N = 1, 1 return 1
     * N = 2, 00, 11 return 2
     * N = 3, 100, 001, 111 return 3
     * N = 4, 0000, 0011, 1100, 1001, 1111 return 5
     *
     * memo[i][j][N] = k
     * 00 i개와 1 j개로 만들 수 있는 N개의 길이 수열은 k개다.
     *
     * Invariant Expression: (2*i)+j = N
     * memo[0][1][1] = 1
     * memo[i][j][1] = 1
     *
     * memo[0][2][2] = 1
     * memo[1][0][2] = 1
     * memo[i][j][2] = 2
     *
     * memo[0][3][3] = 1
     * memo[1][1][3] = 2 (1 00 , 00 1) (memo[1][0][2] '00' * 2)?
     * memo[i][j][3] = 3
     *
     * memo[0][4][4] = 1
     * memo[1][2][4] = 3 (00 1 1, 1 00 1, 1 1 00)
     * memo[2][0][4] = 1 (00 00)
     * memo[i][j][4] = 5
     *
     * memo[0][5][5] = 1
     * memo[1][3][5] = 4 (00 1 1 1, 1 00 1 1, 1 1 00 1, 1 1 1 00)
     * memo[2][1][5] = 3 (00 00 1, 00 1 00, 1 00 00)
     * memo[i][j][5] = 8
     */
    private static int solution(int N) {
        return fibonacci(N);
    }

    private static int fibonacci(int N) {
        if (N == 1)
            return 1;
        if (N == 2)
            return 2;

        int[] fibo = new int[N+1];

        fibo[1] = 1;
        fibo[2] = 2;

        for (int i = 3; i <= N; i++) {
            fibo[i] = (fibo[i-2] + fibo[i-1]) % 15746;
        }

        return fibo[N];
    }
}
