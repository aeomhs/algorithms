package com.aeomhs.codekata.beakjoon;

import java.io.*;

public class P11729 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int N = Integer.parseInt(br.readLine());

            if (N == 1) {
                bw.write("1\n");
                bw.write("1 3\n");
            }
            else {
                StringBuilder builder = new StringBuilder();
                int count = hanoi(N,1, 2, 3, builder);
                builder.insert(0, count+"\n");
                bw.write(builder.toString());
            }
            bw.flush();
        }
    }

    private static int hanoi(int N, int src, int temp, int dest, StringBuilder builder) {
        if (N == 2) {
            builder.append(src).append(" ").append(temp).append("\n");
            builder.append(src).append(" ").append(dest).append("\n");
            builder.append(temp).append(" ").append(dest).append("\n");
            return 3;
        }

        int count = hanoi(N-1, src, dest, temp, builder);
        builder.append(src).append(" ").append(dest).append("\n");
        return count + hanoi(N-1, temp, src, dest, builder) + 1;
    }

}
