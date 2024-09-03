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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frag = this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return setRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        initData(savedInstanceState);
    }

    /**
     * 设置内容布局
     */
    protected View setRootView() {
        return null;
    }

    /**
     * 初始化数据
     */
    protected abstract void initData(@Nullable Bundle savedInstanceState);

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
