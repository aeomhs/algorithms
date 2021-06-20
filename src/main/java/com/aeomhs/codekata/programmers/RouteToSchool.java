package com.aeomhs.codekata.programmers;

class RouteToSchool {

    public static void main(String[] args) {
        RouteToSchool test = new RouteToSchool();
        test.solution(4, 3, new int[][]{{2,2}});
        System.out.println();
        test.solution(1, 2, new int[][]{{}});
        System.out.println();
        test.solution(2, 1, new int[][]{{}});
        System.out.println();
        test.solution(5, 5, new int[][]{{2,2},{2,3},{3,3}});
        System.out.println();
        test.solution(3, 3, new int[][]{{}});
        System.out.println();
        test.solution(10, 10, new int[][]{{1, 2},{2,2},{2,4},{3,3},{4,4},{5,5},{6,6}});
//        test.solution(100, 100, new int[][]{{2,2},{3,3},{4,4},{5,5},{6,6}});
        System.out.println();
    }

    public int solution(int m, int n, int[][] puddles) {

        return route(m,n,puddles);
    }

    public int route(int m, int n, int[][] puddles) {
        int[][] mem = new int[n+1][m+1];

        if (m == 1 || n == 1) {
            if (puddles.length > 0 && puddles[0].length == 2)
                return 0;
            return 1;
        }

        if (puddles.length > 0 && puddles[0].length == 2)
            for (int i = 0; i < puddles.length; i++)
                mem[puddles[i][1]][puddles[i][0]] = -1;

        for (int i = 1; i <= n; i++) {
            if (mem[i][1] == -1)
                break;
            mem[i][1] = 1;
        }
        for (int i = 1; i <= m; i++) {
            if (mem[1][i] == -1)
                break;
            mem[1][i] = 1;
        }

        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= m; j++){
                if (mem[i][j] == -1)
                    continue;

                boolean upperIsPuddle = mem[i-1][j] == -1;
                boolean leftIsPuddle = mem[i][j-1] == -1;

                if (upperIsPuddle && leftIsPuddle)
                    continue;

                else if (upperIsPuddle) {
                    mem[i][j] = mem[i][j-1];
                }
                else if (leftIsPuddle) {
                    mem[i][j] = mem[i-1][j];
                }
                else {
                    mem[i][j] = (mem[i-1][j] + mem[i][j-1]) % 1000000007;
                }
            }
        }

        for (int[] row : mem) {
            for (int i : row) {
                System.out.print(String.format("%10d", i));
            }
            System.out.println();
        }

        return mem[n][m];
    }
}