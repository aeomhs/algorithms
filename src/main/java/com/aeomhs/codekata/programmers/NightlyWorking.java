package com.aeomhs.codekata.programmers;

import java.util.Comparator;
import java.util.PriorityQueue;

public class NightlyWorking {
    public long solution(int n, int[] works) {
        long answer = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (int work : works)
            pq.offer(work);

        for (int i = 0 ; i < n; i++) {
            pq.offer(pq.poll()-1);
        }

        for (int work : pq) {
            answer += Math.pow(work, 2);
        }

        return answer;
    }
}
