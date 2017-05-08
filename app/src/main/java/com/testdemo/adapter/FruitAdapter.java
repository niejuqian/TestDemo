package com.testdemo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.testdemo.R;
import com.testdemo.entity.Fruit;

import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 08 14:02
 * @DESC：
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitHolder> {
    private List<Fruit> fruitList;
    private Context context;
    private ItemClickListener itemClickListener;
    public FruitAdapter(List<Fruit> fruitList) {
        this.fruitList = fruitList;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public FruitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_md_content_item,parent,false);
        return new FruitHolder(view);
    }

    @Override
    public void onBindViewHolder(FruitHolder holder, int position) {
        if (null == fruitList) return;
        final Fruit fruit = fruitList.get(position);
        holder.nameTv.setText(fruit.getName());
        //holder.picIv.setBackgroundResource(fruit.getImgId());
        Glide.with(context).load(fruit.getImgId()).into(holder.picIv);
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != itemClickListener) {
                    itemClickListener.onClick(fruit);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fruitList == null ? 0 : fruitList.size();
    }

    class FruitHolder extends RecyclerView.ViewHolder{
        CardView card_view;
        ImageView picIv;
        TextView nameTv;
        public FruitHolder(View itemView) {
            super(itemView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            picIv = (ImageView) itemView.findViewById(R.id.pic_iv);
            nameTv = (TextView) itemView.findViewById(R.id.name_tv);
        }
    }

    public interface ItemClickListener{
        void onClick(Fruit fruit);
    }

}
