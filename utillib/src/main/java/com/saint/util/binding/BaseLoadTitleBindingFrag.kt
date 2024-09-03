package com.saint.util.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.saint.util.base.BaseFrag
import com.saint.util.loading.LoadingHelper
import com.saint.util.loading.ToolbarAdapter
import com.saint.util.loading.ToolbarHelper
import com.saint.util.loading.ViewType

/**
 * @author Saint  2021/4/23 13:43
 * @DESC: 基类 -- 兼具骨架屏加载及ViewBinding
 */
abstract class BaseLoadTitleBindingFrag<VB : ViewBinding> : BaseFrag() {
    protected lateinit var loadHelper: LoadingHelper
    protected lateinit var binding: VB
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = inflateBindingWithGeneric(layoutInflater, container, false)
        loadHelper = ToolbarHelper.setToolbar(binding.root, setTitle(), setTitleBg(), setLeftIcon(), null, setRightText(), setRightIcon()) {
            onRightClick()
        }
        return loadHelper.decorView
    }

    override fun initData(savedInstanceState: Bundle?) {
        loadHelper.setOnReloadListener { onReload() }
        onReload()
    }


    protected abstract fun setTitle(): String

    protected abstract fun onReload()

    protected open fun setTitleBg(): Int {
        return 0
    }

    protected open fun setRightText(): Int {
        return 0
    }

    protected open fun setRightIcon(): Int {
        return 0
    }

    protected open fun setLeftIcon(): Int {
        return 0
    }

    protected open fun onRightClick() {}

    fun setTitle(title: String) {
        val toolbar = loadHelper.getAdapter<ToolbarAdapter>(ViewType.TITLE)
        if (toolbar != null) toolbar.setTitle(title)
    }
}