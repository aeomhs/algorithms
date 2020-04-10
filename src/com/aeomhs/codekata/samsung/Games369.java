package com.aeomhs.codekata.samsung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Games369 {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int N = Integer.parseInt(br.readLine());

            System.out.println(game(N));

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    private static String game(int n) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (String.valueOf(i).contains("3") || String.valueOf(i).contains("6") || String.valueOf(i).contains("9")) {
                builder.append(String.valueOf(i).replaceAll("[369]", "-").replaceAll("\\d", "")).append(" ");
            } else {
                builder.append(i).append(" ");
            }
        }
        return builder.toString();
    }
}
