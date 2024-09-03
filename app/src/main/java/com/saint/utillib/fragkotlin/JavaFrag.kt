package com.saint.utillib.fragkotlin

import android.os.Bundle
import android.widget.TextView
import com.saint.util.base.BaseFrag
import com.saint.util.binding.BaseBindingFrag
import com.saint.utillib.R
import com.saint.utillib.databinding.FragKotlinTestBinding

class JavaFrag : BaseBindingFrag<FragKotlinTestBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        binding.tvText.text = "***************"
    }
}
