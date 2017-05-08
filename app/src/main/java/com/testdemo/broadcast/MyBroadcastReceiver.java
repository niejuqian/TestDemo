package com.testdemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 18 11:53
 * @DESC：
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    public static final String FILTER_ACTION = "com.textdemo.broadcast.BROAD_CAST_RECEIVER";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"我收到了:" + intent.getAction(),Toast.LENGTH_SHORT).show();
    }
}
