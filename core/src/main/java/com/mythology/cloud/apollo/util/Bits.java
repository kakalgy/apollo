package com.mythology.cloud.apollo.util;

/**
 * Interface for Bitset-like structures.
 *
 * @lucene.experimental
 * @Author kakalgy
 * @Date 2019/12/10 21:43
 **/
public interface Bits {
    /**
     * Returns the value of the bit with the specified <code>index</code>.
     *
     * @param index index, should be non-negative and &lt; {@link #length()}.
     *              The result of passing negative or out of bounds values is undefined
     *              by this interface, <b>just don't do it!</b>
     * @return <code>true</code> if the bit is set, <code>false</code> otherwise.
     */
    boolean get(int index);

    /**
     * Returns the number of bits in this set
     */
    int length();

    Bits[] EMPTY_ARRAY = new Bits[0];

    /**
     * Bits impl of the specified length with all bits set.
     */
    class MatchAllBits implements Bits {
        final int len;

        public MatchAllBits(int len) {
            this.len = len;
        }

        @Override
        public boolean get(int index) {
            return true;
        }

        @Override
        public int length() {
            return len;
        }
    }

    /**
     * Bits impl of the specified length with no bits set.
     */
    class MatchNoBits implements Bits {
        final int len;

        public MatchNoBits(int len) {
            this.len = len;
        }

        @Override
        public boolean get(int index) {
            return false;
        }

        @Override
        public int length() {
            return len;
        }
    }
}

