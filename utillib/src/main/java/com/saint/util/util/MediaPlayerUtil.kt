package com.saint.util.util

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import com.saint.util.R

/*
 * 音乐播放类，用于应用播放raw中的资源文件
 * */
object MediaPlayerUtil {
    private var mp: MediaPlayer? = null
    private var globalCount = 0

    /*
     * 播放声音
     */
    fun playSound(context: Context) {
        play(context, R.raw.ding, 1)
    }

    /*
     * 播放声音
     */
    fun playSound(context: Context, @RawRes rawResId: Int) {
        play(context, rawResId, 1)
    }

    /*
     * 播放声音
     */
    fun playSound(context: Context, @RawRes rawResId: Int, count: Int) {
        play(context, rawResId, count)
    }

    @Synchronized
    private fun play(context: Context, rawResource: Int, count: Int) {
        try {
            stopPlay()
            mp = MediaPlayer()
            mp = MediaPlayer.create(context.applicationContext, rawResource) // 实例化并设置资源文件
            globalCount = 0
            if (count > 0) {
                globalCount = count - 1
                mp!!.setOnCompletionListener {
                    // 播放完成，设置播放次数并重新开始播放
                    try {
                        if (globalCount > 0 && mp != null) {
                            globalCount--
                            mp!!.start()
                        } else if (mp != null) {
                            // 播放完成后释放资源
                            stopPlay()
                        }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }
            } else { // 循环播放
                mp!!.isLooping = true
            }
            mp!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
            // 发生异常时清理资源
            stopPlay()
        } catch (e: Error) {
            e.printStackTrace()
            // 发生错误时清理资源
            stopPlay()
        }
    }

    // 停止播放
    @Synchronized
    fun stopPlay() {
        try {
            if (mp != null) {
                if (mp!!.isPlaying) {
                    mp!!.stop()
                }
                mp!!.release()
                mp = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            mp = null // 确保引用被清除
        }
    }
}
