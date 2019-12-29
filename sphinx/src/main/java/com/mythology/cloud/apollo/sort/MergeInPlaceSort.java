package com.mythology.cloud.apollo.sort;

/**
 * @author gyli
 * @date 2019/12/5 9:58
 */
public class MergeInPlaceSort {

    /**
     * 归并排序
     * <p>
     * https://www.jianshu.com/p/33cffa1ce613
     * </p>
     * 这里是归并排序的第二步：合并两个有序数组的过程
     * <p>
     * 也就是说这里已经默认两个分段[from，mid-1]和[mid，to]已经是排序的，且是从小到大排序
     *
     * @param from
     * @param mid
     * @param to
     */
    static void mergeInPlace(int[] arr, int from, int mid, int to) {

        //排除特殊情况
        if (from == mid || mid == to || compare(arr, mid - 1, mid) <= 0) {
            return;
        } else if (to - from == 2) {
            swap(arr, mid - 1, mid);
            return;
        }


        while (compare(arr, from, mid) <= 0) {
            ++from;
        }
        while (compare(arr, mid - 1, to - 1) <= 0) {
            --to;
        }


        int first_cut, second_cut;
        int len11, len22;

        //
        if (mid - from > to - mid) {
            len11 = (mid - from) >>> 1;
            first_cut = from + len11;
            second_cut = lower(arr, mid, to, first_cut);
            len22 = second_cut - mid;
        } else {
            len22 = (to - mid) >>> 1;
            second_cut = mid + len22;
            first_cut = upper(arr, from, mid, second_cut);
            len11 = first_cut - from;
        }


        rotate(arr, first_cut, mid, second_cut);

        final int new_mid = first_cut + len22;


        mergeInPlace(arr, from, first_cut, new_mid);
        mergeInPlace(arr, new_mid, second_cut, to);
    }

    /**
     * @param from
     * @param to
     * @param val
     * @return
     */
    static int lower(int[] arr, int from, int to, int val) {
        int len = to - from;
        while (len > 0) {
            final int half = len >>> 1;
            final int mid = from + half;
            if (compare(arr, mid, val) < 0) {
                from = mid + 1;
                len = len - half - 1;
            } else {
                len = half;
            }
        }
        return from;
    }

    /**
     * @param from
     * @param to
     * @param val
     * @return
     */
    static int upper(int[] arr, int from, int to, int val) {
        int len = to - from;
        while (len > 0) {
            final int half = len >>> 1;
            final int mid = from + half;
            if (compare(arr, val, mid) < 0) {
                len = half;
            } else {
                from = mid + 1;
                len = len - half - 1;
            }
        }
        return from;
    }

    // faster than lower when val is at the end of [from:to[
    static int lower2(int[] arr, int from, int to, int val) {
        int f = to - 1, t = to;
        while (f > from) {
            if (compare(arr, f, val) < 0) {
                return lower(arr, f, t, val);
            }
            final int delta = t - f;
            t = f;
            f -= delta << 1;
        }
        return lower(arr, from, t, val);
    }

    // faster than upper when val is at the beginning of [from:to[
    static int upper2(int[] arr, int from, int to, int val) {
        int f = from, t = f + 1;
        while (t < to) {
            if (compare(arr, t, val) > 0) {
                return upper(arr, f, t, val);
            }
            final int delta = t - f;
            f = t;
            t += delta << 1;
        }
        return upper(arr, f, to, val);
    }

    /**
     * 翻转数组(注意翻转的下标截止到to-1，最后一位不变)
     *
     * @param arr
     * @param from
     * @param to
     */
    final static void reverse(int[] arr, int from, int to) {
        for (--to; from < to; ++from, --to) {
            swap(arr, from, to);
        }
    }

    /**
     * 围绕mid旋转
     *
     * lo - (mid-1) mid - hi ==>  mid - hi lo - (mid-1)
     *
     * @param arr
     * @param lo
     * @param mid
     * @param hi
     */
    final static void rotate(int[] arr, int lo, int mid, int hi) {
        assert lo <= mid && mid <= hi;
        if (lo == mid || mid == hi) {
            return;
        }
        doRotate(arr, lo, mid, hi);
    }

    static void doRotate(int[] arr, int lo, int mid, int hi) {
        if (mid - lo == hi - mid) {
            // happens rarely but saves n/2 swaps
            while (mid < hi) {
                swap(arr, lo++, mid++);
            }
        } else {
            reverse(arr, lo, mid);
            reverse(arr, mid, hi);
            reverse(arr, lo, hi);
        }
    }

    public static void swap(int[] arr, int i, int j) {
        final int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int compare(int[] arr, int i, int j) {
        if (arr[i] > arr[j]) {
            return 1;
        }
        if (arr[i] < arr[j]) {
            return -1;
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] a = {2, 3, 7, 1, 5, 6, 8, 9};

        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();

        mergeInPlace(a, 0, (0 + a.length - 1) >>> 1, a.length);
//        reverse(a, 0, a.length);
//        rotate(a, 0, (0 + a.length) >>> 1, a.length);
//        lower(a, 0, (0 + a.length) >>> 1, a.length-1);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

}
