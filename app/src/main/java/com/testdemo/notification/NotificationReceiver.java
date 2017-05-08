package com.testdemo.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.testdemo.R;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 03 09:36
 * @DESC：当网络状态发生改变时，发出通知消息
 */

public class NotificationReceiver extends BroadcastReceiver {
    String TAG = NotificationReceiver.class.getSimpleName();
    private final static int NOTIFICATION_ID = 101;
    private final static String TYPE = "type";
    NotificationManager notificationManager;
    ConnectivityManager manager;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (manager == null) {
            manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        if (null == notificationManager){
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Log.e(TAG,"-----------------------启动广播");
        if (intent.getAction().equals("notification_click")){
            //删除
            Log.e(TAG,"-------------------点击监听");
            //点击后取消
            notificationManager.cancel(NOTIFICATION_ID);
        }else if (intent.getAction().equals("notification_cancel")) {
            //取消
            Log.e(TAG,"-------------------取消监听");
        }else {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            boolean isConnected = false;
            if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                //有可用网络
                isConnected = true;
            }else {
                //没有可用网络
                isConnected = false;
            }
            notification(context,isConnected);
        }
    }

    public void notification(Context context,boolean isConnected){
        /*Intent intentClick = new Intent(context,NotificationReceiver.class);
        intentClick.setAction("notification_click");
        intentClick.putExtra(TYPE,NOTIFICATION_ID);*/
        //打开系统设置
        Intent setting = new Intent(Settings.ACTION_SETTINGS);
        PendingIntent clickPendingIntent = PendingIntent.getActivity(context,0,setting,PendingIntent.FLAG_ONE_SHOT);

        String content = isConnected ? "网络已经连接" : "网络已经关闭";
        int smallIcon = isConnected ? R.mipmap.fanli : R.mipmap.tuikuan;
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("网络发生改变")//设置通知标题
                .setContentText(content)//设置通知内容（下拉时即可看见）
                .setSmallIcon(smallIcon)//设置小图标
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),smallIcon))//设置下拉时看到的图标
                .setAutoCancel(true)//设置点击后消失（单独设置么有用，需要跟setContentIntent一起才有作用）
                .setWhen(System.currentTimeMillis())//通知时间
                .setDefaults(Notification.DEFAULT_VIBRATE)//设置震动
                .setContentIntent(clickPendingIntent)//添加点击监听
                .setLights(Color.RED,1000,1000)
                .setPriority(NotificationCompat.PRIORITY_MAX)//设置优先级
                .build();
        notificationManager.notify(NOTIFICATION_ID,notification);
    }



    /*Intent intentCancel = new Intent(context,NotificationReceiver.class);
    intentCancel.setAction("notification_cancel");
    intentCancel.putExtra(TYPE,NOTIFICATION_ID);
    PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(context,0,intentClick,PendingIntent.FLAG_ONE_SHOT);*/
}
