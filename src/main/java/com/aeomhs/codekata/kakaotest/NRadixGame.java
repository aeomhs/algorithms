package com.aeomhs.codekata.kakaotest;

import org.junit.jupiter.api.Assertions;

public class NRadixGame {

    public String solution(int n, int t, int m, int p) {

        StringBuilder predictNumbers = new StringBuilder();
        for (int i = 0; ; i++) {
            predictNumbers.append(Integer.toString(i, n));
            if (predictNumbers.length() / m == t)
                break;
        }

        StringBuilder predictTubeNumbers = new StringBuilder();
        for (int i = 0; ; i++) {
            predictTubeNumbers.append(predictNumbers.charAt((p-1) + i*m));
            if (predictTubeNumbers.length() == t)
                break;
        }

        return predictTubeNumbers.toString().toUpperCase();
    }

    public static void main(String[] args) {
        NRadixGame test = new NRadixGame();
        String result, expected;

        result = test.solution(2, 4, 2, 1);
        expected = "0111";
        Assertions.assertEquals(expected, result);

        result = test.solution(16, 16, 2, 1);
        expected = "02468ACE11111111";
        Assertions.assertEquals(expected, result);

        result = test.solution(16, 16, 2, 2);
        expected = "13579BDF01234567";
        Assertions.assertEquals(expected, result);
    }
}
