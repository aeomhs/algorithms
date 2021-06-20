package com.aeomhs.codekata.beakjoon;

import java.io.*;
import java.util.Arrays;

public class P17779 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int N = Integer.parseInt(br.readLine());
            int[][] map = new int[N+1][N+1];

            for (int i = 1; i <= N; i++) {
                String[] buffer = br.readLine().split(" ");
                for (int j = 1; j <= N; j++) {
                    map[i][j] = Integer.parseInt(buffer[j-1]);
                }
            }

            int min = Integer.MAX_VALUE;
            // d1, d2 ≥ 1,
            // 1 ≤ x < x+d1+d2 ≤ N,
            // 1 ≤ y-d1 < y < y+d2 ≤ N
            for (int d1 = 1; d1 <= N; d1++) {
                for (int d2 = 1; d2+d1 <= N; d2++) {
                    for (int x = 1; x < x+d1+d2; x++) {
                        if (x + d1 + d2 > N)
                            break;

                        for (int y = 1+d1; y <= N; y++) {
                            if (y+d2 > N)
                                break;

                            int dist = getDistMaxMin(map, N, x, y, d1, d2);
                            if (dist < min)
                                min = dist;

                        }
                    }
                }
            }

            bw.write(min+"\n");

            bw.flush();
        }
    }

    // row == x, col == y
    private static int getDistMaxMin(int[][] map, int N, int x, int y, int d1, int d2) {

        int[] populationArr = new int[5];

        for (int r = 1; r <= N; r++) {
            for (int c = 1; c <= N; c++) {

                //1번 선거구: 1 ≤ r < x+d1, 1 ≤ c ≤ y
                if (r < x + d1 && c <= y && !(r >= x && c >= y-(r-x))) {
                    populationArr[0] += map[r][c];
                }
                //2번 선거구: 1 ≤ r ≤ x+d2, y < c ≤ N
                else if (r <= x + d2 && y < c && c <= N && !(r > x && c <= y+(r-x))) {
                    populationArr[1] += map[r][c];
                }
                //3번 선거구: x+d1 ≤ r ≤ N, 1 ≤ c < y-d1+d2
                else if (x + d1 <= r && r <= N && c < y - d1 + d2 && !(r <= x+d1+d2 && c >= y-d1+d2-(x+d1+d2-r))) {
                    populationArr[2] += map[r][c];
                }
                //4번 선거구: x+d2 < r ≤ N, y-d1+d2 ≤ c ≤ N
                else if (x + d2 < r && r <= N && y - d1 + d2 <= c && c <= N && !(r <= x+d1+d2 && c <= y-d1+d2+(x+d1+d2-r))) {
                    populationArr[3] += map[r][c];
                }
                else {
                    populationArr[4] += map[r][c];
                }
            }
        }

        Arrays.sort(populationArr);

        return populationArr[4] - populationArr[0];
    }
}
