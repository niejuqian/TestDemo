package com.testdemo.retrofit.exception;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 27 10:50
 * @DESC：
 */

public class BllException extends Throwable {
    private int code;
    private String msg;
    public BllException(){
    }


    public BllException(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

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
}
