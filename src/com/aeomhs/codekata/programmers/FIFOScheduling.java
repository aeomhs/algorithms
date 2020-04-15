package com.aeomhs.codekata.programmers;

public class FIFOScheduling {

    public static void main(String[] args) {
        FIFOScheduling test = new FIFOScheduling();
        int expected = 2;
        int result = test.solution(6, new int[]{1,2,3});
        System.out.println(expected + ", was" + result);
    }

    public int solution(int n, int[] cores) {
        final int len = cores.length;
        if (n < len)
            return len % n + 1;
        else if (n == len)
            return len;

        int minWorkTime = cores[0];
        int maxWorkTime = cores[0];

        for (int i = 1; i < len; i++) {
            minWorkTime = Math.min(minWorkTime, cores[i]);
            maxWorkTime = Math.max(maxWorkTime, cores[i]);
        }

        int maxTime = (maxWorkTime * n) / len - maxWorkTime;
        int minTime = (minWorkTime * n) / len - minWorkTime;

        while (minTime <= maxTime) {
            // midTime 동안 수행할 수 있는 작업량
            int midTime = (maxTime + minTime) / 2;

            int worked = len;
            int count = 0;
            for (int workTime : cores) {
                worked += midTime / workTime;
                if (midTime % workTime == 0) {
                    count++;
                }
            }

            if (n > worked) {
                minTime = midTime + 1;
            } else if (n <= worked - count) {
                maxTime = midTime - 1;
            } else {
                int k = 0;
                for (int i = 0; i < len; i++) {
                    if (midTime % cores[i] == 0)
                        k++;
                    if (n - (worked - count) == k)
                        return i+1;
                }
            }
        }

        return 0;
    }
}
