package com.testdemo.broadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 18 11:54
 * @DESC：
 */

public class MyBroadcastActivity extends BaseAppCompatActivity {
    MyBroadcastReceiver myBroadcastReceiver;
    LocalBroadcastReceiver localBroadcastReceiver;
    LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_broadcast);
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyBroadcastReceiver.FILTER_ACTION);
        registerReceiver(myBroadcastReceiver,intentFilter);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastReceiver = new LocalBroadcastReceiver();
        IntentFilter localFilter = new IntentFilter(LocalBroadcastReceiver.FILTER_ACTION);
        localBroadcastManager.registerReceiver(localBroadcastReceiver,localFilter);
    }

    @OnClick({R.id.sys_broadcast_btn,R.id.sys_order_broadcast_btn,R.id.local_broadcast_btn,R.id.force_offline_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sys_broadcast_btn:
                sendBroadcast(new Intent(MyBroadcastReceiver.FILTER_ACTION));
                break;
            case R.id.sys_order_broadcast_btn:
                sendOrderedBroadcast(new Intent(MyBroadcastReceiver.FILTER_ACTION),null);
                break;
            case R.id.local_broadcast_btn:
                if (localBroadcastManager != null) {
                    localBroadcastManager.sendBroadcast(new Intent(LocalBroadcastReceiver.FILTER_ACTION));
                }
                break;
            case R.id.force_offline_btn:
                if (null !=localBroadcastManager) {
                    localBroadcastManager.sendBroadcast(new Intent(ForceOfflineReceiver.FILTER_ACTION));
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != myBroadcastReceiver) {
            unregisterReceiver(myBroadcastReceiver);
        }
        if (null != localBroadcastReceiver && null != localBroadcastManager) {
            localBroadcastManager.unregisterReceiver(localBroadcastReceiver);
        }
    }
}
