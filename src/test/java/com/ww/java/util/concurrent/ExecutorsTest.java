package com.ww.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Sun
 * @create: 2021-04-14 15:57
 * @version: v1.0
 */
public class ExecutorsTest {

    /**
     * Test case: {@link Executors#newFixedThreadPool(int)}
     * API描述: 创建一个核心线程个数和最大线程个数都为nThreads的线程池，并且阻塞队列长度为Integer.MAX_VALUE。keepAliveTime=0说明只要
     * 线程个数比核心线程个数多并且当前空闲则回收。
     */
    @Test
    public void newFixedThreadPoolTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
    }

    /**
     * Test case: {@link Executors#newSingleThreadExecutor()}
     * API描述: 创建一个核心线程个数和最大线程个数都为1的线程池，并且阻塞队列长度为Integer.MAX_VALUE。keepAliveTime=0说明只要线程个数比
     * 核心线程个数多并且当前空闲则回收。
     */
    @Test
    public void newSingleThreadExecutorTest() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Test case: {@link Executors#newCachedThreadPool()}
     * API描述: 创建一个按需创建线程的线程池，初始线程个数为0，最多线程个数为Integer.MAX_VALUE，并且阻塞队列为同步队列。keepAliveTime=60
     * 说明只要当前线程在60s内空闲则回收。这个类型的特殊之处在于，加入同步队列的任务会被马上执行，同步队列里面最多只有一个任务。
     */
    @Test
    public void newCachedThreadPoolTest() {
        ExecutorService executorService = Executors.newCachedThreadPool();
    }
}
