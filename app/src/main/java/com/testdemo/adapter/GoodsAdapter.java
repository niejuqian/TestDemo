package com.testdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.testdemo.R;
import com.testdemo.entity.Goods;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 22 15:07
 * @DESC：
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsHolder>{
    private List<Goods> goodsList = new ArrayList<>();
    private GoodsItemClickListener goodsItemClickListener;
    private Context context;
    public GoodsAdapter() {
    }

    public void notifyData(List<Goods> goodses) {
        this.goodsList.clear();
        this.goodsList.addAll(goodses);
        notifyDataSetChanged();
    }

    @Override
    public GoodsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_right_item,parent,false);
        GoodsHolder holder = new GoodsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GoodsHolder holder, int position) {
        final Goods goods = goodsList.get(position);
        Glide.with(context).load(goods.getGoodsLogo()).into(holder.goods_logo_iv);
        holder.goods_name_tv.setText(goods.getGoodsName());
        holder.goods_price_tv.setText(goods.getGoodsPrice()+"");
        holder.right_root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goodsItemClickListener != null) {
                    goodsItemClickListener.itemClick(goods);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsList == null ? 0 : goodsList.size();
    }

    class GoodsHolder extends RecyclerView.ViewHolder{
        LinearLayout right_root_view;
        CircleImageView goods_logo_iv;
        TextView goods_name_tv;
        TextView goods_price_tv;
        public GoodsHolder(View itemView) {
            super(itemView);
            right_root_view = (LinearLayout) itemView.findViewById(R.id.right_root_view);
            goods_logo_iv = (CircleImageView) itemView.findViewById(R.id.goods_logo_iv);
            goods_name_tv = (TextView) itemView.findViewById(R.id.goods_name_tv);
            goods_price_tv = (TextView) itemView.findViewById(R.id.goods_price_tv);
        }
    }

    public void setGoodsItemClickListener(GoodsItemClickListener goodsItemClickListener) {
        this.goodsItemClickListener = goodsItemClickListener;
    }

    public interface GoodsItemClickListener{
        void itemClick(Goods goods);
    }
}
