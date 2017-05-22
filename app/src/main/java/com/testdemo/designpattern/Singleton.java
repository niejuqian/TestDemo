package com.testdemo.designpattern;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 05 17 15:06
 * @DESC：单例模式
 */

public class Singleton {
    /*-----------------饿汉模式------------------*/
    /*private static Singleton singleton = new Singleton();
    private Singleton(){

    }
    public static Singleton getSingleton(){
        return singleton;
    }*/
    /*-----------------懒汉模式------------------*/
    /*private static Singleton singleton;
    private Singleton(){

    }
    public synchronized static Singleton getSingleton(){
        if (null == singleton) {
            singleton = new Singleton();
        }
        return singleton;
    }*/

    /*-----------------DCL模式------------------*/
    /*private volatile static Singleton singleton;
    private Singleton(){

    }
    public static Singleton getSingleton(){
        if (null == singleton) {
            synchronized (Singleton.class){
                if (null == singleton) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }*/
    /*-----------------静态内部类（推荐使用）------------------*/
    private Singleton(){}
    public static Singleton getSingleton(){
        return SingletonHolder.singleton;
    }

    private static class SingletonHolder {
        private final static Singleton singleton = new Singleton();
    }

}
