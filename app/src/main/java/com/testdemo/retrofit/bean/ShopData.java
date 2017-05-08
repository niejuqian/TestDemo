package com.testdemo.retrofit.bean;

import java.util.List;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 26 14:57
 * @DESC：
 */

public class ShopData {
    private Integer pNo;
    private Integer pSize;
    private Integer rCount;
    private List<ShopInfo> lst;

    public Integer getpNo() {
        return pNo;
    }

    public void setpNo(Integer pNo) {
        this.pNo = pNo;
    }

    public Integer getpSize() {
        return pSize;
    }

    public void setpSize(Integer pSize) {
        this.pSize = pSize;
    }

    public Integer getrCount() {
        return rCount;
    }

    public void setrCount(Integer rCount) {
        this.rCount = rCount;
    }

    public List<ShopInfo> getLst() {
        return lst;
    }

    public void setLst(List<ShopInfo> lst) {
        this.lst = lst;
    }

    @Override
    public String toString() {
        return "ShopData{" +
                "pNo=" + pNo +
                ", pSize=" + pSize +
                ", rCount=" + rCount +
                ", lst=" + lst +
                '}';
    }
}
