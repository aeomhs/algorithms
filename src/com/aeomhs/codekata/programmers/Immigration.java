package com.aeomhs.codekata.programmers;


import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Immigration {

    public static void main(String[] args) {
        Immigration test = new Immigration();
        int[] times;
        int n;
        long expected;
        long result;

        times = new int[]{
                7, 10
        };
        n = 9;
        expected = 40;
        result = test.solution(n, times);

        Assertions.assertEquals(expected, result);

        times = new int[]{
                7, 10
        };
        n = 6;
        expected = 28;
        result = test.solution(n, times);

        Assertions.assertEquals(expected, result);


        times = new int[]{
                100000
        };
        n = 1000000000;
        expected = 100000000000000L;
        result = test.solution(n, times);

        Assertions.assertEquals(expected, result);
    }

    /**
     * Ver 1
     * 0. 심사관을 소요 시간 오름차순으로 정렬한다. (조건 유지)
     * 1. 심사 대기 인원(N)이 있는지 확인한다. (불변식)
     * 2. 가장 작은 소요 시간의 심사관을 선택한다.
     * 3. 심사 대기 인원을 감소한다.
     *
     * 이 경우 10억이라는 큰 수를 반복하기 어렵다.
     */
    public long solution_old(int n, int[] times) {
        Arrays.sort(times);

        int[] judgeTimes = Arrays.copyOf(times, times.length);

        while (n > 0) {

            n--;
            if (n == 0) {
                return judgeTimes[0];
            }

            judgeTimes[0] += times[0];

            for (int i = 0; i < judgeTimes.length - 1 ; i++) {
                if (judgeTimes[i] > judgeTimes[i+1]) {
                    swap(judgeTimes, i, i+1);
                    swap(times, i, i+1);
                } else {
                    break;
                }
            }
        }

        return -1;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * 우선순위 큐를 사용하여 swap, 재정렬 로직 제거
     * 하지만 역시 시간 문제.
     * 도저히 감이 안와서 풀이를 보고야 말았다.
     * - 총 걸리는 시간 / 각 심사관 심사 시간 = 각 심사관이 맡는 사람 수
     * 위와 같은 공식을 도출하는 것이 문제의 핵심이었다.
     */
    public long solution_pq(int n, int[] times) {
        PriorityQueue<Judge> pq = new PriorityQueue<>(times.length);
        for (int time : times) {
            pq.offer(new Judge(time));
        }

        while (n > 0) {
            n--;
            if (n == 0) {
                return pq.poll().judgeTime;
            }
            Judge judge = pq.poll();
            judge.judgeTime += judge.time;
            pq.offer(judge);
        }

        return -1;
    }

    static class Judge implements Comparable<Judge> {
        final int time;
        long judgeTime;
        Judge(int time) {
            this.time = time;
            judgeTime = time;
        }

        @Override
        public int compareTo(Judge o) {
            return Long.compare(this.judgeTime, o.judgeTime);
        }
    }

    /**
     * 이분 탐색을 활용해서 풀이
     * 공식을 유도하고 푸는데 20분도 안걸렸는데
     * 위에 삽질을 3시간했다.
     * 기분이 좋다. :)
     * 나중에 다시 풀어봐야겠다.
     */
    public long solution(int n, int[] times) {

        Arrays.sort(times);

        long minTime = 1;
        long maxTime = (long) n * times[times.length-1];
        long bestTime = maxTime;

        // Binary Search
        while (minTime < maxTime) {
            long atThisTime = (maxTime + minTime) / 2;
            int atThisTimeCanImmigrationPerson = 0;

            for (int i = 0; i < times.length; i++)
                atThisTimeCanImmigrationPerson += atThisTime / times[i];

            if (atThisTimeCanImmigrationPerson >= n) {
                if (bestTime > atThisTime)
                    bestTime = atThisTime;
                maxTime = atThisTime - 1;
            }
            else {
                minTime = atThisTime + 1;
            }
        }

        return bestTime;
    }
}
