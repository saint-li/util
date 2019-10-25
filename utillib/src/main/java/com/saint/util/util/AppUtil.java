package com.saint.util.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.*;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.saint.util.UtilConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * `Author: Administrator
 * Time:
 * ReadMe:
 */
public class AppUtil {
    /**
     * 设置透明状态栏
     *
     * @param act
     */
    public static void setAlphaTitle(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarUpperAPI21(act);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarUpperAPI19(act);
        }
    }

    //Android5.0版本以上
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void setStatusBarUpperAPI21(Activity act) {
        Window window = act.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        //由于setStatusBarColor()这个API最低版本支持21, 本人的是15,所以如果要设置颜色,自行到style中通过配置文件设置
        window.setStatusBarColor(Color.TRANSPARENT);
        ViewGroup mContentView = act.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }

    //Android4.4版本-Adnroid5.0版本
    private static void setStatusBarUpperAPI19(Activity act) {
        Window window = act.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = act.findViewById(Window.ID_ANDROID_CONTENT);
        int statusBarHeight = getStatusBarHeight();

        View mTopView = mContentView.getChildAt(0);
        if (mTopView != null && mTopView.getLayoutParams() != null &&
                mTopView.getLayoutParams().height == statusBarHeight) {
            //避免重复添加 View
            mTopView.setBackgroundColor(Color.TRANSPARENT);
            return;
        }
        //使 ChildView 预留空间
        if (mTopView != null) {
            ViewCompat.setFitsSystemWindows(mTopView, true);
        }

        //添加假 View
        mTopView = new View(act);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        mTopView.setBackgroundColor(Color.TRANSPARENT);
        mContentView.addView(mTopView, 0, lp);
    }

    //获取状态栏高度函数
    public static int getStatusBarHeight() {
        int result = 0;
        int resId = UtilConfig.getApp().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = UtilConfig.getApp().getResources().getDimensionPixelSize(resId);
        }
        return result;
    }


    public static int getColor(int colorResId) {
        return ContextCompat.getColor(UtilConfig.getApp(), colorResId);
    }

    public static String getString(int strResId) {
        return UtilConfig.getApp().getResources().getString(strResId);
    }

    public static int getDimen(int dimenResId) {
        return (int) UtilConfig.getApp().getResources().getDimension(dimenResId);
    }

    public static boolean isPhone(String phone) {
        String regex = "^[1|2]\\d{10}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    public static boolean isStringNull(String s) {
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }

    public static int getScreenHeight() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((WindowManager) UtilConfig.getApp().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static int getScreenWidth() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((WindowManager) UtilConfig.getApp().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    //原生状态栏修改
    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    //小米
    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && RomUtils.isMiUIV7OrAbove()) {
//                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
//                    if (dark) {
//                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                    } else {
//                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//                    }
//                }
            } catch (Exception e) {

            }
        }
        return result;
    }

    //魅族
    private static boolean setFlymeLightStatusBar(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

//    public static void showAct(Class clazz) {
//        UtilConfig.app.startActivity(new Intent(UtilConfig.app, clazz));
//    }

//    public static void showAct(Class clazz, int key) {
//        Intent intent = new Intent(UtilConfig.app, clazz);
//        intent.putExtra("key", key);
//        UtilConfig.app.startActivity(intent);
//    }

//    public static void showAct(Class clazz, long key) {
//        Intent intent = new Intent(UtilConfig.app, clazz);
//        intent.putExtra("key", key);
//        UtilConfig.app.startActivity(intent);
//    }

    public static void setWindowAlpha(Activity act, float alpha) {
        if (act == null) return;
        Window window = act.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = alpha;//设置阴影透明度
            window.setAttributes(lp);
        }
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public static String getVersionName() {

        //获取包管理器
        PackageManager pm = UtilConfig.getApp().getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(UtilConfig.getApp().getPackageName(), 0);
            //返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";

    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Activity act, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        act.startActivity(intent);
    }

    /**
     * 判断是否是全面屏
     */
    private volatile static boolean mHasCheckAllScreen;
    private volatile static boolean mIsAllScreenDevice;

    public static boolean isAllScreenDevice(Context context) {
        if (mHasCheckAllScreen) {
            return mIsAllScreenDevice;
        }
        mHasCheckAllScreen = true;
        mIsAllScreenDevice = false;
        // 低于 API 21的，都不会是全面屏。。。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            float width, height;
            if (point.x < point.y) {
                width = point.x;
                height = point.y;
            } else {
                width = point.y;
                height = point.x;
            }
            if (height / width >= 1.97f) {
                mIsAllScreenDevice = true;
            }
        }
        return mIsAllScreenDevice;
    }
}
