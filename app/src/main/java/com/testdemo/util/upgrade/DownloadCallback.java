package com.testdemo.util.upgrade;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 04 17:24
 * @DESC：
 */

public interface DownloadCallback {
    void onProgress(int progress);

    void onDownloadSuccess(String msg);

    void onDownloadFailed();

    void onDownloadPause();
}
