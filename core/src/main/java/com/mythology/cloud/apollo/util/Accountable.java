package com.mythology.cloud.apollo.util;

import java.util.Collection;
import java.util.Collections;

/**
 * An object whose RAM usage can be computed.
 *
 * <p>
 * 可以计算其RAM使用率的对象。
 * </p>
 *
 * @author gyli
 * @lucene.internal
 * @date 2019/12/10 11:14
 */
public interface Accountable {

    /**
     * Return the memory usage of this object in bytes. Negative values are illegal.
     * <p>
     *     返回此对象的内存使用情况（以字节为单位）。 负值是非法的。
     * </p>
     */
    long ramBytesUsed();

    /**
     * Returns nested resources of this class.
     * The result should be a point-in-time snapshot (to avoid race conditions).
     *
     * <p>
     *     返回此类的嵌套资源。 结果应该是时间点快照（以避免争用情况）。
     * </p>
     *
     * @see Accountables
     */
    default Collection<Accountable> getChildResources() {
        return Collections.emptyList();
    }

}
