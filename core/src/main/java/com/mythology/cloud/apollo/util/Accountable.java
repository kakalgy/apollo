package com.mythology.cloud.apollo.util;

import java.util.Collection;
import java.util.Collections;

/**
 * An object whose RAM usage can be computed.
 *
 * @author gyli
 * @lucene.internal
 * @date 2019/12/10 11:14
 */
public interface Accountable {

    /**
     * Return the memory usage of this object in bytes. Negative values are illegal.
     */
    long ramBytesUsed();

    /**
     * Returns nested resources of this class.
     * The result should be a point-in-time snapshot (to avoid race conditions).
     *
     * @see Accountables
     */
    default Collection<Accountable> getChildResources() {
        return Collections.emptyList();
    }

}
