package com.testdemo.animation;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.testdemo.R;
import com.testdemo.BaseAppCompatActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 14 10:08
 * @DESC：
 */

public class ShakeAnimationActivity extends BaseAppCompatActivity{
    @BindView(R.id.vertical_btn)
    Button vertical_btn;
    @BindView(R.id.horizontal_btn)
    Button horizontal_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_shake);
    }


    @OnClick({R.id.vertical_btn,R.id.horizontal_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vertical_btn:
                //垂直抖动
                vertical_btn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake_vertical));
                break;
            case R.id.horizontal_btn:
                //水平抖动
                horizontal_btn.startAnimation(AnimationUtils.loadAnimation(this,R.anim.shake_horicontal));
                break;
        }
    }
}
