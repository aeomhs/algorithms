package com.aeomhs.codekata.programmers;

import java.util.Arrays;
import java.util.stream.Stream;

public class MaxSticker {

    public static void main(String[] args) {
        MaxSticker test = new MaxSticker();
        System.out.println(test.solution(new int[] {14, 5, 1, 2, 5, 6, 2, 100, 3, 2, 10, 1000, 231}));
        System.out.println(test.solution(new int[] {1, 2, 3, 4, 5, 6, 7}));
        System.out.println(test.solution(new int[] {1}));
        System.out.println(test.solution(new int[] {1, 2}));
        System.out.println(test.solution(new int[] {1, 2, 3}));
    }

    public int solution(int[] sticker) {

        if (sticker.length == 1) {
            return sticker[0];
        } else if (sticker.length == 2) {
            return Math.max(sticker[0], sticker[1]);
        } else if (sticker.length == 3) {
            return Math.max(sticker[0], Math.max(sticker[1], sticker[2]));
        }

        int N = sticker.length;
        int[] sumOfHead = new int[N];
        int[] sumOfTail = new int[N];

        sumOfHead[2] = sticker[0] + sticker[2];
        sumOfHead[3] = sticker[0] + sticker[3];
        for (int i = 2; i < sticker.length-3; i++) {
            sumOfHead[i+2] = Math.max(sumOfHead[i+2], sumOfHead[i] + sticker[i+2]);
            if (i+3 < sticker.length-1)
                sumOfHead[i+3] = Math.max(sumOfHead[i+3], sumOfHead[i] + sticker[i+3]);
        }

        sumOfTail[1] = sticker[1];
        sumOfTail[2] = sticker[2];
        for (int i = 1; i < sticker.length-2; i++) {
            sumOfTail[i+2] = Math.max(sumOfTail[i+2], sumOfTail[i] + sticker[i+2]);
            if (i+3 < sticker.length)
                sumOfTail[i+3] = Math.max(sumOfTail[i+3], sumOfTail[i] + sticker[i+3]);
        }

        int headMax = Arrays.stream(sumOfHead).max().getAsInt();
        int tailMax = Arrays.stream(sumOfTail).max().getAsInt();

        System.out.println(Arrays.toString(sumOfHead));
        System.out.println(Arrays.toString(sumOfTail));

        return Math.max(headMax, tailMax);
    }
}
