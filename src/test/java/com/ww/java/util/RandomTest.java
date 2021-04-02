package com.ww.java.util;

import org.junit.Test;

import java.util.Random;

/**
 * @author: Sun
 * @create: 2021-04-02 14:17
 * @version: v1.0
 */
public class RandomTest {

    /**
     * 笔记:
     * 每个Random实例里面都有一个原子性的种子变量用来记录当前的种子值，当要生成新的随机数时需要根据当前种子计算新的种子并更新回原子变量。在
     * 多线程下使用单个Random实例生成随机数时，当多个线程同时计算随机数来计算新的种子时，多个线程会竞争同一个原子变量的更新操作，由于原子变量
     * 的更新是CAS操作，同时只有一个线程会成功，所以会造成大量线程进行自旋重试，这会降低并发性能。
     */

    /**
     * Test case: {@link Random#nextInt(int)}
     * API描述: 返回一个随机数。
     */
    @Test
    public void nextIntTest() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(5));
        }
    }
}
