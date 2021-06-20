package com.aeomhs.codekata.programmers;

import java.math.BigInteger;
import java.util.Arrays;

public class LongJump {

    public static void main(String[] args) {
        LongJump test = new LongJump();
        int n = 1000;
        long result = test.solution(n);
    }

    public long solution(int n) {
        BigInteger answer = BigInteger.valueOf(1);

        BigInteger[] factorial = new BigInteger[n+1];
        factorial[1] = BigInteger.valueOf(1);
        for (int i = 2; i <= n; i++) {
            factorial[i] = factorial[i-1].multiply(BigInteger.valueOf(i));
        }

        System.out.println(Arrays.toString(factorial));

        BigInteger mod = BigInteger.valueOf(1234567);

        for (int k = 1; k <= (n/2); k++) {
            int i = n - (2*k);

            if (i == 0)
                answer = answer.add(BigInteger.valueOf(1));
            else {
                answer = answer.add(factorial[i+k].divide(factorial[i].multiply(factorial[k]))).mod(mod);
            }

            System.out.println(i + ", " + k + " " + answer.longValue());
        }

        return answer.mod(BigInteger.valueOf(1234567)).longValue();
    }
}
