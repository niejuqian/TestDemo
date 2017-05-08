package com.testdemo.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 03 15:04
 * @DESC：
 */

public class MediaActivity extends BaseAppCompatActivity {
    MediaPlayer mediaPlayer;
    @BindView(R.id.progress_sb)
    SeekBar progress_sb;
    @BindView(R.id.start_media_btn)
    Button start_media_btn;
    boolean isStart = false;
    Subscriber<Long> subscriber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_media);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            initMediaPlayer();
        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    private void initMediaPlayer(){
        try {
            mediaPlayer = new MediaPlayer();
            AssetFileDescriptor assetFileDescriptor = this.getAssets().openFd("mp3/baihualin.mp3");
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());//设置播放资源路径
            mediaPlayer.prepare();//准备
            progress_sb.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.start_media_btn)
    void start(){
        String text = start_media_btn.getText().toString();
        boolean isPlay = "播放".equals(text);
        start_media_btn.setText(isPlay ? "暂停" : "播放");
        if (isPlay) {
            play();
        }else {
            pause();
        }

    }

    /**
     * 播放
     */
    void play(){
        if (null != mediaPlayer && !mediaPlayer.isPlaying()){
            mediaPlayer.start();
            isStart = true;
            progress();
        }
    }

    /**
     * 暂停
     */
    void pause(){
        //暂停
        if (null != mediaPlayer && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            isStart = false;
            progress();
        }
    }
    @OnClick(R.id.stop_media_btn)
    void stop(){
        //停止
        if (null != mediaPlayer && mediaPlayer.isPlaying()){
            mediaPlayer.reset();
            progress_sb.setProgress(0);
        }
    }

    private void progress(){
        if (!isStart){
            if (subscriber != null && !subscriber.isUnsubscribed()){
                subscriber.unsubscribe();
                subscriber = null;
            }
            return;
        }
        subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                if (null != mediaPlayer) {
                    if (mediaPlayer.getCurrentPosition() == mediaPlayer.getDuration()) {
                        //说明播放完成
                        progress_sb.setProgress(0);
                        isStart = false;
                        start_media_btn.setText("播放");
                        progress();
                    }else{
                        progress_sb.setProgress(mediaPlayer.getCurrentPosition());
                    }
                }
            }
        };
        Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mediaPlayer){
            mediaPlayer.start();
            mediaPlayer.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initMediaPlayer();
            }else {
                Log.e(TAG,"----------------------------权限未开启");
            }
        }
    }
}
