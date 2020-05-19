package com.aeomhs.codekata.beakjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class P1009 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int T = Integer.parseInt(br.readLine());

            int[] answer = new int[T];
            for (int i = 0; i < T; i++) {
                String[] token = br.readLine().split(" ");
                int a = Integer.parseInt(token[0]);
                int b = Integer.parseInt(token[1]);
                if (a == 1)
                    answer[i] = 1;
                else if (a % 10 == 0)
                    answer[i] = 10;
                else
                    answer[i] = solution(a % 10, a, b % 4 + 4);
            }

            Arrays.stream(answer).forEach(System.out::println);
        }
    }

    public static int solution(int c, int a, int b) {
        if (b == 1)
            return c;
        return solution((a * c) % 10, a, b - 1);
    }
}
