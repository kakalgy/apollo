package com.mythology.cloud.apollo.index;

/**
 * DocValues types. Note that DocValues is strongly typed, so a
 * field cannot have different types across different documents.
 * <p>DocValues类型。 请注意，DocValues是强类型的，因此Field在不同文档中不能具有不同的类型。</p>
 */
public enum DocValuesType {
    /**
     * No doc values for this field.
     */
    NONE,
    /**
     * A per-document Number
     */
    NUMERIC,
    /**
     * A per-document byte[].  Values may be larger than
     * 32766 bytes, but different codecs may enforce their own limits.
     */
    BINARY,
    /**
     * A pre-sorted byte[]. Fields with this type only store distinct byte values
     * and store an additional offset pointer per document to dereference the shared
     * byte[]. The stored byte[] is presorted and allows access via document id,
     * ordinal and by-value.  Values must be {@code <= 32766} bytes.
     */
    SORTED,
    /**
     * A pre-sorted Number[]. Fields with this type store numeric values in sorted
     * order according to {@link Long#compare(long, long)}.
     */
    SORTED_NUMERIC,
    /**
     * A pre-sorted Set&lt;byte[]&gt;. Fields with this type only store distinct byte values
     * and store additional offset pointers per document to dereference the shared
     * byte[]s. The stored byte[] is presorted and allows access via document id,
     * ordinal and by-value.  Values must be {@code <= 32766} bytes.
     */
    SORTED_SET,
}

