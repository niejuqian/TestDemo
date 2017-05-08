package com.testdemo.entity;

import java.io.Serializable;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 02 13:45
 * @DESC：
 */

public class PhoneInfo implements Serializable{
    private String phone;
    private String name;

    public PhoneInfo(String phone, String name) {
        this.phone = phone;
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PhoneInfo{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
