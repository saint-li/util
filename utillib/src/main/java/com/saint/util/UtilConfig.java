package com.saint.util;

import android.app.Activity;
import android.app.Application;

import com.hjq.toast.Toaster;
import com.saint.util.lifecycle.ActivityCallback;
import com.saint.util.lifecycle.IToastCallback;
import com.saint.util.util.AppUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;

public class UtilConfig {
    private static Application app;
    private static boolean isDebug = false;

    private static IToastCallback sToastCallback;

    public static void init(Application app, boolean debug) {
        if (app == null) {
            throw new NullPointerException("初始化Util的application不可为null！");
        }
        if (!AppUtil.isMainProcess(app)) return;
        UtilConfig.app = app;
        isDebug = debug;
        Toaster.init(app);
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

    static {
        //全局设置默认的 Header
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new ClassicsHeader(context));

        //全局设置默认的 Footer
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context));

    }
}
