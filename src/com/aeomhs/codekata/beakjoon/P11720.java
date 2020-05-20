package com.aeomhs.codekata.beakjoon;

import java.io.*;

public class P11720 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            // ignore next line's Length
            br.readLine();

            char[] chars = br.readLine().toCharArray();
            int sum = 0;
            for (char c : chars)
                sum += (c - '0');

            bw.write(sum + "\n");
            bw.flush();
        }
    }
}
