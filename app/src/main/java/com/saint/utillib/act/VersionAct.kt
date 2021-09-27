package com.saint.utillib.act

import com.saint.util.base.BaseAct
import com.saint.util.util.AppUtil
import com.saint.util.util.toast.AppToast
import com.saint.utillib.R
import kotlinx.android.synthetic.main.act_version_test.*

/**
 * @author Saint  2021/9/27 17:10
 * @DESC:
 */
class VersionAct : BaseAct() {
    override fun setLayout(): Int {
        return R.layout.act_version_test
    }

    override fun initData() {
        btnJudge.setOnClickListener { judge() }
    }

    private fun judge() {
        val newV = etNew.text.toString()
        val localV = etOld.text.toString()

        if (newV.isNullOrEmpty()) {
            AppToast.tShort("NewVersion is Empty")
            return
        }
        if (localV.isNullOrEmpty()) {
            AppToast.tShort("LocalVersion is Empty")
            return
        }

        val result = AppUtil.isNewVersionHigh(localV, newV)
        tvResult.text = result.toString()
    }
}