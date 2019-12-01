package com.mythology.cloud.apollo.util;

/**
 * An implementation of a selection algorithm, ie. computing the k-th greatest
 * value from a collection.
 *
 * <p>选择算法的实现，即 计算集合的第k个最大值。</p>
 */
public abstract class Selector {

    /**
     * Reorder elements so that the element at position {@code k} is the same
     * as if all elements were sorted and all other elements are partitioned
     * around it: {@code [from, k)} only contains elements that are less than
     * or equal to {@code k} and {@code (k, to)} only contains elements that
     * are greater than or equal to {@code k}.
     *
     * <p>
     * 对元素进行重新排序，以使位置{@code k}的元素与所有元素都已排序，
     * 并且所有其他元素都围绕它进行分区一样：
     * {@code [from，k）}仅包含小于或等于的元素 {@code k}和{@code（k，to）}仅包含大于或等于{@code k}的元素。
     * </p>
     */
    public abstract void select(int from, int to, int k);

    void checkArgs(int from, int to, int k) {
        if (k < from) {
            throw new IllegalArgumentException("k must be >= from");
        }
        if (k >= to) {
            throw new IllegalArgumentException("k must be < to");
        }
    }

    /**
     * Swap values at slots <code>i</code> and <code>j</code>.
     *
     * <p>
     * 交换插槽<code> i </code>和<code> j </code>上的值。
     * </p>
     */
    protected abstract void swap(int i, int j);
}
