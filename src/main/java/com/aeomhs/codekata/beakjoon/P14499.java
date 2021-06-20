package com.aeomhs.codekata.beakjoon;

import java.io.*;
import java.util.HashMap;

public class P14499 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            String[] buffer = br.readLine().split(" ");

            int N = Integer.parseInt(buffer[0]);
            int M = Integer.parseInt(buffer[1]);
            int[][] map = new int[N][M];

            int row = Integer.parseInt(buffer[2]);
            int col = Integer.parseInt(buffer[3]);
            Location o = new Location(row, col);

            int K = Integer.parseInt(buffer[4]);

            for (int i = 0; i < N; i++) {
                buffer = br.readLine().split(" ");
                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(buffer[j]);
                }
            }

            int[] commands = new int[K];
            buffer = br.readLine().split(" ");
            for (int i = 0; i < K; i++) {
                commands[i] = Integer.parseInt(buffer[i]);
            }

            Dice dice = new Dice(o);

            for (int i = 0; i < K; i++) {
                Direction direction = Direction.getByValue(commands[i]);
                // true : can move!
                if (dice.roll(N, M, direction)) {
                    if (map[dice.location.row][dice.location.col] == 0) {
                        map[dice.location.row][dice.location.col] = dice.bottom;
                    } else {
                        dice.bottom = map[dice.location.row][dice.location.col];
                        map[dice.location.row][dice.location.col] = 0;
                    }
                    bw.write(dice.top+"\n");
                }
            }

            bw.flush();
        }
    }

    static class Dice {

        int front, back, left, right, top, bottom;

        Location location;

        Dice(Location location) {
            this.location = location;
            front = 0;
            back = 0;
            left = 0;
            right = 0;
            top = 0;
            bottom = 0;
        }

        boolean roll(int N, int M, Direction direction) {
            // cannot move
            Location next = location.next(direction);
            if (!next.isValid(N, M))
                return false;

            // move & rotate
            location = next;
            rotate(direction);

            return true;
        }

        void rotate(Direction direction) {
            int temp = top;
            switch (direction) {
                case EAST:
                    top = left;
                    left = bottom;
                    bottom = right;
                    right = temp;
                    break;
                case WEST:
                    top = right;
                    right = bottom;
                    bottom = left;
                    left = temp;
                    break;
                case NORTH:
                    top = front;
                    front = bottom;
                    bottom = back;
                    back = temp;
                    break;
                case SOUTH:
                    top = back;
                    back = bottom;
                    bottom = front;
                    front = temp;
                    break;
            }
        }
    }

    enum Direction {
        // 동쪽은 1, 서쪽은 2, 북쪽은 3, 남쪽은 4
        EAST(1), WEST(2), NORTH(3), SOUTH(4);

        static final HashMap<Integer, Direction> map = new HashMap<>();

        static {
            for (Direction direction : Direction.values()) {
                map.put(direction.value, direction);
            }
        }

        int value;

        Direction(int value) {
            this.value = value;
        }

        static Direction getByValue(int val) {
            return map.get(val);
        }
    }

    static class Location {
        int row, col;

        public Location(int row, int col) {
            this.row = row;
            this.col = col;
        }

        boolean isValid(int N, int M) {
            return !(row < 0 || col < 0 || row >= N || col >= M);
        }

        Location next(Direction direction) {
            switch (direction) {
                case EAST:
                    return new Location(row, col+1);
                case WEST:
                    return new Location(row, col-1);
                case NORTH:
                    return new Location(row-1, col);
                case SOUTH:
                    return new Location(row+1, col);
            }

            throw new RuntimeException("Cannot!!");
        }
    }
}
