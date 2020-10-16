package com.saint.util.util.toast.style;

import android.content.Context;
import android.view.Gravity;

/**
 * desc   : 支付宝样式实现
 */
public class ToastAliPayStyle extends BaseToastStyle {

    public ToastAliPayStyle(Context context) {
        super(context);
    }

    @Override
    public int getGravity() {
        return Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    }

    @Override
    public int getYOffset() {
        return dp2px(100);
    }

    @Override
    public int getCornerRadius() {
        return dp2px(5);
    }

    @Override
    public int getBackgroundColor() {
        return 0XEE575757;
    }

    @Override
    public int getTextColor() {
        return 0XFFFFFFFF;
    }

    @Override
    public float getTextSize() {
        return sp2px(16);
    }

    @Override
    public int getPaddingStart() {
        return dp2px(16);
    }

    @Override
    public int getPaddingTop() {
        return dp2px(10);
    }
}