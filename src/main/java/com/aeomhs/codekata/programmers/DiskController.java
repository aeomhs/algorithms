package com.aeomhs.codekata.programmers;

import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DiskController {

    public static void main(String[] args) {
        DiskController diskController = new DiskController();
        int[][] jobs;
        int result;
        int expected;

        jobs = new int[][] {{0, 3}, {1, 9}, {2, 6}};
        result = diskController.solution(jobs);
        expected = 9;
        Assertions.assertEquals(expected, result);


        jobs = new int[][] {{10, 5}, {7, 10}, {15, 2}};
        result = diskController.solution(jobs);
        expected = 9;
        Assertions.assertEquals(expected, result);
    }

    public int solution(int[][] jobs) {
        int answer = 0;

        Task[] tasks = new Task[jobs.length];

        PriorityQueue<Task> pq = new PriorityQueue<>(tasks.length,
                Comparator.comparingInt((Task o) -> o.taskTime));

        // 요청 시간 순으로 정렬
        for (int i = 0; i < jobs.length; i++) {
            tasks[i] = new Task(jobs[i][0], jobs[i][1]);
        }
        Arrays.sort(tasks);

        int time = 0;
        int index = 0;
        do {
            for (;index < tasks.length; index++) {
                if (tasks[index].requestTime > time)
                    break;
                pq.offer(tasks[index]);
            }
            if (!pq.isEmpty()) {
                Task task = pq.poll();
                answer += task.getResponseTime(time);
                time += task.taskTime;
            } else {
                time++;
            }
        } while (!pq.isEmpty() || index < tasks.length);

        return answer / tasks.length;
    }

    static class Task implements Comparable<Task> {
        int requestTime;
        int taskTime;

        Task(int req, int task) {
            this.requestTime = req;
            this.taskTime = task;
        }

        int getResponseTime(int startTime) {
            return ((startTime + taskTime) - requestTime);
        }

        @Override
        public int compareTo(Task o) {
            return Integer.compare(this.requestTime, o.requestTime);
        }
    }
}
