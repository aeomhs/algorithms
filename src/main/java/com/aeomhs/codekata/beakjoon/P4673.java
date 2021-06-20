package com.aeomhs.codekata.beakjoon;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class P4673 {
    public static void main(String[] args) throws IOException {
        boolean[] isNotSelfNum = new boolean[10001];
        for (int i = 1; i < 10001; i++) {
            int d = d(i);
            while (d <= 10000) {
                if (isNotSelfNum[d])
                    break;
                isNotSelfNum[d] = true;
                d = d(d);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            for (int i = 1; i < 10001; i++) {
                if (!isNotSelfNum[i])
                    bw.write(i+"\n");
            }

            bw.flush();
        }
    }

    private static int d(int n) {
        int sum = n;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }
}