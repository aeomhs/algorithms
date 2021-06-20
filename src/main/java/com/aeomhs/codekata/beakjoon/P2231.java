package com.aeomhs.codekata.beakjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class P2231 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            int N = Integer.parseInt(br.readLine());

            for (int M = 1; M < N; M++) {
                if (isConstructorOfN(M, N)){
                    System.out.println(M);
                    return;
                }
            }

            System.out.println(0);
        }
    }

    private static boolean isConstructorOfN(int M, int N) {
        int sum = M;
        while (M > 0) {
            if (sum > N)
                return false;
            sum += M % 10;
            M /= 10;
        }

        return sum == N;
    }
}
