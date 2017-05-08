package com.testdemo.retrofit;

import com.testdemo.retrofit.bean.AccountData;
import com.testdemo.retrofit.bean.SearchShopResp;
import com.testdemo.retrofit.bean.ShopData;
import com.testdemo.retrofit.callback.HttpResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 26 14:55
 * @DESC：
 */

public interface Service {
    @GET("public/shop/searchShop?cityCode=440306&orderBy=5&longitude=113.887239&latitude=22.548752")
    Call<SearchShopResp> searchShop();

    @GET("public/shop/searchShop?cityCode=440306&orderBy=5&longitude=113.887239&latitude=22.548752")
    Observable<HttpResult<ShopData>> searchShop1();



    @GET("user/getAccountMoney?userId=123456")
    Observable<HttpResult<AccountData>> getAccountMoney();
}
