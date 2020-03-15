package com.aeomhs.codekata.kakaotest;

import org.junit.jupiter.api.Assertions;

import java.util.*;


/**
 * dist 내림차순 정렬,
 * 투입하는 크루 인원 수 1명씩 증가하면서
 * 할당하는 취약 외벽 permutation
 * TODO 최적화
 *  테스트케이스 1개 시간초과 실패.
 */
public class WeakWallChecking {

    private int N;

    private int[] crews;

    public int solution(int n, int[] weak, int[] dist) {
        this.N = n;

        // dist 내림차순 정렬
        Arrays.sort(dist);
        int[] descendingDist = new int[dist.length];
        for (int i = 0; i < dist.length; i++)
            descendingDist[i] = dist[dist.length-i-1];

        this.crews = descendingDist;

        // 투입하는 크루 인원 수 1명씩 증가
        for (int c = 1; c <= dist.length; c++) {
            System.out.println(" "+c);
            if (permWeakWall(weak, 0, weak.length, c))
                return c;
        }

        return -1;
    }

    // n개의 취약 외벽 중 r개를 뽑는다.
    private boolean permWeakWall(int[] weak, int depth, int n, int r) {
        if(depth == r) {
            boolean[] wall = initWeakWall(weak);

            for (int i = 0; i < r; i++) {
                checkWallsOneCrew(wall, weak[i], (weak[i] + crews[i]));
            }

            return isAllChecked(wall);
        }

        for(int i = depth; i < n; i++) {
            int temp = weak[depth];
            weak[depth] = weak[i];
            weak[i] = temp;

            if (permWeakWall(weak, depth + 1, n, r))
                return true;

            weak[i] = weak[depth];
            weak[depth] = temp;
        }

        return false;
    }

    private boolean[] initWeakWall(int[] weak) {
        boolean[] wall = new boolean[N];
        for (int weakWall : weak)
            wall[weakWall] = true;
        return wall;
    }

    private void checkWallsOneCrew(boolean[] wall, int from, int to) {
        for (int i = from; i <= to; i++)
            wall[i%N] = false;
    }

    private boolean isAllChecked(boolean[] wall) {
        for (boolean b : wall) {
            if (b) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        WeakWallChecking test = new WeakWallChecking();
        int result;
        result = test.solution(12, new int[]{1, 5, 6, 10}, new int[]{1, 2, 3, 4});
        Assertions.assertEquals(2, result);

        System.out.println("====TEST CASE====");
        result = test.solution(1, new int[]{0}, new int[]{1});
        Assertions.assertEquals(1, result);

        result = test.solution(3, new int[]{1, 2}, new int[]{1});
        Assertions.assertEquals(1, result);

        result = test.solution(3, new int[]{0, 1, 2}, new int[]{1});
        Assertions.assertEquals(-1, result);

        result = test.solution(3, new int[]{0, 1, 2}, new int[]{3});
        Assertions.assertEquals(1, result);

        result = test.solution(10, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, new int[]{10});
        Assertions.assertEquals(1, result);

        result = test.solution(3, new int[]{0, 2}, new int[]{1});
        Assertions.assertEquals(1, result);

        result = test.solution(3, new int[]{0, 1, 2}, new int[]{3});
        Assertions.assertEquals(1, result);

        result = test.solution(9, new int[]{0, 1, 3, 4, 6, 8}, new int[]{1, 1, 2});
        Assertions.assertEquals(3, result);

        result = test.solution(9, new int[]{0, 1, 3, 5, 7, 8}, new int[]{1, 1, 2});
        Assertions.assertEquals(3, result);

        result = test.solution(9, new int[]{0, 2, 4, 6, 8}, new int[]{1, 2, 3, 8, 4, 2});
        Assertions.assertEquals(1, result);

        result = test.solution(16, new int[]{0, 2, 4, 6, 12, 14, 15}, new int[]{4, 1, 1, 3, 1, 4});
        Assertions.assertEquals(2, result);

        result = test.solution(16, new int[]{0, 1, 2, 3, 4, 15}, new int[]{5});
        Assertions.assertEquals(1, result);

        result = test.solution(50, new int[]{1}, new int[]{6});
        Assertions.assertEquals(1, result);
    }
}