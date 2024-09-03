package com.saint.util.binding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.saint.util.base.BaseAct
import com.saint.util.databinding.LoadingLayoutActFloatTitleBinding
import com.saint.util.loading.LoadingHelper
import com.saint.util.loading.ToolbarAdapter
import com.saint.util.loading.ToolbarHelper
import com.saint.util.loading.ViewType

/**
 * @author Saint  2021/4/23 13:43
 * @DESC: 基类 -- 兼具骨架屏加载及ViewBinding
 */
abstract class BaseLoadTitleBindingAct<VB : ViewBinding> : BaseAct() {
    protected lateinit var loadHelper: LoadingHelper
    protected lateinit var binding: VB

    override fun setRootView(): View {
        binding = inflateBindingWithGeneric(layoutInflater)
        if (titleFloating()) {
            val baseBinding = LoadingLayoutActFloatTitleBinding.inflate(layoutInflater)
            baseBinding.loadingBaseActContainer.addView(binding.root)
            return baseBinding.root
        }
        return binding.root
    }

    override fun initData(savedInstanceState: Bundle?) {
        loadHelper = ToolbarHelper.setToolbar(
            binding.root,
            setTitle(),
            setTitleBg(),
            { onBackPressed() },
            setRightText(),
            setRightIcon()
        ) { onRightClick() }
        loadHelper.setOnReloadListener { onReload() }
        initData()
        onReload()
    }

    protected abstract fun setTitle(): String

    protected open fun initData() {}

    protected open fun onReload() {}

    protected fun titleFloating(): Boolean {
        return false
    }

    protected open fun setTitleBg(): Int {
        return 0
    }

    protected open fun setRightText(): Int {
        return 0
    }

    protected open fun setRightIcon(): Int {
        return 0
    }

    protected open fun onRightClick() {}

    protected fun setTitle(title: String) {
        val toolbar = loadHelper.getAdapter<ToolbarAdapter>(ViewType.TITLE)
        if (toolbar != null) toolbar.setTitle(title)
    }

    protected fun setRightListener(
        @DrawableRes ivRight: Int,
        onRight: View.OnClickListener? = null
    ) {
        val toolbar = loadHelper.getAdapter<ToolbarAdapter>(ViewType.TITLE)
        if (toolbar != null) toolbar.setRightListener(ivRight, onRight)
    }

    protected fun setRightTextListener(
        @StringRes tvRightStr: Int,
        onRight: View.OnClickListener? = null
    ) {
        val toolbar = loadHelper.getAdapter<ToolbarAdapter>(ViewType.TITLE)
        if (toolbar != null) toolbar.setRightTextListener(tvRightStr, onRight)
    }

    fun showAct(clazz: Class<*>, key: String, value: Int) {
        val intent = Intent(act, clazz)
        intent.putExtra(key, value)
        act.startActivity(intent)
    }
}