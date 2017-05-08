package com.testdemo.util.upgrade;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 04 17:28
 * @DESC：
 */

public class DownloadControl {
    private boolean isBackground = false;//是否显示后台下载进度
    private DownloadTask downloadTask;
    private DownloadCallback downloadCallback;
    private static final int NOTIFICATION_ID = 101;
    private DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onSuccess() {
            if (isBackground) {
                NotificationProgress.cancel(NOTIFICATION_ID);
                NotificationProgress.notify(NOTIFICATION_ID,"安装包已经下载完成",-1,true);
            }
            if (null != downloadCallback){
                downloadCallback.onDownloadSuccess("下载成功");
            }
        }

        @Override
        public void onFailed() {
            if (isBackground) {
                NotificationProgress.notify(NOTIFICATION_ID,"安装包已经下载失败",-1);
            }
            if (null != downloadCallback){
                downloadCallback.onDownloadFailed();
            }
        }

        @Override
        public void onPause(int progress) {
            if (isBackground) {
                NotificationProgress.notify(NOTIFICATION_ID,"暂停下载",progress);
            }
            if (null != downloadCallback) {
                downloadCallback.onDownloadPause();
            }
        }

        @Override
        public void onCancel() {
            if (isBackground) {
                NotificationProgress.notify(NOTIFICATION_ID,"取消下载",-1);
            }
            if (null != downloadCallback) {
                downloadCallback.onDownloadSuccess("已经取消下载");
            }
        }

        @Override
        public void onProgress(int progress) {
            if (isBackground) {
                NotificationProgress.notify(NOTIFICATION_ID,"下载中...",progress);
            }
            if (null != downloadCallback){
                //downloadCallback.onProgress(max,progress);
            }
        }
    };

    /**
     * 开始下载
     * @param url 下载地址
     */
    public DownloadControl onStartDownload(String url){
        if (null == downloadTask) {
            downloadTask = new DownloadTask(downloadListener);
        }
        downloadTask.execute(url);
        return this;
    }

    /**
     * 暂停下载
     */
    public void onPauseDownload(){
        if (null != downloadTask) {
            downloadTask.isPause();
        }
    }

    /**
     * 取消下载
     */
    public void onCancelDownload(){
        if (null != downloadTask) {
            downloadTask.isCancel();
        }
    }

    public DownloadControl setDownloadCallback(DownloadCallback downloadCallback) {
        this.downloadCallback = downloadCallback;
        return this;
    }

    public DownloadControl setBackground(boolean isBackground) {
        this.isBackground = isBackground;
        return this;
    }
}
