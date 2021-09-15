package com.saint.utillib.fragkotlin

import android.os.Bundle
import com.saint.util.base.BaseFrag
import com.saint.utillib.R
import kotlinx.android.synthetic.main.frag_kotlin_test.*


class KotlinFrag : BaseFrag() {
    override fun setLayout(): Int {
        return R.layout.frag_kotlin_test
    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}