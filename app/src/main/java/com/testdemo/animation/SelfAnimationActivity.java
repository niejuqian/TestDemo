package com.testdemo.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.testdemo.R;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 05 15:33
 * @DESC：
 */

public class SelfAnimationActivity extends Activity{
    private ImageView open_iv;
    private Button scale_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        open_iv = (ImageView) findViewById(R.id.open_iv);
        scale_btn = (Button) findViewById(R.id.scale_btn);
        scale_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyRotation(0,90);
            }
        });

    }

    private void applyRotation(float start, float end) {
        // 计算中心点
        final float centerX = open_iv.getWidth() / 2.0f;
        final float centerY = open_iv.getHeight() / 2.0f;
        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
                centerX, centerY, 310.0f, true);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        // 设置监听
        rotation.setAnimationListener(new DisplayNextView());
        open_iv.startAnimation(rotation);
    }

    private final class DisplayNextView implements Animation.AnimationListener {
        public void onAnimationStart(Animation animation) {
        }

        // 动画结束
        public void onAnimationEnd(Animation animation) {
            open_iv.post(new SwapViews());
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    private final class SwapViews implements Runnable {
        public void run() {
            final float centerX = open_iv.getWidth() / 2.0f;
            final float centerY = open_iv.getHeight() / 2.0f;
            Rotate3dAnimation rotation = null;
            open_iv.requestFocus();
            rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f,
                    false);
            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());
            // 开始动画
            open_iv.startAnimation(rotation);
        }
    }
}
