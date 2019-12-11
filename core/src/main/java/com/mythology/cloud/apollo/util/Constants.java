package com.mythology.cloud.apollo.util;

import java.util.StringTokenizer;


/**
 * Some useful constants.
 * 主要是java的版本信息
 *
 * <p>
 * https://www.cnblogs.com/huiy/p/9390225.html
 * </p>
 **/

public final class Constants {
    private Constants() {
    }  // can't construct

    /**
     * JVM vendor info.  JVM供应商信息  Java 虚拟机实现供应商
     */
    public static final String JVM_VENDOR = System.getProperty("java.vm.vendor");
    /**
     * Java 虚拟机实现版本
     */
    public static final String JVM_VERSION = System.getProperty("java.vm.version");
    /**
     * Java 虚拟机实现名称
     */
    public static final String JVM_NAME = System.getProperty("java.vm.name");
    /**
     * Java 虚拟机规范版本
     */
    public static final String JVM_SPEC_VERSION = System.getProperty("java.specification.version");

    /**
     * The value of <tt>System.getProperty("java.version")</tt>. java版本
     **/
    public static final String JAVA_VERSION = System.getProperty("java.version");

    /**
     * The value of <tt>System.getProperty("os.name")</tt>. 系统名称
     **/
    public static final String OS_NAME = System.getProperty("os.name");
    /**
     * True iff running on Linux. 是否为linux
     */
    public static final boolean LINUX = OS_NAME.startsWith("Linux");
    /**
     * True iff running on Windows. 是否为windows
     */
    public static final boolean WINDOWS = OS_NAME.startsWith("Windows");
    /**
     * True iff running on SunOS. 是否为SunOS
     */
    public static final boolean SUN_OS = OS_NAME.startsWith("SunOS");
    /**
     * True iff running on Mac OS X. 是否为Mac
     */
    public static final boolean MAC_OS_X = OS_NAME.startsWith("Mac OS X");
    /**
     * True iff running on FreeBSD. 是否为FreeBSD
     */
    public static final boolean FREE_BSD = OS_NAME.startsWith("FreeBSD");

    /**
     * 系统架构 如x86
     */
    public static final String OS_ARCH = System.getProperty("os.arch");
    /**
     * 系统版本 如6.1
     */
    public static final String OS_VERSION = System.getProperty("os.version");
    /**
     * Java 运行时环境供应商
     */
    public static final String JAVA_VENDOR = System.getProperty("java.vendor");

    private static final int JVM_MAJOR_VERSION;
    private static final int JVM_MINOR_VERSION;

    /**
     * True iff running on a 64bit JVM
     */
    public static final boolean JRE_IS_64BIT;

    static {
        //用于分隔字符串，获取java的mayor和minor版本号
        final StringTokenizer st = new StringTokenizer(JVM_SPEC_VERSION, ".");
        JVM_MAJOR_VERSION = Integer.parseInt(st.nextToken());
        if (st.hasMoreTokens()) {
            JVM_MINOR_VERSION = Integer.parseInt(st.nextToken());
        } else {
            JVM_MINOR_VERSION = 0;
        }

        //判断是否为64位版本
        boolean is64Bit = false;
        String datamodel = null;
        try {
            datamodel = System.getProperty("sun.arch.data.model");
            if (datamodel != null) {
                is64Bit = datamodel.contains("64");
            }
        } catch (SecurityException ex) {
        }
        if (datamodel == null) {
            if (OS_ARCH != null && OS_ARCH.contains("64")) {
                is64Bit = true;
            } else {
                is64Bit = false;
            }
        }
        JRE_IS_64BIT = is64Bit;
    }

    public static final boolean JRE_IS_MINIMUM_JAVA8 = JVM_MAJOR_VERSION > 1 || (JVM_MAJOR_VERSION == 1 && JVM_MINOR_VERSION >= 8);
    public static final boolean JRE_IS_MINIMUM_JAVA9 = JVM_MAJOR_VERSION > 1 || (JVM_MAJOR_VERSION == 1 && JVM_MINOR_VERSION >= 9);
    public static final boolean JRE_IS_MINIMUM_JAVA11 = JVM_MAJOR_VERSION > 1 || (JVM_MAJOR_VERSION == 1 && JVM_MINOR_VERSION >= 11);

    /**
     * This is the internal Lucene version, including bugfix versions, recorded into each segment.
     * 这是内部Lucene版本，包括错误修正版本，记录在每个段中。
     *
     * @deprecated Use {@link Version#LATEST}
     */
    @Deprecated
    public static final String LUCENE_MAIN_VERSION = Version.LATEST.toString();

    /**
     * Don't use this constant because the name is not self-describing!
     * 不要使用此常量，因为名称不是自描述的！
     *
     * @deprecated Use {@link Version#LATEST}
     */
    @Deprecated
    public static final String LUCENE_VERSION = Version.LATEST.toString();

}
