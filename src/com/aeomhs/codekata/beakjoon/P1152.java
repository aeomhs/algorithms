package com.aeomhs.codekata.beakjoon;

import java.io.*;

public class P1152 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            String[] words = br.readLine().trim().split(" ");
            if (words.length == 1 && "".equals(words[0]))
                bw.write(0 + "\n");
            else
                bw.write(words.length + "\n");

            bw.flush();
        }
    }
}
