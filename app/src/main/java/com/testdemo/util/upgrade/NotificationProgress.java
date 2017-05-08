package com.testdemo.util.upgrade;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.testdemo.MainActivity;
import com.testdemo.MyApplication;
import com.testdemo.R;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 04 14:57
 * @DESC：
 */

public class NotificationProgress {
    private static NotificationManager getNotificationManager(){
        return (NotificationManager) MyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static Notification getNotification(String title,int progress){
        return getNotification(title,progress,true);
    }

    public static Notification getNotification(String title,int progress,boolean iscancel){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.getInstance())
                .setContentTitle(title)
                .setAutoCancel(!iscancel)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(MyApplication.getInstance().getResources(),R.mipmap.ic_launcher));
        if (iscancel) {
            Intent intent = new Intent(MyApplication.getInstance(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getInstance(),1,intent,0);
            builder.setContentIntent(pendingIntent);
        }
        if (progress > 0) {
            builder.setContentText(progress + "%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }

    public static void notify(int notificationId,String title,int progress) {
        getNotificationManager().notify(notificationId,getNotification(title,progress,true));
    }

    public static void notify(int notificationId,String title,int progress,boolean isCancel) {
        getNotificationManager().notify(notificationId,getNotification(title,progress,isCancel));
    }

    public static void cancel(int notificationId) {
        getNotificationManager().cancel(notificationId);
    }
}
