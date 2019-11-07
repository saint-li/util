package com.saint.utillib

import com.saint.util.base.BaseAct
import com.saint.util.listener.OnItemCLick
import com.saint.util.util.AppLog
import com.saint.util.util.toast.AppToast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseAct(), OnItemCLick {

    override fun setLayout(): Int {
        return R.layout.activity_main
    }

    override fun initTitleView() {
        mActionBar = findViewById(R.id.my_action_bar)
        mActionBar.setTitle(R.string.app_name)
    }

    override fun initView() {

    }

    override fun initData() {
        val adapter = MainAdapter(act)
        recycler_view.adapter = adapter
        adapter.setOnItemCLick(this)
    }

    override fun onItemClick(position: Int) {
        when (position) {
            0 -> showAct(ToastAct::class.java)
            1 -> showAct(ScanAct::class.java)

        }
    }


}
