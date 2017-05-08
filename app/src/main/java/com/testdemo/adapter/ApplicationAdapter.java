package com.testdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testdemo.R;
import com.testdemo.entity.AppInfo;

import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 26 09:22
 * @DESC：
 */

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationHolder> {
    private List<AppInfo> applicationInfos;
    private boolean isGroupFlag;
    public ApplicationAdapter(List<AppInfo> list) {
        this.applicationInfos = list;
    }
    public void isGroup(boolean isGroupFlag){
        this.isGroupFlag = isGroupFlag;
    }

    @Override
    public ApplicationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_application_item,parent,false);
        return new ApplicationHolder(view);
    }

    @Override
    public void onBindViewHolder(ApplicationHolder holder, int position) {
        if (null == applicationInfos) return;
        AppInfo info = applicationInfos.get(position);
        if (isGroupFlag){
            holder.right_type_tv.setVisibility(View.GONE);
            if (position == 0) {
                holder.application_type_tv.setVisibility(View.VISIBLE);
            } else {
                AppInfo pre = applicationInfos.get(position -1);
                if (pre.getAppType() != info.getAppType()) {
                    holder.application_type_tv.setVisibility(View.VISIBLE);
                }else{
                    holder.application_type_tv.setVisibility(View.GONE);
                }
            }
        }else{
            holder.application_type_tv.setVisibility(View.GONE);
            holder.right_type_tv.setVisibility(View.VISIBLE);
        }
        holder.right_type_tv.setText(info.getAppTypeName());
        holder.application_type_tv.setText(info.getAppTypeName());
        holder.application_iv.setBackground(info.getAppIcon());
        holder.application_tv.setText(info.getAppName());
        holder.application_pm_name_tv.setText(info.getPackageName());
    }

    @Override
    public int getItemCount() {
        return applicationInfos==null ? 0 : applicationInfos.size();
    }

    class ApplicationHolder extends RecyclerView.ViewHolder{
        ImageView application_iv;
        TextView application_tv;
        TextView application_pm_name_tv;
        TextView application_type_tv;
        TextView right_type_tv;
        public ApplicationHolder(View itemView) {
            super(itemView);
            application_pm_name_tv = (TextView) itemView.findViewById(R.id.application_pm_name_tv);
            application_iv = (ImageView) itemView.findViewById(R.id.application_iv);
            application_tv = (TextView) itemView.findViewById(R.id.application_tv);
            application_iv = (ImageView) itemView.findViewById(R.id.application_iv);
            application_type_tv = (TextView) itemView.findViewById(R.id.application_type_tv);
            right_type_tv = (TextView) itemView.findViewById(R.id.right_type_tv);
        }
    }
}
