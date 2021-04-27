package com.ww.java.util.concurrent.locks;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Sun
 * @create: 2021-04-08 11:55
 * @version: v1.0
 */
public class ReentrantLockTest {

    /**
     * Test case: {@link ReentrantLock#lock()}
     * API描述: 当一个线程调用该方法时，说明该线程希望获取该锁。如果锁当前没有被其他线程占用并且当前线程之前没有获取过该锁，则当前线程会获取到
     * 该锁，然后设置当前锁的拥有者为当前线程，并设置AQS的状态值为1，然后直接返回。如果当前线程之前已经获取过该锁，则这次只是简单地把AQS的状态
     * 值加1后返回。如果该锁已经被其他线程持有，则调用该方法的线程会被放入AQS队列后阻塞挂起。
     */
    @Test
    public void lockTest() {
        Lock lock = new ReentrantLock();
        lock.lock();

        try {
            System.out.println("do something...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Test case: {@link ReentrantLock#lockInterruptibly()}
     * API描述: 该方法与lock（）方法类似，它的不同在于，它对中断进行响应，就是当前线程在调用该方法时，如果其他线程调用了当前线程的interrupt（）
     * 方法，则当前线程会抛出InterruptedException异常，然后返回。
     */
    @Test
    public void lockInterruptiblyTest() {
        Lock lock = new ReentrantLock();

        Thread threadA = new Thread(() -> {
            try {
                System.out.println("线程A尝试获取锁");
                lock.lock();
                System.out.println("线程A获取到了锁");

                // 等待5秒后把锁释放掉
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("线程A释放了锁");
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                System.out.println("线程B尝试获取锁");
                lock.lockInterruptibly();
                System.out.println("线程B获取到了锁");
            } catch (Exception e) {
                System.out.println("线程B在获取锁的过程中被其他线程打断了");
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            System.out.println("线程C开始打断线程B");
            threadB.interrupt();
            System.out.println("线程C打断了线程B");
        });

        try {
            threadA.start();
            Thread.sleep(1000);
            threadB.start();
            Thread.sleep(1000);
            threadC.start();

            threadC.join();
            threadB.join();
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link ReentrantLock#tryLock()}
     * API描述: 尝试获取锁，如果当前该锁没有被其他线程持有，则当前线程获取该锁并返回true，否则返回false。注意，该方法不会引起当前线程阻塞。
     */
    @Test
    public void tryLockTest() {
        Lock lock = new ReentrantLock();
        boolean b = lock.tryLock();

        System.out.println(b);
    }

    /**
     * Test case: {@link ReentrantLock#tryLock(long, TimeUnit)}
     * API描述: 尝试获取锁，与tryLock（）的不同之处在于，它设置了超时时间，如果超时时间到没有获取到该锁则返回false。另外，该方法会对中断进行
     * 响应，也就是当其他线程调用了该线程的interrupt（）方法中断了当前线程时，当前线程会抛出InterruptedException异常。
     */
    @Test
    public void tryLock2Test() {
        Lock lock = new ReentrantLock();

        try {
            boolean b = lock.tryLock(1, TimeUnit.SECONDS);
            System.out.println(b);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link ReentrantLock#unlock()}
     * API描述: 尝试释放锁，如果当前线程持有该锁，则调用该方法会让该线程对该线程持有的AQS状态值减1，如果减去1后当前状态值为0，则当前线程会
     * 释放该锁，否则仅仅减1而已。如果当前线程没有持有该锁而调用了该方法则会抛出IllegalMonitorStateException异常。
     */
    @Test
    public void unlockTest() {
        Lock lock = new ReentrantLock();

        try {
            lock.lock();
            System.out.println("do something...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
