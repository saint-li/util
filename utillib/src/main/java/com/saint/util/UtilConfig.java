package com.saint.util;

import android.app.Application;

import com.saint.util.util.toast.AppToast;
import com.umeng.commonsdk.UMConfigure;

public class UtilConfig {
    private static Application app;
    private static boolean isDebug = false;

    public static void init(Application app, String um_key, boolean debug) {
        UtilConfig.app = app;
        AppToast.init(app);
        //友盟
        /**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
         * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         * UMConfigure.init调用中appkey和channel参数请置为null）。
         * UMConfigure.init(Context context, String appkey, String channel, int deviceType, String pushSecret);
         * 数据统计/BUG扑捉
         */
        UMConfigure.init(app, um_key, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        isDebug = debug;
    }


    public static Application getApp() {
        return app;
    }

    public static void setIsDebug(boolean isDebug) {
        UtilConfig.isDebug = isDebug;
    }

    public static boolean isDebug() {
        return isDebug;
    }
}
