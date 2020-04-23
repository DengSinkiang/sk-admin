package com.dxj.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

/**
 * @Description:
 * @Author: Sinkiang
 * @Date: 2020/3/30 9:23
 * @CopyRight: 2020 sk-admin all rights reserved.
 */
@Slf4j
public abstract class DateUtils {
    public static final long ONE_YEAR = 366 * 24 * 60 * 60 * 1000L;
    public static final long ONE_DAY_SECOND = 24 * 60 * 60L;
    public static final String DATE_FORMAT_yyyy_MM_dd = "yyyy/MM/dd";
    public static final String DATE_FORMAT_yyyyMMdd = "yyyyMMdd";
    public static final String DATE_FORMAT_yyyyMM = "yyyyMM";
    public static final String DATE_FORMAR_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_yyyy_MM_dd_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_STARNDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_HH_MM_SS = "HH:mm:ss";
    public static final String DATE_FORMAT_MM_dd_HH_MM_SS = "MM-dd HH:mm:ss";

    public final static String YEAR_MONTH_DAY = "yyyy-MM-dd";
    public final static String HOUR_MINUTE = "HH:mm";

    public static DateTimeFormatter formatterYMDHMS = DateTimeFormatter.ofPattern(DATE_STARNDARD_FORMAT);
    public static DateTimeFormatter formatterYMD = DateTimeFormatter.ofPattern(YEAR_MONTH_DAY);
    public static DateTimeFormatter formatterHM = DateTimeFormatter.ofPattern(HOUR_MINUTE);
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(YEAR_MONTH_DAY);

    private DateUtils() {
        // do nothing
    }

    public static final String SDF = "sdf";
    public static final String SDF1 = "sdf1";
    public static final String SDF2 = "sdf2";
    public static final String SDF3 = "sdf3";
    public static final String SDF4 = "sdf4";
    public static final String SDF5 = "sdf5";
    public static final String SDF6 = "sdf6";
    public static final String SDF7 = "sdf7";
    public static final String SDF8 = "sdf8";
    public static final String SDF9 = "sdf9";
    public static final String SDF10 = "sdf10";
    /**
     * use threadlocal of DateFormat to avoid date format exception when used in multithread environment.
     */
    private static ThreadLocal<Map<String, DateTimeFormatter>> threadLocal = ThreadLocal.withInitial(() -> {
        Map<String, DateTimeFormatter> map = new HashMap<>();
        map.put(SDF, formatterYMDHMS);
        map.put(SDF1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        map.put(SDF2, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        map.put(SDF3, DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
        map.put(SDF4, DateTimeFormatter.ofPattern("MMddHHmmyyyy.ss"));
        map.put(SDF5, DateTimeFormatter.ofPattern("yyyyMMdd"));
        map.put(SDF6, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        map.put(SDF7, formatterYMD);
        map.put(SDF8, DateTimeFormatter.ofPattern("yyyyMMddHHmmssS"));
        map.put(SDF9, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        map.put(SDF10, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
        return map;
    });


    /**
     * Description:返回日期格式为：2020/04/13 的字符串
     *
     * @return java.lang.String
     * @author Sinkiang
     */
    public static String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_yyyy_MM_dd));
    }

    /**
     * @param
     * @return int
     * @escription: get current year ,like :2020
     * @authord Sinkiang
     */
    public static int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    public static String getCurrentDate(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * For a instance : getDateBefore(10)
     *
     * @param inter -
     * @return get the day before today
     */
    public static String getDateBefore(long inter) {
        LocalDateTime before = LocalDateTime.now().minusDays(inter);
        return DateTimeFormatter.ofPattern(DATE_FORMAT_yyyy_MM_dd).format(before);
    }

    /**
     * Return the start time of previous month. For example, current date is 2016/03/29: will return long value of 2016/02/01 00:00:00
     */
    public static String getPreviousMonthStart(String localDateTime) {
        LocalDateTime date = LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(DATE_FORMAT_yyyy_MM_dd_HH_MM_SS));
        return date.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeFormatter.ofPattern(DATE_FORMAT_yyyy_MM_dd_HH_MM_SS));
    }

    /**
     * Return the end time of previous month. For example, current date is 2016/03/29: will return long value of 2016/02/29 00:00:00
     */
    public static String getPreviousMonthEnd(String localDateTime) {
        LocalDateTime date = LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(DATE_FORMAT_yyyy_MM_dd_HH_MM_SS));
        return date.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ofPattern(DATE_FORMAT_yyyy_MM_dd_HH_MM_SS));
    }


    public static int getDateDiff(LocalDateTime date1, LocalDateTime date2) {
        long lngDiff = (date1.toInstant(ZoneOffset.of("+8")).toEpochMilli() - date2.toInstant(ZoneOffset.of("+8")).toEpochMilli()) / (24 * 3600 * 1000);
        return ((int) lngDiff);
    }

    public static int getDateMinutes() {
        return LocalDateTime.now().getMinute();
    }

    public static int getDateSeconds() {
        return LocalDateTime.now().getSecond();
    }


    /**
     * 对日期的字符串解析为时间类型！支持格式:
     * "yyyy/MM/dd"及"yyyy/MM/dd HH:mm"及"yyyy/MM/dd HH:mm:ss"
     * "yyyy_MM_dd"及"yyyy_MM_dd HH:mm"及yyyy_MM_dd HH:mm:ss"
     * "yyyy-MM-dd"及"yyyy-MM-dd HH:mm"及"yyyy-MM-dd HH:mm:ss"
     * "yyyyMMdd"及"yyyyMMdd HH:mm"及"yyyyMMdd HH:mm:ss"
     * "yyyyMMdd HHmmss"及"yyyyMMdd HHmm"
     * "HH:mm"及"HH:mm:ss"
     * dbdu at 2019-0707
     *
     * @param dateString
     * @return
     */
    public static LocalDateTime getDateFromString(String dateString) {
        if (dateString != null && "".equals(dateString.trim())) {
            String pattern = "";
            if (dateString.length() == 19) { // 全格式的
                if (dateString.indexOf("/") > 0) {
                    pattern = "yyyy/MM/dd HH:mm:ss";
                } else if (dateString.indexOf("_") > 0) {
                    pattern = "yyyy_MM_dd HH:mm:ss";
                } else if (dateString.indexOf("-") > 0) {
                    pattern = "yyyy-MM-dd HH:mm:ss";
                }
            } else if (dateString.length() == 17) { // 年月日不带分隔符的全格式
                pattern = "yyyyMMdd HH:mm:ss";
            } else if (dateString.length() == 16) { // 不带秒的全格式
                if (dateString.indexOf("/") > 0) {
                    pattern = "yyyy/MM/dd HH:mm";
                } else if (dateString.indexOf("_") > 0) {
                    pattern = "yyyy_MM_dd HH:mm";
                } else if (dateString.indexOf("-") > 0) {
                    pattern = "yyyy-MM-dd HH:mm";
                }
            } else if (dateString.length() == 15) { // 不带带分隔符的年月日格式及时分秒不带冒号
                pattern = "yyyyMMdd HHmmss";
            } else if (dateString.length() == 14) { // 不带带分隔符的年月日格式及时分
                pattern = "yyyyMMdd HH:mm";
            } else if (dateString.length() == 13) { // 不带带分隔符的年月日格式及时分
                pattern = "yyyyMMdd HHmm";
            } else if (dateString.length() == 10) { // 带分隔符的年月日格式
                if (dateString.indexOf("/") > 0) {
                    pattern = "yyyy/MM/dd";
                } else if (dateString.indexOf("_") > 0) {
                    pattern = "yyyy_MM_dd";
                } else if (dateString.indexOf("-") > 0) {
                    pattern = "yyyy-MM-dd";
                }
            } else if (dateString.length() == 8) { // 不带分隔符的年月日格式或者时分秒的格式
                if (dateString.indexOf(":") > 0) {
                    pattern = "HH:mm:ss";
                } else {
                    pattern = "yyyyMMdd";
                }
            } else if (dateString.length() == 5) {  // 仅仅时分的格式
                if (dateString.indexOf(":") > 0) {
                    pattern = "HH:mm";
                }
            } else {
                log.warn("未知的日期格式:" + dateString);
            }

            DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime date;

            date = LocalDateTime.parse(dateString, df);
            return date;
        }
        return null;
    }

    public static String getDateStrFromLong(long dateLong) {
        return getDateStrFromLong(dateLong, DateTimeFormatter.ofPattern(DATE_FORMAT_yyyy_MM_dd));
    }

    public static String getFullDateStrFromLong(long dateLong) {
        return getDateStrFromLong(dateLong, DateTimeFormatter.ofPattern(DATE_FORMAT_yyyy_MM_dd_HH_MM_SS));
    }

    public static String getHHMMSSDateStrFromLong(long dateLong) {
        return getDateStrFromLong(dateLong, DateTimeFormatter.ofPattern(DATE_FORMAT_HH_MM_SS));
    }

    public static String getMdHmsDateStrFromLong(long dateLong) {
        return getDateStrFromLong(dateLong, DateTimeFormatter.ofPattern(DATE_FORMAT_MM_dd_HH_MM_SS));
    }

    /**
     * Description:get time string from os,string like "2016/04/05 15:31:36"
     *
     * @param
     * @return String
     * @author dbdu
     */
    public static String getOsCurrentTime() {
        //get currentTime from os
        long currentLong = System.currentTimeMillis();
        return DateUtils.getFullDateStrFromLong(currentLong);
    }

    public static String getDateStrFromLong(long dateLong, DateTimeFormatter sdFormat) {
        return sdFormat.format(LocalDateTime.ofEpochSecond(dateLong / 1000, 0, ZoneOffset.ofHours(8)));
    }



    /**
     * 获取某年最后一天日期的最后一秒，like：2015/12/31 23:59:59
     *
     * @param year
     * @return long
     */
    public static long getYearLast(int year) {
        String fullDateString = year + "/12/31 23:59:59";

        /*String yearLast = DateTimeUtil.getFullDateStrFromLong(currYearLast);
        System.out.println("getYearLast:" + yearLast);*/
        return getTimeFromString(fullDateString);
    }

    /**
     * Description:dateString 应该是yyyy-MM-dd HH:mm:ss完整格式。
     *
     * @param dateString 日期字符串
     * @return java.util.Date
     * @author Sinkiang
     */
    public static LocalDateTime toDate(String dateString) {
        if (dateString == null || "".equals(dateString.trim()))
            return null;
        try {
            if (dateString.indexOf('/') != -1) {
                return LocalDateTime.parse(dateString, threadLocal.get().get(SDF2));
            } else if (dateString.indexOf('-') != -1) {
                return LocalDateTime.parse(dateString, threadLocal.get().get(SDF));
            }
        } catch (Exception e) {
            log.error("Error to convert date from String", e);
        }
        return null;
    }

    /**
     * Description:dateString 应该是yyyy-MM-dd HH:mm格式。
     *
     * @param dateString 日期字符串
     * @return java.util.Date
     * @author dbdu
     */
    public static LocalDateTime toDateWithoutSecond(String dateString) {
        if (dateString == null || "".equals(dateString.trim()))
            return null;
        try {
            if (dateString.indexOf('/') != -1) {
                return LocalDateTime.parse(dateString, threadLocal.get().get(SDF10));
            } else if (dateString.indexOf('-') != -1) {
                return LocalDateTime.parse(dateString, threadLocal.get().get(SDF9));
            }
        } catch (Exception e) {
            log.error("Error to conver date from String", e);
        }
        return null;
    }

    public static Long toSqlDateTime(String dateString) {
        return toSqlDateTime(dateString, false);
    }

    public static long toSqlDateTime(String dateString, boolean endTime) {
        long ret = 0L;
        LocalDateTime d = null;
        if (dateString == null || "".equals(dateString.trim()))
            return ret;
        try {
            dateString = dateString.replace(" 00:00:00", "");
            if (dateString.indexOf(':') == -1) {
                if (endTime) {
                    dateString = dateString.trim() + " 23:59:59";
                } else {
                    dateString = dateString.trim() + " 00:00:00";
                }
            }

            if (dateString.indexOf('/') == 2) {
                d = LocalDateTime.parse(dateString, threadLocal.get().get(SDF3));
            } else if (dateString.indexOf('/') != -1) {
                d = LocalDateTime.parse(dateString, threadLocal.get().get(SDF2));
            } else if (dateString.indexOf('-') != -1) {
                d = LocalDateTime.parse(dateString, threadLocal.get().get(SDF));
            }
            if (null != d) {
                ret = d.toInstant(ZoneOffset.of("+8")).toEpochMilli();
                if (endTime) {
                    ret += 999;
                }
            }
        } catch (Exception e) {
            log.error("Error to Parse the date time: " + dateString, e);
        }
        return ret;
    }

    /**
     * according type to generate date object
     *
     * @param datetime
     * @param type     0:"yyyy-MM-dd HH:mm:ss" 1:"yyyy/MM/dd HH:mm:ss"
     *                 2:"MM/dd/yyyy HH:mm:ss" 3:"MMddhhmmyyyy.ss" 4:"yyyyMMdd"
     *                 5:"yyyyMMddHHmmss" 6:"yyyy-MM-dd" 7:"yyyyMMddHHmmssS" 8:"yyyy-MM-dd HH:mm"
     * @return
     */
    public static String getDateString(long datetime, int type) {
        if (-1 == datetime) {
            return "";
        }
        LocalDateTime date = LocalDateTime.ofEpochSecond(datetime / 1000, 0, ZoneOffset.ofHours(8));
        try {
            if (type == 0) {
                return threadLocal.get().get(SDF).format(date);
            } else if (type == 1) {
                return threadLocal.get().get(SDF2).format(date);
            } else if (type == 2) {
                return threadLocal.get().get(SDF3).format(date);
            } else if (type == 3) {
                return threadLocal.get().get(SDF4).format(date);
            } else if (type == 4) {
                return threadLocal.get().get(SDF5).format(date);
            } else if (type == 5) {
                return threadLocal.get().get(SDF6).format(date);
            } else if (type == 6) {
                return threadLocal.get().get(SDF7).format(date);
            } else if (type == 7) {
                return threadLocal.get().get(SDF8).format(date);
            } else if (type == 8) {
                return threadLocal.get().get(SDF9).format(date);
            }
        } catch (Exception e) {
            log.error("Error to get data String", e);
        }
        return "";
    }

    /**
     * Description:返回格式为：2018-10-12 13:58:22的字符串
     *
     * @return java.lang.String
     * @author dbdu
     */
    public static String getCurDateStr() {
        return getDateString(System.currentTimeMillis(), 0);
    }

    /**
     * according type to generate date object
     *
     * @param datetime
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String getDateString(long datetime) {
        return getDateString(datetime, 0);
    }

    /**
     * according type to generate date object
     *
     * @param datetime
     * @return "yyyy-MM-dd HH:mm"
     */
    public static String getMinDateString(long datetime) {
        return getDateString(datetime, 8);
    }

    /**
     * according type to generate date object
     *
     * @param date
     * @param type 0:"yyyy-MM-dd HH:mm:ss" 1:"yyyy/MM/dd HH:mm:ss"
     *             2:"MM/dd/yyyy HH:mm:ss" 3:"MMddhhmmyyyy.ss" 4:"yyyyMMdd"
     *             5:"yyyyMMddHHmmss" 6:"yyyy-MM-dd" 7:"yyyyMMddHHmmssS"
     * @return
     */
    public static String getDateString(LocalDateTime date, int type) {
        if (date == null)
            return "";
        if (type == 0) {
            return threadLocal.get().get(SDF).format(date);
        } else if (type == 1) {
            return threadLocal.get().get(SDF2).format(date);
        } else if (type == 2) {
            return threadLocal.get().get(SDF3).format(date);
        } else if (type == 3) {
            return threadLocal.get().get(SDF4).format(date);
        } else if (type == 4) {
            return threadLocal.get().get(SDF5).format(date);
        } else if (type == 5) {
            return threadLocal.get().get(SDF6).format(date);
        } else if (type == 6) {
            return threadLocal.get().get(SDF7).format(date);
        } else if (type == 7) {
            return threadLocal.get().get(SDF8).format(date);
        }
        return "";

    }

    /**
     * 以天为单位计算两个long类型的日期和
     *
     * @param time
     * @param value
     * @return
     */
    public static long getDateByAdd(long time, int value) {

        return time + ((long) value * 24 * 60 * 60 * 1000);
    }

    public static LocalDateTime fromLong(long time) {
        return LocalDateTime.ofEpochSecond(time / 1000, 0, ZoneOffset.ofHours(8));
    }

    /**
     * Description:dateString 应该是yyyy-MM-dd HH:mm:ss完整格式。
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
     *
     * @param dateString 日期字符串
     * @return long ;if success return date object's time, else return -1
     * @author Sinkiang
     */
    public static long getTimeFromString(String dateString) {
        LocalDateTime date = toDate(dateString);
        if (date != null)
            return date.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return -1;
    }

    /**
     * Description:dateString 应该是yyyy-MM-dd HH:mm:ss完整格式。
     * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
     *
     * @param dateString 日期字符串
     * @return java.lang.Long
     * @author dbdu
     */
    public static long getTimeMSFromString(String dateString) {
        LocalDateTime date = toDate(dateString);
        if (date != null)
            return date.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return -1;
    }

    public static long convertStringToMills(String dateString, String type) {

        return LocalDateTime.parse(dateString, threadLocal.get().get(type)).
                toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static LocalDateTime getNowDateFull() {
        //将string to date
        return toDate(formatterYMDHMS.format(LocalDateTime.now()));
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static LocalDateTime getNowDateShort() {
        return toDate(formatterYMD.format(LocalDateTime.now()));
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        return formatterYMDHMS.format(LocalDateTime.now());
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyyMMddHHmmss
     */
    public static String getStringAllDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        return formatterYMD.format(LocalDateTime.now());
    }

    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static LocalDateTime strToDateLong(String strDate) {
        return LocalDateTime.parse(strDate, formatterYMDHMS);
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(LocalDateTime dateDate) {
        return formatterYMDHMS.format(dateDate);
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToStr(LocalDateTime dateDate) {
        return formatterYMD.format(dateDate);
    }

    public static String dateToStr(LocalDate dateDate) {
        return dateFormatter.format(dateDate);
    }


    /**
     * 得到现在时间
     *
     * @return
     */
    public static LocalDateTime getNow() {
        return LocalDateTime.now();
    }

    /**
     * 提取一个月中的最后一天
     *
     * @param day
     * @return
     */
    public static LocalDateTime getLastDate(long day) {
        long date_3_hm = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() - 3600000 * 34 * day;
        return LocalDateTime.ofEpochSecond(date_3_hm / 1000, 0, ZoneOffset.ofHours(8));
    }

    /**
     * 得到现在时间
     *
     * @return 字符串 yyyyMMdd HHmmss
     */
    public static String getStringToday() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 功能：
     *
     * @version 2016年12月16日 下午4:41:51
     */
    public static String getTodayShort() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return formatter.format(LocalDateTime.now());
    }

    /**
     * @param @param  value
     * @param @return
     * @return String
     * @throws
     * @Description: 输入一个整数类型的字符串, 然后转换成时分秒的形式输出
     * 例如：输入568
     * 返回结果为：00:09:28
     * 输入null或者“”
     * 返回结果为:00:00:00
     */
    public static String getHHMMSS(String value) {
        String hour = "00";
        String minute = "00";
        String second = "00";
        if (value != null && !value.trim().equals("")) {
            int v_int = Integer.parseInt(value);
            hour = v_int / 3600 + "";//获得小时;
            minute = v_int % 3600 / 60 + "";//获得小时;
            second = v_int % 3600 % 60 + "";//获得小时;
        }
        return (hour.length() > 1 ? hour : "0" + hour) + ":" + (minute.length() > 1 ? minute : "0" + minute) + ":" + (second.length() > 1 ? second : "0" + second);
    }

    /**
     * 得到现在小时
     */
    public static String getHour() {
        String dateString = formatterYMDHMS.format(LocalDateTime.now());
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 得到现在分钟
     *
     * @return
     */
    public static String getTime() {
        String dateString = formatterYMDHMS.format(LocalDateTime.now());
        String min;
        min = dateString.substring(14, 16);
        return min;
    }

    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param format yyyyMMddhhmmss
     * @return
     */
    public static String getUserDate(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(LocalDateTime.now());
    }

    /**
     * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     */
    public static String getTwoHour(String st1, String st2) {
        String[] kk;
        String[] jj;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
            return "0";
        else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0)
                return y - u + "";
            else
                return "0";
        }
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        long day = 0;

        LocalDateTime date = LocalDateTime.parse(sj1, formatterYMD);
        LocalDateTime mydate = LocalDateTime.parse(sj2, formatterYMD);
        day = (date.toInstant(ZoneOffset.of("+8")).toEpochMilli() - mydate.toInstant(ZoneOffset.of("+8")).toEpochMilli()) / (24 * 60 * 60 * 1000);
        return String.valueOf(day);
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static String getPreTime(String sj1, String jj) {
        String date = "";

        LocalDateTime date1 = LocalDateTime.parse(sj1, formatterYMDHMS);
        long time = date1.toInstant(ZoneOffset.of("+8")).toEpochMilli() + Integer.parseInt(jj) * 60 * 1000;

        LocalDateTime date2 = LocalDateTime.ofEpochSecond(time / 1000, 0, ZoneOffset.ofHours(8));
        date = formatterYMDHMS.format(date2);

        return date;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate(yyyy-mm-dd)为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, String delay) {

        String mdate = "";
        LocalDateTime d = getDateFromString(nowdate);
        assert d != null;
        long myTime = d.toInstant(ZoneOffset.of("+8")).toEpochMilli() + Integer.parseInt(delay) * 24 * 60 * 60 * 1000;
        LocalDateTime d1 = LocalDateTime.ofEpochSecond(myTime / 1000, 0, ZoneOffset.ofHours(8));
        mdate = formatterYMD.format(d1);
        return mdate;
    }

    /**
     * 功能： 距离现在几天的时间是多少
     * 获得一个时间字符串，格式为：yyyy-MM-dd HH:mm:ss
     * day  如果为整数，表示未来时间
     * 如果为负数，表示过去时间
     */
    public static String getFromNow(int day) {
        long dateTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() + day * 24 * 60 * 60 * 1000;
        LocalDateTime date = LocalDateTime.ofEpochSecond(dateTime / 1000, 0, ZoneOffset.ofHours(8));
        return formatterYMDHMS.format(date);
    }

    /**
     * 判断是否润年,ddate格式:"yyyy/MM/dd";"yyyy_MM_dd";"yyyy-MM-dd";"yyyyMMdd"
     *
     * @param ddate
     * @return
     */
    public static boolean isLeapYear(String ddate) {

        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        LocalDateTime d = getDateFromString(ddate);
        assert d != null;
        int year = d.getYear();
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            return (year % 100) != 0;
        } else
            return false;
    }

    /**
     * 返回美国时间格式 26 Apr 2006
     *
     * @param str
     * @return
     */
    public static String getEDate(String str) {
        ParsePosition pos = new ParsePosition(0);
        LocalDateTime strtodate = LocalDateTime.parse(str, formatterYMD);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }

    /**
     * 获取一个月的最后一天
     */
    public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
        String str = dat.substring(0, 8);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            str += "31";
        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            str += "30";
        } else {
            if (isLeapYear(dat)) {
                str += "29";
            } else {
                str += "28";
            }
        }
        return str;
    }

    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     */
    public static int getSeqWeek() {
        LocalDate localDate = LocalDateTime.now().toLocalDate();
        return localDate.get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfYear());
    }

    /**
     * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
     * sdate格式:"yyyy/MM/dd";"yyyy_MM_dd";"yyyy-MM-dd";"yyyyMMdd"
     */
    public static String getWeek(String sdate, int num) {
        // 再转换为时间d
        LocalDate dd = Objects.requireNonNull(getDateFromString(sdate)).toLocalDate();
        LocalDate localDate = null;
        switch (num) {
            case 1:
                // 返回星期一所在的日期
                localDate = dd.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);
                break;
            case 2:
                // 返回星期二所在的日期

                localDate = dd.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(2);
                break;
            case 3:
                // 返回星期三所在的日期
                localDate = dd.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(3);
                break;
            case 4:
                // 返回星期四所在的日期
                localDate = dd.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(4);
                break;
            case 5:
                // 返回星期五所在的日期
                localDate = dd.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(5);
                break;
            case 6:
                // 返回星期六所在的日期
                localDate = dd.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(6);
                break;
            case 7:
                // 返回星期日所在的日期
                localDate = dd.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).minusDays(1);
                break;
        }
        assert localDate != null;
        return localDate.toString();
    }

    /**
     * 根据一个日期(标准格式的字符串)，返回是星期几的字符串
     * sdate:"yyyy/MM/dd";"yyyy_MM_dd";"yyyy-MM-dd";"yyyyMMdd"
     */

    public static String getWeekStr(String sdate) {
        String str = "";
        int t = LocalDate.parse(sdate).getDayOfWeek().getValue();
        switch (t) {
            case 1:
                str = "星期一";
                break;
            case 2:
                str = "星期二";
                break;
            case 3:
                str = "星期三";
                break;
            case 4:
                str = "星期四";
                break;
            case 5:
                str = "星期五";
                break;
            case 6:
                str = "星期六";
                break;
            case 7:
                str = "星期日";
                break;
        }
        return str;
    }

    /**
     * 两个时间之间的天数
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        LocalDateTime date;
        LocalDateTime mydate;

        date = LocalDateTime.parse(date1, formatterYMD);
        mydate = LocalDateTime.parse(date2, formatterYMD);

        return (date.toInstant(ZoneOffset.of("+8")).toEpochMilli() - mydate.toInstant(ZoneOffset.of("+8")).toEpochMilli()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
     * 此函数返回该日历第一行星期日所在的日期 sdate:"yyyy/MM/dd";"yyyy_MM_dd";"yyyy-MM-dd";"yyyyMMdd"
     */
    public static String getNowMonth(String sdate) {
        // 取该时间所在月的一号
        sdate = sdate.substring(0, 8) + "01";

        // 得到这个月的1号是星期几
        LocalDateTime date = getDateFromString(sdate);
        assert date != null;
        int u = date.getDayOfWeek().getValue();
        return getNextDay(sdate, (1 - u) + "");
    }

    /**
     * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
     *
     * @param k 表示是取几位随机数，可以自己定
     */

    public static String getNo(int k) {

        return getUserDate("yyyyMMddhhmmss") + getRandom(k);
    }

    /**
     * 返回一个随机数
     */
    public static String getRandom(int i) {
        Random jjj = new Random();
        // int suiJiShu = jjj.nextInt(9);
        if (i == 0)
            return "";
        StringBuilder jj = new StringBuilder();
        for (int k = 0; k < i; k++) {
            jj.append(jjj.nextInt(9));
        }
        return jj.toString();
    }

    /***************************************************************************
     * //nd=1表示返回的值中包含年度 //yf=1表示返回的值中包含月份 //rq=1表示返回的值中包含日期 //format表示返回的格式 1
     * 以年月日中文返回 2 以横线-返回 // 3 以斜线/返回 4 以缩写不带其它符号形式返回 // 5 以点号.返回
     **************************************************************************/
    public static String getStringDateMonth(String sdate, String nd, String yf, String rq, String format) {
        String dateString = formatterYMD.format(LocalDateTime.now());
        String s_nd = dateString.substring(0, 4); // 年份
        String s_yf = dateString.substring(5, 7); // 月份
        String s_rq = dateString.substring(8, 10); // 日期
        String sreturn = "";
        if (sdate == null || sdate.equals("")) {
            if (nd.equals("1")) {
                sreturn = s_nd;
                // 处理间隔符
                switch (format) {
                    case "1":
                        sreturn = sreturn + "年";
                        break;
                    case "2":
                        sreturn = sreturn + "-";
                        break;
                    case "3":
                        sreturn = sreturn + "/";
                        break;
                    case "5":
                        sreturn = sreturn + ".";
                        break;
                }
            }
            // 处理月份
            if (yf.equals("1")) {
                sreturn = sreturn + s_yf;
                switch (format) {
                    case "1":
                        sreturn = sreturn + "月";
                        break;
                    case "2":
                        sreturn = sreturn + "-";
                        break;
                    case "3":
                        sreturn = sreturn + "/";
                        break;
                    case "5":
                        sreturn = sreturn + ".";
                        break;
                }
            }
            // 处理日期
            if (rq.equals("1")) {
                sreturn = sreturn + s_rq;
                if (format.equals("1"))
                    sreturn = sreturn + "日";
            }
        } else {
            // 不是空值，也是一个合法的日期值，则先将其转换为标准的时间格式
            sdate = getOKDate(sdate);
            s_nd = sdate.substring(0, 4); // 年份
            s_yf = sdate.substring(5, 7); // 月份
            s_rq = sdate.substring(8, 10); // 日期
            if (nd.equals("1")) {
                sreturn = s_nd;
                // 处理间隔符
                switch (format) {
                    case "1":
                        sreturn = sreturn + "年";
                        break;
                    case "2":
                        sreturn = sreturn + "-";
                        break;
                    case "3":
                        sreturn = sreturn + "/";
                        break;
                    case "5":
                        sreturn = sreturn + ".";
                        break;
                }
            }
            // 处理月份
            if (yf.equals("1")) {
                sreturn = sreturn + s_yf;
                switch (format) {
                    case "1":
                        sreturn = sreturn + "月";
                        break;
                    case "2":
                        sreturn = sreturn + "-";
                        break;
                    case "3":
                        sreturn = sreturn + "/";
                        break;
                    case "5":
                        sreturn = sreturn + ".";
                        break;
                }
            }
            // 处理日期
            if (rq.equals("1")) {
                sreturn = sreturn + s_rq;
                if (format.equals("1"))
                    sreturn = sreturn + "日";
            }
        }
        return sreturn;
    }

    public static String getNextMonthDay(String sdate, int m) {
        sdate = getOKDate(sdate);
        int year = Integer.parseInt(sdate.substring(0, 4));
        int month = Integer.parseInt(sdate.substring(5, 7));
        month = month + m;
        if (month < 0) {
            month = month + 12;
            year = year - 1;
        } else if (month > 12) {
            month = month - 12;
            year = year + 1;
        }
        String smonth = "";
        if (month < 10)
            smonth = "0" + month;
        else
            smonth = "" + month;
        return year + "-" + smonth + "-10";
    }

    /**
     * 功能：
     *
     * @author Tony
     * @version 2015-3-31 上午09:29:31 <br/>
     */
    public static String getOKDate(String sdate) {
        if (sdate == null || sdate.equals(""))
            return getStringDateShort();

        // 如果只有8位长度，则要进行转换
        if (sdate.length() == 8)
            sdate = sdate.substring(0, 4) + "-" + sdate.substring(4, 6) + "-" + sdate.substring(6, 8);
        LocalDateTime strToDate = LocalDateTime.parse(sdate, formatterYMD);
        return formatterYMD.format(strToDate);
    }

    /**
     * 获取当前时间的前一天时间
     */
    private static String getBeforeDay(String timeFormat) {
        return LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern(timeFormat));
    }

    /**
     * 获取当前时间的后一天时间
     */
    private static String getAfterDay(String timeFormat) {
        return LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern(timeFormat));
    }


    private static int compareToDate(String date1, String date2) {
        return date1.compareTo(date2);
    }

    private static int compareToDateString(String date1, String date2) {
        int i;

        long ldate1 = LocalDateTime.parse(date1, formatterYMDHMS).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long ldate2 = LocalDateTime.parse(date2, formatterYMDHMS).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        i = Long.compare(ldate1, ldate2);
        return i;
    }


    /**
     * Description:获取当前时间格式为yyyy-MM-dd HH:mm:ss的字符串
     *
     * @author dbdu
     */
    public static String getStringNow() {
        return getStringDateTime(LocalDateTime.now());
    }

    /**
     * Description:获取时间格式为yyyy-MM-dd HH:mm:ss的字符串
     *
     * @param date 日期
     * @return java.lang.String
     * @author dbdu
     */
    public static String getStringDateTime(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return formatterYMDHMS.format(date);
    }

    /**
     * Description:从格式为yyyy-MM-dd HH:mm日期中，
     * 获取时间格式为yyyy-MM-dd的字符串
     *
     * @param date 日期
     * @return java.lang.String
     * @author dbdu
     */
    public static String getStringDate(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return getDateString(date, 6);
    }

    /**
     * Description:获取时间格式为HH:mm的字符串
     *
     * @param date 日期
     * @return java.lang.String
     * @author dbdu
     */
    public static String getStringTime(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return formatterHM.format(date);
    }

    /**
     * Description:取出传来的月份的最后一天
     *
     * @param
     * @return Created at:18-4-25 下午1:27
     */
    public static String getLastDayOfMonth(String dateStr) {
        SimpleDateFormat sfMonth = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = "";
        try {
            Date t = sfMonth.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(t);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, lastDay);
            lastDayOfMonth = sfDate.format(calendar.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lastDayOfMonth;
    }

    /**
     * Description:获取传来的字符串的下一个月份
     *
     * @param
     * @return Created at:18-4-25 上午11:09
     */
    public static String nextMonth(String dateStr) {
        DateTimeFormatter sfMonth = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime t = LocalDateTime.parse(dateStr, sfMonth);

        return String.valueOf(t.getMonthValue() + 1);
    }

    /**
     * Description:获取当前月份
     *
     * @param
     * @return Created at:18-4-25 下午1:13
     */
    public static int nowMonth(String dateStr) {

        DateTimeFormatter sfMonth = DateTimeFormatter.ofPattern("yyyy-MM");
        return LocalDateTime.parse(dateStr, sfMonth).getMonthValue();
    }

    /**
     * Description:获取当前时间字符串的年份
     *
     * @param dateStr 日期字符串
     * @return Created at:18-4-25 下午2:12
     */
    public static int nowYear(String dateStr) {
        DateTimeFormatter sfMonth = DateTimeFormatter.ofPattern("yyyy-MM");
        return LocalDateTime.parse(dateStr, sfMonth).getYear();


    }


    /**
     * Description:获取当天的开始的毫秒时间的，Long型，例如 2018-10-12 00:00:00 000 对应的Long值
     *
     * @return java.lang.Long
     * @author dbdu
     */
    public static long getTodayStart() {
        return getTimeMSFromString(getCurrentDate() + " 00:00:00");
    }

    /**
     * Description:获取当天的结束的毫秒时间的，Long型，例如 2018-10-12 23:59:59 999 对应的Long值
     *
     * @return java.lang.Long
     * @author dbdu
     */
    public static long getTodayEnd() {
        return getTodayStart() + ONE_DAY_SECOND * 1000 - 1;
    }

    public static void main(String[] args) {
//        Date date = toDateWithoutSecond("2017-01-25 08:30");
//        String s = "2018-04-30 08:30:30";
//        String e = "2018-04-01 08:30:30";
//        System.out.println(isSameMonth(formatterYMDHMS.parse(s), formatterYMDHMS.parse(e)));
        //System.out.println(getDateString(date, 6));
//        DateTimeUtil.toDate("1970-01-01");
//        DateTimeUtil.toSqlDateTime("1970-01-01");
//        String val1 = DateTimeUtil.getDateString(new Date(), 0);
//        String val2 = DateTimeUtil.getDateString(Long.valueOf("1463386947012"), 0);
//        System.out.println("G20:" + DateTimeUtil.getDateString(Long.valueOf("1463386947012"), 1));
//        System.out.println(val1 + " : " + val2);

        System.out.println("DateUtils.getCurDateStr():" + getCurDateStr());
        System.out.println("DateUtils.getCurrentDate():" + getCurrentDate());

        System.out.println("DateUtils.getTimeFromString( DateUtils.getCurrentDate()):" + getTimeFromString(getCurrentDate() + " 00:00:00"));
        System.out.println("DateUtils.getTimeMSFromString( DateUtils.getCurrentDate()):" + getTimeMSFromString(getCurrentDate() + " 00:00:00"));
        System.out.println("DateUtils.getTimeMSFromString( DateUtils.getCurrentDate()):" + getTimeMSFromString(getCurrentDate() + " 23:59:59"));

        System.out.println(getDateString(getTimeMSFromString(getCurrentDate() + " 00:00:00")));
        System.out.println(getDateString(getTimeMSFromString(getCurrentDate() + " 23:59:59")));

        System.out.println("DateUtils.getDateString(getTodayStart()):" + getDateString(getTodayStart()));
        System.out.println("DateUtils.getDateString(getTodayEnd()):" + getDateString(getTodayEnd() + 1));
        System.out.println(getTodayEnd() + 1);
        System.out.println(getCurrentDate());
        System.out.println(getCurrentYear());
        System.out.println(getPreviousMonthStart("2020/04/13 00:00:00"));

    }
}
