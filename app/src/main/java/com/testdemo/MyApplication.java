package com.testdemo;

import android.app.Application;

import com.testdemo.util.DBHelper;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 05 18:15
 * @DESC：
 */

public class MyApplication extends Application {
    private static Application instance;
    public static UMShareAPI mShareAPI;
    static{
        PlatformConfig.setQQZone("1106078465", "UPT6Bowa1MOhTdKW");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.getInstance().init(this);
        instance = this;
        mShareAPI = UMShareAPI.get(this);
    }

    public static Application getInstance(){
        return instance;
    }

    public static UMShareAPI getShareAPI(){
        return mShareAPI;
    }
}
