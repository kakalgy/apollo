package com.mythology.cloud.apollo.util;

/**
 * {@link Sorter} implementation based on a variant of the quicksort algorithm
 * called <a href="http://en.wikipedia.org/wiki/Introsort">introsort</a>: when
 * the recursion level exceeds the log of the length of the array to sort, it
 * falls back to heapsort. This prevents quicksort from running into its
 * worst-case quadratic runtime. Small arrays are sorted with
 * insertion sort.
 *
 * <p>
 * 基于快速排序算法的一个变体introsort的实现：
 *
 * 当递归级别超过要排序的数组长度的对数时，它会退回到堆排序。
 * 这样可以防止quicksort进入最坏情况的二次运行时。 小数组通过插入排序进行排序。
 * </p>
 *
 * @lucene.internal
 */
public abstract class IntroSorter extends Sorter {

    /**
     * Create a new {@link IntroSorter}.
     */
    public IntroSorter() {
    }

    @Override
    public final void sort(int from, int to) {
        checkRange(from, to);
        quicksort(from, to, 2 * MathUtil.log(to - from, 2));
    }

    void quicksort(int from, int to, int maxDepth) {
        if (to - from < BINARY_SORT_THRESHOLD) {
            binarySort(from, to);
            return;
        } else if (--maxDepth < 0) {
            heapSort(from, to);
            return;
        }

        final int mid = (from + to) >>> 1;

        if (compare(from, mid) > 0) {
            swap(from, mid);
        }

        if (compare(mid, to - 1) > 0) {
            swap(mid, to - 1);
            if (compare(from, mid) > 0) {
                swap(from, mid);
            }
        }

        int left = from + 1;
        int right = to - 2;

        setPivot(mid);
        for (; ; ) {
            while (comparePivot(right) < 0) {
                --right;
            }

            while (left < right && comparePivot(left) >= 0) {
                ++left;
            }

            if (left < right) {
                swap(left, right);
                --right;
            } else {
                break;
            }
        }

        quicksort(from, left + 1, maxDepth);
        quicksort(left + 1, to, maxDepth);
    }

    // Don't rely on the slow default impl of setPivot/comparePivot since
    // quicksort relies on these methods to be fast for good performance

    @Override
    protected abstract void setPivot(int i);

    @Override
    protected abstract int comparePivot(int j);

    @Override
    protected int compare(int i, int j) {
        setPivot(i);
        return comparePivot(j);
    }
}
