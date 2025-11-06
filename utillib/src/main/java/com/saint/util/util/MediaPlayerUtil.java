package com.saint.util.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import androidx.annotation.RawRes;

import com.saint.util.R;

/*
 * 音乐播放类，用于应用播放raw中的资源文件
 * */
public class MediaPlayerUtil {

    // private static MediaPlayer mp = new MediaPlayer();
    private static MediaPlayer mp;
    private static int globalCount = 0;

    /*
     * 播放声音
     */
    public static void playSound(Context context) {
        play(context, R.raw.ding, 1);
    }

    /*
     * 播放声音
     */
    public static void playSound(Context context, @RawRes int rawResId) {
        play(context, rawResId, 1);
    }


    private synchronized static void play(Context context, int rawResuorce, int count) {
        try {
            stopPlay();
            mp = new MediaPlayer();
            mp = MediaPlayer.create(context.getApplicationContext(), rawResuorce);// 实例化并设置资源文件
            globalCount = 0;
            if (count > 0) {
                globalCount = count - 1;
                mp.setOnCompletionListener(new OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer arg0) {// 播放完成，设置播放次数并重新开始播放
                        try {
                            if (globalCount > 0 && mp != null) {
                                globalCount--;
                                mp.start();
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                });

            } else {// 循环播放
                mp.setLooping(true);
            }
            mp.start();

        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }

    }

    // 停止播放
    public static void stopPlay() {
        try {
            if (mp != null) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
                mp.reset();
                mp = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
