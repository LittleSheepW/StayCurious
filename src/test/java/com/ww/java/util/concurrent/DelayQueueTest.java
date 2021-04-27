package com.ww.java.util.concurrent;

import lombok.Data;
import org.junit.Test;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author: Sun
 * @create: 2021-04-14 11:29
 * @version: v1.0
 */
public class DelayQueueTest {

    /**
     * 笔记:
     * - DelayQueue并发队列是一个无界阻塞延迟队列，队列中的每个元素都有个过期时间，当从队列获取元素时，只有过期元素才会出队列。队列头元素是
     * 最快要过期的元素。DelayQueue内部使用PriorityQueue存放数据，使用ReentrantLock实现线程同步。另外，队列里面的元素要实现Delayed接口，
     * 由于每个元素都有一个过期时间，所以要实现获知当前元素还剩下多少时间就过期了的接口，由于内部使用优先级队列来实现，所以要实现元素之间相互
     * 比较的接口。
     */

    /**
     * Test case: {@link DelayQueue#offer(Delayed)}
     * API描述: 插入元素到队列，如果插入元素为null则抛出NullPointerException异常，否则由于是无界队列，所以一直返回true。插入元素要实现
     * Delayed接口。
     */
    @Test
    public void offerTest() {
        DelayQueue<DelayItem> queue = new DelayQueue<>();
        queue.offer(new DelayItem(System.currentTimeMillis()));
    }

    /**
     * Test case: {@link DelayQueue#take()}
     * API描述: 获取并移除队列里面延迟时间过期的元素，如果队列里面没有过期元素则等待。
     */
    @Test
    public void takeTest() {
        DelayQueue<DelayItem> queue = new DelayQueue<>();
        queue.offer(new DelayItem(System.currentTimeMillis()));

        try {
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link DelayQueue#poll()}
     * API描述: 获取并移除队头过期元素，如果没有过期元素则返回null。
     */
    @Test
    public void pollTest() {
        DelayQueue<DelayItem> queue = new DelayQueue<>();
        queue.offer(new DelayItem(System.currentTimeMillis()));

        try {
            System.out.println(queue.poll());
            Thread.sleep(5500);
            System.out.println(queue.poll());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link DelayQueue#size()}
     * API描述: 计算队列元素个数，包含过期的和没有过期的。
     */
    @Test
    public void sizeTest() {
        DelayQueue<DelayItem> queue = new DelayQueue<>();
        queue.offer(new DelayItem(System.currentTimeMillis()));

        System.out.println(queue.size());
    }

    @Data
    private static class DelayItem implements Delayed {

        /**
         * 5秒
         */
        private final static long DELAY = 5 * 1000;

        /**
         * 开始时间
         */
        private long startTime;
        /**
         * 到期时间
         */
        private long expire;

        public DelayItem(long startTime) {
            this.startTime = startTime;
            this.expire = startTime + DELAY;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }
    }
}
