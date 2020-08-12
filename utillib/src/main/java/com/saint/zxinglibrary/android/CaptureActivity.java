package com.saint.zxinglibrary.android;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.zxing.Result;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.saint.util.R;
import com.saint.util.util.GlideEG;
import com.saint.zxinglibrary.bean.ZxingConfig;
import com.saint.zxinglibrary.camera.CameraManager;
import com.saint.zxinglibrary.common.ScanConstant;
import com.saint.zxinglibrary.decode.DecodeImgCallback;
import com.saint.zxinglibrary.decode.DecodeImgThread;
import com.saint.zxinglibrary.view.ViewfinderView;

import java.io.IOException;
import java.util.List;


/**
 * @author: yzq
 * @date: 2017/10/26 15:22
 * @declare :扫一扫
 */

public class CaptureActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    public ZxingConfig config;
    private SurfaceView previewView;
    private ViewfinderView viewfinderView;
    private AppCompatImageView flashLightIv;
    private TextView flashLightTv;
    private AppCompatImageView backIv;
    private LinearLayoutCompat flashLightLayout;
    private AppCompatImageView albumIv;
    private LinearLayoutCompat bottomLayout;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private SurfaceHolder surfaceHolder;


    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }


    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.BLACK);
        }

        /*先获取配置信息*/
        try {
            config = (ZxingConfig) getIntent().getExtras().get(ScanConstant.INTENT_ZXING_CONFIG);
        } catch (Exception e) {

            Log.i("config", e.toString());
        }

        if (config == null) {
            config = new ZxingConfig();
        }


        setContentView(R.layout.activity_capture);


        initView();

        hasSurface = false;

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        beepManager.setPlayBeep(config.isPlayBeep());
        beepManager.setVibrate(config.isShake());


    }


    private void initView() {
        previewView = findViewById(R.id.preview_view);
        previewView.setOnClickListener(this);

        viewfinderView = findViewById(R.id.viewfinder_view);
        viewfinderView.setZxingConfig(config);


        backIv = findViewById(R.id.backIv);
        backIv.setOnClickListener(this);

        flashLightIv = findViewById(R.id.flashLightIv);
        flashLightTv = findViewById(R.id.flashLightTv);

        flashLightLayout = findViewById(R.id.flashLightLayout);
        flashLightLayout.setOnClickListener(this);
        albumIv = findViewById(R.id.albumIv);
        albumIv.setOnClickListener(this);
        bottomLayout = findViewById(R.id.bottomLayout);


        switchVisibility(bottomLayout, config.isShowbottomLayout());
        switchVisibility(flashLightLayout, config.isShowFlashLight());
//        switchVisibility(albumIv, config.isShowAlbum());


        /*有闪光灯就显示手电筒按钮  否则不显示*/
        if (isSupportCameraLedFlash(getPackageManager())) {
            flashLightLayout.setVisibility(View.VISIBLE);
        } else {
            flashLightLayout.setVisibility(View.GONE);
        }

    }


    /**
     * @param pm
     * @return 是否有闪光灯
     */
    public static boolean isSupportCameraLedFlash(PackageManager pm) {
        if (pm != null) {
            FeatureInfo[] features = pm.getSystemAvailableFeatures();
            if (features != null) {
                for (FeatureInfo f : features) {
                    if (f != null && PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param flashState 切换闪光灯图片
     */
    public void switchFlashImg(int flashState) {

        if (flashState == ScanConstant.FLASH_OPEN) {
            flashLightIv.setImageResource(R.drawable.ic_open);
            flashLightTv.setText(R.string.close_flash);
        } else {
            flashLightIv.setImageResource(R.drawable.ic_close);
            flashLightTv.setText(R.string.open_flash);
        }

    }

    /**
     * @param rawResult 返回的扫描结果
     */
    public void handleDecode(Result rawResult) {

        inactivityTimer.onActivity();

        beepManager.playBeepSoundAndVibrate();

        Intent intent = getIntent();
        intent.putExtra(ScanConstant.CODED_CONTENT, rawResult.getText());
        setResult(RESULT_OK, intent);
        this.finish();


    }


    private void switchVisibility(View view, boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        cameraManager = new CameraManager(getApplication(), config);

        viewfinderView.setCameraManager(cameraManager);
        handler = null;

        surfaceHolder = previewView.getHolder();
        if (hasSurface) {

            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();
        inactivityTimer.onResume();

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager, config.isBind());
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("扫一扫");
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    @Override
    protected void onPause() {

        Log.i("CaptureActivity", "onPause");
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();

        if (!hasSurface) {

            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        viewfinderView.stopAnimator();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.flashLightLayout) {
            /*切换闪光灯*/
            cameraManager.switchFlashLight(handler);
        } else if (id == R.id.albumIv) {
//            /*打开相册*/
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
//                    .setType("image/*")
//                    .addCategory(Intent.CATEGORY_OPENABLE);
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                String[] mimeTypes = {"image/jpeg", "image/png"};
//                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//            }
//            startActivityForResult(intent, Constant.REQUEST_IMAGE);
            selectImg();
        } else if (id == R.id.backIv) {
            finish();
        }
    }

    private void selectImg() {
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
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        if (result != null && result.size() > 0) {
                            LocalMedia localMedia = result.get(0);
                            String path = "";
                            if (localMedia.isCut() && !localMedia.isCompressed()) {
                                // 裁剪过
                                path = localMedia.getCutPath();
                            } else if (localMedia.isCompressed()) {
                                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                                path = localMedia.getCompressPath();
                            } else if (!TextUtils.isEmpty(localMedia.getAndroidQToPath())) {
                                path = localMedia.getAndroidQToPath();
                            } else {
                                // 原图
                                path = localMedia.getPath();
                            }
                            new DecodeImgThread(path, new DecodeImgCallback() {
                                @Override
                                public void onImageDecodeSuccess(Result result) {
                                    handleDecode(result);
                                }

                                @Override
                                public void onImageDecodeFailed() {
                                    Toast.makeText(CaptureActivity.this, R.string.scan_failed_tip, Toast.LENGTH_SHORT).show();
                                }
                            }).run();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == Constant.REQUEST_IMAGE && resultCode == RESULT_OK) {
//            Uri selectedUri = data.getData();
//            if (selectedUri != null) {
//                startCrop(selectedUri);
//            } else {
//                AppToast.tShort("无法检索选定的图像");
//            }
//        }
//        else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
//            final Uri resultUri = UCrop.getOutput(data);
//            String path = ImageUtil.getImageAbsolutePath(this, resultUri);
//            new DecodeImgThread(path, new DecodeImgCallback() {
//                @Override
//                public void onImageDecodeSuccess(Result result) {
//                    handleDecode(result);
//                }
//
//                @Override
//                public void onImageDecodeFailed() {
//                    Toast.makeText(CaptureActivity.this, R.string.scan_failed_tip, Toast.LENGTH_SHORT).show();
//                }
//            }).run();
////            startCrop(path);
//
//        }
    }

//    private void startCrop(@NonNull Uri uri) {
//        String destinationFileName = System.currentTimeMillis() + ".png";
//
//        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
//
//        UCrop.Options options = new UCrop.Options();
//
//        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
//        options.setCompressionQuality(80);
//
//        options.setHideBottomControls(true);
//        options.setFreeStyleCropEnabled(true);
//        uCrop.withOptions(options);
//        uCrop.start(CaptureActivity.this);
//
//    }


}
