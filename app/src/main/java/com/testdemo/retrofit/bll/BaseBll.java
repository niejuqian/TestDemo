package com.testdemo.retrofit.bll;

import com.testdemo.retrofit.RetrofitControl;
import com.testdemo.retrofit.Service;
import com.testdemo.retrofit.bean.busienum.RespCodeEnum;
import com.testdemo.retrofit.callback.HttpResult;
import com.testdemo.retrofit.exception.BllException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 27 11:08
 * @DESC：业务处理父类
 */

public class BaseBll {
    private Service service;
    public BaseBll(){
        service = RetrofitControl.getInstance().getService();
    }
    public <T> Observable<T> observable(Observable<HttpResult<T>> observable) {
        return observable
                .flatMap(new Func1<HttpResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(final HttpResult<T> httpResult) {
                        if (httpResult.getCode() != RespCodeEnum.SUCCESS.getCode()) {
                            return Observable.error(new BllException(httpResult.getCode(),httpResult.getMsg()));
                        } else {
                            return Observable.just(httpResult.getData());
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public <T> Observable.Transformer<HttpResult<T>,T> tTransformer(){
        return new Observable.Transformer<HttpResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<HttpResult<T>> httpResultObservable) {
                return observable(httpResultObservable);
            }
        };
    }
    public Service getService() {
        return service;
    }
}
