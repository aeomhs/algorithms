package com.aeomhs.codekata.programmers;

public class BaseballGame {

    public static void main(String[] args) {
        BaseballGame test = new BaseballGame();

        int[][] baseball = new int[][]{
            {123, 1, 1},
            {356, 1, 0},
            {327, 2, 0},
            {489, 0, 1},
        };

        int result = test.solution(baseball);

        System.out.println(result);

    }

    public int solution(int[][] baseball) {

        int result = 0;

        for (int i = 123; i <= 987; i++) {
            String answer = String.valueOf(i);
            System.out.print(answer);

            if (!isBaseballNum(answer)) {
                System.out.println(" is not baseball number");
                continue;
            }

            boolean pass = true;
            for (int j = 0; j < baseball.length; j++) {
                String query = String.valueOf(baseball[j][0]);
                int strike = baseball[j][1];
                int ball = baseball[j][2];

                if (!isStrike(answer, query, strike)) {
                    System.out.println(" is not matched ball : " + query + ", " + ball);
                    pass = false;
                    break;
                }

                if (!isBall(answer, query, ball)) {
                    System.out.println(" is not matched ball : " + query + ", " + strike);
                    pass = false;
                    break;
                }
            }

            if (pass) {
                System.out.println(" is matched!");
                result++;
            }

        }

        return result;
    }

    private boolean isBaseballNum(String answer) {
        if (answer.length() != 3)
            return false;

        if (answer.chars().anyMatch(c -> c == '0')) {
            return false;
        }

        if (answer.charAt(0) == answer.charAt(1) ||
            answer.charAt(0) == answer.charAt(2) ||
            answer.charAt(1) == answer.charAt(2)) {
            return false;
        }

        return true;
    }

    private boolean isBall(String answer, String query, int count) {
        int ballCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j != i && answer.charAt(i) == query.charAt(j))
                    ballCount++;
            }
        }

        return ballCount == count;
    }

    private boolean isStrike(String answer, String query, int count) {
        int strikeCount = 0;
        for (int i = 0; i < 3; i++) {
            if (answer.charAt(i) == query.charAt(i))
                strikeCount++;
        }

        return strikeCount == count;
    }

}
