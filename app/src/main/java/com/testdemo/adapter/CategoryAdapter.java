package com.testdemo.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.testdemo.R;
import com.testdemo.entity.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 22 15:00
 * @DESC：
 */

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    private List<Category> categoryList = new ArrayList<>();
    private CategroyItemClickListener categroyItemClickListener;
    private long selectCategoryId = -1;
    public CategoryAdapter(List<Category> categories) {
        this.categoryList = categories;
    }
    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_left_item,parent,false);
        CategoryHolder holder = new CategoryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        final Category category = categoryList.get(position);
        holder.categoryNameTv.setText(category.getCategoryName());
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != categroyItemClickListener) {
                    categroyItemClickListener.onItemClick(category);
                }
            }
        });
        if (selectCategoryId == category.getCategoryId()) {
            holder.rootLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.categoryNameTv.setTextColor(Color.parseColor("#58616d"));
        }else{
            holder.rootLayout.setBackgroundColor(Color.parseColor("#58616d"));
            holder.categoryNameTv.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public int getItemCount() {
        return categoryList == null ? 0 : categoryList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        TextView categoryNameTv;
        LinearLayout rootLayout;
        public CategoryHolder(View itemView) {
            super(itemView);
            categoryNameTv = (TextView) itemView.findViewById(R.id.category_name_tv);
            rootLayout = (LinearLayout) itemView.findViewById(R.id.left_root_view);
        }
    }

    public void setCategroyItemClickListener(CategroyItemClickListener categroyItemClickListener) {
        this.categroyItemClickListener = categroyItemClickListener;
    }

    public interface CategroyItemClickListener{
        void onItemClick(Category category);
    }

    public void setSelectCategoryId(long selectCategoryId) {
        this.selectCategoryId = selectCategoryId;
    }
}
