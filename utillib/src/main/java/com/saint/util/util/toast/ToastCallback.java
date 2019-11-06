package com.saint.util.util.toast;


import android.app.Activity;

import com.saint.util.lifecycle.IToastCallback;


final class ToastCallback implements IToastCallback {
    @Override
    public void dismissOnLeave() {
        if (ToastDelegate.hasCreated() && ToastDelegate.get().isDismissOnLeave()) {
            ToastDelegate.get().dismiss();
        }
    }

    @Override
    public void recycleOnDestroy(Activity activity) {
        if (VirtualToastManager.hasCreated()){
            VirtualToastManager.get().destroy(activity);
        }
    }
}
