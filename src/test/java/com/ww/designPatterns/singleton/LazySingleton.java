package com.ww.designPatterns.singleton;

/**
 * 单例模式-懒汉式
 *
 * @author: Sun
 * @create: 2021-05-26 13:48
 * @version: v1.0
 */
public class LazySingleton {

    private static volatile LazySingleton singleton = null;

    /**
     * 线程不安全，调用效率高，无法延时加载
     *
     * @return
     */
    public static LazySingleton getInstance1() {
        if (singleton == null) {
            singleton = new LazySingleton();
        }

        return singleton;
    }

    /**
     * 线程安全，调用效率不高，可以延时加载
     *
     * @return
     */
    public static synchronized LazySingleton getInstance2() {
        if (singleton == null) {
            singleton = new LazySingleton();
        }

        return singleton;
    }

    /**
     * 线程安全，调用效率相对上一种稍微高一些，可以延时加载
     *
     * @return
     */
    public static LazySingleton getInstance3() {
        if (singleton == null) {
            synchronized (LazySingleton.class) {
                if (singleton == null) {
                    singleton = new LazySingleton();
                }
            }
        }

        return singleton;
    }

    private LazySingleton() {

    }
}
