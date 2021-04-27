package com.ww.java.util.concurrent.atomic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author: Sun
 * @create: 2021-04-02 17:30
 * @version: v1.0
 */
@Slf4j
public class LongAdderTest {

    LongAdder longAdder = new LongAdder();

    /**
     * Test case: {@link LongAdder#sum()}
     * API描述: 返回当前的值，内部操作是累加所有Cell内部的value值后再累加base。由于计算总和时没有对Cell数组进行加锁，所以在累加过程中可能
     * 有其他线程对Cell中的值进行了修改，也有可能对数组进行了扩容，所以sum返回的值并不是非常精确的，其返回值并不是一个调用sum方法时的原子快照值。
     */
    @Test
    public void sumTest() {
        longAdder.add(1);
        log.info("[sumTest] [value:{}]", longAdder.sum());
    }

    /**
     * Test case: {@link LongAdder#longValue()}
     * API描述: 等价于sum()
     */
    @Test
    public void longValueTest() {
        longAdder.add(1);
        log.info("[longValueTest] [value:{}]", longAdder.longValue());
    }

    /**
     * Test case: {@link LongAdder#reset()}
     * API描述: 重置操作，把base置为0，如果Cell数组有元素，则元素值被重置为0。
     */
    @Test
    public void resetTest() {
        longAdder.add(1);
        log.info("[resetTest] [Before reset] [value:{}]", longAdder.sum());

        longAdder.reset();
        log.info("[resetTest] [After reset] [value:{}]", longAdder.sum());
    }

    /**
     * Test case: {@link LongAdder#sumThenReset()}
     * API描述: 该方法是sum的改造版本，如下代码在使用sum累加对应的Cell值后，把当前Cell的值重置为0, base重置为0。这样，当多线程调用该方法
     * 时会有问题，比如考虑第一个调用线程清空Cell的值，则后一个线程调用时累加的都是0值。
     */
    @Test
    public void sumThenResetTest() {
        longAdder.add(1);
        log.info("[sumThenResetTest] [value:{}]", longAdder.sumThenReset());
    }

}
