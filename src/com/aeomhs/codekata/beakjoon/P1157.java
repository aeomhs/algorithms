package com.aeomhs.codekata.beakjoon;

import java.io.*;

public class P1157 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            // a - z, ignore Case
            int[] alps = new int[26];

            char[] buffer = br.readLine().toCharArray();

            for (char ch : buffer) {
                alps[Character.toLowerCase(ch) - 'a']++;
            }

            int maxIndex = 0;
            int maxCount = 0;
            boolean doubleMax = false;

            for (int i = 0; i < 26; i++) {
                int count = alps[i];
                if (count >= maxCount) {
                    if (maxCount == count) {
                        doubleMax = true;
                    } else {
                        maxCount = count;
                        maxIndex = i;
                        doubleMax = false;
                    }
                }
            }

            bw.write(doubleMax ? "?\n" : (char)('A'+(maxIndex))+"\n");
            bw.flush();
        }
    }
}
