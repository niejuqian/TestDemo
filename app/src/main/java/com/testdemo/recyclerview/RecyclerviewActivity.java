package com.testdemo.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.testdemo.R;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 11 16:59
 * @DESC：
 */

public class RecyclerviewActivity extends Activity implements View.OnClickListener{
    private Button horizontal_btn,staggered_btn,vertical_btn,grid_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        vertical_btn = (Button) findViewById(R.id.vertical_btn);
        horizontal_btn = (Button) findViewById(R.id.horizontal_btn);
        staggered_btn = (Button) findViewById(R.id.staggered_btn);
        grid_btn = (Button) findViewById(R.id.grid_btn);
        vertical_btn.setOnClickListener(this);
        staggered_btn.setOnClickListener(this);
        horizontal_btn.setOnClickListener(this);
        grid_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.horizontal_btn:
                //横向滑动列表
                startActivity(new Intent(this, HorizontalActivity.class));
                break;
            case R.id.vertical_btn:
                //垂直列表
                startActivity(new Intent(this, VerticalActivity.class));
                break;
            case R.id.staggered_btn:
                //瀑布式列表
                startActivity(new Intent(this, StaggeredGridLayoutManagerActivity.class));
                break;
            case R.id.grid_btn:
                //瀑布式列表
                startActivity(new Intent(this, GridLayoutActivity.class));
                break;
        }
    }
}
