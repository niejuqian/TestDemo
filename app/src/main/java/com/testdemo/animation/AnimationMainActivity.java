package com.testdemo.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.testdemo.R;
import com.testdemo.BaseAppCompatActivity;

import butterknife.BindView;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 14 11:26
 * @DESC：
 */

public class AnimationMainActivity extends BaseAppCompatActivity {
    @BindView(R.id.sys_animation_btn)
    Button sys_animation_btn;
    @BindView(R.id.shake_animation_btn)
    Button shake_animation_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_animation_main);
        shake_animation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ShakeAnimationActivity.class);
            }
        });
        sys_animation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AnimationActivity.class);
            }
        });
    }
}
