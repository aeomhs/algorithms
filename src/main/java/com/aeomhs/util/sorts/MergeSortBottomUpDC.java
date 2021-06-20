package com.aeomhs.util.sorts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MergeSortBottomUpDC {

    private static Comparable[] aux;

    public static void sort(Comparable[] arr) {
        aux = new Comparable[arr.length];
        int N = arr.length;

        for (int sz = 1; sz < N; sz += sz) // 1 2 4 ...
            for (int lo = 0; lo < N-sz; lo += sz+sz) // 8, 16,
                merge(arr, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));

    }

    private static void merge(Comparable[] arr, int lo, int mid, int hi) {
        assert isSorted(arr, lo, mid);
        assert isSorted(arr, mid+1, hi);

        int i = lo;
        int j = mid+1;

        for (int k = lo; k <= hi; k++) {
            aux[k] = arr[k];
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                arr[k] = aux[j++];
            else if (j > hi)
                arr[k] = aux[i++];
            else if (less(aux[j], aux[i]))
                arr[k] = aux[j++];
            else
                arr[k] = aux[i++];
        }
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static boolean isSorted(Comparable[] arr, int from, int to) {
        for (int i = from; i < to; i++) {
            if (less(arr[i+1], arr[i]))
                return false;
        }
        return true;
    }

    @Test
    public void testSort() {
        Integer[] unsorted = new Integer[] { 5, 1, 2, 6, 3, 4 };
        Integer[] sorted = new Integer[] {1, 2, 3, 4, 5, 6};
        MergeSortBottomUpDC.sort(unsorted);

        Assertions.assertArrayEquals(sorted, unsorted);
    }

    @Test
    public void testSortString() {
        String str = "T H A T I S D I V I D E A N D C O N Q U E R B O T T O M T O U P M E R G E S O R T";
        String[] unsorted = str.split(" ");
        String[] sorted = str.split(" ");

        Arrays.sort(sorted);
        ShellSort.sort(unsorted);

        Assertions.assertArrayEquals(sorted, unsorted);
        System.out.println(Arrays.toString(unsorted));
    }
}
