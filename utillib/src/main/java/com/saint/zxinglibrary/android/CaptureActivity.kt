package com.saint.zxinglibrary.android

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.google.zxing.Result
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.saint.util.R
import com.saint.util.base.BaseAct
import com.saint.util.listener.RequestPermissionBack
import com.saint.util.util.AppUtil
import com.saint.util.util.GlideEG
import com.saint.util.util.PermissionUtil
import com.saint.zxinglibrary.bean.ZxingConfig
import com.saint.zxinglibrary.camera.CameraManager
import com.saint.zxinglibrary.common.ScanConstant
import com.saint.zxinglibrary.decode.DecodeImgCallback
import com.saint.zxinglibrary.decode.DecodeImgThread
import com.saint.zxinglibrary.view.ViewfinderView
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.activity_capture.*
import java.io.IOException

class CaptureActivity : BaseAct(), View.OnClickListener, SurfaceHolder.Callback {
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

    override fun setLayout(): Int = R.layout.activity_capture

    override fun initData(savedInstanceState: Bundle?) {
        setStatusBarHeight(layout_status_view)
        viewfinder_view.setZxingConfig(config)
        preview_view.setOnClickListener(this)
        backIv.setOnClickListener(this)
        flashLightLayout.setOnClickListener(this)
        albumIv.setOnClickListener(this)

        switchVisibility(bottomLayout, config!!.isShowbottomLayout)
        switchVisibility(flashLightLayout, config!!.isShowFlashLight)
        switchVisibility(albumIv, config!!.isShowAlbum);

        /*有闪光灯就显示手电筒按钮  否则不显示*/
        if (isSupportCameraLedFlash(packageManager)) {
            flashLightLayout.visibility = View.VISIBLE
        } else {
            flashLightLayout.visibility = View.GONE
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
        viewfinder_view.setCameraManager(cameraManager)
        handler = null
        surfaceHolder = preview_view.getHolder()
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
            override fun onSuccess(permissions: List<String>) {
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

            override fun onFailed(permissions: List<String>) {}
        }, Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
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
        viewfinder_view.stopAnimator()
        super.onDestroy()
    }

    fun getViewfinderView(): ViewfinderView {
        return viewfinder_view;
    }

    fun drawViewfinder() {
        viewfinder_view.drawViewfinder();
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
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage()) // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .imageEngine(GlideEG.createGlideEngine()) // 外部传入图片加载引擎，必传项
            .selectionMode(PictureConfig.SINGLE) // 多选 or 单选
            .isSingleDirectReturn(true) // 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
            .isCamera(false) // 是否显示拍照按钮
            .isZoomAnim(true) // 图片列表点击 缩放效果 默认true
            .isEnableCrop(true) // 是否裁剪
            .isCompress(true) // 是否压缩
            .withAspectRatio(1, 1) // 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            .freeStyleCropEnabled(true)
            .cutOutQuality(90) // 裁剪输出质量 默认100
            .compressQuality(20)
            .minimumCompressSize(150) // 小于多少kb的图片不压缩
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: List<LocalMedia>) {
                    if (result != null && result.size > 0) {
                        val localMedia = result[0]
                        var path: String? = ""
                        path = if (localMedia.isCut && !localMedia.isCompressed) {
                            // 裁剪过
                            localMedia.cutPath
                        } else if (localMedia.isCompressed) {
                            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                            localMedia.compressPath
                        } else if (!TextUtils.isEmpty(localMedia.androidQToPath)) {
                            localMedia.androidQToPath
                        } else {
                            // 原图
                            localMedia.path
                        }
                        DecodeImgThread(path, object : DecodeImgCallback {
                            override fun onImageDecodeSuccess(result: Result) {
                                handleDecode(result)
                            }

                            override fun onImageDecodeFailed() {
                                Toast.makeText(
                                    this@CaptureActivity,
                                    R.string.scan_failed_tip,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }).run()
                    }
                }

                override fun onCancel() {}
            })
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
            flashLightIv.setImageResource(R.drawable.ic_open)
            flashLightTv.setText(R.string.close_flash)
        } else {
            flashLightIv.setImageResource(R.drawable.ic_close)
            flashLightTv.setText(R.string.open_flash)
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