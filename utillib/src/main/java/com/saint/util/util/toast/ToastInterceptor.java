package com.saint.util.util.toast;

import android.widget.Toast;

/**
 * desc   : Toast 默认拦截器
 */
public class ToastInterceptor implements IToastInterceptor {

    @Override
    public boolean intercept(Toast toast, CharSequence text) {
        // 如果是空对象或者空文本就进行拦截
        return text == null || "".equals(text.toString());
    }
}