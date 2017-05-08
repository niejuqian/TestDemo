package com.testdemo.retrofit.bean;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 26 14:57
 * @DESC：
 */

public class ShopInfo {
    private Long shopId;
    private String shopName;
    private String address;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ShopInfo{" +
                "shopId=" + shopId +
                ", shopName=" + shopName +
                ", address=" + address +
                '}';
    }

}
