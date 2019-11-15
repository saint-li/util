package com.saint.util.http;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpParams;
import com.saint.util.UtilConfig;
import okhttp3.OkHttpClient;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * `Author: Saint
 * Time:
 * ReadMe:
 */
public class OkUtil {
    private static final long CONNECT_TIME_OUT = 15000;
    private static final long WRITE_TIME_OUT = 15000;
    private static final long READ_TIME_OUT = 15000;
    private static final String TAG = "HTTP_TU";


    private OkGo mOkGo;
    private static OkUtil okUtil;

    private OkUtil() {
        if (mOkGo == null) {
            init();
        }
    }

    public static OkUtil instance() {
        if (okUtil == null) {
            synchronized (OkUtil.class) {
                if (okUtil == null) {
                    okUtil = new OkUtil();
                }
            }
        }
        return okUtil;
    }

    public OkGo getOkGo() {
        return mOkGo;
    }

    private void init() {
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        //配置打印Log
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(TAG);
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.WARNING);
        mBuilder.addInterceptor(loggingInterceptor);
        //配置超时时间
        mBuilder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS);
        mBuilder.readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS);
        mBuilder.writeTimeout(WRITE_TIME_OUT, TimeUnit.MILLISECONDS);

        //配置Https
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
//        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
//        HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(App.getInstance().getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//        HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        mBuilder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//        builder.hostnameVerifier(new SafeHostnameVerifier());

        //配置全局头参数
//        HttpHeaders headers = new HttpHeaders();
//        headers.put("key","value");

        //配置全局参数
//        HttpParams params = new HttpParams();
//        params.put("key", "value");

        mOkGo = OkGo.getInstance().init(UtilConfig.getApp())
                .setOkHttpClient(mBuilder.build())
                .setCacheMode(CacheMode.NO_CACHE)
                .setRetryCount(0)
//                .addCommonHeaders(headers)
//                .addCommonParams(params)
        ;
    }


    public void postData(String api, HttpParams params, Object tag, StringCallback callback) {
        OkGo.<String>post(api)
                .params(params)
                .tag(tag)
                .execute(callback);
    }

    public void getData(String api, HttpParams params, Object tag, StringCallback callback) {
        OkGo.<String>get(api)
                .params(params)
                .tag(tag)
                .execute(callback);
    }

    public void putData(String api, HttpParams params, Object tag, StringCallback callback) {
        OkGo.<String>put(api)
                .params(params)
                .tag(tag)
                .execute(callback);
    }

    public void deleteData(String api, HttpParams params, Object tag, StringCallback callback) {
        OkGo.<String>delete(api)
                .tag(tag)
                .isSpliceUrl(true)
                .params(params)
                .execute(callback);
    }

    public void download(Object tag, String url, FileCallback callback) {
        OkGo.<File>post(url)
                .tag(tag)
                .execute(callback);
    }

    public void cancelTag(Object obj) {
        mOkGo.cancelTag(obj);
    }
}
