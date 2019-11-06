package com.saint.util.util.toast;

import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.ColorInt;

import com.saint.util.R;
import com.saint.util.UtilConfig;
import com.saint.util.util.AppUtil;


final class EmotionToastVariety extends AbstractToastVariety implements IEmotionShow {

    protected ImageView mIconView;
    protected int mThemeColor;

    public EmotionToastVariety(@ColorInt int themeColor) {
        super(TOAST_TAG_EMOTION);
        mThemeColor = themeColor;
    }

    @Override
    protected Toast createToast() {
        mToast = new Toast(UtilConfig.getApp());
        mView = LayoutInflater.from(UtilConfig.getApp()).inflate(R.layout.layout_emotion_toast, null);
        mMsgView = mView.findViewById(R.id.type_info_message);
        mIconView = mView.findViewById(R.id.type_info_icon);
        mToast.setView(mView);
        return mToast;
    }

    @Override
    protected void updateToast() {
        super.updateToast();
        int iconRes = R.drawable.emotion_info;

        switch (mCurEmotionType) {
            case EMOTION_INFO:
                iconRes = R.drawable.emotion_info;
                break;
            case EMOTION_WARNING:
                iconRes = R.drawable.emotion_warning;
                break;
            case EMOTION_SUCCESS:
                iconRes = R.drawable.emotion_success;
                break;
            case EMOTION_FAIL:
                iconRes = R.drawable.emotion_fail;
                break;
            case EMOTION_FORBID:
                iconRes = R.drawable.emotion_forbid;
                break;

            case EMOTION_WAITING:
                iconRes = R.drawable.emotion_waiting;
                break;
            case EMOTION_ERROR:
                iconRes = R.drawable.emotion_error;
                break;
            case EMOTION_COMPLETE:
                iconRes = R.drawable.emotion_complete;
                break;
        }

        mIconView.setImageResource(iconRes);
        GradientDrawable bg = (GradientDrawable) mToast.getView().getBackground();
        bg.setColor(mThemeColor);
    }


    @Override
    public void info(CharSequence msg) {
        info(msg, R.drawable.emotion_info);
    }

    @Override
    public void info(int msg) {
        info(AppUtil.getStringFromRes(msg));
    }


    @Override
    public void info(int msg, int iconRes) {
        info(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void info(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_INFO, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void infoLong(CharSequence msg) {
        infoLong(msg, R.drawable.emotion_info);
    }

    @Override
    public void infoLong(int msg) {
        infoLong(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void infoLong(int msg, int iconRes) {
        infoLong(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void infoLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_INFO, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }


    @Override
    public void warning(CharSequence msg) {
        warning(msg, R.drawable.emotion_warning);
    }

    @Override
    public void warning(int msg) {
        warning(AppUtil.getStringFromRes(msg));
    }


    @Override
    public void warning(int msg, int iconRes) {
        warning(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void warning(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_WARNING, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void warningLong(CharSequence msg) {
        warningLong(msg, R.drawable.emotion_warning);
    }

    @Override
    public void warningLong(int msg) {
        warningLong(AppUtil.getStringFromRes(msg));
    }


    @Override
    public void warningLong(int msg, int iconRes) {
        waitingLong(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void warningLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_WAITING, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }


    @Override
    public void success(CharSequence msg) {
        success(msg, R.drawable.emotion_success);
    }

    @Override
    public void success(int msg) {
        success(AppUtil.getStringFromRes(msg));
    }


    @Override
    public void success(int msg, int iconRes) {
        success(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void success(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_SUCCESS, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void successLong(CharSequence msg) {
        successLong(msg, R.drawable.emotion_success);
    }

    @Override
    public void successLong(int msg) {
        successLong(AppUtil.getStringFromRes(msg));
    }


    @Override
    public void successLong(int msg, int iconRes) {
        successLong(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void successLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_SUCCESS, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }


    @Override
    public void error(CharSequence msg) {
        error(msg, R.drawable.emotion_error);
    }

    @Override
    public void error(int msg) {
        error(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void error(int msg, int iconRes) {
        error(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void error(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_ERROR, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void errorLong(CharSequence msg) {
        errorLong(msg, R.drawable.emotion_error);
    }


    @Override
    public void errorLong(int msg) {
        errorLong(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void errorLong(int msg, int iconRes) {
        errorLong(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void errorLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_ERROR, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }


    @Override
    public void fail(CharSequence msg) {
        fail(msg, R.drawable.emotion_fail);
    }


    @Override
    public void fail(int msg) {
        fail(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void fail(int msg, int iconRes) {
        fail(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void fail(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_FAIL, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void failLong(CharSequence msg) {
        failLong(msg, R.drawable.emotion_fail);
    }


    @Override
    public void failLong(int msg) {
        failLong(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void failLong(int msg, int iconRes) {
        failLong(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void failLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_FAIL, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }


    @Override
    public void complete(CharSequence msg) {
        complete(msg, R.drawable.emotion_complete);
    }

    @Override
    public void complete(int msg) {
        complete(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void complete(int msg, int iconRes) {
        complete(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void complete(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_COMPLETE, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void completeLong(CharSequence msg) {
        completeLong(msg, R.drawable.emotion_complete);
    }

    @Override
    public void completeLong(int msg) {
        completeLong(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void completeLong(int msg, int iconRes) {
        completeLong(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void completeLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_COMPLETE, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void forbid(CharSequence msg) {
        forbid(msg, R.drawable.emotion_forbid);
    }

    @Override
    public void forbid(int msg) {
        forbid(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void forbid(int msg, int iconRes) {
        forbid(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void forbid(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_FORBID, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void forbidLong(CharSequence msg) {
        forbidLong(msg, R.drawable.emotion_forbid);
    }


    @Override
    public void forbidLong(int msg) {
        forbidLong(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void forbidLong(int msg, int iconRes) {
        forbidLong(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void forbidLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_FORBID, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }


    @Override
    public void waiting(CharSequence msg) {
        waiting(msg, R.drawable.emotion_waiting);
    }

    @Override
    public void waiting(int msg) {
        waiting(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void waiting(int msg, int iconRes) {
        waiting(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void waiting(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_WAITING, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void waitingLong(CharSequence msg) {
        waitingLong(msg, R.drawable.emotion_waiting);
    }

    @Override
    public void waitingLong(int msg) {
        waitingLong(AppUtil.getStringFromRes(msg));
    }

    @Override
    public void waitingLong(int msg, int iconRes) {
        waitingLong(AppUtil.getStringFromRes(msg), iconRes);
    }

    @Override
    public void waitingLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_WAITING, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }
}
