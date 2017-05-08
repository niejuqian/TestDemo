package com.testdemo.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testdemo.R;
import com.testdemo.entity.News;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 12 15:20
 * @DESC：
 */

public class NewsContentFragment extends Fragment {
    View view;
    private TextView titleTv,contentTv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_frag,container,false);
        titleTv = (TextView) view.findViewById(R.id.title_tv);
        contentTv = (TextView) view.findViewById(R.id.content_tv);
        return view;
    }

    public void refresh(News news) {
        titleTv.setText(news.getTitle());
        contentTv.setText(news.getContent());
    }

}
