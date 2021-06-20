package com.aeomhs.codekata.programmers;

import java.util.PriorityQueue;

public class RamenFactory {

    public static void main(String[] args) {
        RamenFactory ramenFactory = new RamenFactory();
        int answer = ramenFactory.solution(
                4,
                new int[] {4, 10, 15},
                new int[] {20, 5, 10},
                30
        );
        System.out.println(answer + " 2");

        answer = ramenFactory.solution(
                30,
                new int[] {4, 10, 15},
                new int[] {20, 5, 10},
                30
        );
        System.out.println(answer + " 0");

        answer = ramenFactory.solution(
                10,
                new int[] {10, 12, 15},
                new int[] {20, 5, 10},
                30
        );
        System.out.println(answer + " 1");


        //24 44 64
        answer = ramenFactory.solution(
                15,
                new int[] {10, 12, 15, 20, 25, 30, 35, 45, 50},
                new int[] {5,   5, 10, 10, 20, 10, 20,  5,  5},
                65
        );
        System.out.println(answer + " 3");


        answer = ramenFactory.solution(
                4,
                new int[] {1, 2, 3, 4},
                new int[] {10, 40, 30, 20},
                100
        );
        System.out.println(answer + " 4");


        answer = ramenFactory.solution(
                5,
                new int[] {1, 2, 3, 4, 5},
                new int[] {10, 40, 20, 20, 40},
                85
        );
        System.out.println(answer + " 2");


        answer = ramenFactory.solution(
                8,
                new int[] { 3,  6, 10, 12, 15, 18, 21, 24},
                new int[] {10, 20, 40,  5,  5,  4, 40,  2},
                110
        );
        System.out.println(answer + " 4");


        answer = ramenFactory.solution(
                9,
                new int[] {8,  9, 15, 38},
                new int[] {1, 50, 10, 10},
                30
        );
        System.out.println(answer + " 1");

        answer = ramenFactory.solution(
                10,
                new int[] {5, 10},
                new int[] {1, 100},
                110
        );
        System.out.println(answer + " 1");
    }

    public int solution(int stock, int[] dates, int[] supplies, int k) {
        int answer = 0;

        PriorityQueue<Integer> pqs = new PriorityQueue<>(dates.length, (o1, o2) -> -1 * Integer.compare(o1, o2));

        int index = 0;

        while (stock < k) {
            for (; index < dates.length; index++) {
                if (dates[index] > stock)
                    break;
                pqs.offer(supplies[index]);
            }

            stock += pqs.poll();
            answer++;
        }

        return answer;
    }
}