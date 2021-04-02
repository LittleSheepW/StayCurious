package com.ww.java.lang.inheritableThreadLocal;

import org.junit.Test;

import java.security.AccessControlContext;

/**
 * InheritableThreadLocal继承自ThreadLocal，其提供了一个特性，就是让子线程可以访问在父线程中设置的本地变量。
 *
 * @author: Sun
 * @create: 2021-03-30 14:07
 * @version: v1.0
 */
public class InheritableThreadLocalTest {

    static ThreadLocal<String> localVariable = new InheritableThreadLocal<>();

    /**
     * Test case: {@link ThreadLocal}
     * 测试目的: 测试InheritableThreadLocal是否支持继承性   结果: 支持继承性，子线程可以访问在父线程中设置的本地变量
     * Thread中的inheritableThreadLocals属性是在这里初始化的: {@link Thread#init(ThreadGroup, Runnable, String, long, AccessControlContext, boolean)}
     */
    @Test
    public void inheritable() {
        // 主线程为localVariable赋值
        localVariable.set("Apple");

        Thread threadA = new Thread(() -> {
            // 子线程读取localVariable
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
