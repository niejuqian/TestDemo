package com.testdemo.myservice;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.testdemo.R;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 04 11:26
 * @DESC：
 */

public class MyService extends Service {
    private String TAG = MyService.class.getSimpleName();
    private final static int NOTI_ID = 1;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"---------------------onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG,"---------------------onCreate");
        super.onCreate();
        Notification notification = new NotificationCompat.Builder(this)
                .setContentText("HI、好伤筋动骨，的发生的国际 三德科技分隔看加单改单")
                .setContentTitle("你好")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .build();
        startForeground(NOTI_ID,notification);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"---------------------onDestroy");
        super.onDestroy();
        stopForeground(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"---------------------onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
}
