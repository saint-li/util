package com.saint.util.util.toast;


import com.saint.util.UtilConfig;

/**
 * Created by 朱志强 on 2017/11/14.
 */

public final class AppToast {

    static {
        UtilConfig.setToastCallback(new ToastCallback());
    }


    private AppToast() {

    }


    public static void tShort(CharSequence content) {
        darkInCenter(content);
    }

    public static void tShort(int contentRes) {
        darkInCenter(contentRes);
    }

    public static void tLong(CharSequence content) {
        darkLongInCenter(content);
    }

    public static void tLong(int contentRes) {
        darkLongInCenter(contentRes);
    }


    public static void show(CharSequence msg) {
        ToastDelegate.get().getDefaultToastVariety().show(msg);
    }

    public static void show(int msg) {
        ToastDelegate.get().getDefaultToastVariety().show(msg);
    }

    public static void showAtTop(CharSequence msg) {
        ToastDelegate.get().getDefaultToastVariety().showAtTop(msg);
    }

    public static void showAtTop(int msg) {
        ToastDelegate.get().getDefaultToastVariety().showAtTop(msg);
    }


    public static void showInCenter(CharSequence msg) {
        ToastDelegate.get().getDefaultToastVariety().showInCenter(msg);
    }


    public static void showInCenter(int msg) {
        ToastDelegate.get().getDefaultToastVariety().showInCenter(msg);
    }


    public static void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        ToastDelegate.get().getDefaultToastVariety().showAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void showAtLocation(int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        ToastDelegate.get().getDefaultToastVariety().showAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void showLong(CharSequence msg) {
        ToastDelegate.get().getDefaultToastVariety().showLong(msg);
    }


    public static void showLong(int msg) {
        ToastDelegate.get().getDefaultToastVariety().showLong(msg);
    }


    public static void showLongAtTop(CharSequence msg) {
        ToastDelegate.get().getDefaultToastVariety().showLongAtTop(msg);
    }


    public static void showLongAtTop(int msg) {
        ToastDelegate.get().getDefaultToastVariety().showLongAtTop(msg);
    }


    public static void showLongInCenter(CharSequence msg) {
        ToastDelegate.get().getDefaultToastVariety().showLongInCenter(msg);
    }


    public static void showLongInCenter(int msg) {
        ToastDelegate.get().getDefaultToastVariety().showLongInCenter(msg);
    }


    public static void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        ToastDelegate.get().getDefaultToastVariety().showLongAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }

    public static void showLongAtLocation(int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        ToastDelegate.get().getDefaultToastVariety().showLongAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }

    public static void dark(CharSequence msg) {
        ToastDelegate.get().getDarkToastVariety().show(msg);
    }

    public static void dark(int msg) {
        ToastDelegate.get().getDarkToastVariety().show(msg);
    }

    public static void darkAtTop(CharSequence msg) {
        ToastDelegate.get().getDarkToastVariety().showAtTop(msg);
    }

    public static void darkAtTop(int msg) {
        ToastDelegate.get().getDarkToastVariety().showAtTop(msg);
    }


    public static void darkInCenter(CharSequence msg) {
        ToastDelegate.get().getDarkToastVariety().showInCenter(msg);
    }


    public static void darkInCenter(int msg) {
        ToastDelegate.get().getDarkToastVariety().showInCenter(msg);
    }


    public static void darkAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        ToastDelegate.get().getDarkToastVariety().showAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void darkAtLocation(int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        ToastDelegate.get().getDarkToastVariety().showAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void darkLong(CharSequence msg) {
        ToastDelegate.get().getDarkToastVariety().showLong(msg);
    }


    public static void darkLong(int msg) {
        ToastDelegate.get().getDarkToastVariety().showLong(msg);
    }


    public static void darkLongAtTop(CharSequence msg) {
        ToastDelegate.get().getDarkToastVariety().showLongAtTop(msg);
    }


    public static void darkLongAtTop(int msg) {
        ToastDelegate.get().getDarkToastVariety().showLongAtTop(msg);
    }


    public static void darkLongInCenter(CharSequence msg) {
        ToastDelegate.get().getDarkToastVariety().showLongInCenter(msg);
    }


    public static void darkLongInCenter(int msg) {
        ToastDelegate.get().getDarkToastVariety().showLongInCenter(msg);
    }


    public static void darkLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        ToastDelegate.get().getDarkToastVariety().showLongAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }

    public static void darkLongAtLocation(int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        ToastDelegate.get().getDarkToastVariety().showLongAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void info(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().info(msg);
    }


    public static void info(int msg) {
        ToastDelegate.get().getEmotionToastVariety().info(msg);
    }


    public static void info(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().info(msg, iconRes);
    }


    public static void info(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().info(msg, iconRes);
    }


    public static void infoLong(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().infoLong(msg);
    }


    public static void infoLong(int msg) {
        ToastDelegate.get().getEmotionToastVariety().infoLong(msg);
    }


    public static void infoLong(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().infoLong(msg, iconRes);
    }


    public static void infoLong(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().infoLong(msg, iconRes);
    }


    public static void warning(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().warning(msg);
    }


    public static void warning(int msg) {
        ToastDelegate.get().getEmotionToastVariety().warning(msg);
    }


    public static void warning(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().warning(msg, iconRes);
    }


    public static void warning(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().warning(msg, iconRes);
    }


    public static void warningLong(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().warningLong(msg);
    }


    public static void warningLong(int msg) {
        ToastDelegate.get().getEmotionToastVariety().warningLong(msg);
    }


    public static void warningLong(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().warningLong(msg, iconRes);
    }


    public static void warningLong(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().warningLong(msg, iconRes);
    }


    public static void success(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().success(msg);
    }


    public static void success(int msg) {
        ToastDelegate.get().getEmotionToastVariety().success(msg);
    }


    public static void success(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().success(msg, iconRes);
    }


    public static void success(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().success(msg, iconRes);
    }


    public static void successLong(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().successLong(msg);
    }


    public static void successLong(int msg) {
        ToastDelegate.get().getEmotionToastVariety().successLong(msg);
    }


    public static void successLong(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().successLong(msg, iconRes);
    }


    public static void successLong(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().successLong(msg, iconRes);
    }


    public static void error(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().error(msg);
    }


    public static void error(int msg) {
        ToastDelegate.get().getEmotionToastVariety().error(msg);
    }


    public static void error(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().error(msg, iconRes);
    }


    public static void error(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().error(msg, iconRes);
    }


    public static void errorLong(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().errorLong(msg);
    }


    public static void errorLong(int msg) {
        ToastDelegate.get().getEmotionToastVariety().errorLong(msg);
    }


    public static void errorLong(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().errorLong(msg, iconRes);
    }


    public static void errorLong(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().errorLong(msg, iconRes);
    }


    public static void fail(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().fail(msg);
    }


    public static void fail(int msg) {
        ToastDelegate.get().getEmotionToastVariety().fail(msg);
    }


    public static void fail(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().fail(msg, iconRes);
    }


    public static void fail(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().fail(msg, iconRes);
    }


    public static void failLong(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().failLong(msg);
    }


    public static void failLong(int msg) {
        ToastDelegate.get().getEmotionToastVariety().failLong(msg);
    }


    public static void failLong(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().failLong(msg, iconRes);
    }


    public static void failLong(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().failLong(msg, iconRes);
    }


    public static void complete(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().complete(msg);
    }


    public static void complete(int msg) {
        ToastDelegate.get().getEmotionToastVariety().complete(msg);
    }


    public static void complete(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().complete(msg, iconRes);
    }


    public static void complete(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().complete(msg, iconRes);
    }

    public static void completeLong(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().completeLong(msg);
    }


    public static void completeLong(int msg) {
        ToastDelegate.get().getEmotionToastVariety().completeLong(msg);
    }


    public static void completeLong(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().completeLong(msg, iconRes);
    }


    public static void completeLong(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().completeLong(msg, iconRes);
    }


    public static void forbid(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().forbid(msg);
    }


    public static void forbid(int msg) {
        ToastDelegate.get().getEmotionToastVariety().forbid(msg);
    }


    public static void forbid(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().forbid(msg, iconRes);
    }

    public static void forbid(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().forbid(msg, iconRes);
    }


    public static void forbidLong(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().forbidLong(msg);
    }

    public static void forbidLong(int msg) {
        ToastDelegate.get().getEmotionToastVariety().forbidLong(msg);
    }


    public static void forbidLong(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().forbidLong(msg, iconRes);
    }


    public static void forbidLong(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().forbidLong(msg, iconRes);
    }


    public static void waiting(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().waiting(msg);
    }


    public static void waiting(int msg) {
        ToastDelegate.get().getEmotionToastVariety().waiting(msg);
    }


    public static void waiting(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().waiting(msg, iconRes);
    }


    public static void waiting(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().waiting(msg, iconRes);
    }


    public static void waitingLong(CharSequence msg) {
        ToastDelegate.get().getEmotionToastVariety().waitingLong(msg);
    }


    public static void waitingLong(int msg) {
        ToastDelegate.get().getEmotionToastVariety().waitingLong(msg);
    }


    public static void waitingLong(int msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().waitingLong(msg, iconRes);
    }


    public static void waitingLong(CharSequence msg, int iconRes) {
        ToastDelegate.get().getEmotionToastVariety().waitingLong(msg, iconRes);
    }


    public static ITextShow get(int tag) {
        return ToastDelegate.get().getToastVarietyByTag(tag);
    }


    public static <T> T variety(int tag) {

        return ToastDelegate.get().getToastVarietyByTag(tag);
    }


    public static boolean isShowing() {
        return ToastDelegate.hasCreated() && ToastDelegate.get().isShowing();
    }

    public static void dismiss() {
        if (ToastDelegate.hasCreated()) {
            ToastDelegate.get().dismiss();
        }
    }


    public static ISetting setting() {
        return ToastDelegate.get().setting();
    }
}
