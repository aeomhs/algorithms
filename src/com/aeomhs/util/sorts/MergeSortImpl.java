package com.aeomhs.util.sorts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MergeSortImpl {

    private static final int CUTOFF = 5;

    private static Comparable[] aux;

    public static void sort(Comparable[] arr) {
        aux = new Comparable[arr.length];
        sort(arr, 0, arr.length-1);
    }

    private static void sort(Comparable[] arr, int lo, int hi) {
        // 작은 부분 배열에 대해서는 삽입 정렬을 시도한다.
        if (hi <= lo + CUTOFF - 1) {
            InsertionSort.sort(arr, lo, hi);
            return;
        }

        int mid = lo + ((hi - lo) / 2);
        sort(arr, lo, mid);
        sort(arr, mid+1, hi);
        // 정렬 상태일 경우 멈춘다.
        if (!less(arr[mid+1], arr[mid]))
            return;
        merge(arr, lo, mid, hi);
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
        MergeSortImpl.sort(unsorted);

        Assertions.assertArrayEquals(sorted, unsorted);
    }

    @Test
    public void testSortString() {
        String str = "T H A T I S I M P L E M E N T E D M E R G E S O R T";
        String[] unsorted = str.split(" ");
        String[] sorted = str.split(" ");

        Arrays.sort(sorted);
        ShellSort.sort(unsorted);

        Assertions.assertArrayEquals(sorted, unsorted);
        System.out.println(Arrays.toString(unsorted));
    }
}
