package com.testdemo.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.testdemo.BaseAppCompatActivity;
import com.testdemo.R;
import com.testdemo.retrofit.bean.AccountData;
import com.testdemo.retrofit.bean.SearchShopResp;
import com.testdemo.retrofit.bean.ShopData;
import com.testdemo.retrofit.bll.SearchBll;
import com.testdemo.retrofit.callback.ErrCallback;
import com.testdemo.retrofit.callback.SuccCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 26 14:53
 * @DESC：
 */

public class RetrofitActivity extends BaseAppCompatActivity {
    SearchBll searchBll;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,R.layout.activity_retrofit);
        searchBll = new SearchBll();
    }

    @OnClick(R.id.sync_get_data_btn)
    void syncGetData(){
        Call<SearchShopResp> call = RetrofitControl.getInstance().getService().searchShop();
        Response<SearchShopResp> respResponse = null;
        SearchShopResp searchShopResp = null;
        try {
            respResponse = call.execute();
            searchShopResp = respResponse.body();
            Log.e(TAG,"----response：" + searchShopResp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.get_data_btn)
    void getData(){
        Call<SearchShopResp> call = RetrofitControl.getInstance().getService().searchShop();
        call.enqueue(new Callback<SearchShopResp>() {
            @Override
            public void onResponse(Call<SearchShopResp> call, Response<SearchShopResp> response) {
                SearchShopResp resp = response.body();
                Log.e(TAG,"----response：" + resp);
                Log.e(TAG,"----call：" + call);
            }

            @Override
            public void onFailure(Call<SearchShopResp> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.rxjava_retrofit_btn)
    void getData1(){
        searchBll.searchShop(new SuccCallback<ShopData>() {
            @Override
            public void call(ShopData searchShopResp) {
                //响应成功
                Log.e(TAG,"----success：" + searchShopResp);
            }
        }, new ErrCallback<Integer, String>() {
            @Override
            public void call(Integer code, String msg) {
                //异常
                Log.e(TAG,"----error：" + code + "-" + msg);
            }
        });
    }

    @OnClick(R.id.rxjava_retrofit_err_btn)
    void getErrorData(){
        EventBus.getDefault().post("0000");
        Map<String,String> params = new HashMap<>();
        params.put("version","1.1.1");
        params.put("osinfo","Android");
        RetrofitControl.getInstance().updateCommParams(params);


        searchBll.getAccountMoney(new SuccCallback<AccountData>() {
            @Override
            public void call(AccountData accountData) {
                //响应成功
                Log.e(TAG,"----success：" + accountData);
            }
        }, new ErrCallback<Integer, String>() {
            @Override
            public void call(Integer code, String msg) {
                Log.e(TAG,"----error：" + code + "-" + msg);
            }
        });
    }
}
