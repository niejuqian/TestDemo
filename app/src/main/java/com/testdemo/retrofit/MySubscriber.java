package com.testdemo.retrofit;

import com.testdemo.retrofit.bean.busienum.RespCodeEnum;
import com.testdemo.retrofit.callback.EndCallback;
import com.testdemo.retrofit.callback.ErrCallback;
import com.testdemo.retrofit.callback.StartCallback;
import com.testdemo.retrofit.callback.SuccCallback;
import com.testdemo.retrofit.exception.BllException;

import rx.Subscriber;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 27 10:42
 * @DESC：
 */

public class MySubscriber<T> extends Subscriber<T> {
    private SuccCallback<T> succCallback;//成功回调
    private ErrCallback errCallback;//失败回调
    private StartCallback startCallback;//开始调用前回调
    private EndCallback endCallback;//完成回调，不管成功、失败都会回调
    public MySubscriber(){
    }
    public MySubscriber(SuccCallback<T> succCallback){
        this.succCallback = succCallback;
    }
    public MySubscriber(EndCallback endCallback){
        this.endCallback = endCallback;
    }
    public MySubscriber(SuccCallback<T> succCallback, ErrCallback errCallback){
        this.succCallback = succCallback;
        this.errCallback = errCallback;
    }
    @Override
    public void onStart() {
        if (null != startCallback) startCallback.call();
    }
    @Override
    public void onCompleted() {
        if (null != endCallback) endCallback.call();
    }
    @Override
    public void onNext(T t) {
        onSuccess(t);
    }
    /**
     * 统一异常处理分发
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof BllException) {
            BllException exception = (BllException) e;
            onError(exception.getCode(),exception.getMsg());
        } else {
            onError(RespCodeEnum.SYS_ERROR.getCode(),RespCodeEnum.SYS_ERROR.getMsg());
        }
    }
    public void onSuccess(T t){
        if (null != succCallback) {
            succCallback.call(t);
        }
    }

    public void onError(int code,String msg){
        if (null != errCallback) {
            errCallback.call(code,msg);
        }
    }


    public void setSuccCallback(SuccCallback<T> succCallback) {
        this.succCallback = succCallback;
    }

    public void setErrCallback(ErrCallback errCallback) {
        this.errCallback = errCallback;
    }

    public void setStartCallback(StartCallback startCallback) {
        this.startCallback = startCallback;
    }

    public void setEndCallback(EndCallback endCallback) {
        this.endCallback = endCallback;
    }
}
