package com.saint.utillib;

import android.app.Application;

import com.saint.util.UtilConfig;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UtilConfig.init(this,"",BuildConfig.DEBUG);
        UtilConfig.setIsDebug(BuildConfig.DEBUG);
    }
}
