package com.think.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * @author think
 * @version V1.0
 */
public class DateUtils {

    /**
     * The UTC time zone  (often referred to as GMT).
     */
    public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");

    public static final String[] FORMATS = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "HH:mm",
            "HH:mm:ss", "yyyy-MM", "yyyyMMddHHmmss", "yyyyMMdd"};

    static Calendar calendar = Calendar.getInstance();

    public static String currentMonth() {
        return String.valueOf(calendar.get(Calendar.MONTH) + 1);
    }

    public static String currentWeek() {
        return String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);
    }

    public static String getWeek(String date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(parseToDate(date));
        System.out.println(parseToDate(date));
        return String.valueOf(ca.get(Calendar.DAY_OF_WEEK) - 1);
    }

    public static String getMonth(String date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(parseToDate(date));
        return String.valueOf(ca.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取指定日期加减天数后的日期字符传
     *
     * @param curDate
     * @param offSet  +-n
     * @param format
     * @return
     */
    public static String getDateStrByOffsetDay(Date curDate, int offSet, String format) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(curDate);
        ca.add(Calendar.DATE, offSet);
        SimpleDateFormat sdf = null;
        if (format != null && !"".equals(format.trim())) {
            sdf = new SimpleDateFormat(format);
        } else {
            sdf = new SimpleDateFormat(FORMATS[0]);
        }
        return sdf.format(ca.getTime());
    }

    /**
     * 时间天数加减
     *
     * @param curDate
     * @param offSet
     * @return
     */
    public static Date getDateByOffsetDay(Date curDate, int offSet) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(curDate);
        ca.add(Calendar.DATE, offSet);
        return ca.getTime();
    }

    /**
     * 时间小时增减
     *
     * @param curDate
     * @param offSet
     * @return
     */
    public static Date getDateByOffsetHour(Date curDate, int offSet) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(curDate);
        ca.add(Calendar.HOUR, offSet);
        return ca.getTime();
    }

    /**
     * 时间分钟增减
     *
     * @param curDate
     * @param offSet
     * @return
     */
    public static Date getDateByOffsetMinute(Date curDate, int offSet) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(curDate);
        ca.add(Calendar.MINUTE, offSet);
        return ca.getTime();
    }

    /**
     * 取得当前时间
     *
     * @return 当前时间
     */
    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取标准时间 防止出现晚8小时的状况
     *
     * @return
     */
    public static String getStandardCurrentDateStr() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATS[2]);
        return sdf.format(getStandardCurrentDate());
    }

    public static Date getStandardCurrentDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    /**
     * 获取指定格式的当前时间
     *
     * @param format
     * @return
     */
    public static String getCurrentDateStr(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        return time;
    }

    /**
     * 获取指定格式的当前时间 格式:yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentDateTimeStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATS[2]);
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        return time;
    }

    /**
     * 获取当前日期 格式:yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDateStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATS[0]);
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        return time;
    }

    /**
     * 字符串转日期(按指定格式)
     *
     * @param dateString
     * @param format
     * @return 错误返回null, 正常返回日期
     */
    public static Date parseToDate(String dateString, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateString.trim());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 字符串转日期
     *
     * @param dateString -字符串日期(支持的格式有: "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd
     *                   HH:mm:ss", "HH:mm", "HH:mm:ss", "yyyy-MM"，"yyyyMMddHHmmss")
     * @return 错误返回null, 正常返回日期
     */
    public static Date parseToDate(String dateString) {
        String format = "";
        for (String formats : FORMATS) {
            if (dateString.trim().length() == formats.length()) format = formats;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateString.trim());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算日期差
     *
     * @param startDate : 日期参数1
     * @param endDate   : 日期参数2
     * @param diffType  : 差值计算类别(1:秒数/2:分钟数/3:小时数/4:天数/5:周数/6:月数/7:年数)
     * @return endDate小于startDate时返回-1,正常返回大于1
     */
    public static long getDateDiff(Date startDate, Date endDate, int diffType) {
        long diffVal = -1;
        if (startDate.after(endDate)) return diffVal;

        Calendar firstDay = Calendar.getInstance();
        Calendar lastDay = Calendar.getInstance();
        firstDay.setTime(startDate);
        lastDay.setTime(endDate);

        long fact = 1000;
        switch (diffType) {
            case 1: // 计算秒数差值
                break;
            case 2: // 计算分钟数差值
                fact = fact * 60;
                break;
            case 3: // 计算小时差值
                fact = fact * 60 * 60;
                break;
            case 4: // 计算天数差值
                fact = fact * 24 * 60 * 60;
                break;
            default:// 计算秒数差值
        }
        diffVal = (lastDay.getTimeInMillis() - firstDay.getTimeInMillis()) / fact;
        return diffVal;
    }

    public static int compare(String dateStrSource, String dateStrTarget, String formatStr) {
        try {
            DateFormat format = new SimpleDateFormat(formatStr);
            Date source = format.parse(dateStrSource);
            Date target = format.parse(dateStrTarget);
            return source.compareTo(target);
        } catch (ParseException pe) {
            pe.printStackTrace();
            return -1;
        }
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static boolean isValidTime(String uploadTime) {
        Date startDate = DateUtils.parseToDate(uploadTime);
        long diffSecond = DateUtils.getDateDiff(startDate, DateUtils.getStandardCurrentDate(), 1);
        return diffSecond > -600 && diffSecond < 600;
    }


    /**
     * 时间转换成年月日时分秒
     *
     * @param date
     * @return
     */
    public static String formatDateText(Date date) {
        String dateStr = "";
        Calendar cal = Calendar.getInstance();
        // 获取日期的年月日等
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);// 获取年份
        int month = cal.get(Calendar.MONTH) + 1;// 获取月份
        int day = cal.get(Calendar.DATE);// 获取日
        int hour = cal.get(Calendar.HOUR_OF_DAY);// 小时
        int minute = cal.get(Calendar.MINUTE);// 分
        int second = cal.get(Calendar.SECOND);// 秒
        dateStr = year + "年" + month + "月" + day + "日  " + hour + "时" + minute + "分" + second + "秒";
        return dateStr;
    }

    public static void main(String[] args) {

        System.out.println(getDateStrByOffsetDay(getStandardCurrentDate(), 3, FORMATS[0]));
        System.out.println(getNow());
        getWeek("2013-10-31 19:16:04");
        System.out.println(getMonth("2013-10-31 19:16:04"));
        System.out.println(currentMonth());
        System.out.println(UUID.randomUUID().toString().replace("-", ""));

        long secondDiff = DateUtils.getDateDiff(DateUtils.parseToDate("20190524122000"),
                DateUtils.getStandardCurrentDate(), 1);
        System.out.println("date diff seconds:" + secondDiff);

        if (isValidTime("20191216112700") == false) {
            System.out.println("time is false");
        } else {
            System.out.println("time is true");
        }
    }

}
