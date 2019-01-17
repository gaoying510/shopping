package com.neuedu.utils;

import java.math.BigDecimal;

/*
* 价格计算工具类
* */
public class BigDecimaUtils {


    /*
    * 加法运算
    *
    * */

    public static BigDecimal add(Double d1,Double d2)
    {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecima2 = new BigDecimal(String.valueOf(d2));
        return bigDecimal.add(bigDecima2);
    }
    /*
     * 减法运算
     *
     * */

    public static BigDecimal sub(Double d1,Double d2)
    {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecima2 = new BigDecimal(String.valueOf(d2));
        return bigDecimal.subtract(bigDecima2);
    }
    /*
     * 乘法运算
     *
     * */

    public static BigDecimal mul(Double d1,Double d2)
    {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecima2 = new BigDecimal(String.valueOf(d2));
        return bigDecimal.multiply(bigDecima2);
    }
    /*
     * 除法运算
     *
     * */

    public static BigDecimal div(Double d1,Double d2)
    {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecima2 = new BigDecimal(String.valueOf(d2));
                                                // 小数点后面保留两位小数，四舍五入
        return bigDecimal.divide(bigDecima2,2,BigDecimal.ROUND_HALF_UP);
    }
}
