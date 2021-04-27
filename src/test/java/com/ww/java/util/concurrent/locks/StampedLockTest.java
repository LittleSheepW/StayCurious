package com.ww.java.util.concurrent.locks;

import org.junit.Test;

import java.util.concurrent.locks.StampedLock;

/**
 * @author: Sun
 * @create: 2021-04-12 10:43
 * @version: v1.0
 */
public class StampedLockTest {

    /**
     * 笔记:
     * - StampedLock是并发包里面JDK8版本新增的一个锁，该锁提供了三种模式的读写控制，当调用获取锁的系列函数时，会返回一个long型的变量，我们
     * 称之为戳记（stamp），这个戳记代表了锁的状态。其中try系列获取锁的函数，当获取锁失败后会返回为0的stamp值。当调用释放锁和转换锁的方法时
     * 需要传入获取锁时返回的stamp值。
     * <p>
     * StampedLock提供的三种读写模式的锁分别如下。
     * - 写锁writeLock：是一个排它锁或者独占锁，某时只有一个线程可以获取该锁，当一个线程获取该锁后，其他请求读锁和写锁的线程必须等待，这类似
     * 于ReentrantReadWriteLock的写锁（不同的是这里的写锁是不可重入锁）；当目前没有线程持有读锁或者写锁时才可以获取到该锁。请求该锁成功后会
     * 返回一个stamp变量用来表示该锁的版本，当释放该锁时需要调用unlockWrite方法并传递获取锁时的stamp参数。并且它提供了非阻塞的tryWriteLock方法。
     * - 悲观读锁readLock：是一个共享锁，在没有线程获取独占写锁的情况下，多个线程可以同时获取该锁。如果已经有线程持有写锁，则其他线程请求获取
     * 该读锁会被阻塞，这类似于ReentrantReadWriteLock的读锁（不同的是这里的读锁是不可重入锁）。这里说的悲观是指在具体操作数据前其会悲观地
     * 认为其他线程可能要对自己操作的数据进行修改，所以需要先对数据加锁，这是在读少写多的情况下的一种考虑。请求该锁成功后会返回一个stamp变量
     * 用来表示该锁的版本，当释放该锁时需要调用unlockRead方法并传递stamp参数。并且它提供了非阻塞的tryReadLock方法。
     * - 乐观读锁tryOptimisticRead：它是相对于悲观锁来说的，在操作数据前并没有通过CAS设置锁的状态，仅仅通过位运算测试。如果当前没有线程持有
     * 写锁，则简单地返回一个非0的stamp版本信息。获取该stamp后在具体操作数据前还需要调用validate方法验证该stamp是否已经不可用，也就是看当调用
     * tryOptimisticRead返回stamp后到当前时间期间是否有其他线程持有了写锁，如果是则validate会返回0，否则就可以使用该stamp版本的锁对数据进
     * 行操作。由于tryOptimisticRead并没有使用CAS设置锁状态，所以不需要显式地释放该锁。该锁的一个特点是适用于读多写少的场景，因为获取读锁只是
     * 使用位操作进行检验，不涉及CAS操作，所以效率会高很多，但是同时由于没有使用真正的锁，在保证数据一致性上需要复制一份要操作的变量到方法栈，
     * 并且在操作数据时可能其他写线程已经修改了数据，而我们操作的是方法栈里面的数据，也就是一个快照，所以最多返回的不是最新的数据，但是一致性
     * 还是得到保障的。
     */

    /**
     * Test case: {@link StampedLock#writeLock()}
     * API描述: 当目前没有线程持有读锁或者写锁时才可以获取到该锁，当一个线程获取该锁后，其他请求读锁和写锁的线程必须等待，这类似于ReentrantReadWriteLock的
     * 写锁（不同的是这里的写锁是不可重入锁）。请求该锁成功后会返回一个stamp变量用来表示该锁的版本，当释放该锁时需要调用unlockWrite方法并
     * 传递获取锁时的stamp参数。
     */
    @Test
    public void writeLockTest() {
        StampedLock stampedLock = new StampedLock();
        long stamp = stampedLock.writeLock();

        try {
            System.out.println("我获取到了写锁");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stampedLock.unlockWrite(stamp);
            System.out.println("我释放了写锁");
        }
    }

    /**
     * Test case: {@link StampedLock#readLock()}
     * API描述: 在没有线程获取独占写锁的情况下，多个线程可以同时获取该锁。如果已经有线程持有写锁，则其他线程请求获取该读锁会被阻塞，这类似于
     * ReentrantReadWriteLock的读锁（不同的是这里的读锁是不可重入锁）。这里说的悲观是指在具体操作数据前其会悲观地认为其他线程可能要对自己
     * 操作的数据进行修改，所以需要先对数据加锁，这是在读少写多的情况下的一种考虑。请求该锁成功后会返回一个stamp变量用来表示该锁的版本，当释放
     * 该锁时需要调用unlockRead方法并传递stamp参数。并且它提供了非阻塞的tryReadLock方法。
     * 测试目的: 测试在没有线程获取独占写锁的情况下，多个线程是否可以同时获取悲观读锁。     可以
     */
    @Test
    public void readLockTest() {
        StampedLock stampedLock = new StampedLock();

        Thread threadA = new Thread(() -> {
            long stamp = stampedLock.readLock();
            System.out.println("线程A获取到了悲观读锁");

            while (!Thread.interrupted()) {

            }
            System.out.println("线程A被打断，结束死循环");
        });

        Thread threadB = new Thread(() -> {
            long stamp = stampedLock.readLock();
            System.out.println("线程B获取到了悲观读锁");

            while (!Thread.interrupted()) {

            }
            System.out.println("线程B被打断，结束死循环");
        });

        threadA.start();
        threadB.start();

        try {
            Thread.sleep(5000);

            threadA.interrupt();
            threadB.interrupt();

            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link StampedLock#tryOptimisticRead()}
     * API描述: 如果当前没有线程持有写锁，则简单地返回一个非0的stamp版本信息。获取该stamp后在具体操作数据前还需要调用validate方法验证该stamp
     * 是否已经不可用，也就是看当调用tryOptimisticRead返回stamp后到当前时间期间是否有其他线程持有了写锁，如果是则validate会返回0，否则就
     * 可以使用该stamp版本的锁对数据进行操作。由于tryOptimisticRead并没有使用CAS设置锁状态，所以不需要显式地释放该锁。该锁的一个特点是适用
     * 于读多写少的场景，因为获取读锁只是使用位操作进行检验，不涉及CAS操作，所以效率会高很多，但是同时由于没有使用真正的锁，在保证数据一致性上
     * 需要复制一份要操作的变量到方法栈，并且在操作数据时可能其他写线程已经修改了数据，而我们操作的是方法栈里面的数据，也就是一个快照，所以最多
     * 返回的不是最新的数据，但是一致性还是得到保障的。
     * 测试目的:
     * 1、多个线程可以同时获取多个乐观读锁吗？    可以
     * 2、A线程先获取乐观读锁，B线程再获取乐观读锁，A线程的乐观读锁会失效吗？    不会
     */
    @Test
    public void tryOptimisticReadTest() {
        StampedLock stampedLock = new StampedLock();

        Thread threadA = new Thread(() -> {
            long stamp = stampedLock.tryOptimisticRead();
            System.out.println("线程A获取到了乐观读锁");

            try {
                // 等待B线程获取乐观读锁
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 在B线程获取乐观读锁后进行验证
            System.out.println(stampedLock.validate(stamp));
        });

        Thread threadB = new Thread(() -> {
            long stamp = stampedLock.tryOptimisticRead();
            System.out.println("线程B获取到了乐观读锁");
        });

        try {
            threadA.start();
            threadB.start();

            threadB.join();
            threadA.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("-----------");

        Thread threadC = new Thread(() -> {
            long stamp = stampedLock.tryOptimisticRead();
            System.out.println("线程C获取到了乐观读锁");

            try {
                // 等待D线程获取乐观读锁
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 在D线程获取写锁进行验证
            System.out.println(stampedLock.validate(stamp));
        });

        Thread threadD = new Thread(() -> {
            long stamp = stampedLock.writeLock();
            System.out.println("线程D获取到了写锁");
        });

        try {
            threadC.start();
            threadD.start();

            threadD.join();
            threadC.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
