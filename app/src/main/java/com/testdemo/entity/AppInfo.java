package com.testdemo.entity;

import android.graphics.drawable.Drawable;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 26 09:40
 * @DESC：
 */

public class AppInfo implements Cloneable{
    private String appName;//应用名称
    private Drawable appIcon;//应用图标
    private String packageName;//应用包名
    private String appTypeName;
    private int appType;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public String getAppTypeName() {
        return appTypeName;
    }

    public void setAppTypeName(String appTypeName) {
        this.appTypeName = appTypeName;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", appIcon=" + appIcon +
                ", packageName='" + packageName + '\'' +
                ", appTypeName='" + appTypeName + '\'' +
                ", appType=" + appType +
                '}';
    }

    @Override
    public AppInfo clone() {
        AppInfo appInfo = null;
        try {
            appInfo = (AppInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return appInfo;
    }
}
