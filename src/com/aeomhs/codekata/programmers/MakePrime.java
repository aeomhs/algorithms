package com.aeomhs.codekata.programmers;

import java.util.Arrays;

public class MakePrime {

    private static final int MAX_NUM = 3000;

    private static final int PICK_NUM = 3;

    private boolean[] isPrime;

    private int count = 0;

    public static void main(String[] args) {
        MakePrime test = new MakePrime();
        test.solution(new int[]{
                1,2,3,4
        });
        System.out.println();
        test.solution(new int[]{
                1,2,4,7,6
        });
    }

    public int solution(int[] nums) {
        eratosArr();

        comb(nums, 0, 0, nums.length, 0, PICK_NUM);

        return count;
    }

    private void comb(int[] arr, int sum, int index, int n, int k, int r) {
        if (k == r) {
            System.out.println(sum);
            if (isPrime[sum])
                count++;
            return;
        }

        if (index == n)
            return;

        comb(arr, sum, index+1, n, k, r);
        comb(arr, sum+arr[index], index+1, n, k+1, r);
    }

    // n 개 중에 r개를 뽑는다.
    private void perm(int[] arr, int n, int index, int r) {
        if (index == r) {
            System.out.println(Arrays.toString(arr));

            int sum = 0;
            for (int i = 0; i < PICK_NUM; i++) {
                sum += arr[i];
            }

            if (isPrime[sum]){
                System.out.println(sum);
                count++;
            }
        }

        for (int i = index; i < n; i++) {
            swap(arr, i, index);
            perm(arr, n, index+1, r);
            swap(arr, i, index);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private void eratosArr() {
        isPrime = new boolean[MAX_NUM+1];
        Arrays.fill(isPrime, true);

        isPrime[1] = false;

        for (int i = 2; i <= MAX_NUM; i++) {
            for (int j = 2; i * j <= MAX_NUM; j++) {
                isPrime[i*j] = false;
            }
        }

//        for (int i=1; i <= MAX_NUM; i++) {
//            System.out.print("("+i+")"+isPrime[i]+" ");
//            if (i % 5 == 0)
//                System.out.println();
//        }
    }
}
