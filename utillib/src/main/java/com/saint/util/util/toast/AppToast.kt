package com.saint.util.util.toast

import android.view.Gravity
import com.hjq.toast.Toaster
import com.saint.util.UtilConfig
import com.saint.util.util.AppUtil

/**
 *
 */
object AppToast {

    init {
        if (!Toaster.isInit()) {
            Toaster.init(UtilConfig.getApp())
        }
    }

    fun show(content: CharSequence?) {
        Toaster.show(content)
    }

    fun show(contentRes: Int) {
        Toaster.show(contentRes)
    }

    fun tShort(content: CharSequence?) {
        Toaster.showShort(content)
    }

    fun tShort(contentRes: Int) {
        Toaster.showShort(contentRes)
    }

    fun tLong(content: CharSequence?) {
        Toaster.showLong(content)
    }

    fun tLong(contentRes: Int) {
        Toaster.showLong(contentRes)
    }

    fun showAtTop(msg: CharSequence?) {
        Toaster.setGravity(Gravity.TOP, 0, 0)
        Toaster.show(msg)
    }

    fun showAtTop(msg: Int) {
        Toaster.setGravity(Gravity.TOP, 0, 0)
        Toaster.show(msg)
    }

    fun showInCenter(msg: CharSequence?) {
        Toaster.setGravity(Gravity.CENTER, 0, 0)
        Toaster.show(msg)
    }

    fun showInCenter(msg: Int) {
        Toaster.setGravity(Gravity.CENTER, 0, 0)
        Toaster.show(msg)
    }

    fun showAtLocation(msg: CharSequence?, gravity: Int, xOffsetDp: Float, yOffsetDp: Float) {
        Toaster.setGravity(gravity, AppUtil.dpToPx(xOffsetDp), AppUtil.dpToPx(yOffsetDp))
        Toaster.show(msg)
    }

    fun showAtLocation(msg: Int, gravity: Int, xOffsetDp: Float, yOffsetDp: Float) {
        Toaster.setGravity(gravity, AppUtil.dpToPx(xOffsetDp), AppUtil.dpToPx(yOffsetDp))
        Toaster.show(msg)
    }
}