package com.bolesky.base.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    /**
     * 时间格式化
     * @param time
     * @param format
     * @return
     */
    public static String format(long time, String format) {
        return new SimpleDateFormat(format).format(time);
    }

    /**
     * 毫秒转换为天数
     * @param time
     * @return
     */
    public static int time2Day(long time) {
        return (int) (time / 1000 / 60 / 60 / 24);
    }

    /**
     * 日期转换字符串
     * @param date
     * @return
     */
    public static String convertDate2String(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 毫秒转换为字符串日期
     * @param time
     * @return
     */
    public static String convertMillisecond2String(long time) {
        return convertDate2String(new Date(time));
    }

    /**
     * 毫秒转换为星期
     * @param time
     * @return
     */
    private static int convertMillisecond2CalendarWeekInt(long time) {
        Date date = new Date(time);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week = c.get(Calendar.DAY_OF_WEEK);
        return week;
    }
}
