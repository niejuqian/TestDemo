package com.testdemo.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testdemo.R;
import com.testdemo.entity.News;
import com.testdemo.news.activity.NewsContentActivity;
import com.testdemo.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 12 15:35
 * @DESC：
 */

public class NewsTitleFragment extends Fragment {
    View view;
    RecyclerView mRecyclerView;
    boolean isTwoPane;//是否双页显示
    List<News> newsList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_title_frag,container,false);
        initNews();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.new_title_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        NewsAdapter newsAdapter = new NewsAdapter(newsList);
        mRecyclerView.setAdapter(newsAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NewsContentFragment fragment = getContentFragment();
        isTwoPane = fragment != null;
        if (isTwoPane) {
            fragment.refresh(newsList.get(0));
        }
    }

    private void initNews(){
        for (int i = 0; i < 50; i++) {
            newsList.add(new News("This is title" + i, StringUtils.getRandomLenName("This is content" + i + ".",20)));
        }
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{
        private List<News> newsList;
        public NewsAdapter(List<News> newses) {
            this.newsList = newses;
        }
        @Override
        public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_title_item,parent,false);
            final NewsViewHolder viewHolder = new NewsViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news = newsList.get(viewHolder.getAdapterPosition());
                    if (isTwoPane) {
                        NewsContentFragment fragment = getContentFragment();
                        fragment.refresh(news);
                    } else {
                        NewsContentActivity.actionStart(getActivity(),news);
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(NewsViewHolder holder, int position) {
            News news = newsList.get(position);
            holder.titleTv.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return null == newsList ? 0 : newsList.size();
        }

        class NewsViewHolder extends RecyclerView.ViewHolder {
            TextView titleTv;
            public NewsViewHolder(View itemView) {
                super(itemView);
                titleTv = (TextView) itemView.findViewById(R.id.item_title_tv);
            }
        }
    }

    public NewsContentFragment getContentFragment(){
        return (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment_layout);
    }
}
