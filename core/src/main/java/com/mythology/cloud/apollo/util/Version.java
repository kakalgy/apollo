package com.mythology.cloud.apollo.util;

import java.text.ParseException;
import java.util.Locale;

/**
 * Use by certain classes to match version compatibility
 * across releases of Lucene.
 * <p>由某些类使用，以匹配Lucene发行版之间的版本兼容性。</p>
 *
 * <p><b>WARNING</b>: When changing the version parameter
 * that you supply to components in Lucene, do not simply
 * change the version at search-time, but instead also adjust
 * your indexing code to match, and re-index.
 *
 * <p> <b>警告</b>：在Lucene中更改为组件提供的版本参数时，
 * 不要简单地在搜索时更改版本，而还要调整索引代码以匹配并重新索引 </p>
 */
public final class Version {

    /**
     * Match settings and bugs in Lucene's 7.0.0 release.
     * <p>匹配Lucene 7.0.0版本中的设置和错误。</p>
     *
     * @deprecated (8.0.0) Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_0_0 = new Version(7, 0, 0);

    /**
     * Match settings and bugs in Lucene's 7.0.1 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_0_1 = new Version(7, 0, 1);

    /**
     * Match settings and bugs in Lucene's 7.1.0 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_1_0 = new Version(7, 1, 0);

    /**
     * Match settings and bugs in Lucene's 7.2.0 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_2_0 = new Version(7, 2, 0);

    /**
     * Match settings and bugs in Lucene's 7.2.1 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_2_1 = new Version(7, 2, 1);


    /**
     * Match settings and bugs in Lucene's 7.3.0 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_3_0 = new Version(7, 3, 0);

    /**
     * Match settings and bugs in Lucene's 7.3.1 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_3_1 = new Version(7, 3, 1);

    /**
     * Match settings and bugs in Lucene's 7.4.0 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_4_0 = new Version(7, 4, 0);

    /**
     * Match settings and bugs in Lucene's 7.5.0 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_5_0 = new Version(7, 5, 0);

    /**
     * Match settings and bugs in Lucene's 7.6.0 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_6_0 = new Version(7, 6, 0);

    /**
     * Match settings and bugs in Lucene's 7.7.0 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_7_0 = new Version(7, 7, 0);

    /**
     * Match settings and bugs in Lucene's 7.7.1 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_7_1 = new Version(7, 7, 1);

    /**
     * Match settings and bugs in Lucene's 7.7.2 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_7_2 = new Version(7, 7, 2);

    /**
     * Match settings and bugs in Lucene's 7.8.0 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_7_8_0 = new Version(7, 8, 0);

    /**
     * Match settings and bugs in Lucene's 8.0.0 release.
     *
     * @deprecated (8.1.0) Use latest
     */
    @Deprecated
    public static final Version LUCENE_8_0_0 = new Version(8, 0, 0);

    /**
     * Match settings and bugs in Lucene's 8.1.0 release.
     *
     * @deprecated (8.2.0) Use latest
     */
    @Deprecated
    public static final Version LUCENE_8_1_0 = new Version(8, 1, 0);

    /**
     * Match settings and bugs in Lucene's 8.1.1 release.
     *
     * @deprecated Use latest
     */
    @Deprecated
    public static final Version LUCENE_8_1_1 = new Version(8, 1, 1);

    /**
     * Match settings and bugs in Lucene's 8.2.0 release.
     *
     * @deprecated (8.3.0) Use latest
     */
    @Deprecated
    public static final Version LUCENE_8_2_0 = new Version(8, 2, 0);

    /**
     * Match settings and bugs in Lucene's 8.3.0 release.
     * <p>
     * Use this to get the latest &amp; greatest settings, bug
     * fixes, etc, for Lucene.
     */
    public static final Version LUCENE_8_3_0 = new Version(8, 3, 0);

    // To add a new version:
    //  * Only add above this comment
    //  * If the new version is the newest, change LATEST below and deprecate the previous LATEST

    /**
     * <p><b>WARNING</b>: if you use this setting, and then
     * upgrade to a newer release of Lucene, sizable changes
     * may happen.  If backwards compatibility is important
     * then you should instead explicitly specify an actual
     * version.
     *
     * <p>
     * 如果使用此设置，然后升级到Lucene的较新版本，
     * 则可能会发生较大的更改。 如果向后兼容性很重要，则应改为明确指定实际版本。
     * </p>
     *
     * <p>
     * If you use this constant then you  may need to
     * <b>re-index all of your documents</b> when upgrading
     * Lucene, as the way text is indexed may have changed.
     * Additionally, you may need to <b>re-test your entire
     * application</b> to ensure it behaves as expected, as
     * some defaults may have changed and may break functionality
     * in your application.
     *
     * <p>
     * 如果使用此常量，则在升级Lucene时可能需要<b>重新索引所有文档</b>，
     * 因为文本的索引方式可能已更改。 此外，您可能需要<b>重新测试整个应用程序</b>，
     * 以确保其表现正常，因为某些默认设置可能已更改，并可能破坏应用程序的功能。
     * </p>
     */
    public static final Version LATEST = LUCENE_8_3_0;

    /**
     * Constant for backwards compatibility.
     *
     * @deprecated Use {@link #LATEST}
     */
    @Deprecated
    public static final Version LUCENE_CURRENT = LATEST;

    /**
     * Parse a version number of the form {@code "major.minor.bugfix.prerelease"}.
     * <p>
     * Part {@code ".bugfix"} and part {@code ".prerelease"} are optional.
     * Note that this is forwards compatible: the parsed version does not have to exist as
     * a constant.
     *
     * <p>
     * 解析格式为{@code“ major.minor.bugfix.prerelease”}的版本号。
     * {@code“ .bugfix”}部分和{@code“ .prerelease”}部分是可选的。
     * <p>
     * 请注意，这是向前兼容的：解析的版本不必作为常量存在。
     * </p>
     *
     * @lucene.internal
     */
    public static Version parse(String version) throws ParseException {

        StrictStringTokenizer tokens = new StrictStringTokenizer(version, '.');
        if (tokens.hasMoreTokens() == false) {
            throw new ParseException("Version is not in form major.minor.bugfix(.prerelease) (got: " + version + ")", 0);
        }

        int major;
        String token = tokens.nextToken();
        try {
            major = Integer.parseInt(token);
        } catch (NumberFormatException nfe) {
            ParseException p = new ParseException("Failed to parse major version from \"" + token + "\" (got: " + version + ")", 0);
            p.initCause(nfe);
            throw p;
        }

        if (tokens.hasMoreTokens() == false) {
            throw new ParseException("Version is not in form major.minor.bugfix(.prerelease) (got: " + version + ")", 0);
        }

        int minor;
        token = tokens.nextToken();
        try {
            minor = Integer.parseInt(token);
        } catch (NumberFormatException nfe) {
            ParseException p = new ParseException("Failed to parse minor version from \"" + token + "\" (got: " + version + ")", 0);
            p.initCause(nfe);
            throw p;
        }

        int bugfix = 0;
        int prerelease = 0;
        if (tokens.hasMoreTokens()) {

            token = tokens.nextToken();
            try {
                bugfix = Integer.parseInt(token);
            } catch (NumberFormatException nfe) {
                ParseException p = new ParseException("Failed to parse bugfix version from \"" + token + "\" (got: " + version + ")", 0);
                p.initCause(nfe);
                throw p;
            }

            if (tokens.hasMoreTokens()) {
                token = tokens.nextToken();
                try {
                    prerelease = Integer.parseInt(token);
                } catch (NumberFormatException nfe) {
                    ParseException p = new ParseException("Failed to parse prerelease version from \"" + token + "\" (got: " + version + ")", 0);
                    p.initCause(nfe);
                    throw p;
                }
                if (prerelease == 0) {
                    throw new ParseException("Invalid value " + prerelease + " for prerelease; should be 1 or 2 (got: " + version + ")", 0);
                }

                if (tokens.hasMoreTokens()) {
                    // Too many tokens!
                    throw new ParseException("Version is not in form major.minor.bugfix(.prerelease) (got: " + version + ")", 0);
                }
            }
        }

        try {
            return new Version(major, minor, bugfix, prerelease);
        } catch (IllegalArgumentException iae) {
            ParseException pe = new ParseException("failed to parse version string \"" + version + "\": " + iae.getMessage(), 0);
            pe.initCause(iae);
            throw pe;
        }
    }

    /**
     * Parse the given version number as a constant or dot based version.
     * <p>This method allows to use {@code "LUCENE_X_Y"} constant names,
     * or version numbers in the format {@code "x.y.z"}.
     *
     * <p>
     * 将给定的版本号解析为基于常量或基于点的版本。
     * 此方法允许使用{@code“ LUCENE_X_Y”}常量名称或格式为{@code“ x.y.z”}的版本号。
     * </p>
     *
     * @lucene.internal
     */
    public static Version parseLeniently(String version) throws ParseException {
        String versionOrig = version;
        version = version.toUpperCase(Locale.ROOT);
        switch (version) {
            case "LATEST":
            case "LUCENE_CURRENT":
                return LATEST;
            default:
                version = version
                        .replaceFirst("^LUCENE_(\\d+)_(\\d+)_(\\d+)$", "$1.$2.$3")
                        .replaceFirst("^LUCENE_(\\d+)_(\\d+)$", "$1.$2.0")
                        .replaceFirst("^LUCENE_(\\d)(\\d)$", "$1.$2.0");
                try {
                    return parse(version);
                } catch (ParseException pe) {
                    ParseException pe2 = new ParseException("failed to parse lenient version string \"" + versionOrig + "\": " + pe.getMessage(), 0);
                    pe2.initCause(pe);
                    throw pe2;
                }
        }
    }

    /**
     * Returns a new version based on raw numbers
     * <p>类似于构造函数</p>
     *
     * @lucene.internal
     */
    public static Version fromBits(int major, int minor, int bugfix) {
        return new Version(major, minor, bugfix);
    }


    //------------------------------------------------------
    //------------------------------------------------------
    //
    // 以下为该类的属性，构造函数，Object类重写方法
    //
    //------------------------------------------------------
    //------------------------------------------------------


    /**
     * Major version, the difference between stable and trunk
     */
    public final int major;
    /**
     * Minor version, incremented within the stable branch
     */
    public final int minor;
    /**
     * Bugfix number, incremented on release branches
     */
    public final int bugfix;
    /**
     * Prerelease version, currently 0 (alpha), 1 (beta), or 2 (final)
     * <p>
     * 预发行版本，当前为0（alpha），1（beta）或2（final）
     * </p>
     */
    public final int prerelease;

    // stores the version pieces, with most significant pieces in high bits 存储版本，最高有效位以高位存储
    // ie:  | 1 byte | 1 byte | 1 byte |   2 bits   |
    //         major   minor    bugfix   prerelease
    private final int encodedValue;

    private Version(int major, int minor, int bugfix) {
        this(major, minor, bugfix, 0);
    }

    private Version(int major, int minor, int bugfix, int prerelease) {
        this.major = major;
        this.minor = minor;
        this.bugfix = bugfix;
        this.prerelease = prerelease;
        // NOTE: do not enforce major version so we remain future proof, except to
        // make sure it fits in the 8 bits we encode it into:
        if (major > 255 || major < 0) {
            throw new IllegalArgumentException("Illegal major version: " + major);
        }
        if (minor > 255 || minor < 0) {
            throw new IllegalArgumentException("Illegal minor version: " + minor);
        }
        if (bugfix > 255 || bugfix < 0) {
            throw new IllegalArgumentException("Illegal bugfix version: " + bugfix);
        }
        if (prerelease > 2 || prerelease < 0) {
            throw new IllegalArgumentException("Illegal prerelease version: " + prerelease);
        }
        if (prerelease != 0 && (minor != 0 || bugfix != 0)) {
            throw new IllegalArgumentException("Prerelease version only supported with major release (got prerelease: " + prerelease + ", minor: " + minor + ", bugfix: " + bugfix + ")");
        }

        encodedValue = major << 18 | minor << 10 | bugfix << 2 | prerelease;

        assert encodedIsValid();
    }

    /**
     * Returns true if this version is the same or after the version from the argument.
     */
    public boolean onOrAfter(Version other) {
        return encodedValue >= other.encodedValue;
    }

    @Override
    public String toString() {
        if (prerelease == 0) {
            return "" + major + "." + minor + "." + bugfix;
        }
        return "" + major + "." + minor + "." + bugfix + "." + prerelease;
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof Version && ((Version) o).encodedValue == encodedValue;
    }


    /**
     * Used only by assert
     * 判定encodedValue与major，minor，bugfix，prerelease是否匹配
     *
     * @return
     */
    private boolean encodedIsValid() {
        assert major == ((encodedValue >>> 18) & 0xFF);
        assert minor == ((encodedValue >>> 10) & 0xFF);
        assert bugfix == ((encodedValue >>> 2) & 0xFF);
        assert prerelease == (encodedValue & 0x03);
        return true;
    }

    @Override
    public int hashCode() {
        return encodedValue;
    }
}

