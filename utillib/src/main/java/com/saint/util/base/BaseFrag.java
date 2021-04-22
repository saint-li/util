package com.saint.util.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.saint.util.http.OkUtil;
import com.saint.widget.MyActionBar;

public abstract class BaseFrag extends Fragment {
    protected Fragment frag;
    protected MyActionBar mActionBar;
    protected View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frag = this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (setRootView() != null) {
            rootView = setRootView();
            return setRootView();
        }
        rootView = inflater.inflate(setLayout(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitleView();
        initView();
        initListener();
        initData(savedInstanceState);
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

    protected int setTitleId() {
        return 0;
    }

    protected void setTitle(int strRes) {
        if (mActionBar != null) mActionBar.setTitle(strRes);
    }

    protected void setTitle(String strRes) {
        if (mActionBar != null) mActionBar.setTitle(strRes);
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
     * 数据处理
     */
    protected void handleMessage(Message msg) {
    }

    @Override
    public void onDestroyView() {
        OkUtil.instance().cancelTag(this);
        super.onDestroyView();
    }

    public <T extends View> T findView(int resId) {
        return (T) rootView.findViewById(resId);
    }


    public void showAct(Class clazz) {
        startActivity(new Intent(getContext(), clazz));
    }

    public void showAct(Class clazz, String key, String value) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    public void showAct(Class clazz, String key, long value) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    public void showAct(Class clazz, String key, int value) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    public void showAct(Class clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showActForResult(Class clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    public void showActForResult(Class clazz, int requestCode) {
        Intent intent = new Intent(getContext(), clazz);
        startActivityForResult(intent, requestCode);
    }

    public void showActForResult(Class clazz, int requestCode, String key, String value) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtra(key, value);
        startActivityForResult(intent, requestCode);
    }

    public void showActForResult(Class clazz, int requestCode, String key, int value) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtra(key, value);
        startActivityForResult(intent, requestCode);
    }

    public void showActForResult(Class clazz, int requestCode, String key, long value) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtra(key, value);
        startActivityForResult(intent, requestCode);
    }
}
