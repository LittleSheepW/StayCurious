package com.ww.designPatterns.singleton;

/**
 * 单例模式-饿汉式
 * 线程安全，调用效率高，但是不能延时加载
 *
 * @author: Sun
 * @create: 2021-05-26 13:58
 * @version: v1.0
 */
public class HungrySingleton {

    private static volatile HungrySingleton singleton = null;

    public static HungrySingleton getInstance() {
        if (singleton == null) {
            singleton = new HungrySingleton();
        }

        return singleton;
    }

    private HungrySingleton() {

    }
}
