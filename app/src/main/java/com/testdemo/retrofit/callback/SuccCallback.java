package com.testdemo.retrofit.callback;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 27 10:27
 * @DESC：成功回调
 */

public interface SuccCallback<T> {
    void call(T t);
}
