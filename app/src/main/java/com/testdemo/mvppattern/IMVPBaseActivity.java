package com.testdemo.mvppattern;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.testdemo.BaseAppCompatActivity;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 17 17:12
 * @DESC：
 */

public abstract class IMVPBaseActivity<V,T extends IBasePresent<V>> extends BaseAppCompatActivity {
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (null != mPresenter) {
            mPresenter.attachView((V)this);
        }
    }

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView();
        }
    }
}
