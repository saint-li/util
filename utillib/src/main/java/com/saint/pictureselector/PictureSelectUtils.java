package com.saint.pictureselector;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.saint.util.UtilConfig;
import com.saint.util.util.toast.AppToast;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * Desc	        ${选择图片工具类}
 * 使用方法：
 * 1. 调用getByCamera()、getByAlbum()可通过拍照或相册获取图片
 * 2. 在onActivityResult中调用本工具类的onActivityResult方法处理通过相册或拍照获取的图片
 */
public class PictureSelectUtils {

    public static final int GET_BY_ALBUM = 0x11;//相册标记
    public static final int GET_BY_CAMERA = 0x12;//拍照标记
    public static final int CROP = 0x13;//裁剪标记
    //    private static Uri takePictureUri;//拍照图片uri
    private static Uri cropPictureTempUri;//裁剪图片uri
//    private static File takePictureFile;//拍照图片File

    public static Uri getCropPictureTempUri() {
        return cropPictureTempUri;
    }

    /**
     * 通过相册获取图片
     */
    public static void getByAlbum(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent, GET_BY_ALBUM);
    }

    /**
     * 通过拍照获取图片
     */
    public static void getByCamera(Activity activity) {
        cropPictureTempUri = createImagePathUri();
        if (cropPictureTempUri != null) {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, cropPictureTempUri);//输出路径（拍照后的保存路径）
            activity.startActivityForResult(i, GET_BY_CAMERA);
        } else {
            AppToast.tShort("无法保存到相册");
        }
    }

    /**
     * 创建一个图片地址uri,用于保存拍照后的照片或裁剪后图片
     *
     * @return 图片的uri
     */
    public static Uri createImagePathUri() {
        Uri uri = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues values = new ContentValues();
            long name = System.currentTimeMillis();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + Constant.APP_NAME);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            uri = UtilConfig.getApp().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            String pathName = "file:///" + getCstDir() + System.currentTimeMillis() + ".jpg";
            uri = Uri.parse(pathName);
        }

        return uri;
    }

    public static String getCstDir() {
        FileUtils.createOrExistsDir(Constant.DIR_ROOT);
        return Constant.DIR_ROOT + "/";
    }

    /**
     * 处理拍照或相册获取的图片
     *
     * @param activity    上下文
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        Intent
     * @param cropEnabled 是否裁剪
     * @param w           输出宽
     * @param h           输出高
     * @param aspectX     宽比例
     * @param aspectY     高比例
     * @return picturePath 图片路径
     */
    public static Uri onActivityResult(Activity activity, int requestCode, int resultCode, Intent data,
                                       boolean cropEnabled, int w, int h, int aspectX, int aspectY) {
        Uri picturePath = null;//图片路径
        if (resultCode == activity.RESULT_OK) {
            Uri uri = null;
            switch (requestCode) {
                case GET_BY_ALBUM:
                    uri = data.getData();
                    if (cropEnabled) {
                        activity.startActivityForResult(crop(uri, w, h, aspectX, aspectY), CROP);
                    } else {
                        picturePath = uri;
                    }
                    break;
                case GET_BY_CAMERA:
                    uri = cropPictureTempUri;
                    if (cropEnabled) {
                        activity.startActivityForResult(crop(uri, w, h, aspectX, aspectY), CROP);
                    } else {
                        picturePath = cropPictureTempUri;
                    }
                    activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));//发送广播通知图库更新
                    break;
                case CROP:
//                    dealCrop(activity);
                    if (cropPictureTempUri != null && cropPictureTempUri.getPath() != null) {
                        File file = new File(cropPictureTempUri.getPath());
                        picturePath = cropPictureTempUri;
                    }
                    break;
            }
        }
        return picturePath;
    }

    /**
     * 裁剪，例如：输出100*100大小的图片，宽高比例是1:1
     *
     * @param uri     图片的uri
     * @param w       输出宽
     * @param h       输出高
     * @param aspectX 宽比例
     * @param aspectY 高比例
     */
    public static Intent crop(Uri uri, int w, int h, int aspectX, int aspectY) {
        if (w == 0 || h == 0) {
            w = h = 480;
        }
        if (aspectX == 0 || aspectY == 0) {
            aspectX = aspectY = 1;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", w);
        intent.putExtra("outputY", h);

        /*解决图片有黑边问题*/
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);

        /*解决跳转到裁剪提示“图片加载失败”问题*/
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropPictureTempUri = createImagePathUri();

        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropPictureTempUri);//输出路径(裁剪后的保存路径)
        // 输出格式
        intent.putExtra("outputFormat", "JPEG");
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", true);
        //是否将数据保留在Bitmap中返回
        intent.putExtra("return-data", false);
        return intent;
    }

    /**
     * 处理裁剪，获取裁剪后的图片
     */
    public static Bitmap dealCrop(Context context) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(cropPictureTempUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}