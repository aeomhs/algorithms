package com.aeomhs.codekata.beakjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Introduction to Algorithms
 * 15. Dynamic Programming. LCS
 */
public class P9251 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String str1 = br.readLine();
            String str2 = br.readLine();

            System.out.println(getLCS(str1, str2).length());
        }
    }

    private static String getLCS(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int[][] c = new int[len1+1][len2+1];
        char[][] b = new char[len1+1][len2+1];

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    c[i][j] = c[i-1][j-1] + 1;
                    b[i][j] = '↖';
                } else if (c[i-1][j] >= c[i][j-1]) {
                    c[i][j] = c[i-1][j];
                    b[i][j] = '↑';
                } else {
                    c[i][j] = c[i][j-1];
                    b[i][j] = '←';
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        buildLCS(b, str1, len1, len2, builder);

        return builder.toString();
    }

    private static void buildLCS(char[][] b, String str, int i, int j, StringBuilder builder) {
        if (i == 0 || j == 0)
            return ;

        if (b[i][j] == '↖') {
            buildLCS(b, str, i-1, j-1, builder);
            builder.insert(0, str.charAt(i-1));
        } else if (b[i][j] == '↑') {
            buildLCS(b, str, i-1, j, builder);
        } else
            buildLCS(b, str, i, j-1, builder);
    }
}
