package com.ww.java.util.concurrent.locks;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * @author: Sun
 * @create: 2021-04-07 15:11
 * @version: v1.0
 */
public class LockSupportTest {

    /**
     * 笔记:
     * LockSupport类与每个使用它的线程都会关联一个许可证，在默认情况下调用LockSupport类的方法的线程是不持有许可证的。
     */

    /**
     * Test case: {@link LockSupport#park()}
     * API描述: 如果调用park方法的线程已经拿到了与LockSupport关联的许可证，则调用LockSupport.park()时会马上返回，否则调用线程会被禁止参
     * 与线程的调度，也就是会被阻塞挂起。park方法返回时不会告诉你因何种原因返回。
     * 在其他线程调用unpark(Thread thread)方法并且将当前线程作为参数时，调用park方法而被阻塞的线程会返回。另外，如果其他线程调用了阻塞线程
     * 的interrupt()方法，设置了中断标志或者线程被虚假唤醒，则阻塞线程也会返回。所以在调用park方法时最好也使用循环条件判断方式。
     * 需要注意的是，因调用park()方法而被阻塞的线程被其他线程中断而返回时并不会抛出InterruptedException异常。
     */
    @Test
    public void parkTest() {
        // 使指定线程获取许可证
        LockSupport.unpark(Thread.currentThread());

        System.out.println("Begin park");
        LockSupport.park();
        System.out.println("End park");
    }

    /**
     * Test case: {@link LockSupport#park(Object)}
     * API描述: 当线程在没有持有许可证的情况下调用park方法而被阻塞挂起时，这个blocker对象会被记录到该线程内部。使用诊断工具可以观察线程被阻塞
     * 的原因，诊断工具是通过调用getBlocker（Thread）方法来获取blocker对象的，所以JDK推荐我们使用带有blocker参数的park方法，并且blocker
     * 被设置为this，这样当在打印线程堆栈排查问题时就能知道是哪个类被阻塞了。
     */
    @Test
    public void park2Test() {
        // 使指定线程获取许可证
        // LockSupport.unpark(Thread.currentThread());

        System.out.println("Begin park");
        LockSupport.park(this);
        System.out.println("End park");
    }

    /**
     * Test case: {@link LockSupport#parkUntil(long)}
     * API描述: 这个方法和parkNanos（Object blocker, long nanos）方法的区别是，后者是从当前算等待nanos秒时间，而前者是指定一个时间点，
     * 比如需要等到2017.12.11日12:00:00，则把这个时间点转换为从1970年到这个时间点的总毫秒数。
     */
    @Test
    public void parkUntilTest() {
        System.out.println("Begin park");
        LockSupport.parkUntil(System.currentTimeMillis() + 5000);
        System.out.println("End park");
    }

    /**
     * Test case: {@link LockSupport#unpark(Thread)}
     * API描述: 当一个线程调用unpark时，如果参数thread线程没有持有thread与LockSupport类关联的许可证，则让thread线程持有。
     * 如果thread之前因调用park()而被挂起，则调用unpark后，该线程会被唤醒。
     * 如果thread之前没有调用park，则调用unpark方法后，再调用park方法，其会立刻返回。
     */
    @Test
    public void unparkTest() {
        // 使指定线程获取许可证
        LockSupport.unpark(Thread.currentThread());

        System.out.println("Begin park");
        LockSupport.park();
        System.out.println("End park");
    }

    /**
     * Test case: {@link LockSupport#parkNanos(long)}
     * API描述: 和park方法类似，如果调用parkNanos方法的线程已经拿到了与LockSupport关联的许可证，则调用LockSupport.parkNanos（long nanos）
     * 方法后会马上返回。该方法的不同在于，如果没有拿到许可证，则调用线程会被挂起nanos时间后修改为自动返回。
     */
    @Test
    public void parkNanosTest() {
        // 使指定线程获取许可证
        // LockSupport.unpark(Thread.currentThread());

        System.out.println("Begin park");
        LockSupport.parkNanos(5L * 1000000000);
        System.out.println("End park");
    }
}
