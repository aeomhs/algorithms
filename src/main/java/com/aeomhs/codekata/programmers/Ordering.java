package com.aeomhs.codekata.programmers;

import java.util.ArrayList;
import java.util.Arrays;

public class Ordering {
    private long[] factorial;

    public static void main(String[] args) {
        Ordering test = new Ordering();
        int n = 3;
        long k = 5;
        int[] expected = new int[] { 3, 1, 2 };
        int[] result = test.solution(n, k);
//        System.out.println(Arrays.toString(expected));
//        System.out.println(Arrays.toString(result));

        n = 20;
        k = 5;
        expected = new int[] { 3, 1, 2 };
        result = test.solution(n, k);
//        System.out.println(Arrays.toString(expected));
//        System.out.println(Arrays.toString(result));
    }

    public int[] solution(int n, long k) {
        factorial = new long[n+1];
        factorial[1] = 1;
        for (int i = 2; i < factorial.length; i++) {
            factorial[i] = factorial[i-1] * i;
        }

        ArrayList<Integer> entries = new ArrayList<>();
        for (int i = 1; i <= n; i++)
            entries.add(i);

        int[] answer = new int[n];

        for (int i = 1; i < n; i++) {
            int index = (int)((k-1) / factorial[n-i]) % entries.size();
            System.out.println(k-1 + " / " + factorial[n-i] + " % " + entries.size());
            answer[i-1] = entries.remove(index);
        }

        answer[n-1] = entries.remove(0);

        System.out.println(Arrays.toString(answer));

        return answer;
    }
}
