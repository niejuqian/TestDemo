package com.testdemo.seniorui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;
import com.testdemo.adapter.CategoryAdapter;
import com.testdemo.adapter.GoodsAdapter;
import com.testdemo.entity.Category;
import com.testdemo.entity.Goods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 22 15:32
 * @DESC：
 */

public class MulitActivity extends BaseAppCompatActivity {


   /* @BindView(R.id.mulitListView)
    MulitListView mulitListView;*/

    private List<Category> categoryList = new ArrayList<>();
    private List<Goods> goodsList = new ArrayList<>();
    @BindView(R.id.left_rv)
    RecyclerView leftRv;
    @BindView(R.id.right_rv)
    RecyclerView rightRv;
    private CategoryAdapter categoryAdapter;
    private GoodsAdapter goodsAdapter;
    private long selectCategoryId = -1;//当前选中的分类
    private int[] ress = new int[]{R.mipmap.orange,R.mipmap.banana,R.mipmap.cherry};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_mulit_list);
        initView();
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                initData();
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        //mulitListView.refresh(categoryList,goodsList);
                        refresh();
                    }
                });
    }

    private void initView() {
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        leftRv.setLayoutManager(layoutManager1);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rightRv.setLayoutManager(layoutManager2);

        categoryAdapter = new CategoryAdapter(categoryList);
        categoryAdapter.setCategroyItemClickListener(new CategoryAdapter.CategroyItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                Log.e(TAG, "----------选中的分类：" + category);
                if (selectCategoryId == category.getCategoryId()) return;
                selectCategoryId = category.getCategoryId();
                categoryAdapter.setSelectCategoryId(selectCategoryId);
                categoryAdapter.notifyDataSetChanged();
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

    public void refresh() {
        if (null != categoryList && categoryList.size() > 0) {
            selectCategoryId = categoryList.get(0).getCategoryId();
        }
        categoryAdapter.setSelectCategoryId(selectCategoryId);
        categoryAdapter.notifyDataSetChanged();
        refreshGoods(selectCategoryId);
    }


    private void refreshGoods(final long categoryId) {
        final long start = System.currentTimeMillis();
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


    public void initData(){
        Random random = new Random();
        int categoryLen = random.nextInt(20) + 1;
        for (int i = 0; i < categoryLen; i++) {
            categoryList.add(new Category(i,"我是分类" + i));
        }
        Log.e(TAG,"------category：" + categoryList);
        long goodsId = 1;
        for (Category category : categoryList) {
            int goodsLen = random.nextInt(20) + 1;
            for (int i = 0; i < goodsLen; i++) {
                goodsList.add(new Goods(goodsId,category.getCategoryName() + "的商品" + i,88.88,ress[random.nextInt(3)],category.getCategoryId()));
                ++goodsId;
            }
        }
        Log.e(TAG,"------goodsList：" + goodsList);
    }
}
