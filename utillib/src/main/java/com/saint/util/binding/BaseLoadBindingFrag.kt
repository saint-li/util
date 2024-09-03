package com.saint.util.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.saint.util.loading.LoadingHelper

abstract class BaseLoadBindingFrag<VB : ViewBinding> : BaseBindingFrag<VB>() {

    protected lateinit var loadHelper: LoadingHelper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = inflateBindingWithGeneric(layoutInflater, container, false)
        loadHelper = LoadingHelper(binding.root)
        return loadHelper.decorView
    }

    override fun initData(savedInstanceState: Bundle?) {
        loadHelper.setOnReloadListener { onReload() }
        initData()
        onReload()
    }

    protected abstract fun onReload()
    protected open fun initData() {}

}