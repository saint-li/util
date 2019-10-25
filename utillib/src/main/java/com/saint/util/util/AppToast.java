package com.saint.util.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import com.saint.util.UtilConfig;

/**
 * `Author: Saint
 * Time:
 * ReadMe:
 */
public class AppToast extends Toast {
    /*
     * 单例toast 防止toast连弹
     */
    private static Toast toast;
    private static boolean isShow = false;

    private AppToast(Context context) {
        super(context);
    }


    public static void tShort(CharSequence content) {
        try {
            if (toast == null || isShow) {
                toast = Toast.makeText(UtilConfig.getApp(), content, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
            } else {
                toast.setText(content);
                toast.setDuration(Toast.LENGTH_SHORT);
            }
            toast.show();
            isShow = false;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void tShort(int contentRes) {
        try {
            if (toast == null || isShow) {
                toast = Toast.makeText(UtilConfig.getApp(), contentRes, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
            } else {
                toast.setText(contentRes);
                toast.setDuration(Toast.LENGTH_SHORT);
            }
            toast.show();
            isShow = false;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // 直接调用此方法即可
    public static void tLong(CharSequence content) {
        try {
            if (toast == null || isShow) {
                toast = Toast.makeText(UtilConfig.getApp(), content, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
            } else {
                toast.setText(content);
                toast.setDuration(Toast.LENGTH_LONG);
            }
            toast.show();
            isShow = true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void tLong(int contentRes) {
        try {
            if (toast == null || isShow) {
                toast = Toast.makeText(UtilConfig.getApp(), contentRes, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
            } else {
                toast.setText(contentRes);
                toast.setDuration(Toast.LENGTH_LONG);
            }
            toast.show();
            isShow = false;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
