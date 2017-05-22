package com.testdemo.mulitthread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;

import butterknife.OnClick;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 18 14:24
 * @DESC：
 */

public class MulitThreadActivity extends BaseAppCompatActivity {
    Handler mMainHandler,mChildHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_mulit_thread);

        mMainHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.e(TAG, "接收到子线程回馈的消息 - " + msg.obj);
                return false;
            }
        });

        /*mMainHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.e(TAG, "接收到子线程回馈的消息 - " + msg.obj);
            }
        };*/

        new ChildThread().start();
    }

    @OnClick(R.id.send_msg_btn)
    void sendMsg(){
        if (mChildHandler != null) {
            //发送消息给子线程
            Message childMsg = mChildHandler.obtainMessage();
            childMsg.obj = mMainHandler.getLooper().getThread().getName() + " says Hello";
            Log.e(TAG, "向子线程发送消息 - " + (String)childMsg.obj);
            mChildHandler.sendMessage(childMsg);
        }
    }

    @OnClick(R.id.dddd_btn)
    void dddd(){
        Toast.makeText(this,"dddddd",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mChildHandler) {
            mChildHandler.getLooper().quit();
        }
    }


    class ChildThread extends Thread {
        @Override
        public void run() {
            Log.e(TAG,"--------------------childThread");
            this.setName("ChildThread");
            //在子线程中实例化handler，必须要调用Looper初始化消息队列
            Looper.prepare();
            mChildHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    Log.e(TAG,"接收到主线程发送过来的消息 -" + msg.obj);
                    /**
                     * 这里的操作是在子线程中执行的，可以在此执行一些耗时操作
                     */
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //发送消息给主线程
                    Message message = mMainHandler.obtainMessage();
                    message.obj = "This is " + this.getLooper().getThread().getName() +
                            ".  Did you send me " + (String)msg.obj + "?";
                    Log.e(TAG, "回馈消息给主线程 - " + message.obj);
                    mMainHandler.sendMessage(message);
                }
            };
            Log.e(TAG, "Child handler is bound to - "+ mChildHandler.getLooper().getThread().getName());
            //启动该消息队列
            Looper.loop();
        }
    }
}
