package com.testdemo.seniorui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;

import butterknife.OnClick;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 11 13:39
 * @DESC：
 */

public class WaterFallActivity extends BaseAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_water_fall);
    }
    @OnClick(R.id.add_image_btn)
    void addImage(){

    }
}
