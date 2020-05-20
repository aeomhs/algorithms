package com.aeomhs.codekata.beakjoon;

import java.io.*;

public class P2908 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            String[] buffer = br.readLine().split(" ");
            char[] A, B;

            A = buffer[0].toCharArray();
            B = buffer[1].toCharArray();

            for (int i = 2; i >= 0; i--) {
                if (A[i] > B[i]) {
                    bw.write(A[2] + "" + A[1] + "" + A[0] + "\n");
                    break;
                }

                else if (A[i] < B[i]) {
                    bw.write(B[2] + "" + B[1] + "" + B[0] + "\n");
                    break;
                }

            }


            bw.flush();
        }
    }
}
