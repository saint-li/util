package com.saint.util.lifecycle;

import android.app.Activity;

public interface IToastCallback {
    void dismissOnLeave();

    void recycleOnDestroy(Activity activity);
}
