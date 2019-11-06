package com.saint.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.saint.util.lifecycle.ActivityCallback;
import com.saint.util.lifecycle.IToastCallback;
import com.umeng.commonsdk.UMConfigure;

public class UtilConfig {
    private static Application app;
    private static boolean isDebug = false;

    private static IToastCallback sToastCallback;

    public static void init(Application app, String um_key, boolean debug) {
        if (app == null) {
            throw new NullPointerException("初始化Util的application不可为null！");
        }
        UtilConfig.app = app;
//        AppToast.init(app, new ToastAliPayStyle(app));
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

        app.registerActivityLifecycleCallbacks(new ActivityCallback() {

            @Override
            public void onActivityPaused(Activity activity) {
                super.onActivityPaused(activity);
                if (sToastCallback != null) {
                    sToastCallback.dismissOnLeave();
                }
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                if (sToastCallback != null) {
                    sToastCallback.recycleOnDestroy(activity);
                }
            }

        });
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

    public static void setToastCallback(IToastCallback toastCallback) {
        sToastCallback = toastCallback;
    }
}
