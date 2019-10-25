package com.saint.util.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class FragHandler<T extends BaseFrag> extends Handler {
    private WeakReference<T> activityWeakReference;

    public FragHandler(T fragment) {
        activityWeakReference = new WeakReference<>(fragment);
    }

    @Override
    public void handleMessage(Message msg) {
        final T fragment = activityWeakReference.get();
        if (null != fragment) {
            fragment.handleMessage(msg);
        }
    }
}
