package com.saint.util.util;

import android.content.Context;
import android.graphics.*;
import android.media.ExifInterface;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PictureFileUtils {

    private PictureFileUtils() {
    }


    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    public static void saveBitmapFile(Bitmap bitmap, File file) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File saveBitmapFile(Context context, Bitmap bitmap) {
        try {
            File file = createFile(context, System.currentTimeMillis() + ".jpg");
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            AppLog.e("保存文件异常：" + e.getMessage());
            return null;
        }
    }


    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;

            left = 0;
            top = 0;
            right = width;
            bottom = width;

            height = width;

            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;

            float clip = (width - height) / 2;

            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;

            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas

        // 以下有两种方法画圆,drawRounRect和drawCircle
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        // canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }

    /**
     * 创建文件夹
     *
     * @param filename
     * @return
     */
    public static String createDir(Context context, String filename) {
        String state = Environment.getExternalStorageState();
        File rootDir = state.equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory() : context.getCacheDir();
        File path = null;
        path = new File(rootDir.getAbsolutePath() + "/TuDingPic");
        if (!path.exists())
        // 若不存在，创建目录，可以在应用启动的时候创建
        {
            path.mkdirs();
        }
        return path + "/" + filename;
    }

    /**
     * 创建文件夹
     *
     * @param filename
     * @return
     */
    public static File createFile(Context context, String filename) {
        String state = Environment.getExternalStorageState();
        File rootDir = state.equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory() : context.getCacheDir();
        File path = null;
        path = new File(rootDir.getAbsolutePath() + "/TuDingPic");
        if (!path.exists())
        // 若不存在，创建目录，可以在应用启动的时候创建
        {
            path.mkdirs();
        }
        return new File(path.getAbsolutePath() + "/" + filename);
    }


    /**
     * image is Damage
     *
     * @param path
     * @return
     */
    public static int isDamage(String path) {
        BitmapFactory.Options options = null;
        if (options == null) options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options); //filePath代表图片路径
        if (options.mCancel || options.outWidth == -1
                || options.outHeight == -1) {
            //表示图片已损毁
            return -1;
        }
        return 0;
    }

    /**
     * 获取某目录下所有文件路径
     *
     * @param dir
     */
    public static List<String> getDirFiles(String dir) {
        File scanner5Directory = new File(dir);
        List<String> list = new ArrayList<>();
        if (scanner5Directory.isDirectory()) {
            for (File file : scanner5Directory.listFiles()) {
                String path = file.getAbsolutePath();
                if (path.endsWith(".jpg") || path.endsWith(".jpeg")
                        || path.endsWith(".png") || path.endsWith(".gif")
                        || path.endsWith(".webp")) {
                    list.add(path);
                }
            }
        }
        return list;
    }

    public static String getDCIMCameraPath() {
        String absolutePath;
        try {
            absolutePath = "%" + Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return absolutePath;
    }

    /**
     * set empty PictureSelector Cache
     *
     * @param mContext
     */
    public static void deleteCacheDirFile(Context mContext) {
        File cutDir = mContext.getCacheDir();
        File compressDir = new File(mContext.getCacheDir() + "/picture_cache");
        File lubanDir = new File(mContext.getCacheDir() + "/luban_disk_cache");
        if (cutDir != null) {
            File[] files = cutDir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }

        if (compressDir != null) {
            File[] files = compressDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }

        if (lubanDir != null) {
            File[] files = lubanDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }

    /**
     * set empty PictureSelector Cache
     *
     * @param mContext
     */
    public static void deleteExternalCacheDirFile(Context mContext) {

        File cutDir = mContext.getExternalCacheDir();
        File compressDir = new File(mContext.getExternalCacheDir() + "/picture_cache");
        File lubanDir = new File(mContext.getExternalCacheDir() + "/luban_disk_cache");
        if (cutDir != null) {
            File[] files = cutDir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }

        if (compressDir != null) {
            File[] files = compressDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }

        if (lubanDir != null) {
            File[] files = lubanDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }


    /**
     * delete file
     *
     * @param path
     */
    public static boolean deleteFile(String path) {
        try {
            if (!TextUtils.isEmpty(path)) {
                File file = new File(path);
                if (file != null) {
                    return file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * @param context
     * @return
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 得到SD卡根目录，SD卡不可用则获取内部存储的根目录
     */
    public static File getRootPath() {
        File path = null;
        if (sdCardIsAvailable()) {
            path = Environment.getExternalStorageDirectory(); //SD卡根目录    /storage/emulated/0
        } else {
            path = Environment.getDataDirectory();//内部存储的根目录    /data
        }
        return path;
    }

    /**
     * SD卡是否可用
     */
    public static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getPath());
            return sd.canWrite();
        } else
            return false;
    }


    /** storage location: sdcard/Android/data/packagename
     * 存储图像至沙盒
     * @param context
     * @param fileName
     * @param image
     * @param environmentType
     * @param dirName
     *
     */
    public static void saveImage2SandBox(Context context, String fileName, byte[] image, String environmentType, String dirName) {
        File standardDirectory;
        String dirPath;

        if (TextUtils.isEmpty(fileName) || 0 == image.length) {
            AppLog.e("saveImage2SandBox: fileName is null or image is null!");
            return;
        }

        if (!TextUtils.isEmpty(environmentType)) {
            standardDirectory = context.getExternalFilesDir(environmentType);
        } else {
            standardDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }

        if (!TextUtils.isEmpty(dirName)) {
            dirPath = standardDirectory + "/" + dirName;
        } else {
            dirPath = String.valueOf(standardDirectory);
        }

        File imageFileDirctory = new File(dirPath);
        if (!imageFileDirctory.exists()) {
            if (!imageFileDirctory.mkdir()) {
                AppLog.e("saveImage2SandBox: mkdir failed! Directory: " + dirPath);
                return;
            }
        }

//        if (queryImageFromSandBox(context, fileName, environmentType, dirName)) {
//            Log.e(TAG, "saveImage2SandBox: The file with the same name already exists！");
//            return;
//        }

        try {
            File imageFile = new File(dirPath + "/" + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            fileOutputStream.write(image);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
