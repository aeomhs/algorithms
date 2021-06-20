package com.aeomhs.codekata.programmers;

public class CookieDiv {

    public static void main(String[] args) {
        CookieDiv test = new CookieDiv();
        int[] cookie = new int[] {1,1,2,3};
        int expected = 3;
        int actual = test.solution(cookie);

        System.out.println(expected + " "+ actual);

        cookie = new int[] {1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6};
        expected = 21;
        actual = test.solution(cookie);

        System.out.println(expected + " "+ actual);
    }

    public int solution(int[] cookie) {
        int N = cookie.length;
        if (N == 1)
            return 0;

        int[][] memo = new int[N+1][N+1];

        memo[N][N] = cookie[N-1];
        for (int i = 1; i < N; i++) {
            memo[i][i] = cookie[i-1];
            for (int j = i+1; j <= N; j++) {
                /* memo[i][j] = i ~ j 까지의 합 */
                memo[i][j] = memo[i][j-1] + cookie[j-1];
            }
        }

        int max = 0;

        for (int l = 1; l < N; l++) {
            for (int m = l; m < N; m++) {
                if (max >= memo[l][m])
                    continue;

                if (memo[l][m] > memo[m+1][N])
                    continue;

                for (int r = m+1; r <= N; r++) {
                    if (memo[l][m] == memo[m + 1][r]) {
                        max = memo[l][m];
                        break;
                    }
                    else if (memo[l][m] < memo[m + 1][r])
                        break;
                }
            }
        }

        return max;
    }
}
