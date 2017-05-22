package com.testdemo.mvppattern;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 17 16:52
 * @DESC：
 */

public abstract class IBasePresent<T> {
    protected Reference<T> mViewRef;
    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    protected T getView(){
        return mViewRef.get();
    }

    public boolean isViewAttached(){
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView(){
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
