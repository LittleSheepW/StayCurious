package com.ww.java.util.concurrent.locks;

import org.junit.Test;

/**
 * @author: Sun
 * @create: 2021-04-08 16:49
 * @version: v1.0
 */
public class AbstractQueuedSynchronizerTest {

    /**
     * 笔记:
     * - notify和wait，是配合synchronized内置锁实现线程间同步的基础设施，条件变量的signal和await方法是用来配合锁（使用AQS实现的锁）实现
     * 线程间同步的基础设施。它们的不同在于，synchronized同时只能与一个共享变量的notify或wait方法实现同步，而AQS的一个锁可以对应多个条件变量。
     * 在调用共享变量的notify和wait方法前必须先获取该共享变量的内置锁，同理在调用条件变量的signal和await方法前也必须先获取条件变量对应的锁。
     */
}
