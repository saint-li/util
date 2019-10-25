package com.saint.util.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BroadcastUtil {
//    public static final String FILTER_SWITCH_STUDENT = "android.intent.action.switch.student";
//    public static final String FILTER_WARN_NOTICE = "android.intent.action.warn.notice.unread";
//    public static final String FILTER_CHANGE_AVATAR = "android.intent.action.change.avatar";
//    public static final String FILTER_READ_WARN_NOTICE = "android.intent.action.read.warn.notice";
//    public static final String FILTER_OPEN_MENU = "android.intent.action.open.menu";
//    public static final String FILTER_SWTICH_SCHOOL = "android.intent.action.switch.school";

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
        AppLog.e("发送广播—" + action);
    }

    public void sendBroadcast(Context context, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        AppLog.e("发送广播—" + action);
    }

    public void sendBroadCast(Activity activity, Intent intent) {
        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent);
    }

//    public void sendSwitchStudent(Activity activity) {
//        sendBroadcast(activity, FILTER_SWITCH_STUDENT);
//    }
//
//    public void sendChangeAvatar(Activity activity) {
//        sendBroadcast(activity, FILTER_CHANGE_AVATAR);
//    }
//
//    public void sendUnreadNotice(Activity activity) {
//        sendBroadcast(activity, FILTER_WARN_NOTICE);
//    }
//
//    public void sendUnreadNotice(Context context) {
//        sendBroadcast(context, FILTER_WARN_NOTICE);
//    }
//
//    public void sendOpenMenu(Activity activity) {
//        sendBroadcast(activity, FILTER_OPEN_MENU);
//    }
//
//    public void sendSwitchSchool(Activity activity) {
//        sendBroadcast(activity, FILTER_SWTICH_SCHOOL);
//    }

}
