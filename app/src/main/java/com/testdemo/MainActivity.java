package com.testdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.testdemo.animation.AnimationMainActivity;
import com.testdemo.animation.GifActivity;
import com.testdemo.broadcast.MyBroadcastActivity;
import com.testdemo.cache.CacheActivity;
import com.testdemo.news.activity.NewsMainActivity;
import com.testdemo.notification.NotificationReceiver;
import com.testdemo.other.DataActivity;
import com.testdemo.other.FullScreenActivity;
import com.testdemo.other.WebViewActivity;
import com.testdemo.permission.PermissionActivity;
import com.testdemo.progress.ProgressActivity;
import com.testdemo.recyclerview.RecyclerviewActivity;
import com.testdemo.retrofit.RetrofitActivity;
import com.testdemo.rxjava.RxJavaActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseAppCompatActivity {
    NotificationReceiver notificationReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_main);
        EventBus.getDefault().register(this);
        /*ButtonClickListener buttonClickListener = new ButtonClickListener();
        LinearLayout layout_content = (LinearLayout) findViewById(R.id.layout_content);
        for (int i = 0; i < layout_content.getChildCount(); i++) {
            View view = layout_content.getChildAt(i);
            if (view instanceof Button) {
                view.setOnClickListener(buttonClickListener);
            }
        }*/
        notificationReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(notificationReceiver,filter);
    }

    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Class<? extends Activity> cls = null;
            switch (v.getId()) {
                case R.id.cache_btn:
                    cls = CacheActivity.class;
                    break;
                case R.id.set_full_screen_btn:
                    cls = FullScreenActivity.class;
                    break;
                case R.id.keyboard_btn:
                    cls = WebViewActivity.class;
                    break;
                case R.id.show_gif_btn:
                    cls = GifActivity.class;
                    break;
                case R.id.animation_btn:
                    cls = AnimationMainActivity.class;
                    break;
                case R.id.data_btn:
                    cls = DataActivity.class;
                    break;
                case R.id.recyclerview_btn:
                    cls = RecyclerviewActivity.class;
                    break;
                case R.id.fragment_btn:
                    //显示动画
                    cls = NewsMainActivity.class;
                    break;
                case R.id.share_btn:
                    cls = com.testdemo.share.MainActivity.class;
                    break;
                case R.id.broadcast_btn:
                    cls = MyBroadcastActivity.class;
                    break;
                case R.id.rx_btn:
                    cls = RxJavaActivity.class;
                    break;
                case R.id.webview_btn:
                    cls = com.testdemo.webview.WebViewActivity.class;
                    break;
                case R.id.progress_btn:
                    cls = ProgressActivity.class;
                    break;
                case R.id.retrofit_btn:
                    cls = RetrofitActivity.class;
                    break;
                case R.id.permission_btn:
                    cls = PermissionActivity.class;
                    break;
            }
            if (null != cls) {
                startActivity(new Intent(MainActivity.this, cls));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshNum(String event) {
        Log.e("MainActivity","--------------通知刷新页面消息数据" + event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (notificationReceiver != null) {
            unregisterReceiver(notificationReceiver);
        }
    }
}
