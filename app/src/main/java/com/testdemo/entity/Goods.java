package com.testdemo.entity;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 22 14:56
 * @DESC：
 */

public class Goods {
    private long goodsId;
    private String goodsName;
    private double goodsPrice;
    private int goodsLogo;
    private long categoryId;

    public Goods(long goodsId, String goodsName, double goodsPrice, int goodsLogo, long categoryId) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsLogo = goodsLogo;
        this.categoryId = categoryId;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsLogo() {
        return goodsLogo;
    }

    public void setGoodsLogo(int goodsLogo) {
        this.goodsLogo = goodsLogo;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsLogo=" + goodsLogo +
                ", categoryId=" + categoryId +
                '}';
    }
}
