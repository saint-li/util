package com.saint.utillib.act

import android.view.View
import androidx.viewbinding.ViewBinding
import com.saint.util.base.BaseAct
import com.saint.utillib.inflateBindingWithGeneric

abstract class BaseBindingAct<VB : ViewBinding> : BaseAct() {
    protected lateinit var binding: VB
    override fun setRootView(): View {
        binding = inflateBindingWithGeneric(layoutInflater)
        return binding.root
    }
}