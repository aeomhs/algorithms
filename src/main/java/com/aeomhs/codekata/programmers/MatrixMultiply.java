package com.aeomhs.codekata.programmers;

import java.util.Arrays;

public class MatrixMultiply {

    public static void main(String[] args) {
        MatrixMultiply test = new MatrixMultiply();
        int[][] matrix_sizes = new int[][] {
                {5,3},{3,10},{10,6},{6,20},{20,10},{10,20},{20,5},{5, 10},{10, 7}
        };
    }

    // http://web.skhu.ac.kr/~mckim1/Lecture/DS/dna/class13/class13_02.html
    public int solution(int[][] matrix_sizes) {
        int N = matrix_sizes.length;
        int[][] M = new int[N+1][N+1];
        int[] d = new int[N+1];
        d[0] = matrix_sizes[0][0];
        for (int i = 0; i < N; i++) {
            d[i+1] = matrix_sizes[i][1];
        }

        for (int n = 1; n <= N; n++) {
            for (int i = 1, j = n+1; i <= j && j <= N; i++, j++) {

                int min = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    min = Math.min(min, M[i][k] + M[k+1][j] + d[i-1]*d[k]*d[j]);
                }
                M[i][j] = min;
            }
        }

//        System.out.println(Arrays.toString(d));
//        for (int[] row : M) {
//            for (int e : row) {
//                System.out.print(String.format("%5d ", e));
//            }
//            System.out.println();
//        }

        return M[1][N];
    }
}
