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
 * <p>
 * 当递归级别超过要排序的数组长度的对数时，它会退回到堆排序。
 * 这样可以防止quicksort进入最坏情况的二次运行时。 小数组通过插入排序进行排序。
 *
 * 该算法是一种混合排序算法，开始于快速排序，
 * 当递归深度超过基于正在排序的元素数目的水平时便切换到堆排序。
 * 它包含了这两种算法优良的部分，它实际的性能相当于在典型数据集上的快速排序和在最坏情况下的堆排序。
 * 由于它使用了两种比较排序，因而它也是一种比较排序
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
        //当需要比较的元素少于20，则使用二分插入排序
        if (to - from < BINARY_SORT_THRESHOLD) {
            binarySort(from, to);
            return;
        }
        //当
        else if (--maxDepth < 0) {
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
