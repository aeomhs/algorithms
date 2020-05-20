package com.aeomhs.codekata.beakjoon;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class P14889 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            // 4 <= N <= 20, N%2 == 0
            int N = Integer.parseInt(br.readLine());
            int[][] S = new int[N][N];

            for (int i = 0; i < N; i++) {
                String[] buffer = br.readLine().split(" ");
                for (int j = 0; j < N; j++) {
                    // 1 <= Sij <= 100
                    S[i][j] = Integer.parseInt(buffer[j]);
                }
            }

            bw.write(solution(N, S) + "\n");
            bw.flush();
        }
    }

    private static int solution(int N, int[][] S) {
        int[] min = new int[]{ Integer.MAX_VALUE };

        comb(new HashSet<>(), 0, N, 0, N/2, S, min);

        return min[0];
    }

    // n개 중 r개 뽑는 조합
    private static void comb(Set<Integer> team, int index, int n, int depth, int r, int[][] S, int[] min) {
        if (depth == r) {
            int aTeam = 0;
            int bTeam = 0;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (team.contains(i) && team.contains(j)) {
                        aTeam += S[i][j];
                    }
                    else if (!team.contains(i) && !team.contains(j)){
                        bTeam += S[i][j];
                    }
                }
            }

            int sub = Math.abs(aTeam - bTeam);

            if (min[0] > sub) {
                min[0] = sub;
            }

            return;
        }

        if (index == n)
            return;

        comb(team, index+1, n, depth, r, S, min);
        team.add(index);
        comb(team, index+1, n, depth+1, r, S, min);
        team.remove(index);
    }
}
