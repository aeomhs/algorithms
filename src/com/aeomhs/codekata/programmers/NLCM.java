package com.aeomhs.codekata.programmers;

public class NLCM {

    /**
     * N개의 수들의 LCM(최소공배수)를 구한다.
     */
    public int solution(int[] arr) {
        int answer = arr[0];

        for (int i = 1; i < arr.length; i++)
            answer = lcm(answer, arr[i]);

        return answer;
    }

    private int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    private int gcd(int a, int b) {
        int euclid = a % b;
        while (euclid != 0){
            a = b;
            b = euclid;
            euclid = a % b;
        }
        return b;
    }
}
