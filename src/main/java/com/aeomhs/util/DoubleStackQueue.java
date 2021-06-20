package com.aeomhs.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * STACK 2개로 Queue 구현하기
 */
public class DoubleStackQueue<V> {

    private Stack<V> doubleStackQueue = new Stack<>();

    public V peek() {
        return doubleStackQueue.peek();
    }

    public void enqueue(V entry) {
        Stack<V> reverse = new Stack<>();

        while (!doubleStackQueue.isEmpty()) {
            reverse.push(doubleStackQueue.pop());
        }

        reverse.push(entry);

        while (!reverse.isEmpty()) {
            doubleStackQueue.push(reverse.pop());
        }
    }

    public V dequeue() {
        return doubleStackQueue.pop();
    }

    @Test
    public void peekResultShouldAlwaysFirstData() {
        DoubleStackQueue<String> queue = new DoubleStackQueue<>();
        String[] datas = new String[] {
                "second", "third", "fourth"
        };
        String firstData = "First";
        queue.enqueue(firstData);
        Assertions.assertEquals(firstData, queue.peek());

        for (String data : datas) {
            queue.enqueue(data);
            Assertions.assertEquals(firstData, queue.peek());
        }
    }

    @Test
    public void listOrderShouldEqualsAfterEnqueueDequeue() {
        DoubleStackQueue<String> queue = new DoubleStackQueue<>();
        String[] before = new String[] {
                "second", "third", "fourth"
        };

        for (String data : before) {
            queue.enqueue(data);
        }

        String[] after = new String[before.length];

        for (int i = 0; i < before.length; i++) {
            after[i] = queue.dequeue();
        }

        Assertions.assertArrayEquals(after, before);
    }
}
