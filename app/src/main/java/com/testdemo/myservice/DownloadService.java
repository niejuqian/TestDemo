package com.testdemo.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.testdemo.util.upgrade.DownloadCallback;
import com.testdemo.util.upgrade.DownloadListener;
import com.testdemo.util.upgrade.DownloadTask;
import com.testdemo.util.upgrade.NotificationProgress;

import java.io.File;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 04 14:11
 * @DESC：下载后台服务
 */

public class DownloadService extends Service {
    private static String TAG = DownloadService.class.getSimpleName();
    private DownloadTask downloadTask;
    private String downloadUrl;
    private static final int NOTIFICATION_ID = 101;

    DownloadBinder downloadBinder = new DownloadBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }

    class DownloadBinder extends Binder{

        private DownloadCallback downloadCallback;

        public DownloadCallback getDownloadCallback() {
            return downloadCallback;
        }

        public void setDownloadCallback(DownloadCallback downloadCallback) {
            this.downloadCallback = downloadCallback;
        }

        /**
         * 开始下载
         * @param url 下载路径
         */
       protected void startDownload(String url){
           Log.e(TAG,"-----------开始启动下载");
            if (null == downloadTask) {
                downloadUrl = url;
                downloadTask = new DownloadTask(downloadListener);
                downloadTask.execute(downloadUrl);
            }
           startForeground(NOTIFICATION_ID,NotificationProgress.getNotification("Downloading...",0));
        }

        protected void cancelDownload(){
            Log.e(TAG,"-----------取消下载");
            //取消下载
            if (downloadTask != null) {
                downloadTask.isCancel();
            }
            stopForeground(true);
            NotificationProgress.cancel(NOTIFICATION_ID);
            try {
                File file = DownloadTask.getFile();
                if (file.exists()){
                    file.delete();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void pauseDownload(){
            Log.e(TAG,"-----------暂停下载");
            //暂停下载
            if (downloadTask != null) {
                downloadTask.isPause();
            }
        }
    }

    DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onSuccess() {
            Log.e(TAG,"---------------onDownloadSuccess");
            downloadTask = null;
            NotificationProgress.notify(NOTIFICATION_ID,"安装包已经下载完成",0);
            if (null != downloadBinder.getDownloadCallback()){
                downloadBinder.getDownloadCallback().onDownloadSuccess("安装包已经下载完成");
            }
        }

        @Override
        public void onFailed() {
            Log.e(TAG,"---------------onDownloadFailed");
            downloadTask = null;
            NotificationProgress.notify(NOTIFICATION_ID,"Download failed...",-1);
            stopForeground(true);
            if (null != downloadBinder.getDownloadCallback()){
                downloadBinder.getDownloadCallback().onDownloadFailed();
            }
        }

        @Override
        public void onPause(int progress) {
            Log.e(TAG,"---------------onPause");
            downloadTask = null;
            NotificationProgress.notify(NOTIFICATION_ID,"Download pause...",progress);
            if (null != downloadBinder.getDownloadCallback()){
                downloadBinder.getDownloadCallback().onDownloadPause();
            }
        }

        @Override
        public void onCancel() {
            Log.e(TAG,"---------------onCancel");
            downloadTask = null;
            NotificationProgress.notify(NOTIFICATION_ID,"Download pause...",-1);
            stopForeground(true);
        }

        @Override
        public void onProgress(int progress) {
            Log.e(TAG,"-------------progress：" + progress);
            NotificationProgress.notify(NOTIFICATION_ID,"Downloading...",progress);
            if (null != downloadBinder.getDownloadCallback()){
                downloadBinder.getDownloadCallback().onProgress(progress);
            }
        }
    };
}
