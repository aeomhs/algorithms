package com.aeomhs.codekata.programmers;

import java.util.PriorityQueue;

public class FIFOScheduling {

    public static void main(String[] args) {
        FIFOScheduling test = new FIFOScheduling();
        int expected = 2;
        int result = test.solution(6, new int[]{1,2,3});
        System.out.println(expected + ", was" + result);
    }

    public int solution(int n, int[] cores) {
        PriorityQueue<Core> pq = new PriorityQueue<>();

        // All Cores working
        for (int i = 0; i < cores.length; i++) {
            pq.add(new Core(i+1, cores[i]));
            if (n == 1) {
                return i+1;
            }
            n--;
        }

        while (!pq.isEmpty()) {
            Core idle = pq.poll();
            System.out.println(idle);

            if (n > 1) {
                idle.time += idle.jobTime;
                pq.add(idle);
                n--;
            }
            else if (n == 1) {
                return idle.id;
            }
        }

        return -1;
    }

    static class Core implements Comparable<Core> {
        int id;

        int jobTime;

        int time;

        Core (int id, int jobTime) {
            this.id = id;
            this.jobTime = jobTime;
            this.time = jobTime;
        }

        @Override
        public int compareTo(Core o) {
            if (this.time < o.time)
                return -1;
            if (this.time > o.time)
                return 1;
            if (this.id < o.id)
                return -1;
            if (this.id > o.id)
                return 1;
            return 0;
        }

        @Override
        public String toString() {
            return "Core{" +
                    "id=" + id +
                    ", jobTime=" + jobTime +
                    ", time=" + time +
                    '}';
        }
    }
}
