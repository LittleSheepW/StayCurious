package com.ww.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author: Sun
 * @create: 2021-04-14 15:04
 * @version: v1.0
 */
public class ThreadPoolExecutorTest {

    /**
     * 笔记:
     * - 线程池主要解决两个问题：一是当执行大量异步任务时线程池能够提供较好的性能。在不使用线程池时，每当需要执行异步任务时直接new一个线程来运
     * 行，而线程的创建和销毁是需要开销的。线程池里面的线程是可复用的，不需要每次执行异步任务时都重新创建和销毁线程。二是线程池提供了一种资源限
     * 制和管理的手段，比如可以限制线程的个数，动态新增线程等。每个ThreadPoolExecutor也保留了一些基本的统计数据，比如当前线程池完成的任务数目等。
     *
     * - ThreadPoolExecutor的实现实际是一个生产消费模型，当用户添加任务到线程池时相当于生产者生产元素，workers线程工作集中的线程直接执行任务
     * 或者从任务队列里面获取任务时则相当于消费者消费元素。
     *
     *
     * 线程池状态含义如下:
     * - RUNNING：接受新任务并且处理阻塞队列里的任务。
     * - SHUTDOWN：拒绝新任务但是处理阻塞队列里的任务。
     * - STOP：拒绝新任务并且抛弃阻塞队列里的任务，同时会中断正在处理的任务。
     * - TIDYING：所有任务都执行完（包含阻塞队列里面的任务）后当前线程池活动线程数为0，将要调用terminated方法。
     * - TERMINATED：终止状态。terminated方法调用完成以后的状态。
     *
     * ThreadPoolExecutor继承了AbstractExecutorService。
     * - ctl是一个Integer的原子变量，用来记录线程池状态和线程池中线程个数，类似于ReentrantReadWriteLock使用一个变量来保存两种信息，线程池状态含义如下:
     *    RUNNING：接受新任务并且处理阻塞队列里的任务。
     *    SHUTDOWN：拒绝新任务但是处理阻塞队列里的任务。
     *    STOP：拒绝新任务并且抛弃阻塞队列里的任务，同时会中断正在处理的任务。
     *    TIDYING：所有任务都执行完（包含阻塞队列里面的任务）后当前线程池活动线程数为0，将要调用terminated方法。
     *    TERMINATED：终止状态。terminated方法调用完成以后的状态。
     * - mainLock是独占锁，用来控制新增Worker线程操作的原子性。
     * - termination是mainLock锁对应的条件队列，在线程调用awaitTermination时用来存放阻塞的线程。
     * - corePoolSize：线程池核心线程个数，即使空闲也会在线程池中保活，除非设置了allowCoreThreadTimeOut；
     * - maximumPoolSize：表示线程池可以同时执行的最大线程数量。
     * - workQueue：用于保存等待执行的任务的阻塞队列(仅保存通过execute方法提交的Runnable任务)，比如基于数组的有界ArrayBlockingQueue、
     * 基于链表的无界LinkedBlockingQueue、最多只有一个元素的同步队列SynchronousQueue及优先级队列PriorityBlockingQueue等。
     * - keepAliveTime：存活时间。如果当前线程池中的线程数量比corePoolSize多，并且是闲置状态，则这些闲置的线程能存活的最大时间。
     * - ThreadFactory：创建线程的工厂。
     * - handler：饱和策略，当队列满并且线程个数达到maximumPoolSize后采取的策略。
     *   - AbortPolicy（抛出异常）
     *   - CallerRunsPolicy（使用调用者所在线程来运行任务）
     *   - DiscardOldestPolicy（调用poll丢弃一个任务，执行当前任务）
     *   - DiscardPolicy（默默丢弃，不抛出异常）。
     *
     * Worker继承AQS和Runnable接口，是具体承载任务的对象。Worker继承了AQS，自己实现了简单不可重入独占锁，其中state=0表示锁未被获取状态，
     * state=1表示锁已经被获取的状态，state=-1是创建Worker时默认的状态，创建时状态设置为-1是为了避免该线程在运行runWorker（）方法前被中断。
     * - firstTask记录该工作线程执行的第一个任务。
     * - thread是具体执行任务的线程。
     *
     * ThreadFactory是线程工厂。
     * - newThread方法是对线程的一个修饰。
     * - poolNumber是个静态的原子变量，用来统计线程工厂的个数。
     * - threadNumber用来记录每个线程工厂创建了多少线程，这两个值也作为线程池和线程的名称的一部分。
     */


    /**
     * Test case: {@link ThreadPoolExecutor#execute(Runnable)}
     * API描述: execute方法的作用是提交任务command到线程池进行执行。
     */
    @Test
    public void executeTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        executorService.execute(() -> {
            System.out.println("Hello world!");
        });
    }

    /**
     * Test case: {@link ThreadPoolExecutor#shutdown()}
     * API描述: 调用shutdown方法后，线程池就不会再接受新的任务了，但是工作队列里面的任务还是要执行的。该方法会立刻返回，并不等待队列任务完成
     * 再返回。
     */
    @Test
    public void shutdownTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        executorService.shutdown();
    }

    /**
     * Test case: {@link ThreadPoolExecutor#shutdownNow()}
     * API描述: 调用shutdownNow方法后，线程池就不会再接受新的任务了，并且会丢弃工作队列里面的任务，正在执行的任务会被中断，该方法会立刻返回，
     * 并不等待激活的任务执行完成。返回值为这时候队列里面被丢弃的任务列表。
     */
    @Test
    public void shutdownNowTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        executorService.shutdownNow();
    }

    /**
     * Test case: {@link ThreadPoolExecutor#shutdown()}
     * API描述: 当线程调用awaitTermination方法后，当前线程会被阻塞，直到线程池状态变为TERMINATED才返回，或者等待时间超时才返回。
     */
    @Test
    public void awaitTerminationTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        try {
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
