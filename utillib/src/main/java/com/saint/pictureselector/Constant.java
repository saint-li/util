package com.saint.pictureselector;

import java.io.File;

public class Constant {
    public static final String APP_NAME = "TuDingPic";//app名称
    public static final String BASE_DIR = APP_NAME + File.separator;///
    public static final String DIR_ROOT = FileUtils.getRootPath() + "/" + Constant.APP_NAME;//文件夹根目录

    public static final int CANCEL = 0;//取消
    public static final int CAMERA = 1;//拍照
    public static final int ALBUM = 2;//相册
}

