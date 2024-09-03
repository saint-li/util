package com.saint.util.loading

import android.app.Activity
import android.view.View
import android.view.View.OnClickListener
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.saint.util.R

/**
 * @author Dylan Cai
 */
object ToolbarHelper {
    fun setToolbar(activity: Activity, title: String?, leftClick: OnClickListener): LoadingHelper {
        val loadingHelper = LoadingHelper(activity)
        loadingHelper.register(ViewType.TITLE, ToolbarAdapter(title, leftClick))
        loadingHelper.setDecorHeader(ViewType.TITLE)
        return loadingHelper
    }

    fun setToolbar(
        activity: Activity,
        title: String,
        @ColorRes barColor: Int,
        onLeftClick: OnClickListener,
        @StringRes rightText: Int,
        @DrawableRes rightRes: Int,
        onRightClick: OnClickListener
    ): LoadingHelper {
        val loadingHelper = LoadingHelper(activity)
        loadingHelper.register(ViewType.TITLE, ToolbarAdapter(title, barColor, onLeftClick, rightText, rightRes, onRightClick))
        loadingHelper.setDecorHeader(ViewType.TITLE)
        return loadingHelper
    }

    fun setToolbar(
        rootView: View,
        title: String, @ColorRes barColor: Int,
        onLeftClick: OnClickListener,
        @StringRes rightText: Int,
        @DrawableRes rightRes: Int,
        onRightClick: OnClickListener
    ): LoadingHelper {
        val loadingHelper = LoadingHelper(rootView)
        loadingHelper.register(ViewType.TITLE, ToolbarAdapter(title, barColor, R.drawable.return_icon, onLeftClick, rightText, rightRes, onRightClick))
        loadingHelper.setDecorHeader(ViewType.TITLE)
        return loadingHelper
    }

    fun setToolbar(
        rootView: View,
        title: String,
        @ColorRes barColor: Int,
        @DrawableRes leftRes: Int,
        onLeftClick: OnClickListener?,
        @StringRes rightText: Int,
        @DrawableRes rightRes: Int,
        onRightClick: OnClickListener
    ): LoadingHelper {
        val loadingHelper = LoadingHelper(rootView)
        loadingHelper.register(ViewType.TITLE, ToolbarAdapter(title, barColor, onLeftClick, rightText, rightRes, onRightClick))
        loadingHelper.setDecorHeader(ViewType.TITLE)
        return loadingHelper
    }

    fun setScrollingToolbar(rootView: View, titleBarView: View): LoadingHelper {
        val loadingHelper = LoadingHelper(rootView);
        loadingHelper.register(ViewType.TITLE, CustomToolbarAdapter(titleBarView))
        loadingHelper.setDecorHeader(ViewType.TITLE)
        return loadingHelper;
    }
}