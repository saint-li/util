package com.saint.util.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.*;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.saint.util.R;
import com.saint.util.UtilConfig;
import com.saint.util.util.toast.AppToast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.CLIPBOARD_SERVICE;

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

//    //获取状态栏高度函数
//    public static int getStatusBarHeight() {
//        int result = 0;
//        int resId = UtilConfig.getApp().getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resId > 0) {
//            result = UtilConfig.getApp().getResources().getDimensionPixelSize(resId);
//        }
//        return result;
//    }


    public static int getColor(int colorResId) {
        return ContextCompat.getColor(UtilConfig.getApp(), colorResId);
    }

    public static String getString(int strResId) {
        return UtilConfig.getApp().getResources().getString(strResId);
    }

    public static String getString(int strResId, Object... formatArgs) {
        return UtilConfig.getApp().getResources().getString(strResId, formatArgs);
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

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && RomAppUtil.isMiUIV7OrAbove()) {
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

    public static int dpToPx(float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, UtilConfig.getApp().getResources().getDisplayMetrics()));
    }

    @ColorInt
    public static int getColorFromRes(@ColorRes int colorRes) {
        return ContextCompat.getColor(UtilConfig.getApp(), colorRes);
    }

    public static LayoutInflater getInflater() {
        return LayoutInflater.from(UtilConfig.getApp());
    }

    public static <T> T requireNonNull(T obj, String tip) {
        if (obj == null) {
            throw new NullPointerException(tip);
        }

        return obj;
    }


    public static boolean isEmpty(CharSequence c) {
        return TextUtils.isEmpty(c) || c.toString().trim().isEmpty();
    }

    public static int screenWidth() {
        return UtilConfig.getApp().getResources().getDisplayMetrics().widthPixels;
    }


    public static int getStatusBarHeight() {
        int resourceId = UtilConfig.getApp().getResources().getIdentifier("status_bar_height", "dimen", "android");
        int height = 0;
        try {
            height = UtilConfig.getApp().getResources().getDimensionPixelSize(resourceId);
        } catch (Resources.NotFoundException e) {
            height = AppUtil.dpToPx(24);
        }

        return height;
    }

    public static int getToolbarHeight() {
        int resourceId = UtilConfig.getApp().getResources().getIdentifier("abc_action_bar_default_height_material", "dimen", "android");
        int height = 0;
        try {
            height = UtilConfig.getApp().getResources().getDimensionPixelSize(resourceId);
        } catch (Resources.NotFoundException e) {
            height = AppUtil.dpToPx(56);
        }

        return height;
    }


    public static boolean isActivityDestroyed(Activity activity) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ?
                activity.isDestroyed() : !ActivityUtil.INSTANCE.isInStack(activity);
    }

    public static void popKeyboardWhenDialogShow(Dialog dialog) {
        if (dialog != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) UtilConfig.getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    public static Drawable getDrawableFromRes(int drawableRes) {
        return ContextCompat.getDrawable(UtilConfig.getApp(), drawableRes);
    }

    public static void setBackgroundDrawable(View view, Drawable drawable) {
        if (view == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
    /**
     * check if the network connected or not
     *
     * @param context context
     * @return true: connected, false:not, null:unknown
     */
    public static Boolean isNetworkConnected(Context context) {
        try {
            context = context.getApplicationContext();
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 判断 activity的状态是可以操作UI
     *
     * @param activity
     * @return
     */
    public static boolean isUpdateActivityUIPermitted(Activity activity) {
        return activity != null && !activity.isFinishing() && !isActivityDestroyed(activity);
    }

    public static boolean isUpdateFragmentUIPermitted(Fragment fragment) {
        return fragment != null && fragment.isVisible() && !fragment.isRemoving()
                && isUpdateActivityUIPermitted(fragment.getActivity());
    }

    public static boolean isCanShowDialogInFragment(Fragment fragment) {
        return isUpdateFragmentUIPermitted(fragment) && fragment.getUserVisibleHint();
    }

    public static String getStringFromRes(@StringRes int msg) {
        return UtilConfig.getApp().getString(msg);
    }

    public static float pxToDp(int px) {
        float scale = UtilConfig.getApp().getResources().getDisplayMetrics().density;
        return px / scale;
    }

    public static boolean isNotificationPermitted() {
        return NotificationManagerCompat.from(UtilConfig.getApp()).areNotificationsEnabled();
    }

    public static int screenHeight() {
        return UtilConfig.getApp().getResources().getDisplayMetrics().heightPixels;
    }

    public static String getActivityInfo(Activity activity) {
        return activity == null ? "the activity == null" : activity.getClass().getSimpleName() + "(" + activity.getClass().hashCode() + ")";
    }

    public static String getObjectDesc(Object object) {
        return object == null ? "(null)" : "(class:" + object.getClass().getSimpleName() + ";hashcode:" + object.hashCode() + ")";
    }

    public static int screenOrientation() {
        return UtilConfig.getApp().getResources().getConfiguration()
                .orientation;
    }

    public static boolean isScreenLandscape() {
        return screenOrientation() == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isScreenPortrait() {
        return screenOrientation() == Configuration.ORIENTATION_PORTRAIT;
    }


    public static void copyString(String str) {
        if (!TextUtils.isEmpty(str)) {
            ClipboardManager clipboardManager = (ClipboardManager) UtilConfig.getApp().getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("text", str);
            clipboardManager.setPrimaryClip(clipData);
            AppToast.INSTANCE.tShort(R.string.copy_succeed);
        }
    }

    /**
     * 检测app是否安装
     *
     * @param appPackageName 应用包名
     * @return
     */
    public static boolean isInstall(String appPackageName) {
        final PackageManager packageManager = UtilConfig.getApp().getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(appPackageName)) {
                    return true;
                }
            }
        }

        return false;
    }
    /**
     * 判断版本更新
     * versionName版本判断本身是有缺陷的，
     * 必须保证三位格式示例：1.1.1
     *
     * @param localVersion 本地app 版本号
     * @param newVersion   最新版本号
     * @return true 需要更新 false 不用
     */
    public static boolean isNewVersionHigh(String localVersion, String newVersion) {
        String[] localArray = localVersion.split("\\.");
        String[] newArray = newVersion.split("\\.");
        if (localArray.length < 3) {
            int cha = 3 - localArray.length;
            for (int i = 0; i < cha; i++) {
                localVersion = localVersion + ".0";
            }
            localArray = localVersion.split("\\.");
        }
        if (newArray.length < 3) {
            int cha = 3 - newArray.length;
            for (int i = 0; i < cha; i++) {
                newVersion = newVersion + ".0";
            }
            newArray = newVersion.split("\\.");
        }
        try {
            //第一位大于，则需更新
            if (Integer.parseInt(newArray[0]) > Integer.parseInt(localArray[0])) {
                return true;
            }
            //第一位小于，则不需更新
            if (Integer.parseInt(newArray[0]) < Integer.parseInt(localArray[0])) {
                return false;
            }
            //第一位相等，则进行下一位判断
            //第二位大于，则需更新
            if (Integer.parseInt(newArray[1]) > Integer.parseInt(localArray[1])) {
                return true;
            }
            //第二位小于，则不需更新
            if (Integer.parseInt(newArray[1]) < Integer.parseInt(localArray[1])) {
                return false;
            }
            //第二位相等，则进行下一位判断
            //第三位大于，则需更新，否则不更新
            return Integer.parseInt(newArray[2]) > Integer.parseInt(localArray[2]);
            //第三位小于或等于，则不需更新
//            if (Integer.parseInt(newArray[2]) <= Integer.parseInt(localArray[2])) {
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
