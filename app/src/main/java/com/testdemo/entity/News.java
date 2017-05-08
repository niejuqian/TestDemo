package com.testdemo.entity;

import java.io.Serializable;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 12 15:15
 * @DESC：
 */

public class News implements Serializable{
    private String title;
    private String content;

    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
