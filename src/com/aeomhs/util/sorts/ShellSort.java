package com.aeomhs.util.sorts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

public class ShellSort {

    public static void sort(Comparable[] arr) {
        int N = arr.length;
        int h = 1;
        while (h < N/3) h = h * 3 + 1;

        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(arr[j], arr[j-h]); j-=h)
                    swap(arr, j, j-h);
            }
            h = h / 3;
        }
    }

    private static boolean less (Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void swap(Comparable[] arr, int i, int j) {
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Test
    public void testSortInteger() {
        Integer[] unsorted = new Integer[] { 5, 1, 2, 6, 3, 4 };
        Integer[] sorted = new Integer[] {1, 2, 3, 4, 5, 6};
        ShellSort.sort(unsorted);

        Assertions.assertArrayEquals(sorted, unsorted);
    }

    @Test
    public void testSortString() {
        String str = "S H E L L S O R T E X A M P L E";
        String[] unsorted = str.split(" ");
        String[] sorted = str.split(" ");

        Arrays.sort(sorted);
        ShellSort.sort(unsorted);

        Assertions.assertArrayEquals(sorted, unsorted);
    }
}
