package com.common.cache;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 17 11:40
 * @DESC：
 */

public class DiskCacheImpl implements IDataCache {
    private final String CACHE_DIR = "sdcard/cache";

    @Override
    public <T> T get(String key) {
        return null;
    }

    @Override
    public <T> void put(String key, T t, long cacheTime) {

    }


    private <T> void put(String key,CacheData<T> cacheData) {

    }

    private boolean isExists(){
        return false;
    }

    /**
     * 序列化对象
     *
     * @param person
     * @return
     * @throws IOException
     */
    private String serialize(Serializable person) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(person);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private <T>T deSerialization(String str) throws IOException, ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        T t = (T) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return t;
    }

}
