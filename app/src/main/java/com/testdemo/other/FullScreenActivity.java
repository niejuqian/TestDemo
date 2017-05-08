package com.testdemo.other;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.testdemo.R;

import qiu.niorgai.StatusBarCompat;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 01 17 16:48
 * @DESC：
 */
public class FullScreenActivity extends Activity {
    Button full_screen_btn;
    boolean flag = true;
    View mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_full_screen);
        full_screen_btn = (Button) findViewById(R.id.full_screen_btn);
        full_screen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullScreen();
            }
        });
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public void fullScreen(){
        if (flag) {
            StatusBarCompat.translucentStatusBar(this);
        }else {
            StatusBarCompat.setStatusBarColor(this, Color.parseColor("#00000000"),50);
        }
        flag = !flag;
    }
}
