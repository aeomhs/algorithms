package com.aeomhs.codekata.beakjoon;

import java.io.*;

public class P1065 {
    public static void main(String[] args) throws IOException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int N = Integer.parseInt(br.readLine());

            if (N < 10)
                bw.write(N+"\n");
            else {
                int count = 9;
                for (int i = 10; i <= N; i++) {
                    if (isSeqNum(i))
                        count++;
                }
                bw.write(count+"\n");
            }

            bw.flush();
        }
    }

    private static boolean isSeqNum(int n) {
        int temp = n % 10;
        n /= 10;
        int c = temp - (n % 10);

        while ((n/10) > 0) {
            temp = n % 10;
            n /= 10;
            if (c != temp - n % 10)
                return false;
        }

        return true;
    }
}
