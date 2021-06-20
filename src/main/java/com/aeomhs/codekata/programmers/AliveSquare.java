package com.aeomhs.codekata.programmers;

public class AliveSquare {
    public long solution(int w,int h) {

        long size = (long)w * h;

        int numberOfSquareThroughLineGCD = gcd(w, h);
        int numberOfSquareThroughLine = (w/numberOfSquareThroughLineGCD) + (h/numberOfSquareThroughLineGCD) - 1;

        long sizeOfSquareThroughLine = numberOfSquareThroughLine * numberOfSquareThroughLineGCD;

        return size - sizeOfSquareThroughLine;
    }

    private int gcd(int a, int b) {
        int eculid = a%b;
        while (eculid != 0) {
            a = b;
            b = eculid;
            eculid = a % b;
        }
        return b;
    }
}
