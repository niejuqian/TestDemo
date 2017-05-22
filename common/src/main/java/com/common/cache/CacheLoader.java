package com.common.cache;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 17 11:03
 * @DESC：
 */

public class CacheLoader {
    private static CacheLoader cacheLoader;
    private IDataCache memoryCache;
    private IDataCache diskCache;
    private CacheStrateEnum strateEnum;
    //默认缓存时间
    private static long DEFAULT_CACHE_TIME = 60 * 60 * 24;
    private CacheLoader(){
        memoryCache = new MemoryCacheImpl();
        diskCache = new DiskCacheImpl();
        strateEnum = CacheStrateEnum.MEMORY;
    }

    public static CacheLoader builder(){
        if (null == cacheLoader) {
            cacheLoader = new CacheLoader();
        }
        return cacheLoader;
    }

    /**
     * 设置默认缓存时间
     * @param cacheTime
     * @return
     */
    public CacheLoader setCacheTime(long cacheTime) {
        this.DEFAULT_CACHE_TIME = cacheTime;
        return this;
    }

    /**
     * 设置缓存策略
     * @param strate 缓存策略枚举
     * @return
     */
    public CacheLoader setCacheStrate(CacheStrateEnum strate) {
        this.strateEnum = strate;
        return this;
    }

    public <T> T get(String key) {
        if (strateEnum == CacheStrateEnum.MEMORY) {
            return memoryCache.get(key);
        } else if (strateEnum == CacheStrateEnum.DISK) {
            return diskCache.get(key);
        } else {
            T r = memoryCache.get(key);
            if ( r == null) {
                r = diskCache.get(key);
            }
            return r;
        }
    }

    public <T> CacheLoader put(String key,T t,long cacheTime) {
        if (strateEnum == CacheStrateEnum.MEMORY) {
            memoryCache.put(key,t,cacheTime);
        } else if (strateEnum == CacheStrateEnum.DISK) {
            diskCache.put(key,t,cacheTime);
        } else {
            memoryCache.put(key,t,cacheTime);
            diskCache.put(key,t,cacheTime);
        }
        return this;
    }

    public <T> CacheLoader put(String key,T t) {
        if (strateEnum == CacheStrateEnum.MEMORY) {
            memoryCache.put(key,t,DEFAULT_CACHE_TIME);
        } else if (strateEnum == CacheStrateEnum.DISK) {
            diskCache.put(key,t,DEFAULT_CACHE_TIME);
        } else {
            memoryCache.put(key,t,DEFAULT_CACHE_TIME);
            diskCache.put(key,t,DEFAULT_CACHE_TIME);
        }
        return this;
    }


}
