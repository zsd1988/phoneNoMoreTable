package com.qingpu.phone.common.utils;


import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * @运算
*/

public class ArithUtil {

    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    private ArithUtil() {

    }

    /**
     * @提供精确的加法运算。
     *
     * @param v1
     *            被加数
     * @param v2
     *            加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * @提供精确的减法运算。
     *
     * @param v1
     *            被减数
     * @param v2
     *            减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * @提供精确的乘法运算。
     *
     * @param v1
     *            被乘数
     * @param v2
     *            乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * @提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * @提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @param scale
     *            表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("参数scale必须为整数为零!");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * @提供精确的小数位四舍五入处理。
     *
     * @param v
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("参数scale必须为整数为零!");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * @返回保留两位小数的数字
     *
     * @param v
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double getRoundTwo(double v) {
        return round(v, 2);
    }

    /**
     *@ 提供精确的类型转换(Float)
     *
     * @param v
     *            需要被转换的数字
     * @return 返回转换结果
     */
    public static float convertsToFloat(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.floatValue();
    }

    /**
     * @提供精确的类型转换(Int)进行四舍五入
     *
     * @param v
     *            需要被转换的数字
     * @return 返回转换结果
     */
    public static int convertsToInt(double v) {
        return Integer.parseInt(new DecimalFormat("0").format(v));
    }

    /**
     * @提供精确的类型转换(Long)
     *
     * @param v
     *            需要被转换的数字
     * @return 返回转换结果
     */
    public static long convertsToLong(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }

    /**
     * @返回两个数中大的一个值
     *
     * @param v1
     *            需要被对比的第一个数
     * @param v2
     *            需要被对比的第二个数
     * @return 返回两个数中大的一个值
     */
    public static double returnMax(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2).doubleValue();
    }

    /**
     * @返回两个数中小的一个值
     *
     * @param v1
     *            需要被对比的第一个数
     * @param v2
     *            需要被对比的第二个数
     * @return 返回两个数中小的一个值
     */
    public static double returnMin(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2).doubleValue();
    }

    /**
     * @精确比较两个数字
     *
     * @param v1
     *            需要被对比的第一个数
     * @param v2
     *            需要被对比的第二个数
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    public static int compareTo(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }

    /**
     * @获取数字小数位数
     *
     * @param number
     *            数字.
     *
     * @return 小数位数
     */
    public static int getDecimals(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        String numberString = decimalFormat.format(number);
        if (numberString.indexOf(".") > 0) {
            return numberString.length() - String.valueOf(number).indexOf(".") - 1;
        } else {
            return 0;
        }
    }

    /**
     * @获取数字小数位数
     *
     * @param number
     *            数字.
     *
     * @return 小数位数
     */
    public static int getDecimals(float number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        String numberString = decimalFormat.format(number);
        if (numberString.indexOf(".") > 0) {
            return numberString.length() - String.valueOf(number).indexOf(".") - 1;
        } else {
            return 0;
        }
    }

    /**
     * 去除末位的0
     * @param v1
     * @return
     */
    public static String subDoubleZero(double v1) {
        if(v1 % 10 == 0){
            if(v1 == 0){
                return "0";
            }
            String temp = v1 + "";
            String[] tempArr = StringUtils.split(temp, ".");
            temp = tempArr[0];
            return Integer.parseInt(temp) + "";
        }else{
            String result = new BigDecimal(v1).stripTrailingZeros() + "";
            if(result.length() > 10){
                result = v1 + "";
            }
            return result;
        }
    }



    /**
     * 获取min到max中的一个随机数，包含min和max
     * @param min
     * @param max
     * @return
     */
    public static int randomDigit(int min , int max){
        Random rand= new Random();
        int tmp = Math.abs(rand.nextInt());
        tmp = tmp%(max-min+1)+min;
        return tmp;
    }

    /**
     * 转成万位数字,最多保留千位
     * @param v1
     * @return
     */
    public static Double toWan(Double v1) {
        if(v1 == null){
            return 0.0;
        }
        return ArithUtil.round(v1/10000, 1);
    }

    /**
     * 获取概率是否发生；0.32则为1到100之间，产生一个随机数是否小于等于32.
     * @param rate
     * @return
     */
    public static Boolean getRateAnswer(Double rate){
        if(rate != null){
            if(rate == 0){
                return false;
            }
            if(rate >= 1){
                return true;
            }
            Integer bits = ArithUtil.getDecimals(rate);
            Integer maxNum = (int)Math.pow(10, bits.doubleValue());
            Integer num = ArithUtil.randomDigit(1,maxNum);
            if(num <= rate * maxNum){
                return true;
            }
        }
        return false;
    }

    /**
     * 十六进制字符串转十进制
     * @param str
     * @return
     */
    public static int hexToDec(String str){
        int result = 0;
        if(StringUtils.isNotBlank(str)){
            result = Integer.parseInt(str.replaceAll("^0[x|X]", ""), 16);
        }
        return result;
    }
}
