package com.aeomhs.codekata.kakaotest;

import java.util.Arrays;

public class PuppeteerGame {

    public static void main(String[] args) {
        PuppeteerGame test = new PuppeteerGame();
        int[][] board = new int[][] {
                {0,0,0,0,0},
                {0,0,1,0,3},
                {0,2,5,0,1},
                {4,2,4,4,2},
                {3,5,1,3,1}
        };
        int[] moves = new int[] {1,5,3,5,1,2,1,4};
        int expected = 4;
        int result = test.solution(board, moves);
        System.out.println(expected + " =? " + result);
    }

    public int solution(int[][] board, int[] moves) {
        int answer = 0;

        int N = board.length;
        Stack[] stacks = new Stack[N];
        for (int i = 0; i < N; i++) {
            stacks[i] = new Stack(N);
            for (int j = N-1; j >= 0; j--)
                if (board[j][i] != 0)
                    stacks[i].push(board[j][i]);
        }

        for (Stack stack : stacks)
            System.out.println(stack);

        Stack basket = new Stack(N*N);

        for (int idx : moves) {
            if (stacks[idx-1].size() == 0)
                continue;

            int pick = stacks[idx-1].pop();
            if (basket.size() > 0 && basket.peek() == pick) {
                basket.pop();
                answer += 2;
            }
            else
                basket.push(pick);


            System.out.println();
            for (Stack stack : stacks)
                System.out.println(stack);

            System.out.println(basket);
        }

        return answer;
    }


}

class Stack {

    private int[] arr;

    private int size;

    private int top;

    Stack(int size) {
        this.arr = new int[size];
        this.size = 0;
        this.top = -1;
    }

    public int pop() {
        if (top == -1)
            throw new IndexOutOfBoundsException("No Entries");
        size--;
        int entry = arr[top];
        arr[top--] = 0;

        return entry;
    }

    public void push(int i) {
        if (top == arr.length-1)
            throw new IndexOutOfBoundsException("Pull Stack");
        arr[++top] = i;
        size++;
    }

    public int peek() {
        if (top == -1)
            throw new IndexOutOfBoundsException("No Entries");
        return arr[top];
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(arr);
    }
}