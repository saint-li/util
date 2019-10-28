package com.saint.utillib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saint.util.UtilConfig
import com.saint.util.util.AppLog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UtilConfig.setIsDebug(true)
        AppLog.e("打印日志-----------")
    }
}
