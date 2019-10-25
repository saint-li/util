package com.saint.util.util;

import android.text.TextUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * `Author: Administrator
 * Time: 2018/9/5 10:06
 * ReadMe:
 */
public class TimeUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat sdfHms = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat sdfHm = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat sdfH = new SimpleDateFormat("HH");
    private static SimpleDateFormat sdfm = new SimpleDateFormat("mm");
    private static SimpleDateFormat sdfMdHms = new SimpleDateFormat("MM/dd HH:mm");
    private static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdfYM = new SimpleDateFormat("yyyy年M月");
    private static SimpleDateFormat sdfYMd = new SimpleDateFormat("yyyy年M月d日");
    private static SimpleDateFormat sdfMDHM = new SimpleDateFormat("MM-dd HH:mm");

    public static String formatYM(long timeMillis) {
        if (timeMillis <= 0) return "";
        return sdfYM.format(timeMillis);
    }

    public static String formatYMd(long timeMillis) {
        if (timeMillis <= 0) return "";
        return sdfYMd.format(timeMillis);
    }

    /**
     * @param timeStr yyyy-MM-dd
     * @return 时间戳
     */
    public static long parseYM(String timeStr) {
        if (TextUtils.isEmpty(timeStr)) return 0;
        try {
            return sdfDay.parse(timeStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String formatMDHM(long timeMillis) {
        if (timeMillis <= 0) return "";
        return sdfMDHM.format(timeMillis);
    }


    public static String formatDay(long timeMillis) {
        if (timeMillis <= 0) return "";
        return sdfDay.format(timeMillis);
    }

    public static String formatTimeSecond(long time) {
        if (time > 0) {
            return sdf.format(time * 1000);
        }
        return "";
    }

    public static String formatTime(long timeMillis) {
        if (timeMillis > 0) {
            return sdf.format(timeMillis);
        }
        return "";
    }

    public static String formatTimeHms(long timeMillis) {
        if (timeMillis > 0) {
            return sdfHms.format(timeMillis);
        }
        return "";
    }

    public static String formatTimeMdHms(long time) {
        if (time > 0) {
            return sdfMdHms.format(time * 1000);
        }
        return "";
    }

    public static String formatTimeMdHm(long time) {
        if (time > 0) {
            return sdfMdHms.format(time * 1000);
        }
        return "";
    }

    public static String formatTimeYMD(long timeMillis) {
        if (timeMillis > 0) {
            return sdf2.format(timeMillis);
        }
        return "";
    }

    public static String formatTimeHm(long timeMillis) {
        if (timeMillis > 0) {
            return sdfHm.format(timeMillis);
        }
        return "";
    }

    public static String formatTimeH(long timeMillis) {
        if (timeMillis > 0) {
            return sdfH.format(timeMillis);
        }
        return "";
    }

    public static String formatTimem(long timeMillis) {
        if (timeMillis > 0) {
            return sdfm.format(timeMillis);
        }
        return "";
    }

    public static long toTimestamp(String timeStr) {
        if (TextUtils.isEmpty(timeStr)) return 0;
        return Timestamp.valueOf(timeStr).getTime();
    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        return sdfHm.format(date);
    }

    public static long parseHmTime(String timeHm) {//可根据需要自行截取数据显示
        try {
            return sdfHm.parse(timeHm).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getCurrentHour() {
        Date date = new Date();
        return date.getHours();
    }

    public static int getCurrentMin() {
        Date date = new Date();
        return date.getMinutes();
    }

    public static long toTimeMillis(String timeyMdHm) {
        try {
            return sdf1.parse(timeyMdHm).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param timeStr yyyy-MM-dd HH:mm:ss
     * @return 时间戳
     */
    public static long parseTime(String timeStr) {
        if (TextUtils.isEmpty(timeStr)) return 0;
        try {
            return sdf.parse(timeStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param timeStr yyyy-MM-dd
     * @return 时间戳
     */
    public static long parseTimeYMd(String timeStr) {
        if (TextUtils.isEmpty(timeStr)) return 0;
        try {
            return sdfDay.parse(timeStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
