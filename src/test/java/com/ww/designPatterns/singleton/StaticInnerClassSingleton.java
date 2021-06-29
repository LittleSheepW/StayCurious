package com.ww.designPatterns.singleton;

/**
 * 单例模式-静态内部类
 * 线程安全，调用效率高，可以延时加载
 *
 * @author: Sun
 * @create: 2021-05-26 13:53
 * @version: v1.0
 */
public class StaticInnerClassSingleton {

    private static class LazyHolder {
        private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }

    private StaticInnerClassSingleton() {

    }

    public static final StaticInnerClassSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }
}
