package com.aeomhs.codekata.beakjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class P1003 {
    public static void main(String[] args) throws IOException  {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int T = Integer.parseInt(br.readLine());
            String[] answers = new String[T];
            for (int i = 0; i < T; i++) {
                int N = Integer.parseInt(br.readLine());
                int[] count = fibonacci(N);

                answers[i] = (String.format("%d %d", count[0], count[1]));

            }
            for (String answer : answers)
                System.out.println(answer);
        }
    }

    private static int[] fibonacci(int N) {
        if (N == 0)
            return new int[]{1,0};
        if (N == 1)
            return new int[]{0,1};

        int[][] count = new int[N+1][2];
        count[0][0] = 1;
        count[0][1] = 0;
        count[1][0] = 0;
        count[1][1] = 1;
        for (int i = 2; i <= N; i++) {
            count[i][0] = count[i-2][0] + count[i-1][0];
            count[i][1] = count[i-2][1] + count[i-1][1];
        }

        return count[N];
    }

    private static int fibonacciFindZero(int N) {
        if (N == 0)
            return 1;
        if (N == 1)
            return 0;

        return fibonacciFindZero(N-2) + fibonacciFindZero(N-1);
    }

    private static int fibonacciFindOne(int N) {
        if (N == 0)
            return 0;
        if (N == 1)
            return 1;

        return fibonacciFindOne(N-2) + fibonacciFindOne(N-1);
    }
}
