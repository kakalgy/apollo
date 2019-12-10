package com.mythology.cloud.apollo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Helper methods for constructing nested resource descriptions
 * and debugging RAM usage.
 * <p>
 * {@code toString(Accountable}} can be used to quickly debug the nested
 * structure of any Accountable.
 * <p>
 * The {@code namedAccountable} and {@code namedAccountables} methods return
 * type-safe, point-in-time snapshots of the provided resources.
 *
 * @Author kakalgy
 * @Date 2019/12/10 21:52
 **/
public class Accountables {
    /**
     * 私有构造函数,某种情况下，我们只需要把某个类（工具类）当成“函数”使用，即只需要用到里面的static方法完成某些功能
     */
    private Accountables() {
    }

    /**
     * Returns a String description of an Accountable and any nested resources.
     * This is intended for development and debugging.
     * <p>
     * 返回一个Accountable和所有的嵌套资源的字符串描述。 这用于开发和调试。
     * </p>
     */
    public static String toString(Accountable a) {
        StringBuilder sb = new StringBuilder();
        toString(sb, a, 0);
        return sb.toString();
    }

    /**
     * 私有方法，
     *
     * @param dest
     * @param a
     * @param depth a的深度
     * @return
     */
    private static StringBuilder toString(StringBuilder dest, Accountable a, int depth) {
        for (int i = 1; i < depth; i++) {
            dest.append("    ");
        }

        if (depth > 0) {
            dest.append("|-- ");
        }

        dest.append(a.toString());
        dest.append(": ");
        dest.append(RamUsageEstimator.humanReadableUnits(a.ramBytesUsed()));
        dest.append(System.lineSeparator());

        for (Accountable child : a.getChildResources()) {
            toString(dest, child, depth + 1);
        }

        return dest;
    }

    /**
     * Augments an existing accountable with the provided description.
     * <p>
     * 返回一个给定的Accountable
     * </p>
     * <p>
     * The resource description is constructed in this format:
     * {@code description [toString()]}
     * <p>
     * This is a point-in-time type safe view: consumers
     * will not be able to cast or manipulate the resource in any way.
     */
    public static Accountable namedAccountable(String description, Accountable in) {
        return namedAccountable(description + " [" + in + "]", in.getChildResources(), in.ramBytesUsed());
    }

    /**
     * Returns an accountable with the provided description and bytes.
     */
    public static Accountable namedAccountable(String description, long bytes) {
        return namedAccountable(description, Collections.<Accountable>emptyList(), bytes);
    }

    /**
     * Converts a map of resources to a collection.
     * <p>
     * The resource descriptions are constructed in this format:
     * {@code prefix 'key' [toString()]}
     * <p>
     * This is a point-in-time type safe view: consumers
     * will not be able to cast or manipulate the resources in any way.
     */
    public static Collection<Accountable> namedAccountables(String prefix, Map<?, ? extends Accountable> in) {
        List<Accountable> resources = new ArrayList<>();
        for (Map.Entry<?, ? extends Accountable> kv : in.entrySet()) {
            resources.add(namedAccountable(prefix + " '" + kv.getKey() + "'", kv.getValue()));
        }
        Collections.sort(resources, new Comparator<Accountable>() {
            @Override
            public int compare(Accountable o1, Accountable o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
        return Collections.unmodifiableList(resources);
    }

    /**
     * Returns an accountable with the provided description, children and bytes.
     * <p>
     * 根据提供的description，children，bytes构建一个Accountable
     * </p>
     * <p>
     * The resource descriptions are constructed in this format:
     * {@code description [toString()]}
     * <p>
     * This is a point-in-time type safe view: consumers
     * will not be able to cast or manipulate the resources in any way, provided
     * that the passed in children Accountables (and all their descendants) were created
     * with one of the namedAccountable functions.
     *
     * <p>
     * 这是point-in-time类型的安全视图：
     * 如果传入的子Accountables（及其所有后代）是使用namedAccountable函数之一创建的，
     * 则消费者将无法以任何方式转换或操纵资源。
     * </p>
     */
    public static Accountable namedAccountable(final String description, final Collection<Accountable> children, final long bytes) {
        return new Accountable() {
            @Override
            public long ramBytesUsed() {
                return bytes;
            }

            @Override
            public Collection<Accountable> getChildResources() {
                return children;
            }

            @Override
            public String toString() {
                return description;
            }
        };
    }
}
