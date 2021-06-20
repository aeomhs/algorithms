package com.aeomhs.util.sorts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Introduction to Algorithms
 *  7. Quick Sort
 */
public class QuickSort {

    public static void sort(Comparable[] arr) {
        sort(arr, 0, arr.length-1);
    }

    private static void sort(Comparable[] arr, int p, int r) {
        if (p < r) {
            int q = partition(arr, p, r);
            sort(arr, p, q-1);
            sort(arr, q+1, r);
        }
    }

    private static int partition(Comparable[] arr, int p, int r) {
        Comparable x = arr[r];
        int i = p - 1;

        for (int j = p; j < r; j++) {
            if (arr[j].compareTo(x) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i+1, r);
        return i+1;
    }

    private static void swap(Comparable[] arr, int i, int j) {
        Comparable temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Test
    public void sortTest() {
        Integer[] arr = new Integer[] {4, 5, 2, 3, 1, 7, 6, 8};
        sort(arr);
        System.out.println(Arrays.toString(arr));
        Assertions.assertArrayEquals(Arrays.stream(arr).sorted().map(Integer::new).toArray(), arr);
    }
}
