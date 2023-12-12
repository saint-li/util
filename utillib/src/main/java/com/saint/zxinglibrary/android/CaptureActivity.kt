package com.saint.zxinglibrary.android

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.zxing.Result
import com.hjq.permissions.Permission
import com.saint.util.R
import com.saint.util.base.BaseAct
import com.saint.util.databinding.ActivityCaptureBinding
import com.saint.util.listener.RequestPermissionBack
import com.saint.util.util.AppUtil
import com.saint.util.util.PermissionUtil
import com.saint.zxinglibrary.bean.ZxingConfig
import com.saint.zxinglibrary.camera.CameraManager
import com.saint.zxinglibrary.common.ScanConstant
import com.saint.zxinglibrary.view.ViewfinderView
import java.io.IOException

class CaptureActivity : BaseAct(), View.OnClickListener, SurfaceHolder.Callback {
    private lateinit var binding: ActivityCaptureBinding
    var config: ZxingConfig? = null

    private var hasSurface = false
    private var inactivityTimer: InactivityTimer? = null
    private var beepManager: BeepManager? = null
    var cameraManager: CameraManager? = null
    var handler: CaptureActivityHandler? = null
    private var surfaceHolder: SurfaceHolder? = null

    override fun getIntentData() {
        // 保持Activity处于唤醒状态
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        /*先获取配置信息*/
        try {
            config = (intent.extras!![ScanConstant.INTENT_ZXING_CONFIG] as ZxingConfig?)!!
        } catch (e: Exception) {
            Log.i("config", e.toString())
        }

        if (config == null) {
            config = ZxingConfig()
        }
    }

    override fun setRootView(): View {
        binding = ActivityCaptureBinding.inflate(layoutInflater)
        return binding.root;
    }

    override fun initData(savedInstanceState: Bundle?) {
        setStatusBarHeight(binding.layoutStatusView)
        binding.viewfinderView.setZxingConfig(config)
        binding.previewView.setOnClickListener(this)
        binding.backIv.setOnClickListener(this)
        binding.flashLightLayout.setOnClickListener(this)
        binding.albumIv.setOnClickListener(this)

        switchVisibility(binding.bottomLayout, config!!.isShowbottomLayout)
        switchVisibility(binding.flashLightLayout, config!!.isShowFlashLight)
//        switchVisibility(binding.albumIv, config!!.isShowAlbum);

        /*有闪光灯就显示手电筒按钮  否则不显示*/
        if (isSupportCameraLedFlash(packageManager)) {
            binding.flashLightLayout.visibility = View.VISIBLE
        } else {
            binding.flashLightLayout.visibility = View.GONE
        }

        hasSurface = false

        inactivityTimer = InactivityTimer(this)
        beepManager = BeepManager(this)
        beepManager!!.setPlayBeep(config!!.isPlayBeep)
        beepManager!!.setVibrate(config!!.isShake)

    }

    override fun onResume() {
        super.onResume()
        cameraManager = CameraManager(application, config)
        binding.viewfinderView.setCameraManager(cameraManager)
        handler = null
        surfaceHolder = binding.previewView.getHolder()
        if (hasSurface) {
            initCamera(surfaceHolder)
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder!!.addCallback(this)
        }
        beepManager!!.updatePrefs()
        inactivityTimer!!.onResume()
    }

    private fun initCamera(surfaceHolder: SurfaceHolder?) {
        checkNotNull(surfaceHolder) { "No SurfaceHolder provided" }
        if (cameraManager!!.isOpen) {
            return
        }
        PermissionUtil.request(this, object : RequestPermissionBack {
            override fun onSuccess(permissions: List<String>, all: Boolean) {
                try {
                    // 打开Camera硬件设备
                    cameraManager!!.openDriver(surfaceHolder)
                    // 创建一个handler来打开预览，并抛出一个运行时异常
                    if (handler == null) {
                        handler = CaptureActivityHandler(
                            this@CaptureActivity,
                            cameraManager,
                            config!!.isBind
                        )
                    }
                } catch (ioe: IOException) {
                    displayFrameworkBugMessageAndExit()
                } catch (e: RuntimeException) {
                    displayFrameworkBugMessageAndExit()
                }
            }

            override fun onFailed(permissions: List<String>, never: Boolean) {}
        }, Permission.CAMERA)
    }

    private fun displayFrameworkBugMessageAndExit() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("扫一扫")
        builder.setMessage(getString(R.string.msg_camera_framework_bug))
        builder.setPositiveButton(R.string.button_ok, FinishListener(this))
        builder.setOnCancelListener(FinishListener(this))
        builder.show()
    }

    override fun onPause() {
        Log.i("CaptureActivity", "onPause")
        if (handler != null) {
            handler!!.quitSynchronously()
            handler = null
        }
        inactivityTimer!!.onPause()
        beepManager!!.close()
        cameraManager!!.closeDriver()
        if (!hasSurface) {
            surfaceHolder!!.removeCallback(this)
        }
        super.onPause()
    }

    override fun onDestroy() {
        inactivityTimer!!.shutdown()
        binding.viewfinderView.stopAnimator()
        super.onDestroy()
    }

    fun getViewfinderView(): ViewfinderView {
        return binding.viewfinderView;
    }

    fun drawViewfinder() {
        binding.viewfinderView.drawViewfinder();
    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        if (!hasSurface) {
            hasSurface = true
            initCamera(holder)
        }
    }


    override fun surfaceDestroyed(holder: SurfaceHolder) {
        hasSurface = false
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun onClick(view: View) {
        when (view.id) {
            R.id.flashLightLayout -> {
                /*切换闪光灯*/
                cameraManager!!.switchFlashLight(handler)
            }
            R.id.albumIv -> {
                selectImg()
            }
            R.id.backIv -> {
                finish()
            }
        }
    }

    private fun selectImg() {
    }

    /**
     * @param rawResult 返回的扫描结果
     */
    fun handleDecode(rawResult: Result) {
        inactivityTimer!!.onActivity()
        beepManager!!.playBeepSoundAndVibrate()
        val intent = intent
        intent.putExtra(ScanConstant.CODED_CONTENT, rawResult.text)
        setResult(RESULT_OK, intent)
        finish()
    }

    /**
     * @param pm
     * @return 是否有闪光灯
     */
    private fun isSupportCameraLedFlash(pm: PackageManager?): Boolean {
        if (pm != null) {
            val features = pm.systemAvailableFeatures
            if (features != null) {
                for (f in features) {
                    if (f != null && PackageManager.FEATURE_CAMERA_FLASH == f.name) {
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * @param flashState 切换闪光灯图片
     */
    fun switchFlashImg(flashState: Int) {
        if (flashState == ScanConstant.FLASH_OPEN) {
            binding.flashLightIv.setImageResource(R.drawable.ic_open)
            binding.flashLightTv.setText(R.string.close_flash)
        } else {
            binding.flashLightIv.setImageResource(R.drawable.ic_close)
            binding.flashLightTv.setText(R.string.open_flash)
        }
    }

    private fun switchVisibility(view: View, b: Boolean) {
        if (b) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    /**
     * 设置状态栏高度
     */
    private fun setStatusBarHeight(view: View) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        val height = AppUtil.getStatusBarHeight()
        val params: ViewGroup.LayoutParams = view.layoutParams
        params.height = height
        view.layoutParams = params
    }
}