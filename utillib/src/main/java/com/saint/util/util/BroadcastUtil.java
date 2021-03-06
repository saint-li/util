package com.saint.util.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BroadcastUtil {

    private static BroadcastUtil instance;

    public static BroadcastUtil instance() {
        if (instance == null) {
            synchronized (BroadcastUtil.class) {
                if (instance == null) {
                    instance = new BroadcastUtil();
                }
            }
        }
        return instance;
    }


    public void sendBroadcast(Activity activity, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
    }

    public void sendBroadcast(Context context, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void sendBroadCast(Activity activity, Intent intent) {
        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
    }

}
