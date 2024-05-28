package com.saint.utillib

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.saint.util.base.BaseAct
import com.saint.util.listener.OnItemCLick
import com.saint.util.util.AppUtil
import com.saint.util.util.clearCache
import com.saint.util.util.toast.AppToast
import com.saint.utillib.act.BaseBindingAct
import com.saint.utillib.act.SImageViewAct
import com.saint.utillib.act.SleepViewAct
import com.saint.utillib.databinding.ActivityMainBinding
import com.saint.utillib.gloading.GLoadingAct
import com.saint.utillib.fragkotlin.KotlinTestAct
import com.saint.utillib.time.TimeTestAct
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener


class MainActivity : BaseBindingAct<ActivityMainBinding>(), OnItemCLick {

    var adapter: MainAdapter? = null

    override fun initTitleView() {
        mActionBar = findViewById(R.id.my_action_bar)
        mActionBar.setTitle("阿斯顿发地方阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬阿斯蒂芬")
    }

    override fun initView() {
        binding.smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                refreshLayout.finishLoadMore()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                refreshLayout.finishRefresh()
            }

        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        adapter = MainAdapter(act)
        binding.recyclerView.adapter = adapter
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
                if (AppUtil.isInstall("com.xuwuji.tudingparent")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tudingparent://pushaction"))
                    startActivity(intent)
                } else {
                    AppToast.tShort("未安装兔盯儿App")
                }
            }
            9 -> {
                if (clearCache(this)) {
                    AppToast.tShort("清除缓存成功")
                    adapter?.notifyDataSetChanged()
                }

            }
            10 -> {
//                showAct(PicTestAct::class.java)
            }
            11 -> {
                showAct(SleepViewAct::class.java)
            }
            12 -> {
                showAct(SImageViewAct::class.java)
            }

        }
    }


}
