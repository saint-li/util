package com.saint.util.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author Saint  2021/4/26 19:32
 * @DESC:
 */
abstract class BaseBindingQuickAdapter<T, VB : ViewBinding>(private val inflate: (LayoutInflater, ViewGroup, Boolean) -> VB, layoutResId: Int = -1) :
    BaseQuickAdapter<T, BaseBindingQuickAdapter.BaseBindingHolder>(layoutResId) {

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int) =
        BaseBindingHolder(inflate(LayoutInflater.from(parent.context), parent, false))

    class BaseBindingHolder(private val binding: ViewBinding) : BaseViewHolder(binding.root) {
        constructor(itemView: View) : this(ViewBinding { itemView })

        @Suppress("UNCHECKED_CAST")
        fun <VB : ViewBinding> getViewBinding() = binding as VB
    }

    fun pageData(data: List<T>,page:Int){
        if (page == 1) {
            setList(data)
        } else {
            addData(data)
        }
    }
}