package com.saint.util.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.saint.util.R;


public class LoadingDialog {

    /*
     * 为了给应用创建统一样式的加载中提示框
     */

    private LoadingDialog() {
    }

    private static Dialog loadingDialog;
    private static Animation animation;

    // 单例
    public synchronized static Dialog show(Context context) {
        try {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (null == loadingDialog) {
                loadingDialog = new Dialog(context, R.style.loading_dialog); // 创建自定义样式dialog
                LayoutInflater inflater = LayoutInflater.from(context);
                View v = inflater.inflate(R.layout.dialog_loading, null); // 得到加载view
                LinearLayout layout = v.findViewById(R.id.dialog_view); // 加载布局
                ImageView spaceshipImage = v.findViewById(R.id.img);
//                animation = AnimationUtils.loadAnimation(context, R.anim.cirle); // 加载动画
//                animation.setInterpolator(new LinearInterpolator());
//                spaceshipImage.startAnimation(animation); // 使用ImageView显示动画

                Glide.with(context)
                        .asGif()
                        .load(R.drawable.loading)
                        .into(spaceshipImage);
                loadingDialog.setCancelable(false);// 不可以用"返回键"取消
                WindowManager.LayoutParams params = loadingDialog.getWindow().getAttributes();
                params.height = LayoutParams.MATCH_PARENT;
                params.gravity = Gravity.CENTER;

                loadingDialog.setContentView(layout, params);
            }
            loadingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        return loadingDialog;
    }

    public static void dismiss() {
        try {
            if (loadingDialog != null)
                loadingDialog.dismiss();
            loadingDialog = null;

            if (animation != null) {
                animation.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
