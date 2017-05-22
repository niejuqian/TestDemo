package com.testdemo.mvppattern;

import com.testdemo.retrofit.bean.ShopInfo;

import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 17 16:54
 * @DESC：
 */

public interface IArticlesView {
    /**
     * 显示加载进度
     */
    void showLoading();

    /**
     * 隐藏加载进度
     */
    void hideLoading();

    /**
     * 显示数据
     * @param shopInfos
     */
    void showShopList(List<ShopInfo> shopInfos);
}
