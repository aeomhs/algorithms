package com.aeomhs.codekata.kakaotest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

// TODO Not Solved
public class CandidateKeys {

    private LinkedList<String> keys;

    // 유일성 검사
    private HashSet<String> candidateKeys;

    // 최소성 검사
    private LinkedList<int[]> usedAttr;

    private int[] used;

    private boolean[] usedCandidateKey;

    public int solution(String[][] relation) {
        int count = 0;
        candidateKeys = new HashSet<>();

        int row = relation.length;
        int col = relation[0].length;

        usedCandidateKey = new boolean[col];

        // 조합 선택 개수
        for (int i = 1; i <= col; i++){
            // 조합 결과 저장 배열
            String[][] keyCombArr = new String[row][];

            // 각 row에 대해 i개 조합 키 구하기
            for (int r = 0; r < row; r++) {
                keys = new LinkedList<>();
                usedAttr = new LinkedList<>();
                used = new int[i];

                keyBuild(relation[r], 0, i, 0, new StringBuilder());


                if (keys.size() == 0)
                    break;
                System.out.println("{size : "+keys.size()+"}" + keys);

                keyCombArr[r] = new String[keys.size()];
                for (int cn = 0; cn < keys.size(); cn++)
                    keyCombArr[r][cn] = keys.get(cn);
            }

            if (keys.size() == 0)
                continue;

            for (int[] selectedSetIndex : usedAttr)
                System.out.print(Arrays.toString(selectedSetIndex) + ", ");
            System.out.println();

            // 유일성 체크
            for (int cn = 0; cn < keyCombArr[0].length; cn++) {
                HashSet<String> set = new HashSet<>();
                for (int r = 0; r < row; r++) {
                    // 유일성 불만족
                    if (set.contains(keyCombArr[r][cn]))
                        break;
                    set.add(keyCombArr[r][cn]);
                }

                // 유일성 만족
                if (set.size() == row) {
                    System.out.println(set);
                    candidateKeys.addAll(set);
                    count++;
                    // 최소성
                    for (int selected : usedAttr.get(cn))
                        usedCandidateKey[selected] = true;
                }
            }
        }

        System.out.println(candidateKeys);
        return count;
    }

    @Test
    public void solutionTest() {
        CandidateKeys solution = new CandidateKeys();
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(2, solution.solution(
                new String[][] {
                        {"100","ryan","music","2"},
                        {"200","apeach","math","2"},
                        {"300","tube","computer","3"},
                        {"400","con","computer","4"},
                        {"500","muzi","music","3"},
                        {"600","apeach","music","2"}
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(3, solution.solution(
                new String[][] {
                        {"a","aa","aaa","1"},
                        {"b","bb","bbb","2"},
                        {"c","cc","ccc","3"},
                        {"a","cc","ddd","4"},
                        {"b","aa","eee","5"},
                        {"c","bb","fff","6"}
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(1, solution.solution(
                new String[][] {
                        {"a"},
                        {"b"},
                        {"c"},
                        {"aa"},
                        {"aaa"},
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(2, solution.solution(
                new String[][] {
                        {"a", "b", "ab"},
                        {"b", "c", "bc"},
                        {"a", "c", "ac"},
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(2, solution.solution(
                new String[][] {
                        {"a", "b", "ab"},
                        {"b", "c", "bc"},
                        {"a", "c", "ac"},
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(0, solution.solution(
                new String[][] {
                        {"xa", "b", "1"},
                        {"bx", "c", "1"},
                        {"cx", "c", "1"},
                        {"bx", "b", "1"},
                        {"c", "xc", "1"},
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(3, solution.solution(
                new String[][] {
                        {"a", "bbc", "5", "4"},
                        {"b", "aab", "4", "4"},
                        {"c", "bab", "3", "4"},
                        {"a", "baa", "2", "1"},
                        {"b", "bbb", "1", "1"},
                }
        ));
        System.out.println("=====Test Case =====");
        Assertions.assertEquals(4, solution.solution(
                new String[][] {
                        {"1", "0", "0", "0"},
                        {"0", "2", "0", "0"},
                        {"0", "0", "3", "0"},
                        {"0", "0", "0", "4"},
                }
        ));
    }

    /**
     * Attribute r개를 뽑아 조합하여 키를 생성한다.
     */
    public void keyBuild(String[] row, int index, int r, int k, StringBuilder builder) {
        // 키 생성 완료
        if (k == r) {
            keys.add(builder.toString());
            usedAttr.add(used.clone());
            return;
        }

        // index 조건 불변식 : index < row.length
        if (index >= row.length)
            return;

        // row[index] 선택 안함
        keyBuild(row, index+1, r, k, builder);

        // row[index] 등록된 후보키인지 확인
        if (usedCandidateKey[index])
            return;

        // row[index] 선택
        used[k] = index;
        builder.append(row[index]);
        keyBuild(row, index+1, r, k+1, builder);

        // 선택 취소
        int start = builder.lastIndexOf(row[index]);
        int end = start + row[index].length();
        builder.delete(start, end);
    }
}