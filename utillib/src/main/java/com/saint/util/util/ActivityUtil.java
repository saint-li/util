package com.saint.util.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * 活动工具类
 */

public class ActivityUtil {
    //activity集合
    public List<Activity> activities = new LinkedList<>();

    private ActivityUtil() {
    }

    //获取ActivityUtil实例,静态内部类单例模式
    public static ActivityUtil getInstance() {
        return ActivityUtilHolder.instance;
    }

    private static class ActivityUtilHolder {
        private static final ActivityUtil instance = new ActivityUtil();
    }

    /**
     * 将activity添加到集合中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 将activity从集合中移除
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activities.contains(activity)) {
            activities.remove(activity);
        }
    }

    public void removeAct(Class clazz) {
        if (!activities.isEmpty()) {
            for (Activity activity : activities) {
                if (activity.getClass() == clazz) {
                    activity.finish();
                    activities.remove(activity);
                }
            }
        }
    }

    /**
     * 关闭所有页面，清空集合
     */
    public void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

    /**
     * 开启页面，不携带数据
     *
     * @param context
     * @param className
     */
    public void actionStart(Context context, Class className) {
        Intent intent = new Intent(context, className);
        context.startActivity(intent);
    }

    /**
     * 开启页面，携带序列化数据
     *
     * @param context
     * @param className
     */
    public void actionStart(Context context, Class className, String key, String value) {
        Intent intent = new Intent(context, className);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    /**
     * 开启页面，携带序列化数据
     *
     * @param context
     * @param className
     */
    public void actionStart(Context context, Class className, String key, int value) {
        Intent intent = new Intent(context, className);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    /**
     * 开启页面，携带序列化数据
     *
     * @param context
     * @param className
     */
    public void actionStart(Context context, Class className, String key, boolean value) {
        Intent intent = new Intent(context, className);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

}
