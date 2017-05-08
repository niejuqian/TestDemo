package com.testdemo.md;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;
import com.testdemo.entity.Fruit;

import butterknife.BindView;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 08 16:05
 * @DESC：
 */

public class FruitDetailActivity extends BaseAppCompatActivity {
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pic_tv)
    ImageView pic_tv;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_friut_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Fruit fruit = getIntent().getParcelableExtra("fruit");
        Glide.with(this).load(fruit.getImgId()).into(pic_tv);
        name_tv.setText(fruit.getName());
        collapsing_toolbar.setTitle(fruit.getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
