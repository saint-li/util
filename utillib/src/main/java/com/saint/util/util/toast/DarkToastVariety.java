package com.saint.util.util.toast;


import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.ColorInt;

import com.saint.util.R;
import com.saint.util.UtilConfig;
import com.saint.util.util.AppUtil;


final class DarkToastVariety extends AbstractToastVariety implements ITextShow {

    protected int mThemeColor;

    public DarkToastVariety(@ColorInt int themeColor) {
        super(TOAST_TAG_DARK);
        mThemeColor = themeColor;
    }

    @Override
    protected Toast createToast() {
        mToast = new Toast(UtilConfig.getApp());
        mView = LayoutInflater.from(UtilConfig.getApp()).inflate(R.layout.layout_drak_toast, null);
        mMsgView = mView.findViewById(R.id.type_info_message);
        mToast.setView(mView);
        return mToast;
    }

    @Override
    protected void updateToast() {
        super.updateToast();
        GradientDrawable bg = (GradientDrawable) mToast.getView().getBackground();
        bg.setColor(mThemeColor);
    }


    @Override
    public void show(CharSequence msg) {
        showHelper(msg, EMOTION_NONE, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, mVerticalAxisOffsetWhenBottom, Toast.LENGTH_SHORT);
    }

    @Override
    public void show(int msgRes) {
        show(AppUtil.getStringFromRes(msgRes));
    }

    @Override
    public void showAtTop(CharSequence msg) {
        showHelper(msg, EMOTION_NONE, Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, mVerticalAxisOffsetWhenTop, Toast.LENGTH_SHORT);
    }

    @Override
    public void showAtTop(int msg) {
        showAtTop(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void showInCenter(CharSequence msg) {
        showHelper(msg, EMOTION_NONE, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }

    @Override
    public void showInCenter(int msg) {
        showInCenter(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showHelper(msg, EMOTION_NONE, gravity, AppUtil.dpToPx(xOffsetDp), AppUtil.dpToPx(yOffsetDp), Toast.LENGTH_SHORT);
    }

    @Override
    public void showAtLocation(int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showAtLocation(AppUtil.getStringFromRes(msg), gravity, xOffsetDp, yOffsetDp);
    }

    @Override
    public void showLong(CharSequence msg) {
        showHelper(msg, EMOTION_NONE, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, mVerticalAxisOffsetWhenBottom, Toast.LENGTH_LONG);
    }

    @Override
    public void showLong(int msg) {
        showLong(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void showLongAtTop(CharSequence msg) {

        showHelper(msg, EMOTION_NONE, Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, mVerticalAxisOffsetWhenTop, Toast.LENGTH_LONG);
    }

    @Override
    public void showLongAtTop(int msg) {
        showLongAtTop(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void showLongInCenter(CharSequence msg) {
        showHelper(msg, EMOTION_NONE, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }

    @Override
    public void showLongInCenter(int msg) {
        showLongInCenter(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showHelper(msg, EMOTION_NONE, gravity, AppUtil.dpToPx(xOffsetDp), AppUtil.dpToPx(yOffsetDp), Toast.LENGTH_LONG);
    }

    @Override
    public void showLongAtLocation(int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showLongAtLocation(AppUtil.getStringFromRes(msg), gravity, xOffsetDp, yOffsetDp);
    }
}
