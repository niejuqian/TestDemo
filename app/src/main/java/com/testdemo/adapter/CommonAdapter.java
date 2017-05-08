package com.testdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


public abstract class CommonAdapter<T> extends BaseAdapter {

    private List<T> list;

    private Context context;

    private int mItemLayoutId;

    public CommonAdapter(Context context, List<T> list, int mItemLayoutId) {

        this.context = context;
        this.list = list;
        this.mItemLayoutId = mItemLayoutId;
    }

    public Context getContext() {
        return context;
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = getViewHolder(position, convertView, parent);

        convert(viewHolder, getItem(position), position);

        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder viewHolder, T item, int position);


    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(getContext(), convertView, parent, mItemLayoutId, position);
    }


}
