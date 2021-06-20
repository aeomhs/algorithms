package com.aeomhs.codekata.samsung;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

class DaughtersNameTest {

//    public static void main(String[] args) throws FileNotFoundException {
//        System.setIn(new FileInputStream("/Users/aeomhs/IdeaProjects/SendBox/src/com/aeomhs/codekata/samsung/input.txt"));
//        // Scanner
//        Scanner scanner = new Scanner(System.in);
//        int T = scanner.nextInt();
//        for (int t = 1; t <= T; t++) {
//            int N, M;
//            char[][] board;
//
//            N = scanner.nextInt();
//            M = scanner.nextInt();
//            board = new char[N][M];
//            for(int i=0;i<N;i++) {
//                String s = scanner.next();
//                for(int j=0;j<M;j++) {
//                    board[i][j]=s.charAt(j);
//                }
//            }
//
//            DaughtersName daughtersName = new DaughtersName();
//            System.out.println("#" + t + " " + daughtersName.buildName(board));
//        }
//    }

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("/Users/aeomhs/IdeaProjects/SendBox/src/com/aeomhs/codekata/samsung/input.txt"));
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int T;

            String line = br.readLine();
            T = Integer.parseInt(line.trim());

            DaughtersName daughtersName = new DaughtersName();

            for (int t = 1; t <= T; t++) {
                int N, M;
                char[][] board;

                String[] rowcol = br.readLine().split(" ");
                if (rowcol.length != 2)
                    throw new IllegalArgumentException("2개의 인자가 필요합니다.");

                N = Integer.parseInt(rowcol[0]);
                M = Integer.parseInt(rowcol[1]);

                if (!((N >= 1 && M >= 1) && (N <= 2000 && M <= 2000)))
                    throw new IllegalArgumentException("1 <= N, M <= 2000");

                board = new char[N][M];

                for (int i = 0; i < N; i++) {
                    line = br.readLine();
                    char[] row = new char[M];
                    int index = 0;

                    for (char c : line.toCharArray())
                        if (c != ' ')
                            row[index++] = c;

                    board[i] = row;
                }

                if (N == 1 && M == 1) {
                    System.out.println("#" + t + " " + board[0][0]);
                    continue;
                }
                System.out.println("#" + t + " " + daughtersName.buildName(board));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void buildNameTest() {
        DaughtersName daughtersName = new DaughtersName();
        char[][] board;
        String result;
        String expected;

        board = new char[][] {
                {'a','b','c'},
                {'d','e','f'},
                {'g','h','i'},
        };
        result = daughtersName.buildName(board);
        expected = "abcfi";
        Assertions.assertEquals(expected, result);

        board = new char[][] {
                {'b','b','b','b','b'},
                {'b','b','b','b','b'},
                {'b','a','z','b','b'},
                {'b','z','b','b','b'},
                {'b','b','b','b','b'},
        };
        result = daughtersName.buildName(board);
        expected = "bbbazbbbb";
        Assertions.assertEquals(expected, result);

        board = new char[][] {
                {'p','o','n','o','c'},
                {'o','h','o','h','o'},
                {'h','l','e','p','o'},
                {'m','i','r','k','o'},
        };
        result = daughtersName.buildName(board);
        expected = "pohlepko";
        Assertions.assertEquals(expected, result);
    }
}

public class DaughtersName {

    private char[][] board;

    private int N;

    private int M;

    private String[][] mem;

    public String buildName(char[][] board) {
        this.board = board;
        this.N = board.length;
        this.M = board[0].length;
        buildNames();
        return mem[N-1][M-1];
    }

    private void buildNames() {
        mem = new String[N][M];
        mem[0][0] = String.valueOf(board[0][0]);

        for (int i = 1; i < N; i++) {
            mem[i][0] = mem[i-1][0] + board[i][0];
        }
        for (int i = 1; i < M; i++) {
            mem[0][i] = mem[0][i-1] + board[0][i];
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                String left = mem[i][j-1];
                String up = mem[i-1][j];

                if (left.compareTo(up) < 0){
                    mem[i][j] = left+board[i][j];
                } else {
                    mem[i][j] = up+board[i][j];
                }
            }
        }
    }

    public String spread() {
        LinkedList<Point> queue = new LinkedList<>();
        PriorityQueue<String> nameList = new PriorityQueue<>();
        Point o = new Point(0, 0);
        o.subName = new StringBuilder();
        queue.addLast(o);

        while (!queue.isEmpty()) {
            // Selected char
            Point pos = queue.removeFirst();
            pos.subName.append(board[pos.row][pos.col]);

            // Escape
            if (pos.row == N-1 && pos.col == M-1) {
                nameList.add(pos.subName.toString());
                continue;
            }

            // Spread Right Or Down
            Point right = pos.right();
            right.subName = new StringBuilder(pos.subName.toString());
            Point down = pos.down();
            down.subName = new StringBuilder(pos.subName.toString());

            boolean endOfCol = right.col == M;
            boolean endOfRow = down.row == N;

            if (endOfCol && !endOfRow)
                queue.addLast(down);

            if (!endOfCol && endOfRow)
                queue.addLast(right);

            if (!endOfCol && !endOfRow){
                if (board[right.row][right.col] < board[down.row][down.col]) {
                    queue.addLast(right);
                }
                else if (board[right.row][right.col] > board[down.row][down.col]) {
                    queue.addLast(down);
                } else {
                    queue.addLast(right);
                    queue.addLast(down);
                }
            }
        }

        return nameList.poll();
    }

    static class Point {
        int row, col;
        StringBuilder subName;

        Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        Point right() {
            return new Point(this.row, this.col+1);
        }

        Point down() {
            return new Point(this.row+1, this.col);
        }

        public String toString() {
            return "("+row+","+col+")";
        }
    }
}

