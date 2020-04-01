package com.aeomhs.codekata.programmers;

public class BiggestSquare {
    public int solution(int [][]board)
    {
        int answer = board[0][0];

        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board[0].length; j++) {
                if (board[i][j] == 0)
                    continue;

                int leftTop = board[i-1][j-1];
                int left = board[i][j-1];
                int upper = board[i-1][j];

                board[i][j] += Math.min(left, Math.min(upper, leftTop));

                if (answer < board[i][j])
                    answer = board[i][j];
            }
        }

        return answer * answer;
    }
}
