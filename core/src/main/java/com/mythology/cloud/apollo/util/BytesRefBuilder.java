package com.mythology.cloud.apollo.util;

/**
 * A builder for {@link BytesRef} instances.
 *
 * @author gyli
 * @lucene.internal
 * @date 2019/12/10 15:01
 */
public class BytesRefBuilder {

    private final BytesRef ref;

    /**
     * Sole constructor.
     * <p>
     * 唯一的构造函数
     * </p>
     */
    public BytesRefBuilder() {
        ref = new BytesRef();
    }

    /**
     * Return a reference to the bytes of this builder.
     */
    public byte[] bytes() {
        return ref.bytes;
    }

    /**
     * Return the number of bytes in this buffer.
     */
    public int length() {
        return ref.length;
    }

    /**
     * Set the length.
     */
    public void setLength(int length) {
        this.ref.length = length;
    }

    /**
     * Return the byte at the given offset. 返回offset位置的byte
     */
    public byte byteAt(int offset) {
        return ref.bytes[offset];
    }

    /**
     * Set a byte. 在offset位置设置值为b
     */
    public void setByteAt(int offset, byte b) {
        ref.bytes[offset] = b;
    }

    /**
     * Ensure that this builder can hold at least <code>capacity</code> bytes
     * without resizing.
     * <p>
     * 确保此builder至少可以容纳<code>capacity</code>个字节而无需调整大小。
     * </p>
     */
    public void grow(int capacity) {
        ref.bytes = ArrayUtil.grow(ref.bytes, capacity);
    }

    /**
     * Append a single byte to this builder.
     */
    public void append(byte b) {
        grow(ref.length + 1);
        ref.bytes[ref.length++] = b;
    }


    /**
     * Append the provided bytes to this builder.
     */
    public void append(byte[] b, int off, int len) {
        grow(ref.length + len);
        System.arraycopy(b, off, ref.bytes, ref.length, len);
        ref.length += len;
    }

    /**
     * Append the provided bytes to this builder.
     */
    public void append(BytesRef ref) {
        append(ref.bytes, ref.offset, ref.length);
    }

    /**
     * Append the provided bytes to this builder.
     */
    public void append(BytesRefBuilder builder) {
        append(builder.get());
    }


    /**
     * Reset this builder to the empty state.
     */
    public void clear() {
        setLength(0);
    }

    /**
     * Replace the content of this builder with the provided bytes. Equivalent to
     * calling {@link #clear()} and then {@link #append(byte[], int, int)}.
     */
    public void copyBytes(byte[] b, int off, int len) {
        clear();
        append(b, off, len);
    }

    /**
     * Replace the content of this builder with the provided bytes. Equivalent to
     * calling {@link #clear()} and then {@link #append(BytesRef)}.
     */
    public void copyBytes(BytesRef ref) {
        clear();
        append(ref);
    }

    /**
     * Replace the content of this builder with the provided bytes. Equivalent to
     * calling {@link #clear()} and then {@link #append(BytesRefBuilder)}.
     */
    public void copyBytes(BytesRefBuilder builder) {
        clear();
        append(builder);
    }


    /**
     * Replace the content of this buffer with UTF-8 encoded bytes that would
     * represent the provided text.
     * <p>
     * 用代表所提供文本的UTF-8编码字节替换此缓冲区的内容。
     * </p>
     */
    public void copyChars(CharSequence text) {
        copyChars(text, 0, text.length());
    }

    /**
     * Replace the content of this buffer with UTF-8 encoded bytes that would
     * represent the provided text.
     */
    public void copyChars(CharSequence text, int off, int len) {
        grow(UnicodeUtil.maxUTF8Length(len));
        ref.length = UnicodeUtil.UTF16toUTF8(text, off, len, ref.bytes);
    }

    /**
     * Replace the content of this buffer with UTF-8 encoded bytes that would
     * represent the provided text.
     */
    public void copyChars(char[] text, int off, int len) {
        grow(UnicodeUtil.maxUTF8Length(len));
        ref.length = UnicodeUtil.UTF16toUTF8(text, off, len, ref.bytes);
    }


    /**
     * Return a {@link BytesRef} that points to the internal content of this
     * builder. Any update to the content of this builder might invalidate
     * the provided <code>ref</code> and vice-versa.
     * <p>
     * 返回指向该builder内部内容的{@link BytesRef}。
     * 对此builder内容的任何更新都可能使提供的<code>ref</code>无效，反之亦然。
     * </p>
     */
    public BytesRef get() {
        assert ref.offset == 0 : "Modifying the offset of the returned ref is illegal";
        return ref;
    }

    /**
     * Build a new {@link BytesRef} that has the same content as this buffer.
     */
    public BytesRef toBytesRef() {
        return new BytesRef(ArrayUtil.copyOfSubArray(ref.bytes, 0, ref.length));
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }
}

