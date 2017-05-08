package com.testdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testdemo.R;
import com.testdemo.entity.PhoneInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 02 13:43
 * @DESC：
 */

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneHolder>{
    private List<PhoneInfo> phoneInfos = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PhoneAdapter(List<PhoneInfo> list) {
        if (list != null) {
            phoneInfos.addAll(list);
        }
    }

    public void notifyData(List<PhoneInfo> phoneInfos) {
        if (phoneInfos != null) {
            this.phoneInfos.clear();
            this.phoneInfos.addAll(phoneInfos);
        }
        notifyDataSetChanged();
    }
    @Override
    public PhoneHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_phone_item,parent,false);
        PhoneHolder holder = new PhoneHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PhoneHolder holder, int position) {
        final PhoneInfo info = phoneInfos.get(position);
        if (null != info) {
            holder.phoneTv.setText(info.getPhone());
            holder.nameTv.setText(info.getName());
        }
        holder.phone_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onCLick(info);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneInfos == null ? 0 : phoneInfos.size();
    }

    class PhoneHolder extends RecyclerView.ViewHolder{
        TextView phoneTv;
        TextView nameTv;
        LinearLayout phone_item_layout;
        public PhoneHolder(View itemView) {
            super(itemView);
            phoneTv = (TextView) itemView.findViewById(R.id.phone_tv);
            nameTv = (TextView) itemView.findViewById(R.id.name_tv);
            phone_item_layout = (LinearLayout) itemView.findViewById(R.id.phone_item_layout);
        }
    }

    public interface OnItemClickListener{
        void onCLick(PhoneInfo info);
    }
}
