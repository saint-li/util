package com.saint.util.util.toast;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;

import com.saint.util.R;
import com.saint.util.util.ActivityUtil;
import com.saint.util.util.AppLog;
import com.saint.util.util.AppUtil;

final class VirtualToastManager {
    public static final int DURATION_SHORT = 2000;
    public static final int DURATION_LONG = 3500;

    private Dialog mVirtualToastDialog;
    private Activity mHostActivity;
    private Handler mDismissHandler = new Handler();

    public void show(Toast toast, WindowManager.LayoutParams windowParams) {
        Activity activity = ActivityUtil.getInstance().getTop();
        if (!AppUtil.isUpdateActivityUIPermitted(activity)) {
            AppLog.d("activity is can not show virtual toast dialog ,so do nothing but return.");
            return;
        }
        boolean hostActivityChanged = mHostActivity != activity;
        mHostActivity = activity;
        recreateVirtualToastDialog(hostActivityChanged);

        mVirtualToastDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mVirtualToastDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        mVirtualToastDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        mVirtualToastDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        WindowManager.LayoutParams lp = mVirtualToastDialog.getWindow().getAttributes();
        lp.width = windowParams.width;
        lp.height = windowParams.height;
        lp.windowAnimations = windowParams.windowAnimations;
        lp.gravity = toast.getGravity();
        lp.x = toast.getXOffset();
        lp.y = toast.getYOffset();
        ViewGroup content = mVirtualToastDialog.findViewById(android.R.id.content);
        if (toast.getView().getParent() != content) {
            if (toast.getView().getParent() != null) {
                ViewGroup parent = (ViewGroup) toast.getView().getParent();
                parent.removeView(toast.getView());
            }
            content.removeAllViews();
            mVirtualToastDialog.setContentView(toast.getView());
        }
        try {
            mVirtualToastDialog.show();
        } catch (WindowManager.BadTokenException e) {
            AppLog.e("bad token has happened when show virtual toast!");
            mHostActivity = null;
        }
        mDismissHandler.removeCallbacks(mDismissToastRunnable);
        mDismissHandler.postDelayed(mDismissToastRunnable, toast.getDuration() == Toast.LENGTH_SHORT ?
                DURATION_SHORT : DURATION_LONG);
    }


    public void recreateVirtualToastDialog(boolean hostActivityChanged) {
        if (mVirtualToastDialog == null || hostActivityChanged) {
            mVirtualToastDialog = new AppCompatDialog(mHostActivity, R.style.smart_show_virtual_toast_dialog);
            AppLog.d("virtual toast dialog" + AppUtil.getObjectDesc(mVirtualToastDialog) + "has created");
        }
    }


    private Runnable mDismissToastRunnable = new Runnable() {
        @Override
        public void run() {
            dismiss();
        }
    };


    public void dismiss() {
        if (isShowing()) {
            mVirtualToastDialog.dismiss();
        }
    }

    public boolean isShowing() {
        return mVirtualToastDialog != null && mVirtualToastDialog.isShowing();
    }

    public void destroy(Activity activity) {
        if (mHostActivity == activity) {
            AppLog.d("recycle resource when host activity" + AppUtil.getObjectDesc(activity) + "of virtual toast destroyed");
            mHostActivity = null;
            dismiss();
            mVirtualToastDialog = null;
        }
    }

    private static VirtualToastManager sVirtualToastManager;

    public static VirtualToastManager get() {
        if (sVirtualToastManager == null) {
            sVirtualToastManager = new VirtualToastManager();
            AppLog.d("create virtual toast manager");
        }
        return sVirtualToastManager;
    }

    private VirtualToastManager() {

    }

    public static boolean hasCreated() {
        return sVirtualToastManager != null;
    }
}
