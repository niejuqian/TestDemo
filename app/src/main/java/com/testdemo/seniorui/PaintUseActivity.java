package com.testdemo.seniorui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.view.seniorui.ShaderView;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 10 16:20
 * @DESC：
 */

public class PaintUseActivity extends BaseAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState, R.layout.activity_paint_use);
        super.onCreate(savedInstanceState);
        setContentView(new ShaderView(this));
    }
}
