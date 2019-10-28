package com.saint.util.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.saint.util.R;
import com.saint.util.listener.RequestPermissionBack;
import com.saint.util.other.RuntimeRationale;
import com.saint.util.util.toast.AppToast;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class PermissionUtil {

    public static void request(Context context, @NonNull RequestPermissionBack back, String... permissions) {
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(back::onSuccess)
                .onDenied(back::onFailed)
                .start();
    }

    public static void request(Activity activity, @NonNull RequestPermissionBack back, String... permissions) {
        AndPermission.with(activity)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(back::onSuccess)
                .onDenied(back::onFailed)
                .start();
    }

    public static void request(Fragment fragment, @NonNull RequestPermissionBack back, String... permissions) {
        AndPermission.with(fragment)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(back::onSuccess)
                .onDenied(back::onFailed)
                .start();
    }

    /**
     * Display setting dialog.
     */
    public static void showSettingDialog(Activity context, List<String> permissions, int requestCode) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed,
                TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context).setCancelable(false)
                .setTitle(R.string.tips)
                .setMessage(message)
                .setPositiveButton(R.string.setting, (dialog, which) -> {
                    Uri packageURI = Uri.parse("package:" + context.getPackageName());
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivityForResult(intent, requestCode);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    AppToast.tLong(R.string.no_permission_tips);
                })
                .show();
    }
}
