package com.aeomhs.codekata.beakjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class P7567 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input = br.readLine();
            char[] shapes = input.toCharArray();
            int height = 10;

            for (int i = 1; i < shapes.length; i++) {
                if (shapes[i-1] != shapes[i])
                    height += 10;
                else
                    height += 5;
            }

            System.out.println(height);
        }
    }
}
