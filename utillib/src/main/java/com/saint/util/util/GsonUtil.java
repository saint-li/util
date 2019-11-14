package com.saint.util.util;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.saint.util.UtilConfig;

public class GsonUtil {
    private Gson gson;
    //    private Type type;
//    private Class<T> clazz;
    private static GsonUtil instance;


    private GsonUtil() {
        if (gson == null) {
            gson = new Gson();
        }
    }

    public static GsonUtil instance() {
        if (instance == null) {
            synchronized (GsonUtil.class) {
                if (instance == null) {
                    instance = new GsonUtil();
                }
            }
        }
        return instance;
    }


    public <T> T fromJson(String json, Class<T> cls) {
        try {
            T t = null;
            if (gson != null) {
                t = gson.fromJson(json, cls);
            }
            return t;
        } catch (Exception e) {
            MobclickAgent.reportError(UtilConfig.getApp(), e.getMessage());
            AppLog.e("GsonUtil", "数据解析异常： " + e.getMessage());
            return null;
        }
    }

    public String beanToJson(Object bean) {
        if (bean == null) return "";
        return gson.toJson(bean);
    }

    public String toJsonStr(Object obj) {
        if (obj == null) return "";
        return gson.toJson(obj);
    }
}
