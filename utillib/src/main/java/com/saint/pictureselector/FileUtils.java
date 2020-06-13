package com.saint.pictureselector;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.saint.util.UtilConfig;
import com.saint.util.util.AppLog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.saint.util.util.PictureFileUtils.createFile;

public final class FileUtils {

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

//        path = ContextCompat.getExternalFilesDirs(UtilConfig.getApp(), null)[0].getAbsoluteFile();
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

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     *
     * @param s
     * @return
     */
    private static boolean isSpace(final String s) {
        if (s == null)
            return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
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
            Log.e("图片选择工具包：", "保存文件异常：" + e.getMessage());
            return null;
        }
    }


    /**
     * @param context
     * @param filePath relative path in Q, such as: "DCIM/" or "DCIM/dir_name/"
     *                 absolute path before Q
     * @return
     */
    private static Cursor searchImageFromPublic(Context context, String filePath, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            AppLog.e("searchImageFromPublic: fileName is null");
            return null;
        }
        if (TextUtils.isEmpty(filePath)) {
            filePath = "DCIM/";
        } else {
            if (!filePath.endsWith("/")) {
                filePath = filePath + "/";
            }
        }

        //兼容androidQ和以下版本
        String queryPathKey = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q ? MediaStore.Images.Media.RELATIVE_PATH : MediaStore.Images.Media.DATA;
        String selection = queryPathKey + "=? and " + MediaStore.Images.Media.DISPLAY_NAME + "=?";
        Cursor cursor = context.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID, queryPathKey, MediaStore.Images.Media.MIME_TYPE, MediaStore.Images.Media.DISPLAY_NAME},
                selection,
                new String[]{filePath, fileName},
                null);

        return cursor;
    }

    /**
     *
     * @param context
     * @param filePath relative path in Q, such as: "DCIM/" or "DCIM/dir_name/"
     *                 absolute path before Q
     * @return
     */
    public static byte[] loadImageFromPublic(Context context, String filePath, String fileName) {
        Cursor cursor = searchImageFromPublic(context, filePath, fileName);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                //循环取出所有查询到的数据
                do {
                    //一张图片的基本信息
                    int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));                     // uri的id，用于获取图片
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.RELATIVE_PATH));   // 图片的相对路径
                    String type = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));       // 图片类型
                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));    // 图片名字
                    AppLog.d("loadImageFromPublic: id = " + id);
                    AppLog.d("loadImageFromPublic: name = " + name);
                    //根据图片id获取uri，这里的操作是拼接uri
                    Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
                    //官方代码：
//                    Uri contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                    if (uri != null) {
                        byte[] image;
                        InputStream inputStream = context.getContentResolver().openInputStream(uri);
                        if (null == inputStream || 0 == inputStream.available()) {
                            return null;
                        }
                        image = new byte[inputStream.available()];
                        inputStream.read(image);
                        inputStream.close();
                        return image;
                    }
                } while (cursor.moveToNext());
            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}