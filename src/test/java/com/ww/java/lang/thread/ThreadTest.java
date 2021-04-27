package com.ww.java.lang.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Sun
 * @create: 2021-03-25 15:25
 * @version: v1.0
 */
@Slf4j
public class ThreadTest {

    /**
     * 笔记:
     * - 调用start方法后线程并没有马上执行而是处于就绪状态，这个就绪状态是指该线程已经获取了除CPU资源外的其他资源，等待获取CPU资源后才会真正
     * 处于运行状态。一旦run方法执行完毕，该线程就处于终止状态。
     *
     * - Thread.sleep()方法: 当一个执行中的线程调用了该方法后，调用线程会暂时让出指定时间的执行权，也就是在这期间不参与CPU的调度，但是该线程
     * 所拥有的监视器资源，比如锁还是持有不让出的。指定的睡眠时间到了后该函数会正常返回，线程就处于就绪状态，然后参与CPU的调度，获取到CPU资源
     * 后就可以继续运行了。如果在睡眠期间其他线程调用了该线程的interrupt()方法中断了该线程，则该线程会在调用sleep方法的地方抛出InterruptedException
     * 异常而返回。
     * - Object.wait()方法: 当一个线程调用一个共享变量的wait()方法时，该调用线程会被阻塞挂起并释放共享变量上的锁，直到发生下面几件事情之一才返回:
     * (1)其他线程调用了该共享对象的notify()或者notifyAll()方法；
     * (2)其他线程调用了该线程的interrupt()方法，该线程抛出InterruptedException异常返回。
     * - Thread.yield()方法:
     * Thread类中有一个静态的yield方法，当一个线程调用yield方法时，实际就是在暗示线程调度器当前线程请求让出自己的CPU使用，但是线程调度器可以
     * 无条件忽略这个暗示。我们知道操作系统是为每个线程分配一个时间片来占有CPU的，正常情况下当一个线程把分配给自己的时间片使用完后，线程调度器
     * 才会进行下一轮的线程调度，而当一个线程调用了Thread类的静态方法yield时，是在告诉线程调度器自己占有的时间片中还没有使用完的部分自己不想
     * 使用了，这暗示线程调度器现在就可以进行下一轮的线程调度。
     * 当一个线程调用yield方法时，当前线程会让出CPU使用权，然后处于就绪状态，线程调度器会从线程就绪队列里面获取一个线程优先级最高的线程，当然
     * 也有可能会调度到刚刚让出CPU的那个线程来获取CPU执行权。
     * 总结: sleep与yield方法的区别在于，当线程调用sleep方法时调用线程会被阻塞挂起指定的时间，在这期间线程调度器不会去调度该线程。而调用yield方
     * 法时，线程只是让出自己剩余的时间片，并没有被阻塞挂起，而是处于就绪状态，线程调度器下一次调度时就有可能调度到当前线程执行。
     *
     * - 什么是线程死锁？
     * 死锁是指两个或两个以上的线程在执行过程中，因争夺资源而造成的互相等待的现象，在无外力作用的情况下，这些线程会一直相互等待而无法继续运行下去。
     * 死锁的产生必须具备以下四个条件:
     * 1、互斥条件: 指线程对已经获取到的资源进行排它性使用，即该资源同时只由一个线程占用。如果此时还有其他线程请求获取该资源，则请求者只能等待，
     * 直至占有资源的线程释放该资源。
     * 2、请求并持有条件: 指一个线程已经持有了至少一个资源，但又提出了新的资源请求，而新资源已被其他线程占有，所以当前线程会被阻塞，但阻塞的同
     * 时并不释放自己已经获取的资源。
     * 3、不可剥夺条件: 指线程获取到的资源在自己使用完之前不能被其他线程抢占，只有在自己使用完毕后才由自己释放该资源。
     * 4、环路等待条件: 指在发生死锁时，必然存在一个线程—资源的环形链，即线程集合{T0, T1,T2, …, Tn}中的T0正在等待一个T1占用的资源，T1正在
     * 等待T2占用的资源，……Tn正在等待已被T0占用的资源。
     *
     */

    /**
     * Test case: {@link MyThread}
     * 测试目的: 学习线程创建方式之一，继承Thread类并重写run方法
     */
    @Test
    public void myThreadTest() {
        // 创建线程
        MyThread myThread = new MyThread();
        // 启动线程
        myThread.start();
    }

    /**
     * Test case: {@link MyRunnable}
     * 测试目的: 学习线程创建方式之一，实现Runnable接口，重写run方法
     */
    @Test
    public void myRunnableTest() {
        MyRunnable runnable = new MyRunnable();

        new Thread(runnable).start();
        new Thread(runnable).start();
    }

    /**
     * Test case: {@link MyCallable}
     * 测试目的: 学习线程创建方式之一，实现Callable接口，重写call方法
     */
    @Test
    public void myCallableTest() {
        // 创建异步任务
        FutureTask<String> futureTask = new FutureTask<>(new MyCallable());
        new Thread(futureTask).start();

        try {
            // 等待任务执行完毕，并返回结果
            log.info("[myCallableTest] [result:{}]", futureTask.get());
        } catch (Exception e) {
            log.error("[myCallableTest] [Catch Exception]", e);
        }
    }

    /**
     * Test case: {@link Thread#join()}
     * API描述: 等待该线程死亡。
     */
    @Test
    public void joinTest() {
        Thread threadA = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("threadA执行完毕");
        });

        Thread threadB = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("threadB执行完毕");
        });

        // 启动子线程
        threadA.start();
        threadB.start();

        System.out.println("开始等待所有线程执行完毕");
        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("所有线程执行完毕");
    }

    /**
     * Test case: {@link Thread#join()}
     * 主线程调用线程A的join方法后会被阻塞，当其他线程调用了主线程的interrupt()方法中断了主线程时，主线程会抛出InterruptedException异常而返回。
     * 测试目的: 测试上述流程。
     * <p>
     * 如下代码在threadA线程里面执行死循环，主线程调用threadA的join方法阻塞自己等待线程threadA执行完毕，待threadB休眠1s后会调用主线程
     * 的interrupt()方法设置主线程的中断标志，从结果看在主线程中的threadA.join()处会抛出InterruptedException异常。这里需要注意的是，
     * 在threadB里面调用的是主线程的interrupt()方法，而不是线程threadA的。
     */
    @Test
    public void joinThrowInterruptedExceptionTest() {
        Thread threadA = new Thread(() -> {
            System.out.println("threadA开始执行死循环");
            while (true) {

            }
        });

        Thread mainThread = Thread.currentThread();

        Thread threadB = new Thread(() -> {
            try {
                // 延时1s
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 中断主线程
            mainThread.interrupt();
        });

        // 启动线程A
        threadA.start();
        // 启动线程B
        threadB.start();

        try {
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link Thread#sleep(long)}
     * API描述: Thread类中有一个静态的sleep方法，当一个执行中的线程调用了Thread的sleep方法后，调用线程会暂时让出指定时间的执行权，也就是在
     * 这期间不参与CPU的调度，但是该线程所拥有的监视器资源，比如锁还是持有不让出的。指定的睡眠时间到了后该函数会正常返回，线程就处于就绪状态，
     * 然后参与CPU的调度，获取到CPU资源后就可以继续运行了。如果在睡眠期间其他线程调用了该线程的interrupt()方法中断了该线程，则该线程会在调用
     * sleep方法的地方抛出InterruptedException异常而返回。
     */
    @Test
    public void sleepTest() {
        Lock lock = new ReentrantLock();

        Thread threadA = new Thread(() -> {
            lock.lock();

            System.out.println("threadA开始休眠");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println("threadA休眠结束");
        });

        Thread threadB = new Thread(() -> {
            lock.lock();

            System.out.println("threadB开始休眠");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println("threadB休眠结束");
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
     * Test case: {@link Thread#sleep(long)}
     * 测试在睡眠期间其他线程调用了该线程的interrupt()方法中断了该线程，则该线程会在调用sleep方法的地方抛出InterruptedException异常而返回。
     * 测试目的: 测试上述流程。
     */
    @Test
    public void sleepThrowInterruptedExceptionTest() {
        Lock lock = new ReentrantLock();

        Thread threadA = new Thread(() -> {
            lock.lock();

            System.out.println("threadA开始休眠");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println("threadA休眠结束");
        });

        threadA.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threadA.interrupt();
    }

    /**
     * Test case: {@link Thread#yield()}
     * API描述: Thread类中有一个静态的yield方法，当一个线程调用yield方法时，实际就是在暗示线程调度器当前线程请求让出自己的CPU使用，但是
     * 线程调度器可以无条件忽略这个暗示。我们知道操作系统是为每个线程分配一个时间片来占有CPU的，正常情况下当一个线程把分配给自己的时间片使用完后，
     * 线程调度器才会进行下一轮的线程调度，而当一个线程调用了Thread类的静态方法yield时，是在告诉线程调度器自己占有的时间片中还没有使用完的部分
     * 自己不想使用了，这暗示线程调度器现在就可以进行下一轮的线程调度。
     * 当一个线程调用yield方法时，当前线程会让出CPU使用权，然后处于就绪状态，线程调度器会从线程就绪队列里面获取一个线程优先级最高的线程，当然
     * 也有可能会调度到刚刚让出CPU的那个线程来获取CPU执行权。
     * 一般很少使用这个方法，在调试或者测试时这个方法或许可以帮助复现由于并发竞争条件导致的问题，其在设计并发控制时或许会有用途。
     */
    @Test
    public void yieldTest() {
        Runnable runnable = () -> {
            for (int i = 0; i < 5; i++) {
                if (i % 5 == 0) {
                    System.out.println(Thread.currentThread() + "yield CPU...");

                    // 当前线程让出CPU执行权，放弃时间片，进行下一轮调度
                    Thread.yield();
                }
            }
            System.out.println(Thread.currentThread() + "is over");
        };

        Thread threadA = new Thread(runnable);
        Thread threadB = new Thread(runnable);
        Thread threadC = new Thread(runnable);

        threadA.start();
        threadB.start();
        threadC.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link Thread#yield()}
     * 问题: 调用yield方法时线程会不会把占有的锁让出来？    结果: 不会
     * 测试目的: 得到上面问题的答案。
     */
    @Test
    public void yieldLock1Test() {
        Lock lock = new ReentrantLock();

        Thread threadA = new Thread(() -> {
            lock.lock();

            try {
                for (int i = 0; i < 10; i++) {
                    if (i == 4) {
                        System.out.println("yield CPU");
                    } else {
                        System.out.println("睡2秒");
                        Thread.sleep(2000);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread threadB = new Thread(() -> {
            lock.lock();
            System.out.println("我拿到了threadA线程释放出来的锁");
            lock.unlock();
        });

        try {
            threadA.start();
            Thread.sleep(1000);
            threadB.start();

            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link Thread#yield()}
     * 问题: 调用yield方法时线程会不会把占有的锁让出来？     结果: 不会
     * 测试目的: 得到上面问题的答案。
     */
    @Test
    public void yieldLock2Test() {
        Object o = new Object();

        Thread threadA = new Thread(() -> {
            synchronized (o) {
                try {
                    for (int i = 0; i < 10; i++) {
                        if (i == 4) {
                            System.out.println("yield CPU");
                        } else {
                            System.out.println("睡2秒");
                            Thread.sleep(2000);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadB = new Thread(() -> {
            synchronized (o) {
                System.out.println("我拿到了threadA线程释放出来的锁");
            }
        });

        try {
            threadA.start();
            Thread.sleep(1000);
            threadB.start();

            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link Thread#interrupt()}
     * API描述: 中断线程，例如，当线程A运行时，线程B可以调用线程A的interrupt()方法来设置线程A的中断标志为true并立即返回。设置标志仅仅是设置标志，
     * 线程A实际并没有被中断，它会继续往下执行。如果线程A因为调用了wait系列函数、join方法或者sleep方法而被阻塞挂起，这时候若线程B调用线程A
     * 的interrupt()方法，线程A会在调用这些方法的地方抛出InterruptedException异常而返回。
     * 测试目的: 测试线程B调用线程A的interrupt()方法来设置线程A的中断标志后，线程A是否还会继续执行。    结果: 会
     */
    @Test
    public void interruptTest() {
        Thread threadA = new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().isInterrupted());
            }
        });

        Thread threadB = new Thread(() -> {
            System.out.println("开始打断A线程");
            threadA.interrupt();
            System.out.println("打断A线程结束");
        });

        try {
            threadA.start();
            Thread.sleep(1);
            threadB.start();

            threadB.join();
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link Thread#interrupt()}
     * 线程A因为调用了wait系列函数、join方法或者sleep方法而被阻塞挂起，这时候若线程B调用线程A的interrupt()方法，线程A会在调用这些
     * 方法的地方抛出InterruptedException异常而返回。
     * 测试目的: 测试上述流程。
     */
    @Test
    public void interruptThrowInterruptedExceptionTest() {
        Thread threadA = new Thread(() -> {
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            System.out.println("开始打断A线程");
            threadA.interrupt();
            System.out.println("打断A线程结束");
        });

        try {
            threadA.start();
            Thread.sleep(1000);
            threadB.start();

            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link Thread#isInterrupted()}
     * API描述: 检测当前线程是否被中断，如果是返回true，否则返回false。
     */
    @Test
    public void isInterruptedTest() {
        Thread threadA = new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().isInterrupted());
            }
        });

        Thread threadB = new Thread(() -> {
            System.out.println("开始打断A线程");
            threadA.interrupt();
            System.out.println("打断A线程结束");
        });

        try {
            threadA.start();
            Thread.sleep(1);
            threadB.start();

            threadB.join();
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@link Thread#interrupted()}
     * API描述: 检测当前线程是否被中断，如果是返回true，否则返回false。与isInterrupted不同的是，该方法如果发现当前线程被中断，则会清除
     * 中断标志，并且该方法是static方法，可以通过Thread类直接调用。注意: 在interrupted()内部是获取当前调用线程的中断标志而不是调用
     * interrupted()方法的实例对象的中断标志。
     */
    @Test
    public void interruptedTest() {
        Thread threadA = new Thread(() -> {
            while (true) {
                boolean interrupted = Thread.interrupted();
                if (interrupted) {
                    log.warn("[interruptedTest] [我被打断了] [Thread.interrupted():{}]", interrupted);

                    log.info("[interruptedTest] [我的标识恢复了] [Thread.interrupted():{}]", Thread.interrupted());
                }
            }
        });

        Thread threadB = new Thread(() -> {
            System.out.println("开始打断A线程");
            threadA.interrupt();
            System.out.println("打断A线程结束");
        });

        try {
            threadA.start();
            Thread.sleep(1);
            threadB.start();

            threadB.join();
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 线程创建方式之一: 继承Thread类并重写run方法
 * <p>
 * 好处:
 * - 在run()方法内获取当前线程直接使用this就可以了，无须使用Thread.currentThread()方法；
 * 缺点:
 * - Java不支持多继承，如果继承了Thread类，那么就不能再继承其他类。
 * - 任务与代码没有分离，当多个线程执行一样的任务时需要多份任务代码，而Runnable则没有这个限制。  【没明白什么意思】
 * - 没有返回值
 */
class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("我是Thread");
    }
}

/**
 * 线程创建方式之一: 实现Runnable接口，重写run方法
 * <p>
 * 优点:
 * - 多个线程可以共用一个task代码逻辑，如果需要，可以给RunnableTask添加参数进行任务区分  【没明白什么意思】
 * 缺点:
 * - 没有返回值
 */
class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("我是Runnable");
    }
}

/**
 * 线程创建方式之一: 实现Callable接口，重写call方法
 * <p>
 * 优点:
 * - 有返回值
 */
class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("我是Callable");

        return "Callable success";
    }
}
