package com.saint.util.binding

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.saint.util.loading.LoadingHelper

/**
 * @author Saint  2021/4/23 13:43
 * @DESC: 基类 -- 兼具骨架屏加载及ViewBinding
 */
abstract class BaseLoadBindingAct<VB : ViewBinding> : BaseBindingAct<VB>() {
    protected lateinit var loadHelper: LoadingHelper

    override fun initData(savedInstanceState: Bundle?) {
        loadHelper = LoadingHelper(binding.root)
        loadHelper.setOnReloadListener { onReload() }
        initData()
        onReload()
    }

    protected abstract fun initData()

    protected abstract fun onReload()
}