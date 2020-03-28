package com.aeomhs.codekata.kakaotest;

import java.util.Arrays;
import java.util.PriorityQueue;

public class CrossBridge {
    public static void main(String[] args) {
        CrossBridge test = new CrossBridge();
        int[] stones = new int[] {
                2, 4, 5, 3, 2, 1, 4, 2, 5, 1
        };
        int k = 3;
        int expected = 3;
        int result = test.solution(stones, k);
        System.out.println(expected + " , " + result);

        stones = new int[] {
                1, 6, 1, 6, 6, 3, 3, 7
        };
        k = 2;
        expected = 3;
        result = test.solution(stones, k);
        System.out.println(expected + " , " + result);


        stones = new int[] {
                1, 3, 5, 7, 9
        };
        k = 3;
        expected = 5;
        result = test.solution(stones, k);
        System.out.println(expected + " , " + result);
    }

    public int solution(int[] stones, int k) {
        int answer = 0;

        PriorityQueue<Stone> pq = new PriorityQueue<>(stones.length);
        for (int i = 0; i < stones.length; i++) {
            pq.add(new Stone(i, stones[i]));
        }

        boolean[] isBroken = new boolean[stones.length];

        while (!pq.isEmpty()) {
            Stone weakestStone = pq.poll();
            isBroken[weakestStone.id] = true;
            pq.removeIf(stone -> {
                if (weakestStone.count == stone.count) {
                    isBroken[stone.id] = true;
                    return true;
                }   else {
                    return false;
                }
            });

            answer = weakestStone.count;
            System.out.println(Arrays.toString(isBroken));
            if (!canCross(isBroken, k)) {
                return answer;
            }

        }

        return answer;
    }

    private boolean canCross(boolean[] isBroken, int k) {
        int count = 0;
        boolean nearBy = true;
        for (boolean isBreak : isBroken){
            if (nearBy && isBreak){
                count++;
                if (count == k)
                    return false;
            } else if (!nearBy && isBreak) {
                nearBy = true;
            }
            else {
                count = 0;
            }
        }

        return true;
    }

    static class Stone implements Comparable<Stone> {
        private int id;
        private int count;
        Stone(int id, int count) {
            this.id = id;
            this.count = count;
        }

        @Override
        public int compareTo(Stone o) {
            return Integer.compare(this.count, o.count);
        }
    }
}
