package com.aeomhs.util.sorts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MergeSort {

    private static Comparable[] aux;

    public static void sort(Comparable[] arr) {
        aux = new Comparable[arr.length];
        sort(arr, 0, arr.length-1);
    }

    private static void sort(Comparable[] arr, int lo, int hi) {
        if (hi <= lo)
            return;

        int mid = (hi + lo) / 2;
        sort(arr, lo, mid);
        sort(arr, mid+1, hi);
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
        MergeSort.sort(unsorted);

        Assertions.assertArrayEquals(sorted, unsorted);
    }
}
