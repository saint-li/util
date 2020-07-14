package com.saint.util.util.toast;


import android.view.Gravity;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.saint.util.UtilConfig;
import com.saint.util.util.AppUtil;

/**
 *
 */

public final class AppToast {

    public static void tShort(CharSequence content) {
        ToastUtils.showShort(content);
    }

    public static void tShort(int contentRes) {
        ToastUtils.showShort(contentRes);
    }

    public static void tLong(CharSequence content) {
        ToastUtils.showLong(content);
    }

    public static void tLong(int contentRes) {
        ToastUtils.showLong(contentRes);
    }

    public static void showAtTop(CharSequence msg) {
        ToastUtils.setGravity(Gravity.TOP, 0, 0);
        ToastUtils.showShort(msg);
    }

    public static void showAtTop(int msg) {
        ToastUtils.setGravity(Gravity.TOP, 0, 0);
        ToastUtils.showShort(msg);
    }

    public static void showInCenter(CharSequence msg) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.showShort(msg);
    }

    public static void showInCenter(int msg) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.showShort(msg);
    }

    public static void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        ToastUtils.setGravity(gravity, ConvertUtils.dp2px(xOffsetDp), ConvertUtils.dp2px(yOffsetDp));
        ToastUtils.showShort(msg);
    }

    public static void showAtLocation(int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        ToastUtils.setGravity(gravity, ConvertUtils.dp2px(xOffsetDp), ConvertUtils.dp2px(yOffsetDp));
        ToastUtils.showShort(msg);
    }

    public static void resetToast() {
        ToastUtils.setMsgColor(-0x1000001);
        ToastUtils.setBgColor(-0x1000001);
        ToastUtils.setBgResource(-1);
        ToastUtils.setGravity(Gravity.CENTER, -1, -1);
    }
}
