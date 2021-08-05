package com.ww.temp;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: Sun
 * @create: 2021-05-27 16:23
 * @version: v1.0
 */
public class ThreadPoolTest2 {
    static class LocalVariable {
        private Long[] a = new Long[1024 * 1024];
    }

    // (1)创建了一个核心线程数和最大线程数都为5的线程池。
    final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 1,
            TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    // (2)创建了一个ThreadLocal的变量，泛型参数为LocalVariable, LocalVariable内部是一个Long数组。
    final ThreadLocal<LocalVariable> localVariable = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        new ThreadPoolTest2().test();
    }

    private void test() throws InterruptedException {
        // (3)向线程池里面放入50个任务。
        for (int i = 0; i < 50; ++i) {
            poolExecutor.execute(new Runnable() {
                public void run() {
                    // (4)设置当前线程的localVariable变量，也就是把new的LocalVariable变量放入当前线程的threadLocals变量中。
                    localVariable.set(new LocalVariable());
                    System.out.println("use local varaible");

                    // 由于localVariable被声明为了static变量，虽然在线程的ThreadLocalMap里面对localVariable进行了弱引用，但是localVariable不会被回收，如果没有下面这行代码，则会造成内存泄漏
//                    localVariable.remove();
                }
            });
            Thread.sleep(1000);
        }
        // (5)由于没有调用线程池的shutdown或者shutdownNow方法，所以线程池里面的用户线程不会退出，进而JVM进程也不会退出。
        System.out.println("pool execute over");
    }
}
