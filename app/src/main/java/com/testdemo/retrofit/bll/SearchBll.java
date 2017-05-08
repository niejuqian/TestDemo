package com.testdemo.retrofit.bll;

import com.testdemo.retrofit.MySubscriber;
import com.testdemo.retrofit.bean.AccountData;
import com.testdemo.retrofit.bean.ShopData;
import com.testdemo.retrofit.callback.ErrCallback;
import com.testdemo.retrofit.callback.SuccCallback;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 27 10:29
 * @DESC：业务处理类
 */

public class SearchBll extends BaseBll{
    public void searchShop(SuccCallback<ShopData> success, ErrCallback<Integer,String> error){
        observable(getService().searchShop1()).subscribe(new MySubscriber<ShopData>(success,error));
        /*observable(getService().searchShop1()).subscribe(new MySubscriber<SearchShopResp>(){
            @Override
            public void onDownloadSuccess(SearchShopResp searchShopResp) {
                success.call(searchShopResp);
            }

            @Override
            public void onError(int code, String msg) {
                error.call(code, msg);
            }
        });*/
    }

    /**
     * 方式一
     * @param succ 成功回调
     * @param error 失败回调
     */
    public void getAccountMoney(SuccCallback<AccountData> succ, ErrCallback<Integer,String> error){
        getService().getAccountMoney().compose(this.<AccountData>tTransformer()).subscribe(new MySubscriber<AccountData>(succ,error));
    }


    /**
     * 方式二
     * @param succ 成功回调
     * @param error 失败回调
     */
    public void getAccountMoney1(final SuccCallback<AccountData> succ, final ErrCallback<Integer,String> error){
        getService().getAccountMoney().compose(this.<AccountData>tTransformer()).subscribe(new MySubscriber<AccountData>(){
            @Override
            public void onSuccess(AccountData accountData) {
                //在这里处理其他业务，如果不需要，直接使用上一种方式即可
                if (null != succ) {
                    succ.call(accountData);
                }
            }

            @Override
            public void onError(int code, String msg) {
                if (null != error) {
                    error.call(code,msg);
                }
            }
        });
    }
}
