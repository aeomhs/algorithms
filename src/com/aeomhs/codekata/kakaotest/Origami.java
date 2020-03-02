package com.aeomhs.codekata.kakaotest;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 오른쪽에서 왼쪽으로 n번 접는다.
 * 다시 종이를 편 상태에서 들어간 부분은 0으로, 나온 부분은 1로 출력한다.
 *
 * n -> 규칙 () 바로 이전에 생긴 자국, [] 그 이전에 생긴 자국, 괄호 없음은 지금 생긴 자국
 * 1 -> 0
 * 2 -> 0 (0) 1
 * 3 -> 0 (0) 1 [0] 0 (1) 1
 * 4 -> 0 (0) 1 [0] 0 (1) 1 [0] 0 (0) 1 [1] 0 (1) 1
 *
 * -> () 양 옆으로 0과 1이 순서대로 발생한다.
 */
public class Origami {

    // 재사용
    private String regex = "\\(([0,1])\\)";

    /**
     *  () 양 옆으로 숫자를 추가, 새로운 숫자를 () 괄호로 감싸고 기존 ()는 제거
     *  [] 사용 X
     *  -> (0)
     *  -> (0)0(1)
     *  -> (0)0(1)0(0)1(1)
     */
    public String fold(String paper, int n) {
        // 종이 접기 끝
        if (n == 0)
            return paper;

        // 첫 번째 접기
        if (paper.length() == 0) {
            return fold("(0)", n-1);
        }

        paper = paper.replaceAll(this.regex, "(0)$1(1)");
        System.out.println(paper);

        return fold(paper, n-1);
    }

    public int[] solution(int n) {

        String result = fold("", n);
        result = result.replaceAll("\\(" , "");
        result = result.replaceAll("\\)" , "");

        int[] answer = new int[result.length()];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = result.charAt(i) - '0';
        }

        System.out.println(Arrays.toString(answer));

        return answer;
    }

//    // Optz Failed
//    public int[] fold(int[] paper, int n) {
//
//        if (n == 0)
//            return paper;
//
//        if (paper == null) {
//            return fold(new int[] {0}, n-1);
//        }
//
//        LinkedList<Integer> newPaper = new LinkedList<>();
//
//        for (int lineBinary : paper) {
//            if (lineBinary < 2) {
//                newPaper.add(0);
//                newPaper.add(lineBinary + 2);
//                newPaper.add(1);
//            }
//            else
//                newPaper.add(lineBinary);
//        }
//
//        paper = new int[newPaper.size()];
//        for (int i = 0; i < paper.length; i++) {
//            paper[i] = newPaper.get(i);
//        }
//
//        return fold(paper, n-1);
//    }
//
//    public int[] solution(int n) {
//        int[] answer = fold(null, n);
//        for (int i = 0; i < answer.length; i++) {
//            if (answer[i] > 1)
//                answer[i] -= 2;
//            System.out.print(answer[i] + ", ");
//        }
//
//        return answer;
//    }

    public static void main(String[] args) {
        Origami test = new Origami();
        test.solution(3);
    }
}
