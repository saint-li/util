package com.saint.util.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.lang.ref.WeakReference
import java.util.*

/**
 * Activity管理工具类
 */
object ActivityUtil {
    //activity集合
    val activities = LinkedList<Activity>()

    /**
     * 将activity添加到集合中
     *
     * @param activity
     */
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    /**
     * 将activity从集合中移除
     *
     * @param activity
     */
    fun removeActivity(activity: Activity) {
        if (activities.contains(activity)) {
            activities.remove(activity)
        }
    }

    fun isInStack(activity: Activity): Boolean {
        return !activities.isEmpty() && activities.contains(activity)
    }

    val top: Activity?
        get() = if (activities.isEmpty()) null else activities[activities.size - 1]

    fun count(): Int {
        return activities.size
    }

    fun removeAct(clazz: Class<*>) {
        if (activities.isNotEmpty()) {
            for (activity in activities) {
                if (activity.javaClass == clazz) {
                    activity.finish()
                    activities.remove(activity)
                }
            }
        }
    }

    /**
     * 关闭所有页面，清空集合
     */
    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
    }

    /**
     * 开启页面，不携带数据
     *
     * @param context
     * @param className
     */
    fun actionStart(context: Context, className: Class<*>) {
        val intent = Intent(context, className)
        context.startActivity(intent)
    }

    /**
     * 开启页面，携带序列化数据
     *
     * @param context
     * @param className
     */
    fun actionStart(context: Context, className: Class<*>, key: String, value: String?) {
        val intent = Intent(context, className)
        intent.putExtra(key, value)
        context.startActivity(intent)
    }

    /**
     * 开启页面，携带序列化数据
     *
     * @param context
     * @param className
     */
    fun actionStart(context: Context, className: Class<*>, key: String, value: Int) {
        val intent = Intent(context, className)
        intent.putExtra(key, value)
        context.startActivity(intent)
    }

    /**
     * 开启页面，携带序列化数据
     *
     * @param context
     * @param className
     */
    fun actionStart(context: Context, className: Class<*>, key: String, value: Boolean) {
        val intent = Intent(context, className)
        intent.putExtra(key, value)
        context.startActivity(intent)
    }

    /**
     * 开启页面，携带序列化数据
     *
     * @param context
     * @param className
     */
    fun actionStart(context: Context, className: Class<*>, bundle: Bundle) {
        val intent = Intent(context, className)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }
}