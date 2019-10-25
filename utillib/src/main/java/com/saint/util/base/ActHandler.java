package com.saint.util.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class ActHandler<T extends BaseAct> extends Handler {
    private WeakReference<T> activityWeakReference;

    public ActHandler(T activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        final T activity = activityWeakReference.get();
        if (null != activity) {
            activity.handleMessage(msg);
        }
    }
}
