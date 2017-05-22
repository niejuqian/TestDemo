package com.testdemo.seniorui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.testdemo.R;
import com.testdemo.adapter.CategoryAdapter;
import com.testdemo.adapter.GoodsAdapter;
import com.testdemo.entity.Category;
import com.testdemo.entity.Goods;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 22 14:53
 * @DESC：
 */

public class MulitListView extends LinearLayout {
    private String TAG = MulitListView.class.getSimpleName();
    private View mView;
    private RecyclerView leftRv;
    private RecyclerView rightRv;
    private CategoryAdapter categoryAdapter;
    private GoodsAdapter goodsAdapter;
    //商品列表
    private List<Goods> goodsList = new ArrayList<>();
    //商品分类列表
    private List<Category> categoryList = new ArrayList<>();

    private long selectCategoryId = -1;//当前选中的分类

    public MulitListView(Context context) {
        this(context, null);
        Log.e(TAG, "========================MulitListView1");
    }

    public MulitListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Log.e(TAG, "========================MulitListView2");
    }

    public MulitListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "========================MulitListView3");
        initView(context);
    }

    private void initView(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.view_mulit_list, null);
        addView(mView);
        leftRv = (RecyclerView) mView.findViewById(R.id.left_rv);
        rightRv = (RecyclerView) mView.findViewById(R.id.right_rv);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        leftRv.setLayoutManager(layoutManager1);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(context);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rightRv.setLayoutManager(layoutManager2);

        categoryAdapter = new CategoryAdapter(categoryList);
        categoryAdapter.setCategroyItemClickListener(new CategoryAdapter.CategroyItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                Log.e(TAG, "----------选中的分类：" + category);
                if (selectCategoryId == category.getCategoryId()) return;
                selectCategoryId = category.getCategoryId();
                refreshGoods(category.getCategoryId());
            }
        });
        goodsAdapter = new GoodsAdapter();
        goodsAdapter.setGoodsItemClickListener(new GoodsAdapter.GoodsItemClickListener() {
            @Override
            public void itemClick(Goods goods) {
                Log.e(TAG, "----------选中的商品：" + goods);
            }
        });
        leftRv.setAdapter(categoryAdapter);
        rightRv.setAdapter(goodsAdapter);
    }

    public void refresh(List<Category> categories, List<Goods> goodses) {
        this.categoryList.clear();
        this.categoryList.addAll(categories);
        this.goodsList = goodses;
        categoryAdapter.notifyDataSetChanged();
        if (null != categories && categories.size() > 0) {
            selectCategoryId = categories.get(0).getCategoryId();
        }
        refreshGoods(selectCategoryId);
    }

    public void notifyData() {
        categoryAdapter.notifyDataSetChanged();
        goodsAdapter.notifyDataSetChanged();
    }

    private void refreshGoods(final long categoryId) {
        final long start = System.currentTimeMillis();
        ;
        Observable.from(goodsList)
                .filter(new Func1<Goods, Boolean>() {
                    @Override
                    public Boolean call(Goods goods) {
                        return null != goods && goods.getCategoryId() == categoryId;
                    }
                }).toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Goods>>() {

                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "------------分组耗时：" + (System.currentTimeMillis() - start));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Goods> goodses) {
                        Log.e(TAG, "=================筛选出的商品：" + goodses);
                        goodsAdapter.notifyData(goodses);
                    }
                });
    }


}
