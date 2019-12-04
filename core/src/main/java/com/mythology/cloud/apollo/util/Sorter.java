package com.mythology.cloud.apollo.util;

import java.util.Comparator;

/**
 * Base class for sorting algorithms implementations.
 * <p>
 * 排序算法实现的基类。
 * </p>
 *
 * @lucene.internal
 */
public abstract class Sorter {

    /**
     *
     */
    static final int BINARY_SORT_THRESHOLD = 20;

    /**
     * Sole constructor, used for inheritance.
     * <p>唯一的构造函数，用于继承。</p>
     */
    protected Sorter() {
    }

    /**
     * Compare entries found in slots <code>i</code> and <code>j</code>.
     * The contract for the returned value is the same as
     * {@link Comparator#compare(Object, Object)}.
     *
     * <p>
     * 比较在插槽<code> i </code>和<code> j </code>中找到的条目。
     * 返回值的约定与{@link Comparator＃compare（Object，Object）}相同。
     * </p>
     */
    protected abstract int compare(int i, int j);

    /**
     * Swap values at slots <code>i</code> and <code>j</code>.
     * <p>
     * 交换插槽<code> i </code>和<code> j </code>上的值。
     * </p>
     */
    protected abstract void swap(int i, int j);

    /**
     * 枢轴位置index
     */
    private int pivotIndex;

    /**
     * Save the value at slot <code>i</code> so that it can later be used as a
     * pivot, see {@link #comparePivot(int)}.
     *
     * <p>
     * 将值保存在插槽<code> i </ code>中，以便以后可以用作枢轴，
     * 请参见{@link #comparePivot（int）}。
     * </p>
     */
    protected void setPivot(int i) {
        pivotIndex = i;
    }

    /**
     * Compare the pivot with the slot at <code>j</code>, similarly to
     * {@link #compare(int, int) compare(i, j)}.
     *
     * <p>
     * 将枢轴与<code> j </ code>上的插槽进行比较，
     * 类似于{@link #compare（int，int）compare（i，j）}。
     * </p>
     */
    protected int comparePivot(int j) {
        return compare(pivotIndex, j);
    }

    /**
     * Sort the slice(切片) which starts at <code>from</code> (inclusive) and ends at
     * <code>to</code> (exclusive).
     *
     * <p>
     * 对从（<code> from </code>（包括）开始，以<code> to </code>（不包括）结束的slice（切片）进行排序。
     * </p>
     */
    public abstract void sort(int from, int to);

    /**
     * 判断to必须大于等于from
     *
     * @param from
     * @param to
     */
    void checkRange(int from, int to) {
        if (to < from) {
            throw new IllegalArgumentException("'to' must be >= 'from', got from=" + from + " and to=" + to);
        }
    }

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
    void mergeInPlace(int from, int mid, int to) {

        //排除特殊情况
        if (from == mid || mid == to || compare(mid - 1, mid) <= 0) {
            return;
        } else if (to - from == 2) {
            swap(mid - 1, mid);
            return;
        }


        while (compare(from, mid) <= 0) {
            ++from;
        }
        while (compare(mid - 1, to - 1) <= 0) {
            --to;
        }


        int first_cut, second_cut;
        int len11, len22;


        if (mid - from > to - mid) {
            len11 = (mid - from) >>> 1;
            first_cut = from + len11;
            second_cut = lower(mid, to, first_cut);
            len22 = second_cut - mid;
        } else {
            len22 = (to - mid) >>> 1;
            second_cut = mid + len22;
            first_cut = upper(from, mid, second_cut);
            len11 = first_cut - from;
        }


        rotate(first_cut, mid, second_cut);

        final int new_mid = first_cut + len22;


        mergeInPlace(from, first_cut, new_mid);
        mergeInPlace(new_mid, second_cut, to);
    }

    /**
     * @param from
     * @param to
     * @param val
     * @return
     */
    int lower(int from, int to, int val) {
        int len = to - from;
        while (len > 0) {
            final int half = len >>> 1;
            final int mid = from + half;
            if (compare(mid, val) < 0) {
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
    int upper(int from, int to, int val) {
        int len = to - from;
        while (len > 0) {
            final int half = len >>> 1;
            final int mid = from + half;
            if (compare(val, mid) < 0) {
                len = half;
            } else {
                from = mid + 1;
                len = len - half - 1;
            }
        }
        return from;
    }

    // faster than lower when val is at the end of [from:to[
    int lower2(int from, int to, int val) {
        int f = to - 1, t = to;
        while (f > from) {
            if (compare(f, val) < 0) {
                return lower(f, t, val);
            }
            final int delta = t - f;
            t = f;
            f -= delta << 1;
        }
        return lower(from, t, val);
    }

    // faster than upper when val is at the beginning of [from:to[
    int upper2(int from, int to, int val) {
        int f = from, t = f + 1;
        while (t < to) {
            if (compare(t, val) > 0) {
                return upper(f, t, val);
            }
            final int delta = t - f;
            f = t;
            t += delta << 1;
        }
        return upper(f, to, val);
    }

    final void reverse(int from, int to) {
        for (--to; from < to; ++from, --to) {
            swap(from, to);
        }
    }

    final void rotate(int lo, int mid, int hi) {
        assert lo <= mid && mid <= hi;
        if (lo == mid || mid == hi) {
            return;
        }
        doRotate(lo, mid, hi);
    }

    void doRotate(int lo, int mid, int hi) {
        if (mid - lo == hi - mid) {
            // happens rarely but saves n/2 swaps
            while (mid < hi) {
                swap(lo++, mid++);
            }
        } else {
            reverse(lo, mid);
            reverse(mid, hi);
            reverse(lo, hi);
        }
    }

    /**
     * A binary sort implementation. This performs {@code O(n*log(n))} comparisons
     * and {@code O(n^2)} swaps. It is typically used by more sophisticated
     * implementations as a fall-back when the numbers of items to sort has become
     * less than {@value #BINARY_SORT_THRESHOLD}.
     *
     * <p>
     * 二叉排序实现。 这将执行{@code O（n * log（n））}比较和{@code O（n ^ 2）}交换。
     * 当要排序的项目数已小于{@value #BINARY_SORT_THRESHOLD}时，更复杂的实现通常将其用作备用。
     * </p>
     *
     * <p>
     * 在将一个新元素插入已排好序的数组的过程中，寻找插入点时，将待插入区域的首元素设置为a[low]，
     * 末元素设置为a[high]，则轮比较时将待插入元素与a[m]，其中m=(low+high)/2相比较,如果比参考元素小，
     * 则选择a[low]到a[m-1]为新的插入区域(即high=m-1)，否则选择a[m+1]到a[high]为新的插入区域（即low=m+1），
     * 如此直至low<=high不成立，即将此位置之后所有元素后移一位，并将新元素插入a[high+1]
     * </p>
     */
    void binarySort(int from, int to) {
        binarySort(from, to, from + 1);
    }

    /**
     * @param from
     * @param to
     * @param i
     */
    void binarySort(int from, int to, int i) {
        for (; i < to; ++i) {
            setPivot(i);
            int l = from;
            int h = i - 1;
            while (l <= h) {
                final int mid = (l + h) >>> 1;
                final int cmp = comparePivot(mid);
                if (cmp < 0) {
                    h = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            for (int j = i; j > l; --j) {
                swap(j - 1, j);
            }
        }
    }

    /**
     * Use heap sort to sort items between {@code from} inclusive and {@code to}
     * exclusive. This runs in {@code O(n*log(n))} and is used as a fall-back by
     * {@link IntroSorter}.
     *
     * <p>
     * 使用堆排序在包含{@code from}和不包含{@code to}之间对项目进行排序。
     * 它以{@code O（n * log（n））}运行，并被{@link IntroSorter}用作后备。
     * </p>
     */
    void heapSort(int from, int to) {
        if (to - from <= 1) {
            return;
        }

        //调整堆
        heapify(from, to);

        // 上述逻辑，建堆结束,下面，开始排序逻辑
        for (int end = to - 1; end > from; --end) {

            // 元素交换,作用是去掉大顶堆
            // 把大顶堆的根元素，放到数组的最后；换句话说，就是每一次的堆调整之后，都会有一个元素到达自己的最终位置
            swap(from, end);

            // 元素交换之后，毫无疑问，最后一个元素无需再考虑排序问题了。
            // 接下来我们需要排序的，就是已经去掉了部分元素的堆了，这也是为什么此方法放在循环里的原因
            // 而这里，实质上是自上而下，自左向右进行调整的
            siftDown(from, from, end);
        }
    }

    void heapify(int from, int to) {
        for (int i = heapParent(from, to - 1); i >= from; --i) {
            siftDown(i, from, to);
        }
    }

    void siftDown(int i, int from, int to) {
        for (int leftChild = heapChild(from, i); leftChild < to; leftChild = heapChild(from, i)) {
            final int rightChild = leftChild + 1;
            if (compare(i, leftChild) < 0) {
                if (rightChild < to && compare(leftChild, rightChild) < 0) {
                    swap(i, rightChild);
                    i = rightChild;
                } else {
                    swap(i, leftChild);
                    i = leftChild;
                }
            } else if (rightChild < to && compare(i, rightChild) < 0) {
                swap(i, rightChild);
                i = rightChild;
            } else {
                break;
            }
        }
    }

    /**
     * 返回节点arr[from ---- i]的最后一个非叶节点的index
     *
     * @param from
     * @param i
     * @return
     */
    static int heapParent(int from, int i) {
        return ((i - 1 - from) >>> 1) + from;
    }

    /**
     * 返回节点arr[i]的左子节点index
     *
     * @param from
     * @param i
     * @return
     */
    static int heapChild(int from, int i) {
        return ((i - from) << 1) + 1 + from;
    }

}
