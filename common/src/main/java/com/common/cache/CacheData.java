package com.common.cache;

import java.io.Serializable;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 17 11:41
 * @DESC：
 */

public class CacheData<T> implements Serializable {
    //缓存时长 单位秒
    long cacheTime;
    //缓存时间，缓存时毫秒数
    long cacheNowTime;
    //缓存数据
    T t;

    public long getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }

    public long getCacheNowTime() {
        return cacheNowTime;
    }

    public void setCacheNowTime(long cacheNowTime) {
        this.cacheNowTime = cacheNowTime;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    /**
     * 是否超时
     * @return
     */
    public boolean isTimeout(){
        long nowTime = System.currentTimeMillis();
        long cacheNowTime = getCacheNowTime();
        long diffTime = nowTime - cacheNowTime;
        //如果时间差
        if (diffTime / 1000 > getCacheTime()) {
            return true;
        }
        return false;
    }
}
