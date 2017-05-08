package com.testdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 08 13:58
 * @DESC：
 */

public class Fruit implements Parcelable{
    private String name;
    private int imgId;

    public Fruit() {
    }

    public Fruit(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(imgId);
    }

    public static final Parcelable.Creator<Fruit> CREATOR = new Parcelable.Creator<Fruit>(){

        @Override
        public Fruit createFromParcel(Parcel source) {
            Fruit fruit = new Fruit();
            fruit.setName(source.readString());
            fruit.setImgId(source.readInt());
            return fruit;
        }

        @Override
        public Fruit[] newArray(int size) {
            return new Fruit[size];
        }
    };
}
