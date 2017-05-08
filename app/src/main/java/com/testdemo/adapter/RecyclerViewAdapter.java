package com.testdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.testdemo.R;
import com.testdemo.entity.RecyclerViewEnum;
import com.testdemo.entity.TestBean;

import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 11 17:05
 * @DESC：
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ItemClickListener listener;
    private List<TestBean> list;
    private RecyclerViewEnum recyclerViewEnum;
    public RecyclerViewAdapter(List<TestBean> list,RecyclerViewEnum recyclerViewEnum) {
        this.list = list;
        this.recyclerViewEnum = recyclerViewEnum;
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (recyclerViewEnum == RecyclerViewEnum.STAGGERED_GRID || recyclerViewEnum == RecyclerViewEnum.GRID) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recyclerview_staggered_item,parent,false);
        } else if (recyclerViewEnum == RecyclerViewEnum.HORIZONTAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recyclerview_horizontal_item,parent,false);
        }else if (recyclerViewEnum == RecyclerViewEnum.VERTICAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recyclerview_vertical_item,parent,false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final TestBean bean = list.get(position);
        holder.imageView.setBackgroundResource(bean.getResId());
        holder.textView.setText(bean.getContent());
        holder.item_layout_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onItemClick(position,bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        RelativeLayout item_layout_rl;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            item_layout_rl = (RelativeLayout) itemView.findViewById(R.id.item_layout_rl);
        }
    }

    public interface ItemClickListener{
        void onItemClick(int position,TestBean testBean);
    }
}
