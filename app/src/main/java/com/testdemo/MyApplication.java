package com.testdemo;

import android.app.Application;

import com.testdemo.util.DBHelper;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 05 18:15
 * @DESC：
 */

public class MyApplication extends Application {
    private static Application instance;
    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.getInstance().init(this);
        instance = this;
    }

    public static Application getInstance(){
        return instance;
    }
}
