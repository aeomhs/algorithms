package com.aeomhs.codekata.beakjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class P2748 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());

            System.out.println(fibonacci(N));
        }
    }

    private static long fibonacci(int N) {
        if (N == 0)
            return 0;
        if (N == 1)
            return 1;

        long[] memo = new long[N+1];
        memo[0] = 0;
        memo[1] = 1;
        for (int i = 2; i <= N; i++) {
            memo[i] = memo[i-2] + memo[i-1];
        }

        return memo[N];
    }
}
