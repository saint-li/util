package com.saint.util.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.saint.util.base.BaseFrag

abstract class BaseBindingFrag<VB : ViewBinding> : BaseFrag() {
    protected lateinit var binding: VB
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = inflateBindingWithGeneric(layoutInflater, container, false)
        return binding.root
    }
}