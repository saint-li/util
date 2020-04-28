package com.saint.utillib

import android.content.Intent
import android.net.Uri
import com.saint.util.base.BaseAct
import com.saint.util.listener.OnItemCLick
import com.saint.util.util.AppUtil
import com.saint.util.util.clearCache
import com.saint.util.util.toast.AppToast
import com.saint.utillib.gloading.GLoadingAct
import com.saint.utillib.fragkotlin.KotlinTestAct
import com.saint.utillib.time.TimeTestAct
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseAct(), OnItemCLick {

    var adapter: MainAdapter? = null

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
        adapter = MainAdapter(act)
        recycler_view.adapter = adapter
        adapter!!.setOnItemCLick(this)
    }

    override fun onItemClick(position: Int) {
        when (position) {
            0 -> showAct(ToastAct::class.java)
            1 -> showAct(ScanAct::class.java)
            2 -> showAct(NotificationAct::class.java)
            3 -> showAct(TimeTestAct::class.java)
            4 -> showAct(KotlinTestAct::class.java)
            5 -> showAct(GLoadingAct::class.java)
            6 -> {
                if (AppUtil.isInstall("com.xu5g.protection")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tudingprotection://splash"))
                    startActivity(intent)
                } else {
                    AppToast.tShort("未安装兔盯守护App")
                }
            }
            7 -> {
                if (AppUtil.isInstall("com.xu5g.ccba")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("ccser://splash"))
                    startActivity(intent)
                } else {
                    AppToast.tShort("未安装CCSERApp")
                }
            }
            8 -> {
                if (clearCache(this)) {
                    AppToast.tShort("清除缓存成功")
                    adapter?.notifyDataSetChanged()
                }

            }
        }
    }


}
