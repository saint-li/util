package com.saint.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.saint.util.R;

public class EmptyView extends LinearLayout {
    private LinearLayout layoutEmpty;
    private TextView tvTips;
    private ImageView ivTips;

    public EmptyView(Context context) {
        super(context);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        View view = inflate(getContext(), R.layout.layout_empty, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        addView(view);
        layoutEmpty = view.findViewById(R.id.layout_empty);
        tvTips = view.findViewById(R.id.tv_empty_tips);
        ivTips = view.findViewById(R.id.iv_empty_tips);

    }

    public void setTextTips(String str) {
        tvTips.setText(str);
    }

    public void setTextTips(int strRes) {
        tvTips.setText(strRes);
    }

    public void noData() {
        tvTips.setText(R.string.noData);
        ivTips.setImageResource(R.drawable.content_empty);
        layoutEmpty.setVisibility(VISIBLE);
    }

    public void noData(String tips) {
        tvTips.setText(tips);
        ivTips.setImageResource(R.drawable.content_empty);
        layoutEmpty.setVisibility(VISIBLE);
    }

    public void noData(int strResId) {
        tvTips.setText(strResId);
        ivTips.setImageResource(R.drawable.content_empty);
        layoutEmpty.setVisibility(VISIBLE);
    }

    public void netError() {
        tvTips.setText(R.string.loadDataError);
        ivTips.setImageResource(R.drawable.network_error);
        layoutEmpty.setVisibility(VISIBLE);
    }

    public void setIvTips(int drawableId) {
        ivTips.setImageResource(drawableId);
    }

    public void setNoImg() {
        ivTips.setVisibility(GONE);
    }

}
