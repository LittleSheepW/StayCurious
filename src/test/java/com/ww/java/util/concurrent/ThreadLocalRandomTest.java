package com.ww.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: Sun
 * @create: 2021-04-02 15:49
 * @version: v1.0
 */
public class ThreadLocalRandomTest {

    /**
     * 笔记:
     * Random的缺点是多个线程会使用同一个原子性种子变量，从而导致对原子变量更新的竞争。ThreadLocalRandom中每个线程都维护一个种子变量，每个
     * 线程生成随机数时都根据自己老的种子计算新的种子，并使用新种子更新老的种子，再根据新种子计算随机数，就不会存在竞争问题了，这会大大提高并发
     * 性能。
     */

    /**
     * Test case: {@link ThreadLocalRandom#nextInt(int)}
     * API描述: 计算当前线程的下一个随机数。
     */
    @Test
    public void nextIntTest() {
        ThreadLocalRandom current = ThreadLocalRandom.current();

        for (int i = 0; i < 10; i++) {
            System.out.println(current.nextInt(5));
        }
    }
}
