package com.aeomhs.codekata.samsung;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class DaughtersNameTest {

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("/Users/aeomhs/IdeaProjects/SendBox/src/com/aeomhs/codekata/samsung/input.txt"));

        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            int T;

            String line = br.readLine();
            T = Integer.parseInt(line.trim());

            DaughtersName daughtersName = new DaughtersName();

            for(int t = 1; t <= T; t++) {
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

                if (N == 1 && M == 1){
                    System.out.println("#"+t+" "+ board[0][0]);
                    continue;
                }
                System.out.println("#"+t+" "+ daughtersName.buildName(board));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            br.close();
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

    private List<String> candidateNameList;

    public String buildName(char[][] board) {
        this.board = board;
        this.N = board.length;
        this.M = board[0].length;

        this.candidateNameList = new ArrayList<>();

        buildName(0, 0, N+M-2, new StringBuilder(), 0);

//        System.out.println(candidateNameList);
        return Collections.min(candidateNameList);
    }

//    private void buildName(int row, int col, int depth, StringBuilder builder, int index) {
//        // append
//        builder.append(board[row][col]);
//
////        System.out.println("["+row + "," + col + "]"+"("+depth+")"+builder.toString());
//
//        // build new Name
//        if (depth == 0 && row == N-1 && col == M-1) {
//            candidateNameList.add(builder.toString());
//            builder.delete(index-1, index);
//            return;
//        }
//
//        // Fail to Build
//        if (depth == 0){
//            builder.delete(index-1, index);
//            return;
//        }
//
//        if (col < M-1){
//            // go to Right
//            buildName(row, col+1, depth-1, builder, index+1);
//        }
//
//        if (row < N-1){
//            // go to Down
//            buildName(row+1, col, depth-1, builder, index+1);
//        }
//
//        builder.delete(index, index+1);
//    }
    private void buildName(int row, int col, int depth, StringBuilder builder, int index) {
        builder.append(board[row][col]);

        // build new Name
        if (depth == 0 && row == N-1 && col == M-1) {
            candidateNameList.add(builder.toString());
            builder.delete(index-1, index);
            return;
        }

        // Fail to Build
        if (depth == 0){
            builder.delete(index-1, index);
            return;
        }

        // 아래로만 진행
        if (col == M-1 && row < N-1){
            buildName(row+1, col, depth-1, builder, index+1);
        }
        // 오른쪽으로만 진행
        else if (row == N-1 && col < M-1){
            buildName(row, col+1, depth-1, builder, index+1);
        }
        // 오른쪽 아래 중 character 작은 값 선택
        else if ((board[row + 1][col] < board[row][col + 1])) {
            buildName(row+1, col, depth-1, builder, index+1);
        } else if  ((board[row + 1][col] > board[row][col + 1])){
            buildName(row, col+1, depth-1, builder, index+1);
        } else {
            buildName(row+1, col, depth-1, builder, index+1);
            buildName(row, col+1, depth-1, builder, index+1);
        }

        builder.delete(index, index+1);
    }
}

