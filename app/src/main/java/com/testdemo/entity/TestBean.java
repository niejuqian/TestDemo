package com.testdemo.entity;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 11 17:04
 * @DESC：
 */

public class TestBean {
    int resId;
    String content;

    public TestBean(int resId, String content) {
        this.resId = resId;
        this.content = content;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "resId=" + resId +
                ", content='" + content + '\'' +
                '}';
    }
}
