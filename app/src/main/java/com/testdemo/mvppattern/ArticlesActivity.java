package com.testdemo.mvppattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.testdemo.R;
import com.testdemo.retrofit.bean.ShopInfo;

import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 17 16:51
 * @DESC：
 */

public class ArticlesActivity extends IMVPBaseActivity<IArticlesView,ArticlesPresentImpl> implements IArticlesView{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_articles);
        mPresenter.fetchShopList();
    }

    @Override
    protected ArticlesPresentImpl createPresenter() {
        return new ArticlesPresentImpl();
    }

    @Override
    public void showLoading() {
        Log.e(TAG,"--------------------showLoading");
    }

    @Override
    public void hideLoading() {
        Log.e(TAG,"--------------------hideLoading");
    }

    @Override
    public void showShopList(List<ShopInfo> shopInfos) {
        Log.e(TAG,"------------shopInfos：" + shopInfos);
    }
}
