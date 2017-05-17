package com.common.cache;

import android.util.LruCache;

import java.io.Serializable;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 17 11:07
 * @DESC：
 */

public class MemoryCacheImpl implements IDataCache {
    /**
     * 初始化内存缓存容器
     */
    LruCache<String,Serializable> lruCaches;
    {
        //最大内存
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        //取四分之一内存大小作为缓存
        int cacheSize = maxMemory / 4;
        lruCaches = new LruCache<String,Serializable>(cacheSize);
    }

    @Override
    public <T> T get(String key) {
        return getData(key);
    }

    @Override
    public <T> void put(String key, T t, long cacheTime) {
        if (!isExists(key)) {
            CacheData<T> cacheData = new CacheData<>();
            cacheData.setCacheNowTime(System.currentTimeMillis());
            cacheData.setCacheTime(cacheTime);
            cacheData.setT(t);
            insertData(key,cacheData);
        }
    }


    private boolean isExists(String key) {
        return lruCaches.get(key) != null;
    }

    private void remove(String key) {
        lruCaches.remove(key);
    }

    private <T> void insertData(String key, CacheData<T> cacheData) {
        lruCaches.put(key,cacheData);
    }

    private <T> T getData(String key) {
        CacheData<T> cacheData = (CacheData<T>) lruCaches.get(key);
        if (null == cacheData) return null;
        if (cacheData.isTimeout()) {
            remove(key);
            return null;
        }
        return cacheData.getT();
    }
}
