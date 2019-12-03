package com.mythology.cloud.apollo.sort;

public class BinarySort {

    public static void binarySort(int[] arr, int low, int high) {

        for (int i = low + 1; i <= high; i++) {
            int left = low;
            int right = i;

            while (left <= right) {
                int mid = (left + right) >>> 1;

                if (arr[mid] > arr[i]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            for (int j = i ; j > left; j--) {
                swap(arr, j - 1, j);
            }
        }

    }

    public static void swap(int[] arr, int i, int j) {
        final int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] a = {3, 8, 2, 7, 5, 9, 6, 1};

        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();

        binarySort(a, 0, a.length - 1);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }
}
