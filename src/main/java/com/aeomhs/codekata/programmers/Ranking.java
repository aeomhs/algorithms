package com.aeomhs.codekata.programmers;

import java.util.Arrays;

public class Ranking {
    public static void main(String[] args) {
        Ranking test = new Ranking();
        test.solution(5, new int[][] {
                {4, 3},
                {4, 2},
                {3, 2},
                {1, 2},
                {2, 5}
        });

        test.solution(6, new int[][] {
                {1, 5},
                {5, 4},
                {3, 4},
                {5, 2},
                {4, 2},
                {4, 6},
        });
    }

    public int solution(int n, int[][] results) {
        int answer = 0;

        int[][] floydMarshall = new int[n+1][n+1];

        for (int i = 1; i < n+1; i++)
            Arrays.fill(floydMarshall[i], 0);

        // 각 정점쌍은 순위를 비교할 수 있음을 나타낸다. 즉, 도달할 수 있음을 나타낸다.
        for (int[] row : results) {
            floydMarshall[row[0]][row[1]] = row[0];
        }

        // 플로이드 와샬 알고리즘
        // 모든 쌍의 최단 경로를 구하는 알고리즘에서, 도달 가능함만 체크한다.
        // Introduction to Algorithm 714p
        for (int k = 1; k < n+1; k++) {
            System.out.println();
            for (int r = 1; r < n+1; r++) {
                for (int c = 1; c < n+1; c++) {
                    System.out.print(String.format("%3d", floydMarshall[r][c]));
                }
                System.out.println();
            }
            for (int i = 1; i < n+1; i++) {
                for (int j = 1; j < n+1; j++) {
                    // 각 정점을 중간 정점으로 하는 경로가 있음을 알 수 있다.
                    // i -> j 경로에 대한 k 중간 정점
                    if (i != j && floydMarshall[i][k] != 0 && floydMarshall[k][j] != 0) {
                        floydMarshall[i][j] = k;
                    }
                }
            }
        }

        // 들어오는 방향과 나가는 방향에 대해 모든 경로가 존재하는지 확인한다.
        for (int i = 1; i < n+1; i++) {
            boolean iCanGoEveryWhere = true;
            for (int j = 1; j < n+1; j++) {
                if (i != j && floydMarshall[i][j] + floydMarshall[j][i] == 0){
                    iCanGoEveryWhere = false;
                    break;
                }
            }
            if (iCanGoEveryWhere)
                answer++;
        }


        System.out.println(answer);


        return answer;
    }
}
