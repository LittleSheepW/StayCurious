package com.ww.sun.misc.unsafe;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.misc.Unsafe;
import sun.reflect.Reflection;

import java.lang.reflect.Field;

/**
 * @author: Sun
 * @create: 2021-03-31 10:23
 * @version: v1.0
 */
@Slf4j
public class UnsafeTest {

    private volatile int id = 1;
    private volatile int age = 12;
    private volatile String name = "sun";

    /**
     * Test case: {@link Unsafe#objectFieldOffset(Field)}
     * API描述: 返回指定的变量在所属类中的内存偏移地址
     *
     * @throws Exception
     */
    @Test
    public void objectFieldOffsetTest() throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        long idOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("id"));
        long ageOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("age"));
        long nameOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("name"));
        System.out.println(idOffset + "..." + ageOffset + "..." + nameOffset);
    }

    /**
     * Test case: {@link Unsafe#arrayBaseOffset(Class)}
     * API描述: 获取数组中第一个元素的地址。
     *
     * @throws Exception
     */
    @Test
    public void arrayBaseOffsetTest() throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        int[] array = new int[]{};
        System.out.println(unsafe.arrayBaseOffset(array.getClass()));
    }

    /**
     * Test case: {@link Unsafe#arrayIndexScale(Class)}
     * API描述: 获取数组中一个元素占用的字节。
     *
     * @throws Exception
     */
    @Test
    public void arrayIndexScaleTest() throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        int[] array = new int[]{};
        System.out.println(unsafe.arrayIndexScale(array.getClass()));
    }

    /**
     * Test case: {@link Unsafe#compareAndSwapInt(Object, long, int, int)}
     * API描述: compareAndSwap的意思是比较并交换。CAS有四个操作数，分别为: 对象内存位置、对象中的变量的偏移量、变量预期值和新的值。其操作
     * 含义是，如果对象obj中内存偏移量为valueOffset的变量值为expect，则使用新的值update替换旧的值expect。这是处理器提供的一个原子性指令。
     *
     * @throws Exception
     */
    @Test
    public void compareAndSwapIntTest() throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        System.out.println("Age before update: " + age);
        boolean flag = unsafe.compareAndSwapInt(this, unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("age")), 12, 13);
        if (flag) {
            System.out.println("Age after update: " + this.age);
        } else {
            System.out.println("Update failed");
        }
    }

    /**
     * Test case: {@link Unsafe#getIntVolatile(Object, long)}
     * API描述: 获取对象obj中偏移量为offset的变量对应volatile语义的值。
     *
     * @throws Exception
     */
    @Test
    public void getIntVolatileTest() throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        System.out.println(unsafe.getIntVolatile(this, unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("id"))));
    }

    /**
     * Test case: {@link Unsafe#putIntVolatile(Object, long, int)}
     * API描述: 设置obj对象中offset偏移的类型为int的field的值为value，支持volatile语义。
     *
     * @throws Exception
     */
    @Test
    public void putIntVolatileTest() throws Exception {
        Unsafe unsafe = getUnsafeInstance();
        long offset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("id"));

        System.out.println(unsafe.getIntVolatile(this, offset));
        unsafe.putIntVolatile(this, offset, 2);

        System.out.println(unsafe.getIntVolatile(this, offset));
    }

    /**
     * Test case: {@link Unsafe#putOrderedInt(Object, long, int)}
     * API描述: 设置obj对象中offset偏移地址对应的int型field的值为value。这是一个有延迟的putIntVolatile方法，并且不保证值修改对其他线程
     * 立刻可见。只有在变量使用volatile修饰并且预计会被意外修改时才使用该方法。
     *
     * @throws Exception
     */
    @Test
    public void putOrderedIntTest() throws Exception {
        Unsafe unsafe = getUnsafeInstance();
        long offset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("id"));

        System.out.println(unsafe.getIntVolatile(this, offset));
        unsafe.putOrderedInt(this, offset, 2);

        System.out.println(unsafe.getIntVolatile(this, offset));
    }

    /**
     * Test case: {@link Unsafe#park(boolean, long)}
     * API描述: 阻塞当前线程。其中参数isAbsolute等于false且time等于0表示一直阻塞。time大于0表示等待指定的time(ns)后阻塞线程会被唤醒，这个time
     * 是个相对值，是个增量值，也就是相对当前时间累加time后当前线程就会被唤醒。如果isAbsolute等于true，并且time大于0，则表示阻塞的线程到指定
     * 的时间点后会被唤醒，这里time是个绝对时间，是将某个时间点换算为ms后的值。另外，当其他线程调用了当前阻塞线程的interrupt方法而中断了当前
     * 线程时，当前线程也会返回，而当其他线程调用了unPark方法并且把当前线程作为参数时当前线程也会返回。
     *
     * @throws Exception
     */
    @Test
    public void parkTest() throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        Thread thread = new Thread(() -> {
            log.info("[parkTest] [Begin wait...] [beginTime:{}]", System.currentTimeMillis());
            unsafe.park(false, 1000000000L * 3);
            log.info("[parkTest] [End wait...] [endTime:{}]", System.currentTimeMillis());

            log.info("[parkTest] [Begin wait...] [beginTime:{}]", System.currentTimeMillis());
            unsafe.park(true, System.currentTimeMillis() + 3000);
            log.info("[parkTest] [End wait...] [endTime:{}]", System.currentTimeMillis());
        });

        thread.start();
        thread.join();
    }

    /**
     * Test case: {@link Unsafe#unpark(Object)}
     * API描述: 唤醒调用park后阻塞的线程。
     *
     * @throws Exception
     */
    @Test
    public void unparkTest() throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        Thread threadA = new Thread(() -> {
            log.info("[parkTest] [Begin wait...] [beginTime:{}]", System.currentTimeMillis());
            unsafe.park(true, System.currentTimeMillis() + 100000);
            log.info("[parkTest] [End wait...] [endTime:{}]", System.currentTimeMillis());
        });

        Thread threadB = new Thread(() -> {
            log.info("[parkTest] [Begin unpark...]");
            unsafe.unpark(threadA);
            log.info("[parkTest] [End unpark...]");
        });

        threadA.start();

        threadB.start();
        threadB.join();
    }

    /**
     * Test case: {@link Unsafe#getAndSetInt(Object, long, int)}
     * API描述: 获取对象obj中偏移量为offset的变量volatile语义的当前值，并设置变量volatile语义的值为update。
     *
     * @throws Exception
     */
    @Test
    public void getAndSetIntTest() throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        long offset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("id"));
        int oldValue = unsafe.getAndSetInt(this, offset, 2);
        System.out.println(oldValue);

        int newValue = unsafe.getIntVolatile(this, offset);
        System.out.println(newValue);
    }

    /**
     * Test case: {@link Unsafe#getAndAddInt(Object, long, int)}
     * API描述: 获取对象obj中偏移量为offset的变量volatile语义的当前值，并设置变量值为原始值+addValue。
     *
     * @throws Exception
     */
    @Test
    public void getAndAddIntTest() throws Exception {
        Unsafe unsafe = getUnsafeInstance();

        long offset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("id"));
        int oldValue = unsafe.getAndAddInt(this, offset, 1);
        System.out.println(oldValue);

        int newValue = unsafe.getIntVolatile(this, offset);
        System.out.println(newValue);
    }

    // ---------------------------------------- PRIVATE METHOD ---------------------------------------- //

    /**
     * Unsafe对象无法通过new的形式获取，也无法通过Unsafe.getUnsafe()来获取实例，但是可以通过反射获取已经存在的Unsafe实例。
     *
     * @return
     * @throws Exception
     */
    private Unsafe getUnsafeInstance() throws Exception {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);

        return (Unsafe) theUnsafeInstance.get(Unsafe.class);
    }
}
