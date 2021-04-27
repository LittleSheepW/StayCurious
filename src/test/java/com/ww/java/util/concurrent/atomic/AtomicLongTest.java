package com.ww.java.util.concurrent.atomic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: Sun
 * @create: 2021-04-02 16:24
 * @version: v1.0
 */
@Slf4j
public class AtomicLongTest {

    /**
     * 笔记:
     * - AtomicLong通过CAS提供了非阻塞的原子性操作，相比使用阻塞算法的同步器来说它的性能已经很好了，但在高并发下大量线程会同时去竞争更新同一个
     * 原子变量，但是由于同时只有一个线程的CAS操作会成功，这就造成了大量线程竞争失败后，会通过无限循环不断进行自旋尝试CAS的操作，而这会白白浪费
     * CPU资源。
     */

    AtomicLong atomicLong = new AtomicLong();

    /**
     * Test case: {@link AtomicLong#incrementAndGet()}
     * API描述: 以原子方式将当前值增加一，返回更新后的值。
     */
    @Test
    public void incrementAndGetTest() {
        log.info("[incrementAndGet] [Before update] [oldValue:{}]", atomicLong.get());

        log.info("[incrementAndGet] [Before update] [newValue:{}]", atomicLong.incrementAndGet());
    }

    /**
     * Test case: {@link AtomicLong#decrementAndGet()}
     * API描述: 以原子方式将当前值减一，返回更新后的值。
     */
    @Test
    public void decrementAndGetTest() {
        log.info("[decrementAndGet] [Before update] [oldValue:{}]", atomicLong.get());

        log.info("[decrementAndGet] [Before update] [newValue:{}]", atomicLong.decrementAndGet());
    }

    /**
     * Test case: {@link AtomicLong#getAndIncrement()}
     * API描述: 以原子方式将当前值增加一，返回先前的值。
     */
    @Test
    public void getAndIncrementTest() {
        log.info("[getAndIncrementTest] [Before update] [oldValue:{}] [newValue:{}]", atomicLong.getAndIncrement(), atomicLong.get());
    }

    /**
     * Test case: {@link AtomicLong#getAndDecrement()}
     * API描述: 以原子方式将当前值减一，返回先前的值。
     */
    @Test
    public void getAndDecrementTest() {
        log.info("[getAndDecrementTest] [Before update] [oldValue:{}] [newValue:{}]", atomicLong.getAndDecrement(), atomicLong.get());
    }

    /**
     * Test case: {@link AtomicLong#compareAndSet(long, long)}
     * API描述: 如果当前值==期望值，则以原子方式将该值设置为给定的更新值。
     */
    @Test
    public void compareAndSetTest() {
        log.info("[getAndDecrementTest] [Before update] [oldValue:{}]", atomicLong.get());

        atomicLong.compareAndSet(0, 1);
        log.info("[getAndDecrementTest] [Before update] [newValue:{}]", atomicLong.get());
    }

    @Test
    public void test() {

    }
}
