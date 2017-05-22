package com.testdemo.mvppattern;

import com.testdemo.retrofit.bean.ShopData;
import com.testdemo.retrofit.bll.SearchBll;
import com.testdemo.retrofit.callback.ErrCallback;
import com.testdemo.retrofit.callback.SuccCallback;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 17 16:53
 * @DESC：
 */

public class ArticlesPresentImpl extends IBasePresent<IArticlesView> {
    private IArticlesView articlesView;
    private SearchBll searchBll;

    public ArticlesPresentImpl() {
        this.articlesView = getView();
        searchBll = new SearchBll();
    }

    public void fetchShopList(){
        articlesView.showLoading();
        searchBll.searchShop(new SuccCallback<ShopData>() {
            @Override
            public void call(ShopData shopData) {
                articlesView.showShopList(shopData.getLst());
                articlesView.hideLoading();
            }
        }, new ErrCallback<Integer, String>() {
            @Override
            public void call(Integer integer, String s) {
                articlesView.hideLoading();
            }
        });
    }
}
