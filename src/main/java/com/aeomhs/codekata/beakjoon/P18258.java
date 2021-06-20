package com.aeomhs.codekata.beakjoon;

import java.io.*;
import java.util.Deque;
import java.util.LinkedList;

public class P18258 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int N = Integer.parseInt(br.readLine());

            Queue queue = new Queue();

            for (int i = 0; i < N; i++) {
                String[] buffer = br.readLine().split(" ");
                if ("push".equals(buffer[0])) {
                    queue.push(Integer.parseInt(buffer[1]));
                } else if ("pop".equals(buffer[0])) {
                    bw.write(queue.pop()+"\n");
                } else if ("size".equals(buffer[0])) {
                    bw.write(queue.size()+"\n");
                } else if ("empty".equals(buffer[0])) {
                    bw.write(queue.isEmpty() ? "1\n" : "0\n");
                } else if ("front".equals(buffer[0])) {
                    bw.write(queue.front()+"\n");
                } else if ("back".equals(buffer[0])) {
                    bw.write(queue.back()+"\n");
                }
            }

            bw.flush();
        }
    }

    static class Queue {

        Deque<Integer> integerDeque;

        Queue() {
            integerDeque = new LinkedList<>();
        }

        void push(int v) {
            integerDeque.addFirst(v);
        }

        int pop() {
            if (isEmpty())
                return -1;
            return integerDeque.removeLast();
        }

        int size() {
            return integerDeque.size();
        }

        boolean isEmpty() {
            return integerDeque.isEmpty();
        }

        int front() {
            if (isEmpty())
                return -1;
            return integerDeque.peekLast();
        }

        int back() {
            if (isEmpty())
                return -1;
            return integerDeque.peekFirst();
        }
    }
}
