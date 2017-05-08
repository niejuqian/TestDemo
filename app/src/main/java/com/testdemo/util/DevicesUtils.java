package com.testdemo.util;

import com.testdemo.MyApplication;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 03 11:41
 * @DESC：
 */

public class DevicesUtils {
    public static String getPackageName(){
        return MyApplication.getInstance().getPackageName();
    }
}
