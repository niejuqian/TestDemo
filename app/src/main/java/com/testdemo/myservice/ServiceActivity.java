package com.testdemo.myservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;
import com.testdemo.util.upgrade.DownloadCallback;
import com.testdemo.util.upgrade.DownloadConst;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 04 11:28
 * @DESC：
 */

public class ServiceActivity extends BaseAppCompatActivity implements DownloadCallback{
    private static final String DOWNLOAD_URL = "http://192.168.1.155/group1/M00/04/08/wKgBm1j1EYmAbBoaAeJmpo9dIR0101.apk";
    private DownloadService.DownloadBinder downloadBinder;
    private Intent downloadServiceIntent;
    @BindView(R.id.start_download_btn)
    Button start_download_btn;

    @BindView(R.id.desc_total_tv)
    TextView desc_total_tv;

    @BindView(R.id.desc_current_tv)
    TextView desc_current_tv;

    @BindView(R.id.download_progress_tv)
    TextView download_progress_tv;

    @BindView(R.id.download_progress_sb)
    SeekBar download_progress_sb;
    String totalDesc = "文件大小：%s byte";
    String currentDesc = "已下载：%s byte";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_service);
        downloadServiceIntent = new Intent(this, DownloadService.class);
        downloadServiceIntent.putExtra(DownloadConst.BACKGROUND_FLAG,true);
        startService(downloadServiceIntent);
        bindService(downloadServiceIntent,connection,BIND_AUTO_CREATE);
        initView();
    }

    private String format(String format, Object... params) {
        return String.format(Locale.CHINA, format, params);
    }

    private void initView() {
        /*downloadControl = new DownloadControl()
                .setBackground(true)
                .setDownloadCallback(new DownloadCallback() {

                    long lastTime = 0;

                    @Override
                    public void onProgress(long max, long progress) {
                        long startTime = System.currentTimeMillis();
                        if (startTime - lastTime < 200) {
                            return;
                        }
                        lastTime = startTime;
                        Log.e(TAG, "--------------------------onProgress：" + max + "-" + progress);
                        setView(max, progress);
                    }

                    @Override
                    public void onDownloadSuccess(String msg) {
                        Log.e(TAG, "--------------------------下载完成：" + msg);
                    }

                    @Override
                    public void onDownloadFailed() {
                        Log.e(TAG, "--------------------------出错了");
                    }

                    @Override
                    public void onPause() {
                        Log.e(TAG, "--------------------------暂停下载");
                    }
                });*/
        download_progress_sb.setMax(100);
        setView(0, 0);
    }

    private void setView(long max, int progress) {
        desc_current_tv.setText(format(currentDesc, 0 + ""));
        desc_total_tv.setText(format(totalDesc, max + ""));
        download_progress_sb.setProgress(progress);
        download_progress_tv.setText(progress + "%");
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
            if (null != downloadBinder) {
                downloadBinder.setDownloadCallback(ServiceActivity.this);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @OnClick(R.id.start_download_btn)
    void startOrpause() {
        //开始/暂停
        String text = start_download_btn.getText().toString();
        boolean isStart = text.equals("开始下载") ? true : false;
        start_download_btn.setText(isStart ? "暂停下载" : "开始下载");
        if (null != downloadBinder) {
            if (isStart) {
                downloadBinder.startDownload(DOWNLOAD_URL);
            } else {
                downloadBinder.pauseDownload();
            }
        }
    }

    @OnClick(R.id.cancel_download_btn)
    void calcel() {
        //取消
        if (null != downloadBinder) {
            downloadBinder.cancelDownload();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downloadBinder.setDownloadCallback(null);
        if (null != downloadServiceIntent) {
            unbindService(connection);
            stopService(downloadServiceIntent);
        }
    }

    @Override
    public void onProgress(int progress) {
        Log.e(TAG, "--------------------------onProgress："+ progress);
        setView(0, progress);
    }

    @Override
    public void onDownloadSuccess(String msg) {
        Log.e(TAG, "--------------------------下载完成：" + msg);
    }

    @Override
    public void onDownloadFailed() {
        Log.e(TAG, "--------------------------出错了");
    }

    @Override
    public void onDownloadPause() {
        Log.e(TAG, "--------------------------暂停下载");
    }
}
