package com.ww.java.lang.object;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author: Sun
 * @create: 2021-03-25 17:08
 * @version: v1.0
 */
@Slf4j
public class ObjectTest {

    /**
     * Test case: {@link Object#wait()}
     * 当一个线程调用一个共享变量的wait()方法时，该调用线程会被阻塞挂起并释放共享变量上的锁，直到发生下面几件事情之一才返回:
     * （1）其他线程调用了该共享对象的notify()或者notifyAll()方法；
     * （2）其他线程调用了该线程的interrupt()方法，该线程抛出InterruptedException异常返回。
     * 另外需要注意的是:
     * - 如果调用wait()方法的线程没有事先获取该对象的监视器锁，则调用wait()方法时调用线程会抛出IllegalMonitorStateException异常。
     * - 当一个线程调用共享对象的wait（）方法被阻塞挂起后，如果其他线程中断了该线程，则该线程会抛出InterruptedException异常并返回。
     * 那么一个线程如何才能获取一个共享变量的监视器锁呢？
     * （1）执行synchronized同步代码块时，使用该共享变量作为参数。
     * synchronized (共享变量) {
     *     // do something
     * }
     * （2）调用该共享变量的方法，并且该方法使用了synchronized修饰。
     * synchronized void add(int a, int b) {
     *     // do something
     * }
     * <p>
     * 另外需要注意的是，一个线程可以从挂起状态变为可以运行状态（也就是被唤醒），即使该线程没有被其他线程调用notify（）、notifyAll（）方法
     * 进行通知，或者被中断，或者等待超时，这就是所谓的虚假唤醒。虽然虚假唤醒在应用实践中很少发生，但要防患于未然，做法就是不停地去测试该线程
     * 被唤醒的条件是否满足，不满足则继续等待，也就是说在一个循环中调用wait（）方法进行防范。退出循环的条件是满足了唤醒该线程的条件。
     */
    @Test
    public void waitTest() {
        Integer integer = 1;

        try {
            // 如果调用wait()方法的线程没有事先获取该对象的监视器锁，则调用wait()方法时调用线程会抛出IllegalMonitorStateException异常
            integer.wait();
        } catch (Exception e) {
            log.error("[waitTest] [异常]", e);
        }
    }

    /**
     * Test case: {@link Object#wait(long)}
     * 该方法相比wait（）方法多了一个超时参数，它的不同之处在于，如果一个线程调用共享对象的该方法挂起后，没有在指定的timeout ms时间内被其他
     * 线程调用该共享变量的notify（）或者notifyAll（）方法唤醒，那么该函数还是会因为超时而返回。如果将timeout设置为0则和wait方法效果一样，
     * 因为在wait方法内部就是调用了wait（0）。需要注意的是，如果在调用该函数时，传递了一个负的timeout则会抛出IllegalArgumentException异常。
     */
    @Test
    public void wait2Test() {
        Integer integer = 1;

        synchronized (integer) {
            try {
                System.out.println("开始等待");
                integer.wait(1000);
                System.out.println("结束等待");
            } catch (InterruptedException e) {
                log.error("[wait2Test] [异常]", e);
            }
        }
    }

    /**
     * Test case: {@link Object#wait(long, int)}
     * 当nanos大于0时timeout++，wait(0, 0)等同与wait(0)
     */
    @Test
    public void wait3Test() {
        Integer integer = 1;

        synchronized (integer) {
            try {
                System.out.println("开始等待");
                integer.wait(1000, 99999);
                System.out.println("结束等待");
            } catch (InterruptedException e) {
                log.error("[wait3Test] [异常]", e);
            }
        }
    }


    /**
     * Test case: {@link Object#wait()}
     * 测试其他线程调用了该线程的interrupt()方法，该线程抛出InterruptedException异常返回。
     */
    @Test
    public void waitInterruptedExceptionTest() {
        Integer integer = 1;

        Thread threadA = new Thread(() -> {
            try {
                synchronized (integer) {
                    System.out.println("开始等待");
                    integer.wait(10000);
                    System.out.println("结束等待");
                }
            } catch (InterruptedException e) {
                log.error("[waitInterruptedExceptionTest] [异常]", e);
            }
        });

        Thread threadB = new Thread(() -> {
            threadA.interrupt();
        });

        threadA.start();
        threadB.start();

        try {
            threadA.join();
        } catch (InterruptedException e) {
            log.error("[waitInterruptedExceptionTest] [异常]", e);
        }
    }

    /**
     * {@link Object#notify()}
     * notify() 函数一个线程调用共享对象的notify（）方法后，会唤醒一个在该共享变量上调用wait系列方法后被挂起的线程。一个共享变量上可能会有
     * 多个线程在等待，具体唤醒哪个等待的线程是随机的。
     * 此外，被唤醒的线程不能马上从wait方法返回并继续执行，它必须在获取了共享对象的监视器锁后才可以返回，也就是唤醒它的线程释放了共享变量上
     * 的监视器锁后，被唤醒的线程也不一定会获取到共享对象的监视器锁，这是因为该线程还需要和其他线程一起竞争该锁，只有该线程竞争到了共享变量
     * 的监视器锁后才可以继续执行。类似wait系列方法，只有当前线程获取到了共享变量的监视器锁后，才可以调用共享变量的notify（）方法，否则会
     * 抛出IllegalMonitorStateException异常。
     */
    @Test
    public void notifyTest() {
        Integer integer = 1;

        synchronized (integer) {
            integer.notify();
        }
    }

    /**
     * {@link Object#notifyAll()}
     * notifyAll() 函数不同于在共享变量上调用notify（）函数会唤醒被阻塞到该共享变量上的一个线程，notifyAll（）方法则会唤醒所有在该共享变量
     * 上由于调用wait系列方法而被挂起的线程。
     */
    @Test
    public void notifyAllTest() {
        Integer integer = 1;

        synchronized (integer) {
            integer.notifyAll();
        }
    }
}
