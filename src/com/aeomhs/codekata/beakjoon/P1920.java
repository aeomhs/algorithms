package com.aeomhs.codekata.beakjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class P1920 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String buffer = br.readLine();
            int N = Integer.parseInt(buffer);
            int[] array = new int[N];
            buffer = br.readLine();
            String[] tokens = buffer.split(" ");
            for (int i = 0; i < N; i++) {
                array[i] = Integer.parseInt(tokens[i]);
            }

            buffer = br.readLine();
            int M = Integer.parseInt(buffer);
            int[] targets = new int[M];
            buffer = br.readLine();
            tokens = buffer.split(" ");
            for (int i = 0; i < M; i++) {
                targets[i] = Integer.parseInt(tokens[i]);
            }

            Arrays.sort(array);

            for (int target : targets)
                if (binarySearch(array, target))
                    System.out.println(1);
                else
                    System.out.println(0);
        }
    }

    private static boolean binarySearch(int[] array, int target) {
        int lo = 0;
        int hi = array.length - 1;

        while (lo <= hi) {
            int mid = (hi + lo) / 2;

            int i = array[mid];
            if (i < target)
                lo = mid + 1;
            else if (i > target)
                hi = mid - 1;
            else
                return true;
        }

        return false;
    }
}
