package com.saint.utillib;

import android.app.Application;

import com.saint.util.UtilConfig;
import com.saint.util.util.AppUtil;
import com.saint.utillib.gloading.GlobalAdapter;
import com.saint.widget.GLoading;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (AppUtil.isMainProcess(this)) {
            UtilConfig.init(this, BuildConfig.DEBUG);
            UtilConfig.setIsDebug(BuildConfig.DEBUG);
            //全局加载、空 视图
            GLoading.debug(BuildConfig.DEBUG);
            GLoading.initDefault(new GlobalAdapter());
        }

    }
}
