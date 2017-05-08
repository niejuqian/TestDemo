package com.testdemo.retrofit.bean.busienum;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 27 10:53
 * @DESC：
 */

public enum RespCodeEnum {
    SUCCESS(0,"操作成功"),
    SYS_ERROR(-1,"系统异常"),
    NET_WORK_ERROR(-2,"网络异常");
    int code;
    String msg;
    RespCodeEnum(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
