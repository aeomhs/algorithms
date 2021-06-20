package com.aeomhs.codekata.kakaotest;

/**
 * 돌기는 1, 홈은 0으로 표현한다.
 * lock 배열이 모두 1이 되게 하는 key 배열이 존재하는지 찾는다.
 *   0 <= i, j <= lock.length
 *   lock[i][j] == 1
 *
 */
public class LockAndKey {

    public boolean solution(int[][] key, final int[][] lock) {

        // travel each rotate pos
        for (int i = 0; i < 4; i++) {
            if (findMatchesKeyPos(key, lock))
                return true;
            key = rotateKey(key);
        }

        return false;
    }

    public boolean findMatchesKeyPos(final int[][] key, final int[][] lock) {
        int N = lock.length;
        int M = key.length;

        for (int i = 0; i < N + M; i++) {
            for (int j = 0; j < N + M; j++) {
                int[][] lockAndKey = keyIntoLock(i, j, key, lock);
                printArrays(lockAndKey);
                if (canOpenedLockAndKey(lockAndKey))
                    return true;
            }
        }

        return false;
    }

    public int[][] keyIntoLock(int lockPy, int lockPx, final int[][] key, final int[][] lock) {
        int N = lock.length;
        int[][] result = new int[N][N];
        for (int i = 0; i < N; i++)
            System.arraycopy(lock[i], 0, result[i], 0, N);

        int lockX = lockPx;
        for (int i = key.length - 1; i >= 0; i--) {
            if (lockPy < 0)
                break;

            for (int j = key.length - 1; j >= 0; j--) {
                if (lockPx < 0)
                    break;

                if (lockPy < N && lockPx < N)
                    result[lockPy][lockPx] += key[i][j];

                lockPx--;
            }
            lockPx = lockX;
            lockPy--;
        }

        return result;
    }

    public boolean canOpenedLockAndKey(int[][] lockAndKey) {
        for (int[] line : lockAndKey) {
            for (int entry : line) {
                if (entry != 1)
                    return false;
            }
        }
        return true;
    }

    public int[][] rotateKey(int[][] key) {
        int M = key.length;

        int [][] rotated = new int[M][M];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                rotated[j][M-i-1] = key[i][j];
            }
        }
        return rotated;
    }

    private void printArrays(int[][] arrays) {
        System.out.println("print Arrays");
        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[i].length; j++) {
                System.out.print(arrays[i][j]+", ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        LockAndKey test = new LockAndKey();
        boolean result = test.solution(
                new int[][] {
                        {0, 0, 0},
                        {1, 0, 0},
                        {0, 1, 1}
                },
                new int[][] {
                        {1, 1, 1},
                        {1, 1, 0},
                        {1, 0, 1}
                });

        if (result != true) throw new AssertionError("expected : true, but was " + result);

    }
}
