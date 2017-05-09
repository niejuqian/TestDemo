package com.testdemo.seniorui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;
import com.testdemo.view.seniorui.CircleProgressBar;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 09 11:15
 * @DESC：
 */

public class SeniorUiDrawActivity extends BaseAppCompatActivity {
    @BindView(R.id.view_root_layout)
    LinearLayout view_root_layout;
    @BindView(R.id.my_circle_progress)
    CircleProgressBar my_circle_progress;

    CircleProgressBar circleProgressBar;
    int progress = 0;
    int max = 500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_senior_ui);
        circleProgressBar = new CircleProgressBar(this).setMax(max);
        view_root_layout.addView(getCircleProgressBarView());
    }

    @OnClick(R.id.circ_progress_bar_btn)
    void circleProgressBar() {
        view_root_layout.removeAllViews();
        view_root_layout.addView(getCircleProgressBarView());
        new Thread(new Runnable() {
            Random random = new Random();

            @Override
            public void run() {
                while (progress < max) {
                    progress += random.nextInt(3);
                    if (progress >= max) {
                        progress = max;
                    }
                    circleProgressBar.setProgress(progress).refresh();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private View getCircleProgressBarView() {
        return circleProgressBar;
    }
}
