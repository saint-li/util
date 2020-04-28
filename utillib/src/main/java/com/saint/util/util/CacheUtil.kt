package com.saint.util.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.math.BigDecimal


fun clearCache(context: Context): Boolean {
    var success = deleteDir(context.cacheDir)
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        success = deleteDir(context.externalCacheDir)
    }


    return success
}

fun deleteDir(file: File?): Boolean {
    if (file == null) return false
    if (file.isDirectory) {
        file.list()?.forEach {
            val success = deleteDir(File(file, it))
            if (!success) return false
        }
    }
    return file.delete()
}

fun deleteSingleFile(filePath: String?): Boolean {
    if (filePath == null || filePath.isEmpty()) return false
    val file = File(filePath)
    if (file.exists() && file.isFile) {
        return file.delete()
    }
    return false
}

fun getCacheSize(context: Context): String {
    var cacheSize = getFolderSize(context.cacheDir)
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        cacheSize += getFolderSize(context.externalCacheDir)
    }
    return getFormatSize(cacheSize)
}

fun getFolderSize(file: File?): Long {
    var size = 0L
    file?.listFiles()?.forEach {
        size += if (it.isDirectory) getFolderSize(it) else it.length()
    }

    return size
}

fun getFormatSize(size: Long): String {
    val kb = size / 1024.0
//    if (kb < 1) {
//        return "${getBigDecimalSize(size.toDouble())}B"
//    }
    val mb = kb / 1024.0
//    if (mb < 1) {
//        return "${getBigDecimalSize(kb)}K"
//    }
    val gb = mb / 1024.0
    if (gb < 1) {
        return "${getBigDecimalSize(mb)}M"
    }
    val tb = gb / 1024.0
    if (tb < 1) {
        return "${getBigDecimalSize(gb)}G"
    }
    return "${getBigDecimalSize(tb)}T"
}

fun getBigDecimalSize(size: Double): String {
    return BigDecimal(size).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
}
