package com.qingpu.phone.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    // 默认日期格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    //小时分钟秒
    public static final String TIME_FORMAT = "HH:mm:ss";

    // 默认日期时间格式
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // 8为日期格式
    public static final String DATE_FORMAT_8 = "yyyyMMdd";

    // 14为日期时间格式
    public static final String DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";

    public final static String YEAR = " year ";

    public final static String MONTH = " month ";

    public final static String DAY = " day ";

    public final static String WEEK = " week ";


    public final static String HOUR = " hour ";

    public final static String MINUTE = " minute ";

    public final static String SECOND = " second ";

    /**
     * 判断参数是否等于null或者空
     *
     * @param para
     * @return boolean
     */
    private static boolean checkPara(Object para) {
        return null == para || "".equals(para);
    }

    public static String getToday(String fmt) {
        SimpleDateFormat format = new SimpleDateFormat(fmt);
        return format.format(new Date());
    }

    public static String getYear(int type) {
        if (type == 0) {
            return getToday("yyyy") + "0101";
        } else {
            return getToday("yyyy") + "1231";
        }
    }

    public static String getNow() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    public static String getNowDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    /**
     * 日期文件夹
     *
     * @return
     */
    public static String getFolderDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }

    //返回今天+dayNum天后的日期
    public static String getAfterToday(int dayNum) {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return df.format(new Date(d.getTime() + (long) dayNum * 24 * 60 * 60 * 1000));

    }

    //返回今天+hourNum小时后的日期
    public static String getAfterTodayByHour(int hourNum) {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return df.format(new Date(d.getTime() + (long) hourNum * 60 * 60 * 1000));

    }

    //返回今天-dayNum天的日期
    public static String getBeforeToday(int dayNum) {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return df.format(new Date(d.getTime() - (long) dayNum * 24 * 60 * 60 * 1000));

    }


    /**
     * 获取当天时间的00:00:00
     *
     * @param date
     * @return
     */
    public static Date dayStart(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(11, cal.getActualMinimum(11));
        cal.set(12, cal.getActualMinimum(12));
        cal.set(13, cal.getActualMinimum(13));
        cal.set(14, cal.getActualMinimum(14));
        return cal.getTime();
    }

    /**
     * 获取当天时间的24:00:00
     *
     * @param date
     * @return
     */
    public static Date dayEnd(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(11, cal.getActualMaximum(11));
        cal.set(12, cal.getActualMaximum(12));
        cal.set(13, cal.getActualMaximum(13));
        cal.set(14, cal.getActualMaximum(14));
        return cal.getTime();
    }

    //返回今天-dayNum天的日期
    public static String getBeforeToday(String dateType) {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (Integer.parseInt(dateType) == 1) {
            return df.format(new Date(d.getTime() - (long) 3 * 24 * 60 * 60 * 1000));
        } else if (Integer.parseInt(dateType) == 2) {
            return df.format(new Date(d.getTime() - (long) 7 * 24 * 60 * 60 * 1000));
        } else if (Integer.parseInt(dateType) == 3) {
            return df.format(new Date(d.getTime() - (long) 30 * 24 * 60 * 60 * 1000));
        } else if (Integer.parseInt(dateType) == 4) {
            return df.format(new Date(d.getTime() - (long) 90 * 24 * 60 * 60 * 1000));
        }
        return "";
    }

    //返回今天+dayNum天后的日期
    public static String getAfterToday(int dayNum, String fmt) {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat(fmt);

        return df.format(new Date(d.getTime() + (long) dayNum * 24 * 60 * 60 * 1000));

    }

    //返回N个月后的日期
    public static String getAfterMonth(int n, String fmt) {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat(fmt);

        return df.format(new Date(d.getTime() + (long) n * 24 * 60 * 60 * 1000 * 30));

    }

    //返回N个月前的日期
    public static String getBeforeMonth(int n, String fmt) {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat(fmt);

        return df.format(new Date(d.getTime() - (long) n * 24 * 60 * 60 * 1000 * 30));

    }

    /**
     * 按时间格式相加
     *
     * @param dateStr 原来的时间
     * @param pattern 时间格式
     * @param type    "year"年、"month"月、"day"日、"week"周、
     *                "hour"时、"minute"分、"second"秒
     *                通过常量来设置,e.g.DateFormatUtils.YEAR
     * @param count   相加数量
     * @return
     */
    public static String addDate(String dateStr, String pattern,
                                 String type, int count) {
        if (checkPara(dateStr) || checkPara(pattern) || checkPara(type)) {
            return "";
        }
        if (0 == count) {
            return dateStr;
        }
        Date date = parseStrToCustomPatternDate(dateStr, pattern);
        Locale loc = Locale.getDefault();
        Calendar cal = new GregorianCalendar(loc);
        cal.setTime(date);

        if (YEAR.equals(type)) {
            cal.add(Calendar.YEAR, count);
        } else if (MONTH.equals(type)) {
            cal.add(Calendar.MONTH, count);
        } else if (DAY.equals(type)) {
            cal.add(Calendar.DAY_OF_MONTH, count);
        } else if (WEEK.equals(type)) {
            cal.add(Calendar.WEEK_OF_MONTH, count);
        } else if (HOUR.equals(type)) {
            cal.add(Calendar.HOUR, count);
        } else if (MINUTE.equals(type)) {
            cal.add(Calendar.MINUTE, count);
        } else if (SECOND.equals(type)) {
            cal.add(Calendar.SECOND, count);
        } else {
            return "";
        }

        return formatDate(cal.getTime(), pattern);
    }

    /**
     * 将时间或者时间日期字符串按照指定格式转换为Date
     *
     * @param dateStr
     * @param pattern
     * @return Date
     */
    public static Date parseStrToCustomPatternDate(String dateStr, String pattern) {
        if (checkPara(pattern) || checkPara(dateStr)) {
            return null;
        }
        SimpleDateFormat dateFormator = new SimpleDateFormat(
                pattern);
        Date resDate = dateFormator.parse(dateStr, new ParsePosition(0));

        return resDate;
    }

    public static String GetSysDate(String format, String StrDate, int year, int month,
                                    int day) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sFmt = new SimpleDateFormat(format);
        cal.setTime(sFmt.parse((StrDate), new ParsePosition(0)));
        if (day != 0) {
            cal.add(cal.DATE, day);
        }
        if (month != 0) {
            cal.add(cal.MONTH, month);
        }
        if (year != 0) {
            cal.add(cal.YEAR, year);

        }
        return sFmt.format(cal.getTime());
    }

    public static String getMonth(String fmt) {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat(fmt);

        return df.format(new Date(d.getTime()));
    }


    //返回day日期 + x分钟后的日期
    public static String getAfterMimutes(String day, int x) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
        Date date = null;
        try {
            date = format.parse(day);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, x);// 24小时制
        date = cal.getTime();
        cal = null;
        return format.format(date);
    }

    //返回day日期 + x秒钟后的日期
    public static String getAfterSeconds(String day, int x) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// 24小时制
        Date date = null;
        try {
            date = format.parse(day);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, x);// 24小时制
        date = cal.getTime();
        cal = null;
        return format.format(date);
    }

    //获取时间序列，赋值给ID
    public static String getTimeID() {
        String now = getToday("yyyyMMddHHmmss");

        return now + "0000";
    }

    public static String formatTime(String time, String str) {
        String[] strs = time.split("-");
        time = "";
        for (String s : strs) {
            time += s;
        }
        time += str;
        return time;
    }

    public static String formatDateAddSplit(String date) {
        if (date == null || "".equals(date)) {
            return "";
        }
        if (date.length() != 8) {
            return date;
        }
        StringBuffer sbuBuffer = new StringBuffer();
        sbuBuffer.append(date.substring(0, 4)).append("-");
        sbuBuffer.append(date.substring(4, 6)).append("-");
        sbuBuffer.append(date.substring(6, 8));

        return sbuBuffer.toString();
    }


    public static String formatTimeAddSplit(String time) {
        if (time == null || "".equals(time)) {
            return "";
        }
        if (time.length() != 14) {
            return time;
        }
        StringBuffer sbuBuffer = new StringBuffer();
        sbuBuffer.append(time.substring(0, 4)).append("-");
        sbuBuffer.append(time.substring(4, 6)).append("-");
        sbuBuffer.append(time.substring(6, 8)).append(" ");
        sbuBuffer.append(time.substring(8, 10)).append(":");
        sbuBuffer.append(time.substring(10, 12)).append(":");
        sbuBuffer.append(time.substring(12, 14));

        return sbuBuffer.toString();
    }

    /**
     * 将时间字符串从一种格式转换成另一种格式.
     *
     * @param dateStr     e.g. String dateStr = "2006-10-12 16:23:06";
     * @param patternFrom e.g. "yyyy-MM-dd HH:mm:ss"
     * @param patternTo   e.g. "yyyyMMddHHmmss"
     * @return string
     */
    public static String convertDatePattern(String dateStr,
                                            String patternFrom, String patternTo) {
        if (checkPara(patternFrom) || checkPara(patternTo) || checkPara(dateStr)) {
            return "";
        }

        SimpleDateFormat dateFormator = new SimpleDateFormat(patternFrom);
        Date resDate = dateFormator.parse(dateStr, new ParsePosition(0));
        return formatDate(resDate, patternTo);
    }

    /**
     * 输入日期，按照指定格式返回
     */
    public static String formatDate(Date date, String pattern) {
        if (checkPara(pattern) || checkPara(date)) {
            return "";
        }
        SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);

        return dateFormator.format(date);
    }

    //将20130816013057 格式转换为
    public static String formatDate(String time) {
        Date date = null;
        String after = "";
        try {
            if (StringUtil.isNotNull(time)) {
                date = new SimpleDateFormat("yyyyMMddHHmmss").parse(time);
                after = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒").format(date);
            }
        } catch (ParseException e) {
            after = time;
            e.printStackTrace();

        }
        return after;
    }

    //将20130816013057 格式转换为2013-08-16
    //只能为yyyyMMddHHmmss格式输入，输出格式可配
    public static String formatDate(String time, String fmt) {
        Date date = null;
        String after = "";
        try {
            if (StringUtil.isNotNull(time)) {
                date = new SimpleDateFormat("yyyyMMddHHmmss").parse(time);
                after = new SimpleDateFormat(fmt).format(date);
            }
        } catch (ParseException e) {
            after = time;
            e.printStackTrace();
        }
        return after;
    }

    /**
     * 将一定格式的时间数据转换为格式数据
     *
     * @param infmt  数据格式
     * @param time   数据
     * @param outfmt 输出格式
     * @return
     */
    public static String formatDate(String infmt, String time, String outfmt) {
        Date date = null;
        String after = "";
        try {
            if (StringUtil.isNotNull(time)) {
                date = new SimpleDateFormat(infmt).parse(time);
                after = new SimpleDateFormat(outfmt).format(date);
            }
        } catch (ParseException e) {
            after = time;
            e.printStackTrace();
        }
        return after;
    }


    /*
     * 将时间转换为时间戳
     */
    public static int dateToStamp(String s) {
        int ts = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(s);
            ts = (int) (date.getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ts;
    }


    /*
     * 将时间转换为时间戳
     */
    public static int dateDetailToStamp(String s) {
        int ts = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = simpleDateFormat.parse(s);
            ts = (int) (date.getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ts;
    }


    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    public static String stampToDateDetail(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String yunToDateDetail(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static boolean createTimeBefore2Hour(int createTime) {
        int nowTime = (int) (System.currentTimeMillis() / 1000);
        int time = createTime - nowTime;
        if (time > 2 * 60 * 60 || time < 0) {
            return true;
        }
        return false;
    }


    /**
     * 日期转换为字符串
     *
     * @param date    转换的对象
     * @param format  字符串格式
     * @param nullStr 当对象为空时返回的自定义字符串
     * @return
     */
    public static String formatDate(Date date, String format, String nullStr) {
        if (date == null) {
            return nullStr == null ? "" : nullStr;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * @param time
     * @return Date
     * @Description 根据时间戳获取时间
     * @author shidong.zhao
     * @date 2016-7-13 下午2:38:29
     */
    public static Date getDateByTimeStamp(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(time);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formateDateMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        return first;
    }

    public static String formatDateToDateTimeStr(Date date) {
        if (date == null)
            return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String formatDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String formatDateTime(Date date, Integer index) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (index != null) {
            if (index == 1) {
                dateFormat = new SimpleDateFormat("yyyyMMdd");
            }
        }
        return dateFormat.format(date);
    }

    public static Date strToDate(String date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDateToDateStr(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String formatDateToHourStr(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @param index
     * @return String
     * @Description
     * @author shidong.zhao
     * @date 2016-10-22 下午3:27:20
     */
    public static String dateToString(Date date, int index) {
        SimpleDateFormat simpleDateFormat = DateUtil.getSimpleDateFormatByIndes(index);
        try {
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static SimpleDateFormat getSimpleDateFormatByIndes(Integer index) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (index == 1) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else if (index == 2) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (index == 3) {
            dateFormat = new SimpleDateFormat("yyyyMMdd");
        } else if (index == 4) {
            dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        } else if (index == 5) {
            dateFormat = new SimpleDateFormat("HH:mm");
        } else if (index == 6) {
            dateFormat = new SimpleDateFormat("MM-dd");
        } else if (index == 7) {
            dateFormat = new SimpleDateFormat("yyyyMM");
        } else if (index == 8) {
            dateFormat = new SimpleDateFormat("MM-dd HH:mm");
        } else if (index == 9) {
            dateFormat = new SimpleDateFormat("yyyy-MM");
        }
        return dateFormat;
    }

    /**
     * @param strDate
     * @param index
     * @return Date
     * @Description 默认格式为年月日
     * @author shidong.zhao
     * @date 2016-8-11 上午10:20:18
     */
    public static Date strDateToDate(String strDate, int index) {
        switch (index) {
            case 5:
                index = 1;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String year = format.format(new Date());
                strDate = year + " " + strDate;
                break;
        }
        SimpleDateFormat simpleDateFormat = DateUtil.getSimpleDateFormatByIndes(index);
        try {
            return simpleDateFormat.parse(strDate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串转换成日期
     *
     * @param strDate   转换对象
     * @param strFormat 需要转换的格式，例：yyyy-MM-dd HH:mm:ss
     * @return date
     */
    public static Date StrToDate(String strDate, String strFormat) {
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        try {
            return format.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * java中对日期的加减操作
     * gc.add(1,-1)表示年份减一.
     * gc.add(2,-1)表示月份减一.
     * gc.add(3.-1)表示周减一.
     * gc.add(5,-1)表示天减一.
     * 以此类推应该可以精确的毫秒吧.没有再试.大家可以试试.
     * GregorianCalendar类的add(int field,int amount)方法表示年月日加减.
     * field参数表示年,月.日等.
     * amount参数表示要加减的数量.
     */
    public static Date computing(Date obj, int field, int amonut) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(obj);
        gc.add(field, amonut);
        return gc.getTime();
    }

    public static String dateConvertStr(Date date) {
        if (date == null) {
            return "";
        }
        String now = formatDate(new Date(), "yyyy-MM-dd HH:mm:ss", null);
        Date today = StrToDate(now + " 00:00:00", "yyyy-MM-dd HH:mm:ss");

        String unnow = formatDate(date, "yyyy-MM-dd HH:mm:ss", null);
        Date untoday = StrToDate(unnow + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        long days = daysBetween(untoday, today);

        if (days == 0) {
            long _day = (long) (new Date().getTime() - date.getTime()) / 1000;
            if (_day < 60) {
                return "1分钟前";
            }
            _day = (long) _day / 60;
            if (_day < 60) {
                return _day + " 分钟前";
            }
            _day = (long) _day / 60;
            if (_day < 24) {
                return _day + " 小时前";
            }
        }
        if (days == 1) {

            return "昨天: " + formatDate(date, "HH:mm", null);
        }
        if (days < 31) {
            return days + " 天前";
        }
        return formatDate(date, "yyyy-MM-dd HH:mm:ss", null);
    }

    /**
     * 计算2个时间直接的小时差毫秒数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static long millisecondsBetween(Date startTime, Date endTime) {
        if ((startTime == null) || (endTime == null)) {
            return 0;
        }
        long ld1 = startTime.getTime();
        long ld2 = endTime.getTime();
        return ld2 - ld1;
    }

    /**
     * 计算2个时间直接的小时数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static long hoursBetween(Date startTime, Date endTime) {
        return millisecondsBetween(startTime, endTime) / (1 * 60 * 60 * 1000);
    }

    /**
     * 计算2个时间直接的分钟数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static long minutesBetween(Date startTime, Date endTime) {
        return millisecondsBetween(startTime, endTime) / (60 * 1000);
    }

    /**
     * 计算2个时间直接的秒数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static long secondBetween(Date startTime, Date endTime) {
        return millisecondsBetween(startTime, endTime) / (1000);
    }

    /**
     * 计算2个时间相差多少时间，返回类型为：00：03：34
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static String getSubTime(Date startTime, Date endTime) {
        long subTime = millisecondsBetween(startTime, endTime);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return formatter.format(subTime);
    }

    /**
     * 计算2个时间间隔天数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static long daysBetween(Date startTime, Date endTime) {
        long days = millisecondsBetween(startTime, endTime) / (24 * 60 * 60 * 1000);
        return days;
    }

    /**
     * 验证一个时间间隔是否在在2个时间间隔之间
     * 失效返回true,否则返回false
     *
     * @param startTime
     * @param endTime
     * @param validate  小时（h）
     * @return
     */
    public static boolean validateTimeIsEffect(Date startTime, Date endTime, int validate) {
        if ((startTime == null) || (endTime == null)) {
            return true;
        }
        long ld1 = startTime.getTime();
        long ld2 = endTime.getTime();

        long valiate_interval = ld2 - ld1;
        if (valiate_interval <= validate * 60 * 60 * 1000) {
            return false;
        } else {
            return true;
        }
    }

    /***
     * 取days天后的时间
     * @param days
     * @return
     */
    public static Date getAfterAFewDays(Date d, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    /**
     * add by cupai
     * 获取几天前时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.add(Calendar.DAY_OF_MONTH, -day);
        return now.getTime();
    }

    /**
     * add by xupai
     *
     * @param d
     * @return
     */
    public static Date getDateBeforeMonth(Date d, int month) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.add(Calendar.MONTH, -month);
        return now.getTime();
    }

    /***
     * 前几天或后几天的时间列表
     * @param sourceDate 参照时间
     * @param calculate "prev" or "next"
     * @param days
     * @return
     * @throws Exception
     */
    public static List<Date> getDateList(Date sourceDate, String calculate, int days) throws Exception {
        List<Date> dateList = new ArrayList<Date>();
        if (StringUtils.equals(calculate, "prev")) {
            Calendar cl = Calendar.getInstance();
            cl.setTime(sourceDate);
            cl.set(Calendar.DATE, cl.get(Calendar.DATE) - days);
            return getDateList(cl.getTime(), sourceDate);
        }
        if (StringUtils.equals(calculate, "next")) {
            Calendar cl = Calendar.getInstance();
            cl.setTime(sourceDate);
            cl.set(Calendar.DATE, cl.get(Calendar.DATE) + days);
            return getDateList(sourceDate, cl.getTime());
        }
        return dateList;
    }


    /***
     * 获取两个时间段内的日期列表
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static List<Date> getDateList(Date startDate, Date endDate) throws Exception {
        List<Date> dateList = new ArrayList<Date>();
        long start = startDate.getTime();
        long end = endDate.getTime();
        if (start > end) {
            throw new Exception("开始时间不能大于结束时间！");
        }
        int days = getBetweenDays(startDate, endDate);

        dateList.add(startDate);
        for (int i = 1; i <= days; i++) {
            Calendar cl = Calendar.getInstance();
            cl.setTime(startDate);
            cl.set(Calendar.DATE, cl.get(Calendar.DATE) + i);
            dateList.add(cl.getTime());
        }
        return dateList;
    }

    public static int getBetweenDays(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if (c1.after(c2)) {
            c1.setTime(d2);
            c2.setTime(d1);
        }

        int betweenYears = c2.get(1) - c1.get(1);
        int betweenDays = c2.get(6) - c1.get(6);

        for (int i = 0; i < betweenYears; ++i) {
            betweenDays += c1.getActualMaximum(6);
            c1.set(1, c1.get(1) + 1);
        }

        return betweenDays;
    }

    public static String formatDateToEnglish(Date date, String format, String nullStr) {
        String dateStr = DateUtil.formatDate(new Date(), format, null);
        dateStr = dateStr.replace("一月", "Jan").replace("二月", "Feb").replace("三月", "Mar").replace("四月", "Apr");
        dateStr = dateStr.replace("五月", "May").replace("六月", "Jun").replace("七月", "Jul").replace("八月", "Aug");
        dateStr = dateStr.replace("九月", "Sep").replace("十月", "Oct").replace("十一月", "Nov").replace("十二月", "Dec");
        dateStr = dateStr.replace("星期一", "Mon").replace("星期二", "Tue").replace("星期三", "Wed").replace("星期四", "Thu");
        dateStr = dateStr.replace("星期五", "Fri").replace("星期六", "Sat").replace("星期日", "Sun");
        dateStr = dateStr.replace("上午", "AM").replace("下午", "PM");
        return dateStr;
    }

    public static final int ADDYEAR = 1;
    public static final int ADDMONTH = 2;
    public static final int ADDWEEK = 3;
    public static final int ADDDAY = 5;
    public static final int ADDHOUR = 10;
    public static final int ADDMINUTE = 12;
    public static final int ADDSECOND = 13;

    /**
     * 增加或减少时间，返回新的date对象
     *
     * @param date
     * @param addType
     * @param val
     * @return
     */
    public static Date add(Date date, int addType, int val) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(addType, val);
        return calendar.getTime();
    }


    /**
     * 返回 输入时间的 某个整点时间
     *
     * @param date
     * @param hour 整点
     * @return Date
     * @Description
     * @author peng.wang
     * @date 2015-6-19 下午1:31:52
     */
    public static Date getHourOfDay(Date date, int hour) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.set(Calendar.DATE, cl.get(Calendar.DATE));
        cl.set(Calendar.HOUR_OF_DAY, hour);
        cl.set(Calendar.MINUTE, 0);
        cl.set(Calendar.SECOND, 0);
        cl.set(Calendar.MILLISECOND, 0);
        Date rsDate = cl.getTime();
        return rsDate;
    }

    /**
     * 返回 输入时间的 某时时间
     *
     * @param date   当前时间
     * @param hour   小时
     * @param minute 分钟
     * @param second 秒
     * @return Date
     * @Description
     * @author peng.wang
     * @date 2015-6-19 下午1:33:47
     */
    public static Date getTimeOfDay(Date date, int hour, int minute, int second) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.set(Calendar.DATE, cl.get(Calendar.DATE));
        cl.set(Calendar.HOUR_OF_DAY, hour);
        cl.set(Calendar.MINUTE, minute);
        cl.set(Calendar.SECOND, second);
        cl.set(Calendar.MILLISECOND, 0);
        Date rsDate = cl.getTime();
        return rsDate;
    }

    /**
     * @param args
     * @Test
     */
    public static void main(String[] args) {
        //System.out.println(prettySeconds(280213));
//    	System.out.println(formatDate(getDateBeforeMonth(new Date(),3) , "yyyy-MM-dd HH:mm:ss", null));
//		System.out.println(formatDate(getDateBefore(new Date(), 121) , "yyyy-MM-dd HH:mm:ss", null));
		/*Date now = new Date();
		Calendar ddd = Calendar.getInstance();
		ddd.setTime(now);
		ddd.set(Calendar.MONTH, 5);
		ddd.set(Calendar.DAY_OF_MONTH, 27);
		ddd.set(Calendar.HOUR_OF_DAY, 0);
		ddd.set(Calendar.MINUTE, 0);
		System.out.println(ddd.getTime());*/
		/*long reservedTime = ddd.getTimeInMillis();
    	try {
			int flag = 0;
			Calendar cl = Calendar.getInstance();
			cl.setTime(now);
			for(int i =0;i<3;i++){
				if(i!=0){
					cl.set(Calendar.DATE, cl.get(Calendar.DATE)+1);
				}
				//获取当天10点时间
				Date date10 = DateUtil.getHourOfDay(cl.getTime(),10);
				System.out.println(date10);
				//获取当天18：15时间,如果是立即下单 下单时间变成当前时间+15分钟，所以取18:15分的时间
				Date date18 = DateUtil.getTimeOfDay(cl.getTime(),18,15,0);
				System.out.println(date18);
				if(date10.getTime()<=reservedTime&&reservedTime<=date18.getTime()){
					flag = 1;break;
				}
			}
			if(flag ==0){
				System.out.println(0);
			}else {
				System.out.println(1);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*System.out.println(DateUtil.formatDate(getStartWeekTime(49),"yyyy-MM-dd HH:mm:ss"));
		System.out.println(getBeginDayOfWeek());*/

	/*	List<Map<String, Object>> mapList = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();

				result.put("day","2017-10-20");
				result.put("value",3);
		mapList.add(result);
		System.out.println(JsonUtil.getJsonStrFromEntity(mapList).toString());*/
        //System.out.println(DateUtil.formatDate(getStartWeekTime("2017-12-07"),"yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtil.formatDate(StrToDate("2017-12" + "-15", "yyyy-MM-dd"), "yyyy-MM-dd HH:mm:ss"));

    }

    /**
     * 根据日期字符串判断当月第几周
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String getWeek(String str) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(str);
        return getWeek(date);
    }

    public static String getWeek(Date date) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //第几周
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);
        String ret = year + "";
        if (week < 10) {
            ret += "0" + week;
        } else {
            ret += week;
        }
        //第几天，从周日开始
        return ret;
    }

    /**
     * 显示秒值为**年**月**天 **时**分**秒  如1年2个月3天 10小时
     *
     * @param totalSeconds
     * @return
     */
    public static String prettySeconds(int totalSeconds) {
        StringBuilder s = new StringBuilder();
        int second = totalSeconds % 60;
        if (totalSeconds > 0 && second > 0) {
            s.append("秒");
            s.append(StringUtils.reverse(String.valueOf(second)));
        }

        totalSeconds = totalSeconds / 60;
        int minute = totalSeconds % 60;
        if (totalSeconds > 0 && minute > 0) {
            s.append("分");
            s.append(StringUtils.reverse(String.valueOf(minute)));
        }

        totalSeconds = totalSeconds / 60;
        int hour = totalSeconds % 24;
        if (totalSeconds > 0 && hour > 0) {
            s.append(StringUtils.reverse("小时"));
            s.append(StringUtils.reverse(String.valueOf(hour)));
        }

        totalSeconds = totalSeconds / 24;
        int day = totalSeconds % 31;
        if (totalSeconds > 0 && day > 0) {
            s.append("天");
            s.append(StringUtils.reverse(String.valueOf(day)));
        }

        totalSeconds = totalSeconds / 31;
        int month = totalSeconds % 12;
        if (totalSeconds > 0 && month > 0) {
            s.append("月");
            s.append(StringUtils.reverse(String.valueOf(month)));
        }

        totalSeconds = totalSeconds / 12;
        int year = totalSeconds;
        if (totalSeconds > 0 && year > 0) {
            s.append("年");
            s.append(StringUtils.reverse(String.valueOf(year)));
        }
        return s.reverse().toString();
    }

    /**
     * 根据日期获得星期
     *
     * @param date
     * @return String
     * @Description
     * @author shidong.zhao
     * @date 2016-12-29 下午1:55:54
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDaysName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }


    //获取一天的开始时间
    public static Date getDayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date d = calendar.getTime();
        return d;
    }

    //获取当天24点时间戳
    public static Date getDayEndTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 24);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date d = c.getTime();
        return d;
    }


    /**
     * 根据日期字符串获取时间
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getStartDateTime(String str) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            date = sdf.parse(str + " 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据日期字符串获取时间
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getEndDateTime(String str) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            date = sdf.parse(str + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据日期字符串获取时间
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getBetweenDateTime(String str) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            date = sdf.parse(str + " 12:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 根据日期字符串获取时间
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getBetweenWeekTime(String weekTime) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(weekTime));
            calendar.set(Calendar.DAY_OF_WEEK, 2);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            date = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 根据日期字符串获取时间
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getBetweenMonthTime(String monthTime) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(monthTime));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            date = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 获取当月的第一天
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getStartMonthTime(String monthTime) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(monthTime));
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            date = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取本月的第一天
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getStartMonthTime() {
        Date date = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            date = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 获取当月的第最后一天
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getEndMonthTime(String monthTime) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(monthTime));
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            date = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取本月的第最后一天
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getEndMonthTime() {
        Date date = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            date = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 获取本周的开始时间
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getStartWeekTime(String weekTime) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(weekTime));
            int weekIndex = calendar.get(Calendar.DAY_OF_WEEK);
            System.out.println(weekIndex);
            if (weekIndex == 1) {
                calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR) - 1);
            }
            calendar.set(Calendar.DAY_OF_WEEK, 2);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            date = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取上一周的开始时间
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getStartBeforeWeekTime(String weekTime) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(weekTime));
            int weekIndex = calendar.get(Calendar.DAY_OF_WEEK);
            if (weekIndex == 1) {
                calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR) - 2);
            } else {
                calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR) - 1);
            }
            calendar.set(Calendar.DAY_OF_WEEK, 2);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            date = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 获取上一周的结束时间
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getEndBeforeWeekTime(String weekTime) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(weekTime));
            int weekIndex = calendar.get(Calendar.DAY_OF_WEEK);
            if (weekIndex == 1) {
                calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR) - 1);
            }
            calendar.set(Calendar.DAY_OF_WEEK, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 23);
            calendar.set(Calendar.SECOND, 23);
            date = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 获取本周的结束时间
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Date getEndWeekTime(String weekTime) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(weekTime));
            int weekIndex = calendar.get(Calendar.DAY_OF_WEEK);
            if (weekIndex != 1) {
                calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR) + 1);
            }
            calendar.set(Calendar.DAY_OF_WEEK, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            date = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 获取上周一
     *
     * @param date
     * @return
     */
    public static Date getLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    /**
     * 获取本周一
     *
     * @param date
     * @return
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获取下周一
     *
     * @param date
     * @return
     */
    public static Date getNextWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }


    //获取本周的开始时间
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //获取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }


    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * add by cupai
     * 获取几天前的凌晨
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBeforeIndex(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.add(Calendar.DAY_OF_MONTH, -day);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return now.getTime();
    }

    /**
     * add by cupai
     * 获取几天前的日期
     *
     * @param d
     * @param day
     * @return
     */
    //返回今天-dayNum天的日期
    public static String getBeforeTodayIndex(int dayNum) {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(d.getTime() - (long) dayNum * 24 * 60 * 60 * 1000));

    }


    /**
     * 检测是否为本月的第一天
     *
     * @return
     */
    public static Boolean checkIsMonthFirst(Date now) {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        c.setTime(now);
        int date = c.get(Calendar.DATE);
        if (date == 1) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为周末
     *
     * @param date
     * @return
     */
    public static Boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断是否为周一
     *
     * @param date
     * @return
     */
    public static Boolean isWeekStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            return true;
        } else {
            return false;
        }
    }


    //判断选择的日期是否是本周
    public static boolean isThisWeek(Date date) {
        Date weekStartDay = DateUtil.getBeginDayOfWeek();
        Date weekEndDay = DateUtil.getEndDayOfWeek();
        if(DateUtil.millisecondsBetween(weekStartDay, date) >= 0 && DateUtil.millisecondsBetween(date, weekEndDay) >= 0){
            return true;
        }
        return false;
    }

    //判断选择的日期是否是今天
    public static boolean isToday(Date date) {
        return isThisTime(date, "yyyy-MM-dd");
    }

    //判断选择的日期是否是本月
    public static boolean isThisMonth(Date date) {
        return isThisTime(date, "yyyy-MM");
    }

    private static boolean isThisTime(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

}
