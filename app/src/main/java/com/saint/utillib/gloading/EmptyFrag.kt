package com.saint.utillib.gloading

import android.os.Bundle
import com.saint.util.base.BaseFrag
import com.saint.util.binding.BaseBindingFrag
import com.saint.util.util.toast.AppToast
import com.saint.utillib.R
import com.saint.utillib.databinding.FragGloadingEmptyBinding
import com.saint.widget.GLoading

class EmptyFrag : BaseBindingFrag<FragGloadingEmptyBinding>() {
    private var holder: GLoading.Holder? = null

    override fun initData(savedInstanceState: Bundle?) {
        holder = GLoading.getDefault().wrap(binding.root).withRetry {
            AppToast.tShort("重试点击-----")
        }
        holder!!.showLoading()
        binding.root.postDelayed({
            holder!!.showEmpty()
        }, 1000)
    }
}