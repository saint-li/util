package com.saint.util.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.fragment.app.Fragment;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.saint.util.UtilConfig;
import com.saint.util.listener.RequestPermissionBack;
import com.saint.util.util.toast.AppToast;

import java.util.List;

public class PermissionUtil {

    public static void request(Context context, @NonNull RequestPermissionBack back, @NonNull @RequiresPermission String... permissions) {
        XXPermissions.with(context)
                .permission(permissions)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        back.onSuccess(permissions, all);
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        back.onFailed(permissions, never);
                    }
                });
    }

    public static void request(Activity activity, @NonNull RequestPermissionBack back, @NonNull @RequiresPermission String... permissions) {
        XXPermissions.with(activity)
                .permission(permissions)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        back.onSuccess(permissions, all);
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        back.onFailed(permissions, never);
                    }
                });
    }

    public static void request(Fragment fragment, @NonNull RequestPermissionBack back, @NonNull @RequiresPermission String... permissions) {
        XXPermissions.with(fragment)
                .permission(permissions)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        back.onSuccess(permissions, all);
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        back.onFailed(permissions, never);
                    }
                });
    }

    /**
     * 判断一个或多个权限是否全部授予了
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasPermissions(Context context, @NonNull @RequiresPermission String... permissions) {
        return XXPermissions.isGranted(context, permissions);
    }

    /**
     * 获取没有授予的权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static List<String> getDenied(Context context, @NonNull @RequiresPermission String... permissions) {
        return XXPermissions.getDenied(context, permissions);
    }

    /**
     * 判断某个权限是否为特殊权限
     *
     * @param permission
     * @return
     */
    public static boolean isSpecial(@NonNull @RequiresPermission String permission) {
        return XXPermissions.isSpecial(permission);
    }

    /**
     * 显示设置提示框
     */
    public static void showSettingDialog(Context context, String msg) {
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle("提示")
                .setMessage(msg)
                .setPositiveButton("确定", (dialog1, which) -> {
                    Uri packageURI = Uri.parse("package:" + UtilConfig.getApp().getPackageName());
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//                    startActivityForResult(intent, PERMISSION_REQUEST);
                    UtilConfig.getApp().startActivity(intent);
                })
                .setNegativeButton("取消", (dialog12, which) -> {
                    AppToast.INSTANCE.tShort("取消获取权限");
                })
                .create();
        dialog.show();
    }
}
