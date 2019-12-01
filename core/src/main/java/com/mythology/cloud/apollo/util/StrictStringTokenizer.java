package com.mythology.cloud.apollo.util;

/**
 * Used for parsing Version strings so we don't have to
 * use overkill String.split nor StringTokenizer (which silently
 * skips empty tokens).
 *
 * <p>
 * 用于解析Version字符串，
 * 因此我们不必使用过大的String.split或StringTokenizer（它会静默跳过空标记）。
 * </p>
 */

final class StrictStringTokenizer {

    public StrictStringTokenizer(String s, char delimiter) {
        this.s = s;
        this.delimiter = delimiter;
    }

    public final String nextToken() {
        if (pos < 0) {
            throw new IllegalStateException("no more tokens");
        }

        int pos1 = s.indexOf(delimiter, pos);
        String s1;
        if (pos1 >= 0) {
            s1 = s.substring(pos, pos1);
            pos = pos1 + 1;
        } else {
            s1 = s.substring(pos);
            pos = -1;
        }

        return s1;
    }

    public final boolean hasMoreTokens() {
        return pos >= 0;
    }

    private final String s;
    private final char delimiter;
    private int pos;
}

