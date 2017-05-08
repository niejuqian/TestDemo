package com.testdemo.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.testdemo.retrofit.callback.CommonParamsInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 26 15:02
 * @DESC：
 */

public class RetrofitControl {
    private static RetrofitControl instance = new RetrofitControl();
    private static final String BASETESTURL = "http://192.168.1.121:8989/legend/interface/";
    //添加okhttp 网络请求，公共参数拦截器
    private CommonParamsInterceptor commonParamsInterceptor;
    private static final int TIME_OUT_TIME = 10;//超时时间
    private Service service;
    private Retrofit retrofit;
    private RetrofitControl(){
        commonParamsInterceptor = new CommonParamsInterceptor();
        //日志输出拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)//添加日志打印拦截器
                .addInterceptor(commonParamsInterceptor)//公共参数添加拦截器
                .connectTimeout(TIME_OUT_TIME, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_TIME,TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_TIME,TimeUnit.SECONDS);


        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASETESTURL)//
                .client(httpBuilder.build())//底层使用的http请求框架
                .addConverterFactory(GsonConverterFactory.create(gson))//数据转换
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加rx支持
                .build();
        service = createApi(Service.class);
    }
    public static RetrofitControl getInstance(){
        return instance;
    }
    public <T> T createApi(Class<T> clazz) {
        return retrofit.create(clazz);
    }
    public Service getService(){
        return service;
    }
    public void updateCommParams(Map<String,String> params){
        this.commonParamsInterceptor.setParams(params);
    }
}
