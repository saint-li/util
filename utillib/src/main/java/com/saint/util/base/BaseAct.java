package com.saint.util.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.saint.util.http.OkUtil;
import com.umeng.analytics.MobclickAgent;
import com.saint.util.util.ActivityUtil;
import com.saint.util.util.AppUtil;
import com.saint.widget.MyActionBar;

/**
 * `Author: Administrator
 * Time:
 * ReadMe:
 */
public abstract class BaseAct extends AppCompatActivity {
    protected BaseAct act;

    protected MyActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setTransparent())
            AppUtil.setAlphaTitle(this);
        ActivityUtil.INSTANCE.addActivity(this);
        AppUtil.setAndroidNativeLightStatusBar(this, true);
        act = this;
        if (setRootView() != null) {
            setContentView(setRootView());
        } else {
            setContentView(setLayout());
        }
//        setFitsSystemWindows();
        getIntentData();
        initTitleView();
        initView();
        initListener();
        initData(savedInstanceState);
    }

    protected boolean setTransparent() {
        return true;
    }

    /**
     * 设置内容布局
     */
    protected int setLayout() {
        return 0;
    }

    protected View setRootView() {
        return null;
    }

    /**
     * 初始化标题栏
     */
    protected void initTitleView() {
    }


    /**
     * 初始化布局
     */
    protected void initView() {
    }

    ;

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化数据携带存储数据
     */

    protected void initData(@Nullable Bundle savedInstanceState) {
        initData();
    }

    /**
     * 初始化监听
     */
    protected void initListener() {

    }


    /**
     * 获取intent携带数据
     */
    protected void getIntentData() {

    }

    /**
     * 数据处理
     */
    protected void handleMessage(Message msg) {
    }

    @Override
    protected void onDestroy() {
        ActivityUtil.INSTANCE.removeActivity(this);
        OkUtil.instance().cancelTag(this);
        super.onDestroy();
    }

    protected boolean hideKeyboard() {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!hideKeyboard()) return super.dispatchTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    getWindow().getDecorView().setClickable(true);
//                    getWindow().getDecorView().setFocusable(true);
//                    getWindow().getDecorView().setFocusableInTouchMode(true);
                    getWindow().getDecorView().requestFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    private void setFitsSystemWindows() {
        // 华为,OPPO机型在StatusBarUtil.setLightStatusBar后布局被顶到状态栏上去了
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View content = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            if (content != null) {
                content.setFitsSystemWindows(true);
            }
        }
    }

    public void showAct(Class clazz) {
        act.startActivity(new Intent(act, clazz));
    }

    public void showAct(Class clazz, String key, String value) {
        Intent intent = new Intent(act, clazz);
        intent.putExtra(key, value);
        act.startActivity(intent);
    }

    public void showAct(Class clazz, String key, long value) {
        Intent intent = new Intent(act, clazz);
        intent.putExtra(key, value);
        act.startActivity(intent);
    }

    public void showAct(Class clazz, Bundle bundle) {
        Intent intent = new Intent(act, clazz);
        intent.putExtras(bundle);
        act.startActivity(intent);
    }


    public void showActForResult(Class clazz, int requestCode) {
        Intent intent = new Intent(act, clazz);
        startActivityForResult(intent, requestCode);
    }

    public void showActForResult(Class clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(act, clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    public void showActForResult(Class clazz, int requestCode, String key, String value) {
        Intent intent = new Intent(act, clazz);
        intent.putExtra(key, value);
        startActivityForResult(intent, requestCode);
    }

    public void showActForResult(Class clazz, int requestCode, String key, int value) {
        Intent intent = new Intent(act, clazz);
        intent.putExtra(key, value);
        startActivityForResult(intent, requestCode);
    }

    public void showActForResult(Class clazz, int requestCode, String key, long value) {
        Intent intent = new Intent(act, clazz);
        intent.putExtra(key, value);
        startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.fontScale != 1) { //fontScale不为1，需要强制设置为1
            getResources();
        }
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources.getConfiguration().fontScale != 1) { //fontScale不为1，需要强制设置为1
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置成默认值，即fontScale为1
            resources.updateConfiguration(newConfig, resources.getDisplayMetrics());
        }
        return resources;
    }
}
