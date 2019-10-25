package com.saint.util.util;

import android.text.TextUtils;
import android.util.Log;
import com.saint.util.UtilConfig;

/**
 * `Author: Administrator
 * Time: 2018/6/6 14:47
 * ReadMe:
 */
public class AppLog {
    private static String TAG = "APP_LOG";

    public static void d(String TAG, String str) {
        if (UtilConfig.isDebug())
            Log.d(TAG, str);
    }

    public static void v(String TAG, String str) {
        if (UtilConfig.isDebug())
            Log.v(TAG, str);
    }

    public static void e(String TAG, String str) {
        if (UtilConfig.isDebug()) {
            if (!TextUtils.isEmpty(str)) {
                Log.e(TAG, CodeUtil.unicodeToUTF_8(str));
            } else {
                Log.e(TAG, "日志为null");
            }
        }
    }

    public static void i(String TAG, String str) {
        if (UtilConfig.isDebug())
            Log.i(TAG, str);
    }

    public static void i(String str) {
        if (UtilConfig.isDebug())
            Log.i(TAG, str);
    }

    public static void d(String str) {
        if (UtilConfig.isDebug())
            Log.d(TAG, str);
    }

    public static void v(String str) {
        if (UtilConfig.isDebug())
            Log.v(TAG, str);
    }

    public static void e(String str) {
        if (UtilConfig.isDebug()) {
            if (!TextUtils.isEmpty(str)) {
                Log.e(TAG, CodeUtil.unicodeToUTF_8(str));
            } else {
                Log.e(TAG, "日志为null");
            }
        }
    }


}
