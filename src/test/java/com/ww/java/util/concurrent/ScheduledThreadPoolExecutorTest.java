package com.ww.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author: Sun
 * @create: 2021-04-15 17:35
 * @version: v1.0
 */
public class ScheduledThreadPoolExecutorTest {

    /**
     * 笔记:
     * ScheduledThreadPoolExecutor继承了ThreadPoolExecutor并实现了ScheduledExecutorService接口。线程池队列是DelayedWorkQueue，
     * 其和DelayedQueue类似，是一个延迟队列。
     *
     * ScheduledFutureTask是具有返回值的任务，继承自FutureTask。FutureTask的内部有一个变量state用来表示任务的状态，一开始状态为NEW，
     * 所有状态为:
     * - private static final int NEW          = 0;     // 初始状态
     * - private static final int COMPLETING   = 1;     // 执行中状态
     * - private static final int NORMAL       = 2;     // 正常运行结束状态
     * - private static final int EXCEPTIONAL  = 3;     // 运行中异常
     * - private static final int CANCELLED    = 4;     // 任务被取消
     * - private static final int INTERRUPTING = 5;     // 任务正在被中断
     * - private static final int INTERRUPTED  = 6;     // 任务已经被中断
     * 可能的任务状态转换路径为:
     * NEW -> COMPLETING -> NORMAL
     * NEW -> COMPLETING -> EXCEPTIONAL
     * NEW -> CANCELLED
     * NEW -> INTERRUPTING -> INTERRUPTED
     * ScheduledFutureTask内部还有一个变量period用来表示任务的类型，任务类型如下:
     * - period=0，说明当前任务是一次性的，执行完毕后就退出了。
     * - period为负数，说明当前任务为fixed-delay任务，是固定延迟的定时可重复执行任务。
     * - period为正数，说明当前任务为fixed-rate任务，是固定频率的定时可重复执行任务。
     */

    /**
     * Test case: {@link ScheduledThreadPoolExecutor#schedule(Runnable, long, TimeUnit)}
     * API描述: 该方法的作用是提交一个延迟执行的任务，任务从提交时间算起延迟单位为unit的delay时间后开始执行。提交的任务不是周期性任务，任务
     * 只会执行一次。
     */
    @Test
    public void executeTest() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

        scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println("Hello world!");
        }, 10, TimeUnit.SECONDS);

        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
