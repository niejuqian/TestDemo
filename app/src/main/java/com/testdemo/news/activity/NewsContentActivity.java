package com.testdemo.news.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;
import com.testdemo.entity.News;
import com.testdemo.news.fragment.NewsContentFragment;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 12 15:26
 * @DESC：
 */

public class NewsContentActivity extends BaseAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_new_content);
        News news = (News) getIntent().getSerializableExtra("newsData");
        if (null != news) {
            NewsContentFragment fragment = (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.new_content_fragment);
            if (null != fragment) {
                fragment.refresh(news);
            }
        }
    }

    public static void actionStart(Context context,News news) {
        Intent intent = new Intent(context,NewsContentActivity.class);
        intent.putExtra("newsData",news);
        context.startActivity(intent);

    }
}
