package com.common.cache;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 17 11:04
 * @DESC：
 */

public interface IDataCache {

    /**
     * 获取缓存数据
     * @param key
     * @param <T>
     * @return
     */
   <T> T get(String key);

    /**
     * 设置缓存数据
     * @param key 缓存key
     * @param t 缓存数据
     * @param cacheTime 缓存时间 单位秒
     */
    <T> void put(String key,T t, long cacheTime);
}
