package com.aeomhs.codekata.beakjoon;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class P13460 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int N, M;
            String[] buffer = br.readLine().split(" ");
            N = Integer.parseInt(buffer[0]);
            M = Integer.parseInt(buffer[1]);

            boolean[][] map = new boolean[N][M];
            Position o = null, b = null, r = null;
            for (int i = 0; i < N; i++) {
                buffer = br.readLine().split("");
                for (int j = 0; j < M; j++) {
                    switch (buffer[j]) {
                        case "#":
                            map[i][j] = false;
                            break;
                        case ".":
                            map[i][j] = true;
                            break;
                        case "R":
                            r = new Position(i, j);
                            map[i][j] = true;
                            break;
                        case "B":
                            b = new Position(i, j);
                            map[i][j] = true;
                            break;
                        case "O":
                            o = new Position(i, j);
                            map[i][j] = true;
                            break;
                    }
                }
            }

            Board board = new Board(map, o, N, M);
            Queue<RedBluePositionSet> queue = new LinkedList<>();
            RedBluePositionSet initSet = new RedBluePositionSet(r, b);
            initSet.count = 0;
            queue.offer(initSet);

            while (!queue.isEmpty()) {
                RedBluePositionSet rbPos = queue.poll();

                if (rbPos.count > 10)
                    continue;

                if (board.isGoal(rbPos.r.row, rbPos.r.col)) {
                    bw.write(rbPos.count+"\n");
                    bw.flush();
                    return;
                }

                RedBluePositionSet leanLeft = board.leanLeft(rbPos);
                RedBluePositionSet leanRight= board.leanRight(rbPos);
                RedBluePositionSet leanUp = board.leanUp(rbPos);
                RedBluePositionSet leanDown = board.leanDown(rbPos);

                if (!(rbPos.r.row == leanLeft.r.row && rbPos.r.col == leanLeft.r.col &&
                        rbPos.b.row == leanLeft.b.row && rbPos.b.col == leanLeft.b.col) &&
                    !board.isGoal(leanLeft.b.row, leanLeft.b.col)) {
                    leanLeft.count = rbPos.count+1;
                    queue.offer(leanLeft);
                }

                if (!(rbPos.r.row == leanRight.r.row && rbPos.r.col == leanRight.r.col &&
                        rbPos.b.row == leanRight.b.row && rbPos.b.col == leanRight.b.col) &&
                    !board.isGoal(leanRight.b.row, leanRight.b.col)) {
                    leanRight.count = rbPos.count+1;
                    queue.offer(leanRight);
                }

                if (!(rbPos.r.row == leanUp.r.row && rbPos.r.col == leanUp.r.col &&
                        rbPos.b.row == leanUp.b.row && rbPos.b.col == leanUp.b.col) &&
                    !board.isGoal(leanUp.b.row, leanUp.b.col)) {
                    leanUp.count = rbPos.count+1;
                    queue.offer(leanUp);
                }

                if (!(rbPos.r.row == leanDown.r.row && rbPos.r.col == leanDown.r.col &&
                        rbPos.b.row == leanDown.b.row && rbPos.b.col == leanDown.b.col) &&
                    !board.isGoal(leanDown.b.row, leanDown.b.col)) {
                    leanDown.count = rbPos.count+1;
                    queue.offer(leanDown);
                }
            }

            bw.write("-1\n");
            bw.flush();
        }
    }

    static class Board {
        // true : non-blocked, N x M
        final boolean[][] map;

        final Position o;

        final int N, M;

        Board(boolean[][] map, Position o, int N, int M) {
            this.map = map;
            this.o = o;
            this.N = N;
            this.M = M;
        }

        boolean isBlocked(int row, int col) {
            return !map[row][col];
        }

        RedBluePositionSet leanLeft(final RedBluePositionSet rbPos) {
            Position leftPos = null, rightPos = null;

            if (rbPos.r.col < rbPos.b.col) {
                leftPos = new Position(rbPos.r.row, rbPos.r.col);
                rightPos = new Position(rbPos.b.row, rbPos.b.col);
            } else {
                rightPos = new Position(rbPos.r.row, rbPos.r.col);
                leftPos = new Position(rbPos.b.row, rbPos.b.col);
            }

            while (!isBlocked(leftPos.row, leftPos.col-1)) {
                leftPos.col -= 1;
                if (isGoal(leftPos.row, leftPos.col))
                    break;
            }

            while (!isBlocked(rightPos.row, rightPos.col-1)) {
                if (!isGoal(leftPos.row, leftPos.col) && (leftPos.row == rightPos.row && leftPos.col == rightPos.col-1))
                    break;
                rightPos.col -= 1;
                if (isGoal(rightPos.row, rightPos.col))
                    break;
            }

            return rbPos.r.col < rbPos.b.col ? new RedBluePositionSet(leftPos, rightPos) : new RedBluePositionSet(rightPos, leftPos);
        }

        RedBluePositionSet leanRight(final RedBluePositionSet rbPos) {
            Position leftPos = null, rightPos = null;

            if (rbPos.r.col < rbPos.b.col) {
                leftPos = new Position(rbPos.r.row, rbPos.r.col);
                rightPos = new Position(rbPos.b.row, rbPos.b.col);
            } else {
                rightPos = new Position(rbPos.r.row, rbPos.r.col);
                leftPos = new Position(rbPos.b.row, rbPos.b.col);
            }

            while (!isBlocked(rightPos.row, rightPos.col+1)) {
                rightPos.col += 1;
                if (isGoal(rightPos.row, rightPos.col))
                    break;
            }

            while (!isBlocked(leftPos.row, leftPos.col+1)) {
                if (!isGoal(rightPos.row, rightPos.col) && (leftPos.row == rightPos.row && leftPos.col+1 == rightPos.col))
                    break;
                leftPos.col += 1;
                if (isGoal(leftPos.row, leftPos.col))
                    break;
            }

            return rbPos.r.col < rbPos.b.col ? new RedBluePositionSet(leftPos, rightPos) : new RedBluePositionSet(rightPos, leftPos);
        }

        RedBluePositionSet leanUp(final RedBluePositionSet rbPos) {
            Position upPos = null, downPos = null;

            if (rbPos.r.row < rbPos.b.row) {
                upPos = new Position(rbPos.r.row, rbPos.r.col);
                downPos = new Position(rbPos.b.row, rbPos.b.col);
            } else {
                downPos = new Position(rbPos.r.row, rbPos.r.col);
                upPos = new Position(rbPos.b.row, rbPos.b.col);
            }

            while (!isBlocked(upPos.row-1, upPos.col)) {
                upPos.row -= 1;
                if (isGoal(upPos.row, upPos.col))
                    break;
            }

            while (!isBlocked(downPos.row-1, downPos.col)) {
                if (!isGoal(upPos.row, upPos.col) && (upPos.row == downPos.row-1 && upPos.col == downPos.col))
                    break;
                downPos.row -= 1;
                if (isGoal(downPos.row, downPos.col))
                    break;
            }

            return rbPos.r.row < rbPos.b.row ? new RedBluePositionSet(upPos, downPos) : new RedBluePositionSet(downPos, upPos);
        }

        RedBluePositionSet leanDown(final RedBluePositionSet rbPos) {
            Position upPos = null, downPos = null;

            if (rbPos.r.row < rbPos.b.row) {
                upPos = new Position(rbPos.r.row, rbPos.r.col);
                downPos = new Position(rbPos.b.row, rbPos.b.col);
            } else {
                downPos = new Position(rbPos.r.row, rbPos.r.col);
                upPos = new Position(rbPos.b.row, rbPos.b.col);
            }

            while (!isBlocked(downPos.row+1, downPos.col)) {
                downPos.row += 1;
                if (isGoal(downPos.row, downPos.col))
                    break;
            }

            while (!isBlocked(upPos.row+1, upPos.col)) {
                if (!isGoal(downPos.row, downPos.col) && (upPos.row+1 == downPos.row && upPos.col == downPos.col))
                    break;
                upPos.row += 1;
                if (isGoal(upPos.row, upPos.col))
                    break;
            }

            return rbPos.r.row < rbPos.b.row ? new RedBluePositionSet(upPos, downPos) : new RedBluePositionSet(downPos, upPos);
        }

        boolean isGoal(int row, int col) {
            return o.col == col && o.row == row;
        }

    }

    static class RedBluePositionSet {
        Position r, b;
        int count;

        public RedBluePositionSet(Position r, Position b) {
            this.r = r;
            this.b = b;
        }
    }

    static class Position {
        int row, col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }
    }
}
