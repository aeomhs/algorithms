package com.aeomhs.codekata.samsung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Comparator {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int T = Integer.parseInt(br.readLine());
            char[] answers = new char[T];

            for (int t = 1; t <= T; t++) {
                String[] inputs = br.readLine().split(" ");
                int n = Integer.parseInt(inputs[0]);
                int m = Integer.parseInt(inputs[1]);
                answers[t-1] = n > m ? '>' : (n == m ? '=' : '<');
            }

//            System.out.println(Arrays.toString(answers));

            for (int t = 1; t <= T; t++) {
                System.out.println("#"+t+" "+answers[t-1]);
            }

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
