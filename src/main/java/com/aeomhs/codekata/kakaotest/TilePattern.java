package com.aeomhs.codekata.kakaotest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;

public class TilePattern {

    @Test
    public void solutionFiboTest() {
        TilePattern test = new TilePattern();
        int n;
        int expected;
        int result;

        n = 1;
        expected = 1;
        result = test.solutionFibo(n);
        Assertions.assertEquals(expected, result);

        n = 2;
        expected = 2;
        result = test.solutionFibo(n);
        Assertions.assertEquals(expected, result);

        n = 3;
        expected = 3;
        result = test.solutionFibo(n);
        Assertions.assertEquals(expected, result);

        n = 4;
        expected = 5;
        result = test.solutionFibo(n);
        Assertions.assertEquals(expected, result);

        n = 5;
        expected = 8;
        result = test.solutionFibo(n);
        Assertions.assertEquals(expected, result);

        n = 6;
        expected = 13;
        result = test.solutionFibo(n);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void solutionTest() {
        TilePattern test = new TilePattern();
        int n;
        int expected;
        int result;

        n = 1;
        expected = 1;
        result = test.solution(n);
        Assertions.assertEquals(expected, result);

        n = 2;
        expected = 2;
        result = test.solution(n);
        Assertions.assertEquals(expected, result);

        n = 3;
        expected = 3;
        result = test.solution(n);
        Assertions.assertEquals(expected, result);

        n = 4;
        expected = 5;
        result = test.solution(n);
        Assertions.assertEquals(expected, result);

        n = 5;
        expected = 8;
        result = test.solution(n);
        Assertions.assertEquals(expected, result);

        n = 6;
        expected = 13;
        result = test.solution(n);
        Assertions.assertEquals(expected, result);

    }


    public int solution(int n) {
        System.out.println();
        long result = 0;

        for (int i = 0; i <= n/2; i++) {
            result += perm(n-(2*i), i);
            System.out.println(result);
        }
        return (int) (result % 1000000007);
    }

    /**
     * 가로 너비 N에 대하여
     * 세로 타일 a개
     * 가로 타일 b개
     * 의 순서가 정해진 순열 경우의 수 구하기
     */
    private long perm(long a, long b) {
        System.out.println("("+a+"/"+b+")");
        return factorial(a+b) / (factorial(a) * factorial(b));
    }

    private long factorial(long N) {
        BigInteger f = new BigInteger(String.valueOf(1));

        for (long i = N; i > 1; i--){
            f = f.multiply(BigInteger.valueOf(i));
        }

        return f.mod(BigInteger.valueOf(1000000007)).intValue();
    }

    /**
     * 타일 조합 수가 피보나치 수열과 일치하다.
     * 왜 몰랐을까?
     */
    public int solutionFibo(int n) {
        return (int) (fibo(n) % 1000000007);
    }

    private long fibo(int N) {
        if (N == 1)
            return 1;

        int[] mem = new int[N];

        mem[0] = 1;
        mem[1] = 2;

        for (int i = 2; i < N; i++) {
            mem[i] = (mem[i-2] + mem[i-1]) % 1000000007;
        }

        System.out.println(Arrays.toString(mem));

        return mem[N-1];
    }
}
