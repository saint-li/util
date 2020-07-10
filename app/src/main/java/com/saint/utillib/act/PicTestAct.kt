//package com.saint.utillib.act
//
////import javafx.scene.Cursor.cursor
//import android.app.Activity
//import android.content.Intent
//import android.database.Cursor
//import android.net.Uri
//import android.provider.MediaStore
//import com.saint.pictureselector.Constant
//import com.saint.pictureselector.PictureSelector
//import com.saint.util.GlideApp
//import com.saint.util.base.BaseAct
//import com.saint.util.util.AppLog
//import com.saint.util.util.deleteDir
//import com.saint.util.util.deleteSingleFile
//import com.saint.util.util.toast.AppToast
//import com.saint.utillib.R
//import kotlinx.android.synthetic.main.act_pic_test.*
//import java.io.File
//
//
//class PicTestAct : BaseAct() {
//    var picPath = ""
//
//
//    override fun setLayout(): Int = R.layout.act_pic_test
//
//    override fun initTitleView() {
//        my_action_bar.setTitle("图片选择")
//        my_action_bar.setTitleColor(R.color.color333)
//        my_action_bar.setLeftListener { finish() }
//    }
//
//    override fun initData() {
//        btn_select_pic.setOnClickListener {
//            PictureSelector.create(this, PictureSelector.SELECT_REQUEST_CODE)
//                .selectPicture(true, 400, 400, 0, 0)
//        }
//        btn_delete_pic.setOnClickListener {
//            if (deleteSingleFile(picPath)) {
//                AppToast.tShort("图片删除成功$picPath")
//                picPath = ""
//            } else {
//                AppToast.tShort("图片删除失败$picPath")
//            }
//        }
//        btn_delete_pic_dir.setOnClickListener {
//            if (deleteDir(File(Constant.DIR_ROOT))) {
//                AppToast.tShort("图片目录成功${Constant.DIR_ROOT}")
//            } else {
//                AppToast.tShort("图片目录失败${Constant.DIR_ROOT}")
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == PictureSelector.SELECT_REQUEST_CODE) {
//            if (data != null) {
//                AppLog.e("Uri： " + data.data)
//
//                val uri = data.data
//                if (uri != null) {
//                    GlideApp.with(this)
//                        .load(uri)
//                        .into(iv_crop)
//                    tv_crop.text = "图片路径： $uri"
//                } else {
//                    GlideApp.with(this)
//                        .load(File(picPath))
//                        .into(iv_crop)
//                }
//
//            }
//        }
//    }
//
//    // 通过Uri查找图片名称，然后输出图片到Sd卡指定位置，将图片路径返回
////    private fun getPathByUri(uri: Uri): String? {
////        val resolver = this.contentResolver
////        var fileName = System.currentTimeMillis().toString()
////        val cursor: Cursor? = resolver.query(uri, null, null, null, null)
////        if (cursor != null && cursor.count > 0) {
////            cursor.moveToFirst()
////            fileName =
////                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
////            cursor.close()
////        }
////        return CopyUriToSd.Companion.getInstance().copyUriToExternalFilesDir(this, uri, fileName)
////    }
//
//    override fun finish() {
//        deleteSingleFile(picPath)
//        super.finish()
//    }
//}