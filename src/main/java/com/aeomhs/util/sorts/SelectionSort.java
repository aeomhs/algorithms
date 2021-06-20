package com.aeomhs.util.sorts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SelectionSort {

    public static void sort(Comparable[] arr) {
        // invariant : any i arr[i] < arr[i+1]
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i; j < arr.length; j++) {
                if (less(arr[j], arr[minIndex]))
                    minIndex = j;
            }
            swap(arr, minIndex, i);
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
    public void testSort() {
        Integer[] unsorted = new Integer[] { 5, 1, 2, 6, 3, 4 };
        Integer[] sorted = new Integer[] {1, 2, 3, 4, 5, 6};
        SelectionSort.sort(unsorted);

        Assertions.assertArrayEquals(sorted, unsorted);
    }
}
