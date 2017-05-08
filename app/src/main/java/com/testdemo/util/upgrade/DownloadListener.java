package com.testdemo.util.upgrade;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 04 14:03
 * @DESC：下载监听
 */

public interface DownloadListener {
    /**
     * 下载成功
     */
    void onSuccess();

    /**
     * 下载失败
     */
    void onFailed();

    /**
     * 暂停下载
     */
    void onPause(int progress);

    /**
     * 取消下载
     */
    void onCancel();

    /**
     * 下载进度
     * @param progress
     */
    void onProgress(int progress);
}
