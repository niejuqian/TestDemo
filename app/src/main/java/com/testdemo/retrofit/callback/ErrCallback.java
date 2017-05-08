package com.testdemo.retrofit.callback;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 27 10:27
 * @DESC：错误回调
 */

public interface ErrCallback<T1,T2> {
    void call(T1 t1,T2 t2);
}
