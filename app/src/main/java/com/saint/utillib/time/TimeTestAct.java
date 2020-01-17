package com.saint.utillib.time;

import android.text.TextUtils;
import android.widget.TextView;

import com.saint.util.base.BaseAct;
import com.saint.util.util.TimeUtil;
import com.saint.utillib.R;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTestAct extends BaseAct {
    TextView tvTime;

    @Override
    protected int setLayout() {
        return R.layout.act_time_test;
    }

    @Override
    protected void initView() {
        tvTime = findViewById(R.id.tv_text);
    }

    @Override
    protected void initData() {
        long time = System.currentTimeMillis();

        StringBuilder sb = new StringBuilder();
        sb.append("时间戳：").append(time).append("\n");

        sb.append(formatMillisecond(time))
                .append("     ")
                .append(TimeUtil.getInstance().formatMillisecond(time))
                .append("\n");

        sb.append(formatTimeHms(time))
                .append("     ")
                .append(TimeUtil.getInstance().formatTimeHms(time))
                .append("\n");

        sb.append(formatTime(time))
                .append("     ")
                .append(TimeUtil.getInstance().formatTime(time))
                .append("\n");

        sb.append(formatTimeHm(time))
                .append("     ")
                .append(TimeUtil.getInstance().formatTimeHm(time))
                .append("\n");

        sb.append(formatTimeH(time))
                .append("     ")
                .append(TimeUtil.getInstance().formatTimeH(time))
                .append("\n");

        sb.append(formatYM(time))
                .append("     ")
                .append(TimeUtil.getInstance().formatYM(time))
                .append("\n");

        sb.append(formatYMd(time))
                .append("     ")
                .append(TimeUtil.getInstance().formatYMd(time))
                .append("\n");

        sb.append(formatTimeHms(time))
                .append("     ")
                .append(TimeUtil.getInstance().formatTimeHms(time))
                .append("\n");

        sb.append(formatTimeMdHms(time / 1000))
                .append("     ")
                .append(TimeUtil.getInstance().formatTimeMdHms(time / 1000))
                .append("\n");

        sb.append(formatTimeMdHm(time / 1000))
                .append("     ")
                .append(TimeUtil.getInstance().formatTimeMdHm(time / 1000))
                .append("\n");

        sb.append(formatTimeYMD(time))
                .append("     ")
                .append(TimeUtil.getInstance().formatTimeYMD(time))
                .append("\n");
        sb.append(formatTimem(time))
                .append("     ")
                .append(TimeUtil.getInstance().formatTimem(time))
                .append("\n");
        sb.append(formatTimem(time))
                .append("     ")
                .append(TimeUtil.getInstance().formatTimem(time))
                .append("\n");
        sb.append(formatMDHM(time))
                .append("     ")
                .append(TimeUtil.getInstance().formatMDHM(time))
                .append("\n");
        sb.append(formatDay(time))
                .append("     ")
                .append(TimeUtil.getInstance().formatDay(time))
                .append("\n");
        sb.append(formatTimeSecond(time / 1000))
                .append("     ")
                .append(TimeUtil.getInstance().formatTimeSecond(time / 1000))
                .append("\n");


        tvTime.setText(sb.toString());
    }


    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdfStr = new SimpleDateFormat("yyyyMMddHHmmssSSS");
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

    public String formatYM(long timeMillis) {
        if (timeMillis <= 0) return "";
        return sdfYM.format(timeMillis);
    }

    public String formatMillisecond(long timeMillis) {
        if (timeMillis <= 0) return "";
        return sdfStr.format(timeMillis);
    }

    public String formatYMd(long timeMillis) {
        if (timeMillis <= 0) return "";
        return sdfYMd.format(timeMillis);
    }

    /**
     * @param timeStr yyyy-MM-dd
     * @return 时间戳
     */
    public long parseYM(String timeStr) {
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
