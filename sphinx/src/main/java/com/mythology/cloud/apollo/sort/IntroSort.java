package com.mythology.cloud.apollo.sort;

/**
 * 快速排序
 */
public class IntroSort {

    public static void quickSort1(int[] arr, int low, int high) {
        int i, j, temp;

        if (low > high) {
            return;
        }

        i = low;
        j = high;
        temp = low;

        while (i < j) {

            while (arr[temp] <= arr[j] && i < j) {
                j--;
            }

            while (arr[temp] >= arr[i] && i < j) {
                i++;
            }

            if (i < j) {
                swap(arr, i, j);
            }
        }

        int t = arr[temp];
        arr[low] = arr[i];
        arr[i] = t;

        quickSort1(arr, low, j - 1);
        quickSort1(arr, j + 1, high);
    }

    public static void swap(int[] arr, int i, int j) {
        final int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void quickSort2(int[] arr, int low, int high) {
        //选取中间元素为基准点

        int i, j, mid;

        if (low > high) {
            return;
        }

        i = low;
        j = high;
        mid = (low + high) >>> 1;

        while (i < j) {
            while (arr[mid] < arr[j]) {
                j--;
            }

            while (arr[mid] >= arr[i] && i < j) {
                i++;
            }

            if (i < j) {
                swap(arr, i, j);
            }
        }

        swap(arr, mid, i);

        quickSort2(arr, low, j - 1);
        quickSort2(arr, j + 1, high);
    }

    public static void main(String[] args) {
        int[] a = {3, 8, 2, 7, 5, 9, 6, 1};

        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();

        quickSort2(a, 0, a.length - 1);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }


    }
}
