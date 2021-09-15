package com.saint.utillib.gloading

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.saint.util.base.BaseAct
import com.saint.utillib.R
import kotlinx.android.synthetic.main.act_toast.*

class GLoadingAct : BaseAct() {
    private val frags = ArrayList<Fragment>()

    override fun setLayout(): Int {
        return R.layout.act_loading
    }

    override fun initTitleView() {
        my_action_bar.setTitle("GLoading测试")
        my_action_bar.setLeftListener { finish() }
    }

    override fun initData(savedInstanceState: Bundle?) {
        frags.add(WrapRootViewFragment())
        frags.add(EmptyFrag())
        onTabSelect(1)
    }

    fun onTabSelect(position: Int) {
        if (frags.isEmpty() || position >= frags.size) return
        val mTransaction = supportFragmentManager.beginTransaction()
        hideAllFragment(mTransaction)
        val fragment =
            supportFragmentManager.findFragmentByTag(frags[position].javaClass.simpleName)
        if (fragment == null) {
            mTransaction.add(
                R.id.fragment_container,
                frags[position],
                frags[position].javaClass.simpleName
            )
        }
        mTransaction.show(frags[position])
        mTransaction.commit()
    }

    /**
     * 隐藏fragment
     *
     * @param transaction
     */
    private fun hideAllFragment(transaction: FragmentTransaction) {
        if (frags.isNotEmpty()) {
            for (fragment in frags) {
                transaction.hide(fragment)
            }
        }
    }

    fun btn1(v: View) {
        onTabSelect(0)
    }

    fun btn2(v: View) {
        onTabSelect(1)
    }

    fun btn3(v: View) {

    }
}