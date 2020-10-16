package com.saint.util.util.toast

import android.view.Gravity
import android.widget.Toast
import com.saint.util.UtilConfig
import com.saint.util.util.AppUtil

/**
 *
 */
object AppToast {

    init {
        ToastUtils.init(UtilConfig.getApp())
    }

    fun show(content: CharSequence?) {
        ToastUtils.show(content)
    }

    fun show(contentRes: Int) {
        ToastUtils.show(contentRes)
    }

    fun tShort(content: CharSequence?) {
        ToastUtils.getToast().duration = Toast.LENGTH_SHORT
        ToastUtils.show(content)
    }

    fun tShort(contentRes: Int) {
        ToastUtils.getToast().duration = Toast.LENGTH_SHORT
        ToastUtils.show(contentRes)
    }

    fun tLong(content: CharSequence?) {
        ToastUtils.getToast().duration = Toast.LENGTH_LONG
        ToastUtils.show(content)
    }

    fun tLong(contentRes: Int) {
        ToastUtils.getToast().duration = Toast.LENGTH_LONG
        ToastUtils.show(contentRes)
    }

    fun showAtTop(msg: CharSequence?) {
        ToastUtils.setGravity(Gravity.TOP, 0, 0)
        ToastUtils.show(msg)
    }

    fun showAtTop(msg: Int) {
        ToastUtils.setGravity(Gravity.TOP, 0, 0)
        ToastUtils.show(msg)
    }

    fun showInCenter(msg: CharSequence?) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.show(msg)
    }

    fun showInCenter(msg: Int) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.show(msg)
    }

    fun showAtLocation(msg: CharSequence?, gravity: Int, xOffsetDp: Float, yOffsetDp: Float) {
        ToastUtils.setGravity(gravity, AppUtil.dpToPx(xOffsetDp), AppUtil.dpToPx(yOffsetDp))
        ToastUtils.show(msg)
    }

    fun showAtLocation(msg: Int, gravity: Int, xOffsetDp: Float, yOffsetDp: Float) {
        ToastUtils.setGravity(gravity, AppUtil.dpToPx(xOffsetDp), AppUtil.dpToPx(yOffsetDp))
        ToastUtils.show(msg)
    }
}