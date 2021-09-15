package com.saint.utillib.gloading

import android.os.Bundle
import com.saint.util.base.BaseFrag
import com.saint.util.util.toast.AppToast
import com.saint.utillib.R
import com.saint.widget.GLoading

class EmptyFrag : BaseFrag() {
    private var holder: GLoading.Holder? = null
    override fun setLayout(): Int {
        return R.layout.frag_gloading_empty
    }

    override fun initData(savedInstanceState: Bundle?) {
        holder = GLoading.getDefault().wrap(rootView).withRetry {
            AppToast.tShort("重试点击-----")
        }
        holder!!.showLoading()
        rootView.postDelayed({
            holder!!.showEmpty()
        }, 1000)
    }
}