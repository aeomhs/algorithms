package com.aeomhs.codekata.kakaotest;

public class WalkerHeaven {
    int MOD = 20170805;

    public static void main(String[] args) {
        WalkerHeaven test = new WalkerHeaven();
        test.solution(3, 6, new int[][] {{0, 2, 0, 0, 0, 2}, {0, 0, 2, 0, 1, 0}, {1, 0, 0, 2, 2, 0}});
        test.solution(10, 10, new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 2, 1, 2, 0, 2, 0, 2, 0, 0},
                {0, 2, 0, 2, 1, 2, 0, 2, 0, 0},
                {0, 2, 0, 2, 0, 2, 1, 2, 0, 0},
                {0, 2, 0, 2, 0, 2, 0, 2, 1, 0},
                {0, 2, 0, 2, 0, 2, 0, 2, 0, 0},
                {0, 2, 0, 2, 0, 2, 0, 2, 0, 0},
                {0, 2, 0, 2, 0, 2, 0, 2, 0, 0},
                {0, 2, 0, 2, 0, 2, 0, 2, 0, 0},
                {0, 2, 0, 2, 0, 2, 0, 2, 0, 0},
        });
        test.solution(5, 5, new int[][] {
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0},
                {0, 0, 2, 0, 2},
                {0, 0, 0, 2, 0},
                {0, 0, 0, 0, 0},
        });
    }

    public int solution(int m, int n, int[][] cityMap) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(String.format("%3d ",cityMap[i][j]));
            }
            System.out.println();
        }
        System.out.println();

        int[][] distArr = new int[m][n];
        for (int i = 0; i < m; i++) {
            if (cityMap[i][0] == 0) {
                distArr[i][0] = 1;
            }
            else if (cityMap[i][0] == 1) {
                break;
            }
        }
        for (int j = 0; j < n; j++) {
            if (cityMap[0][j] == 0) {
                distArr[0][j] = 1;
            }
            else if (cityMap[0][j] == 1) {
                break;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (cityMap[i][j] == 1) {
                    distArr[i][j] = 0;
                }

                else if (cityMap[i][j] == 0) {
                    distArr[i][j] = (distArr[i][j] + distArr[i-1][j] + distArr[i][j-1]) % MOD;
                }

                else if (cityMap[i][j] == 2) {
                    if (distArr[i-1][j] > 0) {
                        for (int k = i+1; k < m; k++) {
                            if (cityMap[k][j] == 0) {
                                distArr[k][j] = (distArr[i-1][j] + distArr[k][j]) % MOD;
                                break;
                            }
                            else if (cityMap[k][j] == 1) {
                                break;
                            }
                        }
                    }
                    if (distArr[i][j-1] > 0) {
                        for (int k = j+1; k < n; k++) {
                            if (cityMap[i][k] == 0) {
                                distArr[i][k] = (distArr[i][j-1] + distArr[i][k]) % MOD;
                                break;
                            }
                            else if (cityMap[i][k] == 1) {
                                break;
                            }
                        }
                    }
                }
            }
        }


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(String.format("%3d ",distArr[i][j]));
            }
            System.out.println();
        }

        return distArr[m-1][n-1];
    }
}
