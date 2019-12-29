package com.mythology.cloud.apollo.util;

/**
 * Represents int[], as a slice (offset + length) into an
 * existing int[].  The {@link #ints} member should never be null; use
 * {@link #EMPTY_INTS} if necessary.
 *
 * <p>
 * 将int []表示为现有int []中的一个切片（偏移量+长度）。
 * {@link #ints}成员不应为null； 如有必要，请使用{@link #EMPTY_INTS}。
 * </p>
 *
 * @author gyli
 * @lucene.internal
 * @date 2019/12/10 11:10
 */
public final class IntsRef implements Comparable<IntsRef>, Cloneable {
    /**
     * An empty integer array for convenience
     */
    public static final int[] EMPTY_INTS = new int[0];

    /**
     * The contents of the IntsRef. Should never be {@code null}.
     */
    public int[] ints;
    /**
     * Offset of first valid integer.
     */
    public int offset;
    /**
     * Length of used ints.
     */
    public int length;

    /**
     * Create a IntsRef with {@link #EMPTY_INTS}
     */
    public IntsRef() {
        ints = EMPTY_INTS;
    }

    /**
     * Create a IntsRef pointing to a new array of size <code>capacity</code>.
     * Offset and length will both be zero.
     * <p>
     * 创建一个IntsRef指向一个新的数组，其大小为<code>capacity</code>。
     * 偏移量和长度均为零。
     * </p>
     */
    public IntsRef(int capacity) {
        ints = new int[capacity];
    }

    /**
     * This instance will directly reference ints w/o making a copy.
     * ints should not be null.
     */
    public IntsRef(int[] ints, int offset, int length) {
        this.ints = ints;
        this.offset = offset;
        this.length = length;
        assert isValid();
    }

    /**
     * Returns a shallow clone of this instance (the underlying ints are
     * <b>not</b> copied and will be shared by both the returned object and this
     * object.
     * <p>
     * 返回此实例的浅拷贝副本（底层ints不会被复制，并且将由返回的对象和此对象共享)。
     * </p>
     *
     * @see #deepCopyOf
     */
    @Override
    public IntsRef clone() {
        return new IntsRef(ints, offset, length);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 0;
        final int end = offset + length;
        for (int i = offset; i < end; i++) {
            result = prime * result + ints[i];
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof IntsRef) {
            return this.intsEquals((IntsRef) other);
        }
        return false;
    }

    /**
     * IntsRef 的比较，ints相同则返回true
     *
     * @param other
     * @return
     */
    public boolean intsEquals(IntsRef other) {
        return FutureArrays.equals(this.ints, this.offset, this.offset + this.length,
                other.ints, other.offset, other.offset + other.length);
    }

    /**
     * Signed int order comparison
     * 无符号字节顺序比较
     */
    @Override
    public int compareTo(IntsRef other) {
        return FutureArrays.compare(this.ints, this.offset, this.offset + this.length,
                other.ints, other.offset, other.offset + other.length);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        final int end = offset + length;
        for (int i = offset; i < end; i++) {
            if (i > offset) {
                sb.append(' ');
            }
            sb.append(Integer.toHexString(ints[i]));
        }
        sb.append(']');
        return sb.toString();
    }

    /**
     * Creates a new IntsRef that points to a copy of the ints from
     * <code>other</code>
     * <p>
     * 深拷贝，新建一个IntsRef对象，指向other的一个复制的ints
     * </p>
     * <p>
     * The returned IntsRef will have a length of other.length
     * and an offset of zero.
     */
    public static IntsRef deepCopyOf(IntsRef other) {
        return new IntsRef(ArrayUtil.copyOfSubArray(other.ints, other.offset, other.offset + other.length), 0, other.length);
    }

    /**
     * Performs internal consistency checks.
     * Always returns true (or throws IllegalStateException)
     */
    public boolean isValid() {
        if (ints == null) {
            throw new IllegalStateException("ints is null");
        }
        if (length < 0) {
            throw new IllegalStateException("length is negative: " + length);
        }
        if (length > ints.length) {
            throw new IllegalStateException("length is out of bounds: " + length + ",ints.length=" + ints.length);
        }
        if (offset < 0) {
            throw new IllegalStateException("offset is negative: " + offset);
        }
        if (offset > ints.length) {
            throw new IllegalStateException("offset out of bounds: " + offset + ",ints.length=" + ints.length);
        }
        if (offset + length < 0) {
            throw new IllegalStateException("offset+length is negative: offset=" + offset + ",length=" + length);
        }
        if (offset + length > ints.length) {
            throw new IllegalStateException("offset+length out of bounds: offset=" + offset + ",length=" + length + ",ints.length=" + ints.length);
        }
        return true;
    }
}

