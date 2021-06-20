package com.aeomhs.codekata.beakjoon;

import java.io.*;
import java.util.Arrays;

public class P14500 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int N, M;
            String[] buffer = br.readLine().split(" ");
            N = Integer.parseInt(buffer[0]);
            M = Integer.parseInt(buffer[1]);
            int[][] map = new int[N][M];
            for (int i = 0; i < N; i++) {
                buffer = br.readLine().split(" ");
                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(buffer[j]);
                }
            }

            int max = -1;
            for (Tetromino shape : Tetromino.values()) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {
                        int sum = shape.sum(map, N, M, new Location(i, j));
                        if (max < sum){
                            max = sum;
                        }
                    }
                }
            }

            bw.write(max + "\n");
            bw.flush();
        }
    }

    enum Tetromino {
        TYPE1(Block.RIGHT, Block.RIGHT, Block.RIGHT),
        TYPE2(Block.UP, Block.UP, Block.UP),
        TYPE3(Block.RIGHT, Block.DOWN, Block.LEFT),
        TYPE4(Block.DOWN, Block.DOWN, Block.RIGHT),
        TYPE5(Block.RIGHT, Block.RIGHT, Block.UP),
        TYPE6(Block.RIGHT, Block.DOWN, Block.DOWN),
        TYPE7(Block.UP , Block.RIGHT, Block.RIGHT),
        TYPE8(Block.RIGHT , Block.UP, Block.RIGHT),
        TYPE9(Block.DOWN , Block.RIGHT, Block.DOWN),

        TYPE10(Block.DOWN , Block.LEFT, Block.RIGHT_DOWN),
        TYPE11(Block.RIGHT , Block.UP, Block.RIGHT_DOWN),
        TYPE12(Block.LEFT , Block.LEFT, Block.RIGHT_DOWN),
        TYPE13(Block.UP , Block.UP, Block.RIGHT_DOWN),

        TYPE14(Block.LEFT, Block.DOWN, Block.DOWN),
        TYPE15(Block.RIGHT, Block.RIGHT, Block.DOWN),
        TYPE16(Block.RIGHT, Block.UP, Block.UP),
        TYPE17(Block.DOWN, Block.RIGHT, Block.RIGHT),
        TYPE18(Block.UP, Block.RIGHT, Block.UP),
        TYPE19(Block.RIGHT, Block.DOWN, Block.RIGHT);

        Block[] blocks;

        Tetromino(Block... blocks) {
            this.blocks = blocks;
        }

        int sum(int[][] map, int N, int M, final Location location) {
            if (location.isNotValid(N, M))
                return -1;

            int sum = map[location.row][location.col];

            Location next = location;
            for (Block block : blocks) {
                next = block.nextBlockLocation(next);
                if (next.isNotValid(N, M))
                    return -1;
                sum += map[next.row][next.col];
            }

            return sum;
        }
    }

    enum Block {
        UP, DOWN, RIGHT, LEFT, RIGHT_DOWN;

        Location nextBlockLocation(final Location location) {
            switch (this) {
                case UP:
                    return new Location(location.row-1, location.col);
                case DOWN:
                    return new Location(location.row+1, location.col);
                case RIGHT:
                    return new Location(location.row, location.col+1);
                case LEFT:
                    return new Location(location.row, location.col-1);
                case RIGHT_DOWN:
                    return new Location(location.row+1, location.col+1);
            }
            throw new RuntimeException("Cannot ");
        }

        @Override
        public String toString() {
            return this.name();
        }
    }

    static class Location {
        final int row, col;

        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }

        boolean isNotValid(int N, int M) {
            return (row < 0 || col < 0 || row >= N || col >= M);
        }

        @Override
        public String toString() {
            return row+","+col;
        }
    }
}
