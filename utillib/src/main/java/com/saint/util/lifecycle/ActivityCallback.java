package com.saint.util.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.saint.util.util.ActivityUtil;
import com.saint.util.util.AppLog;
import com.saint.util.util.AppUtil;

public class ActivityCallback implements Application.ActivityLifecycleCallbacks  {

    private int mVisibleCounter = 0;
    private Activity mLastVisibleActivity;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//        AppLog.d("activity created:" + AppUtil.getActivityInfo(activity));
    }

    @Override
    public void onActivityStarted(Activity activity) {
//        AppLog.d("activity started:" + AppUtil.getActivityInfo(activity));
        if (mVisibleCounter == 0) {
            onAppForeground(mLastVisibleActivity == null);
        }
        mVisibleCounter++;
        mLastVisibleActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
//        AppLog.d("activity resumed:" + AppUtil.getActivityInfo(activity));
    }

    @Override
    public void onActivityPaused(Activity activity) {
//        AppLog.d("activity paused:" + AppUtil.getActivityInfo(activity));
    }

    @Override
    public void onActivityStopped(Activity activity) {
//        AppLog.d("activity stopped:" + AppUtil.getActivityInfo(activity));

        mVisibleCounter--;

        if (mVisibleCounter == 0) {
            onAppBackground(ActivityUtil.INSTANCE.count() == 1 && activity.isFinishing());
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//        AppLog.d("activity saveState:" + AppUtil.getActivityInfo(activity));
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
//        AppLog.d("activity destroyed:" + AppUtil.getActivityInfo(activity));
        if (activity == mLastVisibleActivity) {
            mLastVisibleActivity = null;
        }

//        AppLog.d("the activity stack size:" + ActivityUtil.getInstance().count());
    }

    public void onAppForeground(boolean restartMainActivity) {
        AppLog.d("App foreground");
    }

    public void onAppBackground(boolean activityStackEmpty) {
        AppLog.d("App background");
    }

}
