package com.aeomhs.codekata.programmers;

import java.util.Arrays;

public class SteppingStones {
    public int solution(int distance, int[] rocks, int n) {
        int answer = 0;

        Arrays.sort(rocks);
        int minDist = 1;
        int maxDist = distance;


        while (minDist <= maxDist) {
            int dist = (minDist + maxDist) / 2;
            // 모든 사이 거리가 dist 이상을 만족하기 위해 제거되어야하는 rock 개수를 의미한다.
            int rock = 0;
            int prevRockPos = 0;
            for (int i = 0; i < rocks.length; i++) {
                if (dist > rocks[i] - prevRockPos)
                    rock++;
                else
                    prevRockPos = rocks[i];
            }
            if (dist > distance - prevRockPos)
                rock++;

            // 제거해야하는 수가 제거할 수 있는 수보다 작으면 더 높은 거리를 찾는다.
            if (rock <= n) {
                minDist = dist + 1;
                answer = dist;
            }
            else {
                maxDist = dist - 1;
            }
        }

        return answer;
    }
}
