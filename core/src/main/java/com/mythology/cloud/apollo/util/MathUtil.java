package com.mythology.cloud.apollo.util;

import java.math.BigInteger;

/**
 * Math static utility methods.
 *
 * <p>
 * 数学静态实用程序方法。
 * </p>
 */
public final class MathUtil {

    // No instance:
    private MathUtil() {
    }

    /**
     * Returns {@code x <= 0 ? 0 : Math.floor(Math.log(x) / Math.log(base))}
     *
     * <p>
     * Math.floor(x)：返回小于等于x的最大整数；
     * Math.log(x)：返回参数的自然数底数（e=Math.E）的对数值，即loge(X)
     * 若x<=0，则返回结果为0；否则，Math.log(x) / Math.log(base) 即为log(e)(x) / log(e)(base) = log(x)(base)，以x为底数
     * </p>
     *
     * @param base must be {@code > 1}
     */
    public static int log(long x, int base) {
        if (base <= 1) {
            throw new IllegalArgumentException("base must be > 1");
        }
        int ret = 0;
        while (x >= base) {
            x /= base;
            ret++;
        }
        return ret;
    }

    /**
     * Calculates logarithm in a given base with doubles.
     *
     * <p>
     * log(e)(x) / log(e)(base) = log(x)(base)，以x为底数
     * </p>
     */
    public static double log(double base, double x) {
        return Math.log(x) / Math.log(base);
    }

    /**
     * Return the greatest common divisor of <code>a</code> and <code>b</code>,
     * consistently with {@link BigInteger#gcd(BigInteger)}.
     * <p>
     * 返回与{@link BigInteger＃gcd（BigInteger）}一致的<code> a </code>和<code> b </code>的最大公约数。
     * </p>
     * <p><b>NOTE</b>: A greatest common divisor must be positive, but
     * <code>2^64</code> cannot be expressed as a long although it
     * is the GCD of {@link Long#MIN_VALUE} and <code>0</code> and the GCD of
     * {@link Long#MIN_VALUE} and {@link Long#MIN_VALUE}. So in these 2 cases,
     * and only them, this method will return {@link Long#MIN_VALUE}.
     *
     * <p>
     * <p> <b>注意</ b>：最大公约数必须为正，但是<code> 2 ^ 64 </code>不能表示为long，
     * 尽管它是{@link Long＃MIN_VALUE}的GCD 和<code> 0 </code>以及{@link Long＃MIN_VALUE}和{@link Long＃MIN_VALUE}的GCD。
     * 因此，只有这两种情况，此方法将返回{@link Long＃MIN_VALUE}。
     * </p>
     */
    // see http://en.wikipedia.org/wiki/Binary_GCD_algorithm#Iterative_version_in_C.2B.2B_using_ctz_.28count_trailing_zeros.29
    public static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (a == 0) {
            return b;
        } else if (b == 0) {
            return a;
        }
        final int commonTrailingZeros = Long.numberOfTrailingZeros(a | b);
        a >>>= Long.numberOfTrailingZeros(a);
        while (true) {
            b >>>= Long.numberOfTrailingZeros(b);
            if (a == b) {
                break;
            } else if (a > b || a == Long.MIN_VALUE) { // MIN_VALUE is treated as 2^64
                final long tmp = a;
                a = b;
                b = tmp;
            }
            if (a == 1) {
                break;
            }
            b -= a;
        }
        return a << commonTrailingZeros;
    }


    /**
     * Calculates inverse hyperbolic sine of a {@code double} value.
     * 计算{@code double}值的反双曲正弦值。
     * <p>
     * Special cases:
     * <ul>
     * <li>If the argument is NaN, then the result is NaN.
     * <li>If the argument is zero, then the result is a zero with the same sign as the argument.
     * <li>If the argument is infinite, then the result is infinity with the same sign as the argument.
     * </ul>
     * <p>
     * Math.sqrt(x)：此方法返回一个正平方根。如果参数是NaN或小于为零，那么结果为NaN，例Math.sqrt(9)=3.0
     * Math.abs(x)：返回数的绝对值
     * <p>
     * https://baike.baidu.com/item/%E5%8F%8D%E5%8F%8C%E6%9B%B2%E5%87%BD%E6%95%B0/7924014?fr=aladdin
     */
    public static double asinh(double a) {
        final double sign;
        // check the sign bit of the raw representation to handle -0
        /**
         * Double类中doubleToRawLongBits，double类型占64位，而long类型也是占64位，
         * 两个类型在计算机中存储的机器码都是64位二进制，从0和1的角度来看，是没有任何区别的。
         * 区别在于，对应同一64位二进制机器码，两者的解析方式不同。
         *
         *所以，在jdk中double类（float与int对应）中提供了double与long转换，
         * doubleToRawLongBits就是将double转换为long，
         * 这个方法是原始方法（底层不是java实现，是c++实现的）
         */
        if (Double.doubleToRawLongBits(a) < 0) {
            a = Math.abs(a);
            sign = -1.0d;
        } else {
            sign = 1.0d;
        }

        return sign * Math.log(Math.sqrt(a * a + 1.0d) + a);
    }

    /**
     * Calculates inverse hyperbolic cosine of a {@code double} value.
     * 计算{@code double}值的反双曲余弦值。
     * <p>
     * Special cases:
     * <ul>
     * <li>If the argument is NaN, then the result is NaN.
     * <li>If the argument is +1, then the result is a zero.
     * <li>If the argument is positive infinity, then the result is positive infinity.
     * <li>If the argument is less than 1, then the result is NaN.
     * </ul>
     * <p>
     * https://baike.baidu.com/item/%E5%8F%8D%E5%8F%8C%E6%9B%B2%E5%87%BD%E6%95%B0/7924014?fr=aladdin
     */
    public static double acosh(double a) {
        return Math.log(Math.sqrt(a * a - 1.0d) + a);
    }

    /**
     * Calculates inverse hyperbolic tangent of a {@code double} value.
     *
     * <p>
     * 计算{@code double}值的反双曲正切值。
     * </p>
     * <p>
     * Special cases:
     * <ul>
     * <li>If the argument is NaN, then the result is NaN.
     * <li>If the argument is zero, then the result is a zero with the same sign as the argument.
     * <li>If the argument is +1, then the result is positive infinity.
     * <li>If the argument is -1, then the result is negative infinity.
     * <li>If the argument's absolute value is greater than 1, then the result is NaN.
     * </ul>
     * <p>
     * https://baike.baidu.com/item/%E5%8F%8D%E5%8F%8C%E6%9B%B2%E5%87%BD%E6%95%B0/7924014?fr=aladdin
     */
    public static double atanh(double a) {
        final double mult;
        // check the sign bit of the raw representation to handle -0
        if (Double.doubleToRawLongBits(a) < 0) {
            a = Math.abs(a);
            mult = -0.5d;
        } else {
            mult = 0.5d;
        }
        return mult * Math.log((1.0d + a) / (1.0d - a));
    }

    /**
     * Return a relative error bound for a sum of {@code numValues} positive doubles,
     * computed using recursive summation, ie. sum = x1 + ... + xn.
     * <p>
     * 返回相对误差范围，该误差范围是使用递归求和法计算的{@code numValues}正双精度和的和。
     * sum = x1 + ... + xn。
     * </p>
     *
     * <p>
     * NOTE: This only works if all values are POSITIVE so that Σ |xi| == |Σ xi|.
     * This uses formula 3.5 from Higham, Nicholas J. (1993),
     * "The accuracy of floating point summation", SIAM Journal on Scientific Computing.
     */
    public static double sumRelativeErrorBound(int numValues) {
        if (numValues <= 1) {
            return 0;
        }
        // u = unit roundoff in the paper, also called machine precision or machine epsilon
        double u = Math.scalb(1.0, -52);
        return (numValues - 1) * u;
    }



}
