package com.saint.util.util.toast;

import android.util.SparseArray;
import android.widget.Toast;

import com.saint.util.UtilConfig;


class ToastCache {
    private static ToastCache sToastCache;
    private SparseArray<AbstractToastVariety> mToastVarieties;
    private IToastProvider mToastProvider;

    private ToastCache() {

    }

    public static ToastCache get() {
        if (sToastCache == null) {
            sToastCache = new ToastCache();
        }

        return sToastCache;
    }


    private SparseArray<AbstractToastVariety> getToastTagContainer() {
        if (mToastVarieties == null) {
            mToastVarieties = new SparseArray<>(4);
        }
        return mToastVarieties;
    }


    private boolean isToastVarietyCacheEmpty() {
        return mToastVarieties == null || mToastVarieties.size() == 0;
    }

    public void setToastProvider(IToastProvider toastProvider) {
        mToastProvider = toastProvider;
    }

    public AbstractToastVariety retrieveToastTagFromCache(final int toastTag) {
        AbstractToastVariety toastVariety = getToastTagContainer().get(toastTag);
        if (toastVariety == null) {
            switch (toastTag) {
                case ToastTags.TOAST_TAG_SRC:
                    toastVariety = new SrcToastVariety();
                    break;
                case AbstractToastVariety.TOAST_TAG_EMOTION:
                    int color = ToastDelegate.get().hasSetting() ? ToastDelegate.get().setting().getEmotionToastThemeColor() : ISetting.DEFAULT_EMOTION_TOAST_THEME_COLOR;
                    toastVariety = new EmotionToastVariety(color);
                    break;
                case AbstractToastVariety.TOAST_TAG_DARK:
                    int color1 = ToastDelegate.get().hasSetting() ? ToastDelegate.get().setting().getEmotionToastThemeColor() : ISetting.DEFAULT_EMOTION_TOAST_THEME_COLOR;
                    toastVariety = new DarkToastVariety(color1);
                    break;
                default:
                    toastVariety = new DefaultToastVariety(toastTag) {
                        @Override
                        protected Toast createToast() {
                            return mToastProvider.createCustomToast(toastTag, UtilConfig.getApp());
                        }
                    };
                    break;
            }
            toastVariety.setShowCallback(ToastDelegate.get());
            getToastTagContainer().put(toastTag, toastVariety);
        }

        return toastVariety;
    }


}
