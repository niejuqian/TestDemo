package com.testdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 12 16:26
 * @DESC：
 */

public class BaseAppCompatActivity extends AppCompatActivity {
    public static String TAG;
    private LocalBroadcastManager localBroadcastManager;
    private ForceOfflineReceiver forceOfflineReceiver;
    private Activity mActivity;
    protected void onCreate(@Nullable Bundle savedInstanceState,int layoutId) {
        //禁止横屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.hide();
        }
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        mActivity = this;
        TAG = this.getClass().getSimpleName();
    }

    protected void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this,cls));
    }

    @Override
    protected void onResume() {
        super.onResume();
        forceOfflineReceiver = new ForceOfflineReceiver();
        IntentFilter filter = new IntentFilter(BaseAppCompatActivity.ForceOfflineReceiver.FILTER_ACTION);
        if (null != localBroadcastManager) {
            localBroadcastManager.registerReceiver(forceOfflineReceiver,filter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != localBroadcastManager && forceOfflineReceiver != null) {
            localBroadcastManager.unregisterReceiver(forceOfflineReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager = null;
    }


    public class ForceOfflineReceiver extends BroadcastReceiver {
        public static final String FILTER_ACTION = "com.testdemo.FORCE_OFFLINE_RECEIVER";
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"收到离线通知",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("离线通知");
            builder.setMessage("你的账号的其他地方登陆，请重新登录或离开");
            builder.setCancelable(false);
            builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
        }
    }
}
