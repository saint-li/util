package com.saint.utillib.fragkotlin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.saint.util.base.BaseFrag;
import com.saint.utillib.R;

public class JavaFrag extends BaseFrag {
    TextView tvText;

    @Override
    protected int setLayout() {
        return R.layout.frag_kotlin_test;
    }

    @Override
    protected void initView() {
        tvText = findView(R.id.tv_text);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        tvText.setText("***************");
    }
}
