package com.testdemo.retrofit.callback;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 27 14:07
 * @DESC：公共响应参数
 */

public class HttpResult<T> {
    private int code;//返回码
    private String msg;//返回信息
    private T data;//返回数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
