package com.testdemo.retrofit.bean;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 26 14:58
 * @DESC：
 */

public class SearchShopResp {
    private Integer code;
    private String msg;
    private ShopData data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ShopData getData() {
        return data;
    }

    public void setData(ShopData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SearchShopResp{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
