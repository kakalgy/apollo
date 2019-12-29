package com.mythology.cloud.apollo.util;

/**
 * Represents byte[], as a slice (offset + length) into an
 * existing byte[].  The {@link #bytes} member should never be null;
 * use {@link #EMPTY_BYTES} if necessary.
 *
 * <p>
 * 将byte []表示为现有byte []中的切片（偏移量和长度）。
 * {@link #bytes}成员不应为null； 如有必要，请使用{@link #EMPTY_BYTES}。
 * </p>
 *
 * <p><b>Important note:</b> Unless otherwise noted, Lucene uses this class to
 * represent terms that are encoded as <b>UTF8</b> bytes in the index. To
 * convert them to a Java {@link String} (which is UTF16), use {@link #utf8ToString}.
 * Using code like {@code new String(bytes, offset, length)} to do this
 * is <b>wrong</b>, as it does not respect the correct character set
 * and may return wrong results (depending on the platform's defaults)!
 *
 * <b>重要说明：</ b>除非另有说明，否则Lucene使用此类表示在索引中编码为<b>UTF8</b>字节的术语。
 * 要将它们转换为Java {@link String}（即UTF16），请使用{@link＃utf8ToString}。
 * 使用{@code new String（bytes，offset，length）}之类的代码来完成此操作是<b>错误</ b>，
 * 因为它不遵守正确的字符集，并且可能会返回错误的结果（取决于平台的默认设置） ！
 *
 * <p>{@code BytesRef} implements {@link Comparable}. The underlying byte arrays
 * are sorted lexicographically, numerically treating elements as unsigned.
 * This is identical to Unicode codepoint order.
 *
 * <p>
 * {@code BytesRef}实现{@link Comparable}。
 * 底层字节数组按字典顺序排序，将元素视为无符号数字。 这与Unicode代码点顺序相同。
 * </p>
 */

public final class BytesRef implements Comparable<BytesRef>, Cloneable {
    /**
     * An empty byte array for convenience
     */
    public static final byte[] EMPTY_BYTES = new byte[0];

    /**
     * The contents of the BytesRef. Should never be {@code null}.
     */
    public byte[] bytes;

    /**
     * Offset of first valid byte.
     */
    public int offset;

    /**
     * Length of used bytes.
     */
    public int length;

    /**
     * Create a BytesRef with {@link #EMPTY_BYTES}
     */
    public BytesRef() {
        this(EMPTY_BYTES);
    }

    /**
     * This instance will directly reference bytes w/o making a copy.
     * bytes should not be null.
     * <p>
     * 该实例对bytes将直接引用,不进行复制。
     * bytes不能为空
     * </p>
     */
    public BytesRef(byte[] bytes, int offset, int length) {
        this.bytes = bytes;
        this.offset = offset;
        this.length = length;
        assert isValid();
    }

    /**
     * This instance will directly reference bytes w/o making a copy.
     * bytes should not be null
     * <p>
     * 该实例对bytes将直接引用,不进行复制。
     * bytes不能为空
     * </p>
     */
    public BytesRef(byte[] bytes) {
        this(bytes, 0, bytes.length);
    }

    /**
     * Create a BytesRef pointing to a new array of size <code>capacity</code>.
     * Offset and length will both be zero.
     * <p>
     * 创建一个BytesRef指向一个新的数组，其大小为<code> capacity </code>。
     * 偏移量和长度均为零。
     * </p>
     */
    public BytesRef(int capacity) {
        this.bytes = new byte[capacity];
    }

    /**
     * Initialize the byte[] from the UTF8 bytes
     * for the provided String.
     * <p>
     * 从提供的String的UTF8字节初始化byte[]。
     * </p>
     *
     * @param text This must be well-formed
     *             unicode text, with no unpaired surrogates.这必须是格式正确的unicode文本，没有不成对的替代。
     */
    public BytesRef(CharSequence text) {
        this(new byte[UnicodeUtil.maxUTF8Length(text.length())]);
        length = UnicodeUtil.UTF16toUTF8(text, 0, text.length(), bytes);
    }


    /**
     * Expert: compares the bytes against another BytesRef,
     * returning true if the bytes are equal.
     *
     * @param other Another BytesRef, should not be null.
     * @lucene.internal
     */
    public boolean bytesEquals(BytesRef other) {
        return FutureArrays.equals(this.bytes, this.offset, this.offset + this.length,
                other.bytes, other.offset, other.offset + other.length);
    }

    /**
     * Returns a shallow clone of this instance (the underlying bytes are
     * <b>not</b> copied and will be shared by both the returned object and this
     * object.
     *
     * <p>
     * 返回此实例的浅拷贝副本（底层bytes不会被复制，并且将由返回的对象和此对象共享)。
     * </p>
     *
     * @see #deepCopyOf 深拷贝方法
     */
    @Override
    public BytesRef clone() {
        return new BytesRef(bytes, offset, length);
    }

    /**
     * Calculates the hash code as required by TermsHash during indexing.
     * <p> This is currently implemented as MurmurHash3 (32
     * bit), using the seed from {@link
     * StringHelper#GOOD_FAST_HASH_SEED}, but is subject to
     * change from release to release.
     *
     * <p>
     * 在编制索引期间，根据TermsHash计算哈希码。
     * <p>当前使用{@link StringHelper＃GOOD_FAST_HASH_SEED}的种子将其实现为MurmurHash3（32位），但可能会因版本而异。
     * </p>
     */
    @Override
    public int hashCode() {
        return StringHelper.murmurhash3_x86_32(this, StringHelper.GOOD_FAST_HASH_SEED);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof BytesRef) {
            return this.bytesEquals((BytesRef) other);
        }
        return false;
    }

    /**
     * Interprets stored bytes as UTF8 bytes, returning the
     * resulting string
     *
     * <p>
     * 将存储的bytes解释为UTF8字节，返回结果字符串
     * </p>
     */
    public String utf8ToString() {
        final char[] ref = new char[length];
        final int len = UnicodeUtil.UTF8toUTF16(bytes, offset, length, ref);
        return new String(ref, 0, len);
    }

    /**
     * Returns hex encoded bytes, eg [0x6c 0x75 0x63 0x65 0x6e 0x65]
     * <p>
     * 返回十六进制编码的字节
     * </p>
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        final int end = offset + length;
        for (int i = offset; i < end; i++) {
            if (i > offset) {
                sb.append(' ');
            }
            sb.append(Integer.toHexString(bytes[i] & 0xff));
        }
        sb.append(']');
        return sb.toString();
    }

    /**
     * Unsigned byte order comparison
     * 无符号字节顺序比较
     */
    @Override
    public int compareTo(BytesRef other) {
        return FutureArrays.compareUnsigned(this.bytes, this.offset, this.offset + this.length,
                other.bytes, other.offset, other.offset + other.length);
    }

    /**
     * Creates a new BytesRef that points to a copy of the bytes from
     * <code>other</code>
     *
     * <p>
     * 深拷贝，新建一个BytesRef对象，指向other的一个复制的bytes
     * </p>
     * <p>
     * The returned BytesRef will have a length of other.length
     * and an offset of zero.
     *
     * <p>
     * 新的BytesRef长度为other的长度， 偏移量为零。
     * </p>
     */
    public static BytesRef deepCopyOf(BytesRef other) {
        return new BytesRef(ArrayUtil.copyOfSubArray(other.bytes, other.offset, other.offset + other.length), 0, other.length);
    }

    /**
     * Performs internal consistency checks.
     * Always returns true (or throws IllegalStateException)
     * <p>
     * 执行内部一致性检查。 始终返回true（或引发IllegalStateException）
     * </p>
     */
    public boolean isValid() {
        if (bytes == null) {
            throw new IllegalStateException("bytes is null");
        }
        if (length < 0) {
            throw new IllegalStateException("length is negative: " + length);
        }
        if (length > bytes.length) {
            throw new IllegalStateException("length is out of bounds: " + length + ",bytes.length=" + bytes.length);
        }
        if (offset < 0) {
            throw new IllegalStateException("offset is negative: " + offset);
        }
        if (offset > bytes.length) {
            throw new IllegalStateException("offset out of bounds: " + offset + ",bytes.length=" + bytes.length);
        }
        if (offset + length < 0) {
            throw new IllegalStateException("offset+length is negative: offset=" + offset + ",length=" + length);
        }
        if (offset + length > bytes.length) {
            throw new IllegalStateException("offset+length out of bounds: offset=" + offset + ",length=" + length + ",bytes.length=" + bytes.length);
        }
        return true;
    }
}

