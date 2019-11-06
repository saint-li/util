package com.saint.util.util.toast;

import android.widget.Toast;

import com.saint.util.UtilConfig;

import static com.saint.util.util.toast.ToastTags.TOAST_TAG_SRC;

final class SrcToastVariety extends DefaultToastVariety {
    public SrcToastVariety() {
        super(TOAST_TAG_SRC);
    }

    @Override
    protected Toast createToast() {
        return Toast.makeText(UtilConfig.getApp(), "", Toast.LENGTH_SHORT);
    }
}
