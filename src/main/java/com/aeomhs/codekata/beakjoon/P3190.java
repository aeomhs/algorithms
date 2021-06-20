package com.aeomhs.codekata.beakjoon;

import java.io.*;
import java.util.*;

public class P3190 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int N = Integer.parseInt(br.readLine());

            int K = Integer.parseInt(br.readLine());
            Set<Location> apples = new HashSet<>();
            for (int i = 0; i < K; i++) {
                String[] buffer = br.readLine().split(" ");
                apples.add(new Location(Integer.parseInt(buffer[0])-1, Integer.parseInt(buffer[1])-1));
            }

            int L = Integer.parseInt(br.readLine());
            Queue<Command> commands = new LinkedList<>();
            for (int i = 0; i < L; i++) {
                String[] buffer = br.readLine().split(" ");
                commands.offer(new Command(Integer.parseInt(buffer[0]), buffer[1].charAt(0)));
            }

            Snake snake = new Snake();
            int count = 0;
            while (true) {

                if (!commands.isEmpty() && count == commands.peek().sec) {
                    snake.rotate(commands.poll());
                }

                if (!snake.forward(N, apples)) {
                    break;
                }

                count++;
            }

            bw.write((count+1)+"\n");
            bw.flush();
        }
    }

    static class Snake {

        Queue<Location> body;

        Location head;

        Direction direction;

        Snake() {
            body = new LinkedList<>();
            head = new Location(0, 0);
            direction = Direction.EAST;
        }

        boolean forward(int N, Set<Location> apples) {
            Location next = direction.next(head);

            // end condition
            if (next.isOutOfSquare(N) || body.contains(next)) {
                return false;
            }

            body.offer(head);
            head = next;

            // apple
            if (apples.contains(next)) {
                apples.remove(next);
            }
            else {
                body.poll();
            }

            return true;
        }

        void rotate(Command command) {
            direction = direction.rotate(command);
        }
    }

    enum Direction {
        EAST, SOUTH, WEST, NORTH;

        Location next(Location location) {
            switch (this) {
                case EAST:
                    return new Location(location.row, location.col+1);
                case SOUTH:
                    return new Location(location.row+1, location.col);
                case WEST:
                    return new Location(location.row, location.col-1);
                case NORTH:
                    return new Location(location.row-1, location.col);
            }

            throw new RuntimeException("Cannot!!");
        }

        Direction rotate(Command command) {
            switch (this) {
                case EAST:
                    return command.value == 'L' ? NORTH : SOUTH;
                case SOUTH:
                    return command.value == 'L' ? EAST : WEST;
                case WEST:
                    return command.value == 'L' ? SOUTH : NORTH;
                case NORTH:
                    return command.value == 'L' ? WEST : EAST;
            }

            throw new RuntimeException("Cannot!!");
        }
    }

    static class Location {
        int row, col;

        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }

        boolean isOutOfSquare(int N) {
            return row < 0 || col < 0 || row >= N || col >= N;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Location)) return false;
            Location location = (Location) o;
            return row == location.row &&
                    col == location.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    static class Command {
        int sec;
        char value;

        Command(int sec, char value) {
            this.sec = sec;
            this.value = value;
        }
    }
}
