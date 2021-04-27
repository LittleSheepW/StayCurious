package com.ww.java.util.concurrent.locks;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: Sun
 * @create: 2021-04-09 14:45
 * @version: v1.0
 */
public class ReentrantReadWriteLockTest {

    // ------------------------------ ReentrantReadWriteLock.WriteLock ------------------------------ //

    /**
     * Test case: {@link ReentrantReadWriteLock.WriteLock#lock()}
     * API描述: 写锁是个独占锁，某时只有一个线程可以获取该锁。如果当前没有线程获取到读锁和写锁，则当前线程可以获取到写锁然后返回。如果当前已经
     * 有线程获取到读锁和写锁，则当前请求写锁的线程会被阻塞挂起。另外，写锁是可重入锁，如果当前线程已经获取了该锁，再次获取只是简单地把可重入
     * 次数加1后直接返回。
     */
    @Test
    public void WriteLock_lockTest() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

        // 写锁是个独占锁，某时只有一个线程可以获取该锁。获取写锁时如果当前没有其它线程获取到读锁和写锁，则当前线程可以获取到写锁然后返回
        try {
            Thread threadA = new Thread(() -> {
                System.out.println("我是线程A，我开始获取写锁");
                writeLock.lock();
                System.out.println("我是线程A，我获取到写锁了，开始搞事情");

                writeLock.unlock();
                System.out.println("我是线程A，我释放了写锁");
            });

            threadA.start();
            threadA.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------------------");

        // 获取写锁时如果当前已经有线程获取到读锁，则当前请求写锁的线程会被阻塞挂起
        try {
            Thread threadB = new Thread(() -> {
                System.out.println("我是线程B，我开始获取读锁");
                readLock.lock();
                System.out.println("我是线程B，我获取到读锁了，开始搞事情");

                try {
                    Thread.sleep(5000);

                    readLock.unlock();
                    System.out.println("我是线程B，我释放了读锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread threadC = new Thread(() -> {
                System.out.println("我是线程C，我开始获取写锁");
                writeLock.lock();
                System.out.println("我是线程C，我获取到写锁了，开始搞事情");

                writeLock.unlock();
                System.out.println("我是线程C，我释放了写锁");
            });

            threadB.start();
            Thread.sleep(1000);
            threadC.start();

            threadB.join();
            threadC.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------------------");

        // 获取写锁时如果当前已经有其它线程获取到写锁，则当前请求写锁的线程会被阻塞挂起
        try {
            Thread threadD = new Thread(() -> {
                System.out.println("我是线程D，我开始获取写锁");
                writeLock.lock();
                System.out.println("我是线程D，我获取到写锁了，开始搞事情");

                try {
                    Thread.sleep(5000);

                    writeLock.unlock();
                    System.out.println("我是线程D，我释放了写锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread threadE = new Thread(() -> {
                System.out.println("我是线程E，我开始获取写锁");
                writeLock.lock();
                System.out.println("我是线程E，我获取到写锁了，开始搞事情");

                writeLock.unlock();
                System.out.println("我是线程E，我释放了写锁");
            });

            threadD.start();
            Thread.sleep(1000);
            threadE.start();

            threadD.join();
            threadE.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link ReentrantReadWriteLock.WriteLock#lockInterruptibly()}
     * API描述: 类似于lock（）方法，它的不同之处在于，它会对中断进行响应，也就是当其他线程调用了该线程的interrupt（）方法中断了当前线程时，
     * 当前线程会抛出异常InterruptedException异常。
     */
    @Test
    public void WriteLock_lockInterruptiblyTest() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        Thread threadA = new Thread(() -> {
            try {
                System.out.println("ThreadA do something...");

                while (!Thread.currentThread().isInterrupted()) {

                }

                // 必须在当前线程没有获取到锁之前打断当前线程才有用
                writeLock.lockInterruptibly();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        });

        Thread threadB = new Thread(() -> {
            System.out.println("开始打断A线程");
            threadA.interrupt();
        });

        try {
            threadA.start();
            threadB.start();

            threadB.join();
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link ReentrantReadWriteLock.WriteLock#tryLock()}
     * API描述: 尝试获取写锁，如果当前没有其他线程持有写锁或者读锁，则当前线程获取写锁会成功，然后返回true。如果当前已经有其他线程持有写锁或者
     * 读锁则该方法直接返回false，且当前线程并不会被阻塞。如果当前线程已经持有了该写锁则简单增加AQS的状态值后直接返回true。
     */
    @Test
    public void WriteLock_tryLockTest() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        boolean b = writeLock.tryLock();

        System.out.println(b);
    }

    /**
     * Test case: {@link ReentrantReadWriteLock.WriteLock#tryLock(long, TimeUnit)}
     * API描述: 尝试获取锁，与tryLock（）的不同之处在于，它设置了超时时间，如果超时时间到没有获取到该锁则返回false。另外，该方法会对中断进行
     * 响应，也就是当其他线程调用了该线程的interrupt（）方法中断了当前线程时，当前线程会抛出InterruptedException异常。
     */
    @Test
    public void WriteLock_try2LockTest() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        try {
            boolean b = writeLock.tryLock(1, TimeUnit.MINUTES);
            System.out.println(b);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link ReentrantReadWriteLock.WriteLock#unlock()}
     * API描述: 尝试释放锁，如果当前线程持有该锁，调用该方法会让该线程对该线程持有的AQS状态值减1，如果减去1后当前状态值为0则当前线程会释放该锁，
     * 否则仅仅减1而已。如果当前线程没有持有该锁而调用了该方法则会抛出IllegalMonitorStateException异常。
     */
    @Test
    public void WriteLock_unlockTest() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        try {
            writeLock.lock();
            System.out.println("do something...");
            writeLock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ------------------------------ ReentrantReadWriteLock.ReadLock ------------------------------ //

    /**
     * Test case: {@link ReentrantReadWriteLock.ReadLock#lock()}
     * API描述: 获取读锁，如果当前没有其他线程持有写锁，则当前线程可以获取读锁，AQS的状态值state的高16位的值会增加1，然后方法返回。否则如果
     * 其他一个线程持有写锁，则当前线程会被阻塞。
     */
    @Test
    public void ReadLock_lockTest() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

        // 获取读锁时如果当前没有线程持有写锁，则当前线程可以获取读锁
        try {
            Thread threadA = new Thread(() -> {
                System.out.println("我是线程A，我开始获取读锁");
                readLock.lock();
                System.out.println("我是线程A，我获取到读锁了，开始搞事情");

                readLock.unlock();
                System.out.println("我是线程A，我释放了读锁");
            });

            threadA.start();
            threadA.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------------------");

        // 获取读锁时如果当前已经有其他线程获取到读锁，当前线程照样可以获取到读锁
        try {
            Thread threadB = new Thread(() -> {
                System.out.println("我是线程B，我开始获取读锁");
                readLock.lock();
                System.out.println("我是线程B，我获取到读锁了，开始搞事情");

                try {
                    Thread.sleep(5000);

                    readLock.unlock();
                    System.out.println("我是线程B，我释放了读锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread threadC = new Thread(() -> {
                System.out.println("我是线程C，我开始获取读锁");
                readLock.lock();
                System.out.println("我是线程C，我获取到读锁了，开始搞事情");

                readLock.unlock();
                System.out.println("我是线程C，我释放了读锁");
            });

            threadB.start();
            Thread.sleep(1000);
            threadC.start();

            threadB.join();
            threadC.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----------------------");

        // 获取读锁时如果已有线程持有写锁，则当前线程获取读锁失败，会被阻塞
        try {
            Thread threadD = new Thread(() -> {
                System.out.println("我是线程D，我开始获取写锁");
                writeLock.lock();
                System.out.println("我是线程D，我获取到写锁了，开始搞事情");

                try {
                    Thread.sleep(5000);

                    writeLock.unlock();
                    System.out.println("我是线程D，我释放了写锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread threadE = new Thread(() -> {
                System.out.println("我是线程E，我开始获取读锁");
                readLock.lock();
                System.out.println("我是线程E，我获取到读锁了，开始搞事情");

                readLock.unlock();
                System.out.println("我是线程E，我释放了读锁");
            });

            threadD.start();
            Thread.sleep(1000);
            threadE.start();

            threadD.join();
            threadE.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link ReentrantReadWriteLock.ReadLock#lockInterruptibly()}
     * API描述: 类似于lock（）方法，不同之处在于，该方法会对中断进行响应，也就是当其他线程调用了该线程的interrupt（）方法中断了当前线程时，
     * 当前线程会抛出InterruptedException异常。
     */
    @Test
    public void ReadLock_lockInterruptiblyTest() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

        Thread threadA = new Thread(() -> {
            try {
                System.out.println("ThreadA do something...");

                while (!Thread.currentThread().isInterrupted()) {

                }

                // 必须在当前线程没有获取到锁之前打断当前线程才有用
                readLock.lockInterruptibly();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
            }
        });

        Thread threadB = new Thread(() -> {
            System.out.println("开始打断A线程");
            threadA.interrupt();
        });

        try {
            threadA.start();
            threadB.start();

            threadB.join();
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link ReentrantReadWriteLock.ReadLock#tryLock()}
     * API描述: 尝试获取读锁，如果当前没有其他线程持有写锁，则当前线程获取读锁会成功，然后返回true。如果当前已经有其他线程持有写锁则该方法直接
     * 返回false，但当前线程并不会被阻塞。如果当前线程已经持有了该读锁则简单增加AQS的状态值高16位后直接返回true。
     */
    @Test
    public void ReadLock_tryLockTest() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        boolean b = readLock.tryLock();

        System.out.println(b);
    }

    /**
     * Test case: {@link ReentrantReadWriteLock.ReadLock#tryLock(long, TimeUnit)}
     * API描述: 尝试获取锁，与tryLock（）的不同之处在于，它设置了超时时间，如果超时时间到没有获取到该锁则返回false。另外，该方法会对中断进行
     * 响应，也就是当其他线程调用了该线程的interrupt（）方法中断了当前线程时，当前线程会抛出InterruptedException异常。
     */
    @Test
    public void ReadLock_try2LockTest() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

        try {
            boolean b = readLock.tryLock(1, TimeUnit.MINUTES);
            System.out.println(b);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link ReentrantReadWriteLock.ReadLock#unlock()}
     * API描述:
     */
    @Test
    public void ReadLock_unlockTest() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

        try {
            readLock.lock();
            System.out.println("do something...");
            readLock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        System.out.println(sharedCount(0));
        System.out.println(sharedCount(Integer.MIN_VALUE));

        System.out.println("-------");

        System.out.println(exclusiveCount(0));
        System.out.println(exclusiveCount(1));
    }

    final int SHARED_SHIFT = 16;
    final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

    final int SHARED_UNIT = (1 << SHARED_SHIFT);
    final int MAX_COUNT = (1 << SHARED_SHIFT) - 1;

    int sharedCount(int c) {
        // 除2
        return c >>> SHARED_SHIFT;
    }

    int exclusiveCount(int c) {
        // 取余
        return c & EXCLUSIVE_MASK;
    }
}
