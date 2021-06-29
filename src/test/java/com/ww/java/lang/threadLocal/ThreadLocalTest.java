package com.ww.java.lang.threadLocal;

import org.junit.Test;

/**
 * @author: Sun
 * @create: 2021-03-29 17:34
 * @version: v1.0
 */
public class ThreadLocalTest {

    /**
     * 笔记:
     * Thread类中有一个threadLocals和一个inheritableThreadLocals，它们都是ThreadLocalMap类型的变量，而ThreadLocalMap是一个定制化的Hashmap。
     * 在默认情况下，每个线程中的这两个变量都为null，只有当前线程第一次调用ThreadLocal的set或者get方法时才会创建它们。其实每个线程的本地变量不是
     * 存放在ThreadLocal实例里面，而是存放在调用线程的threadLocals变量里面。也就是说，ThreadLocal类型的本地变量存放在具体的线程内存空间中。
     * ThreadLocal就是一个工具壳，它通过set方法把value值放入调用线程的threadLocals里面并存放起来，当调用线程调用它的get方法时，再从当前线程
     * 的threadLocals变量里面将其拿出来使用。如果调用线程一直不终止，那么这个本地变量会一直存放在调用线程的threadLocals变量里面，所以当不需要
     * 使用本地变量时可以通过调用ThreadLocal变量的remove方法，从当前线程的threadLocals里面删除该本地变量。
     * <p>
     * Thread里面的threadLocals为何被设计为map结构？
     * 很明显是因为每个线程可以关联多个ThreadLocal变量。
     */

    static ThreadLocal<String> localVariable = new ThreadLocal<>();

    static void print(String str) {
        System.out.println(str + ":" + localVariable.get());

        localVariable.remove();
    }

    /**
     * Test case: {@link ThreadLocal}
     * 测试目的: 认识ThreadLocal
     */
    @Test
    public void threadLocalTest() {
        Thread threadA = new Thread(() -> {
            // 设置threadA中的localVariable
            localVariable.set("threadA local variable");
            // 打印threadA中的localVariable，打印过后清空
            print("threadA");
            // 再次打印threadA中的localVariable
            System.out.println("threadA remove after:" + localVariable.get());
        });

        Thread threadB = new Thread(() -> {
            // 设置threadB中的localVariable
            localVariable.set("threadB local variable");
            // 打印threadB中的localVariable，打印过后清空
            print("threadB");
            // 再次打印threadB中的localVariable
            System.out.println("threadB remove after:" + localVariable.get());
        });

        threadA.start();
        threadB.start();

        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link ThreadLocal}
     * 测试目的: 测试ThreadLocal是否支持继承性   结果: 不支持继承性，同一个ThreadLocal变量在父线程中被设置值后，在子线程中是获取不到的。
     */
    @Test
    public void notInheritable() {
        localVariable.set("Apple");

        Thread threadA = new Thread(() -> {
            System.out.println("threadA:" + localVariable.get());
        });
        threadA.start();

        System.out.println("threadMain:" + localVariable.get());

        try {
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
