package com.saint.utillib;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.NotificationUtils;
import com.blankj.utilcode.util.Utils;
import com.saint.util.base.BaseAct;
import com.saint.util.binding.BaseBindingAct;
import com.saint.util.listener.RequestPermissionBack;
import com.saint.util.util.PermissionUtil;
import com.saint.util.util.toast.AppToast;
import com.saint.utillib.databinding.ActNotificationBinding;

import java.io.File;
import java.util.List;

public class NotificationAct extends BaseBindingAct<ActNotificationBinding> {
    private Button btnSendNotifi;
    NotificationManager manager;

    @Override
    protected void initTitleView() {
        mActionBar = findViewById(R.id.my_action_bar);
        mActionBar.setTitle("通知测试");
    }

    @Override
    protected void initView() {
        btnSendNotifi = findViewById(R.id.send_notifi);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        btnSendNotifi.setOnClickListener(this::sendNotifi);
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendNotifi(View view) {


//        //高版本需要渠道
//        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            //只在Android O之上需要渠道
//            NotificationChannel notificationChannel = new NotificationChannel("channelid1", "channelname", NotificationManager.IMPORTANCE_HIGH);
//
//            notificationChannel.setSound((Uri.parse("android.resource://com.saint.utillib/" + R.raw.beep)), new AudioAttributes.Builder().build());
//
//            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，通知才能正常弹出
//            manager.createNotificationChannel(notificationChannel);
//        }
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channelid1");
//        builder
//                .setContentText("通知内容")
//                .setContentTitle("通知标题")
//                .setSmallIcon(R.mipmap.scan_icon)
//                .setSound((Uri.parse("android.resource://com.saint.util/"+ R.raw.beep))) //设置通知提示音
////                .setVibrate(new long[]{0, 1000, 1000, 1000}) //设置振动， 需要添加权限  <uses-permission android:name="android.permission.VIBRATE"/>
////                .setLights(Color.GREEN, 1000, 1000)//设置前置LED灯进行闪烁， 第一个为颜色值  第二个为亮的时长  第三个为暗的时长
////                .setDefaults(NotificationCompat.DEFAULT_ALL)  //使用默认效果， 会根据手机当前环境播放铃声， 是否振动
//        ;
//        manager.notify(1, builder.build());
//
//        NotificationCompat.Builder status = new NotificationCompat.Builder(this, "channelid1");
//        status.setAutoCancel(true)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.drawable.return_icon)
//                //.setOnlyAlertOnce(true)
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText("通知内容")
//                .setVibrate(new long[]{0, 500, 1000})
//                .setDefaults(Notification.DEFAULT_LIGHTS)
//                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.ding))
////                .setContentIntent(pendingIntent)
////                .setContent(views);
//        ;
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        mNotificationManager.notify(100, status.build());


        if (NotificationUtils.areNotificationsEnabled()) {
            NotificationUtils.notify(1000, builder ->
                    builder.setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("通知标题")
                            .setContentText("通知内容")
//                            .setContentIntent(Pen)
                            .setAutoCancel(true));
        } else {
            AppToast.INSTANCE.tLong("请开启APP通知权限");
            PermissionUtil.request(this, new RequestPermissionBack() {
                @Override
                public void onSuccess(List<String> permissions, boolean all) {

                }

                @Override
                public void onFailed(List<String> permissions, boolean never) {

                }
            }, Manifest.permission.POST_NOTIFICATIONS);
        }
    }
}
