package com.ww.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author: Sun
 * @create: 2021-04-13 13:40
 * @version: v1.0
 */
public class ArrayBlockingQueueTest {

    /**
     * 笔记:
     * - ArrayBlockingQueue是使用独占锁实现的有界阻塞队列。其底层数据结构使用数组实现，ArrayBlockingQueue的内部有一个数组items，用来存放队列
     * 元素，putindex变量表示入队元素下标，takeIndex是出队下标，count统计队列元素个数。从定义可知，这些变量并没有使用volatile修饰，这是因为
     * 访问这些变量都是在锁块内，而加锁已经保证了锁块内变量的内存可见性了。另外有个独占锁lock用来保证出、入队操作的原子性，这保证了同时只有一个
     * 线程可以进行入队、出队操作。另外，notEmpty、notFull条件变量用来进行出、入队的同步。
     */

    /**
     * Test case: {@link ArrayBlockingQueue#offer(Object)}
     * API描述: 向队列尾部插入一个元素，如果队列有空闲空间则插入成功后返回true，如果队列已满则丢弃当前元素然后返回false。如果e元素为null则
     * 抛出NullPointerException异常。另外，该方法是不阻塞的。
     */
    @Test
    public void offerTest() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        queue.offer(1);
    }

    /**
     * Test case: {@link ArrayBlockingQueue#put(Object)}
     * API描述: 向队列尾部插入一个元素，如果队列有空闲则插入后直接返回true，如果队列已满则阻塞当前线程直到队列有空闲并插入成功后返回true，如果
     * 在阻塞时被其他线程设置了中断标志，则被阻塞线程会抛出InterruptedException异常而返回。另外，如果e元素为null则抛出NullPointerException异常。
     */
    @Test
    public void putTest() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        try {
            queue.put(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link ArrayBlockingQueue#poll()}
     * API描述: 从队列头部获取并移除一个元素，如果队列为空则返回null，该方法是不阻塞的。
     */
    @Test
    public void pollTest() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        queue.poll();
    }

    /**
     * Test case: {@link ArrayBlockingQueue#take()}
     * API描述: 获取当前队列头部元素并从队列里面移除它。如果队列为空则阻塞当前线程直到队列不为空然后返回元素，如果在阻塞时被其他线程设置了
     * 中断标志，则被阻塞线程会抛出InterruptedException异常而返回。
     */
    @Test
    public void takeTest() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        try {
            queue.put(1);

            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link ArrayBlockingQueue#peek()}
     * API描述: 获取队列头部元素但是不从队列里面移除它，如果队列为空则返回null，该方法是不阻塞的。
     */
    @Test
    public void peekTest() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        try {
            System.out.println(queue.peek());

            queue.put(1);

            System.out.println(queue.peek());
            System.out.println(queue.peek());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link ArrayBlockingQueue#peek()}
     * API描述: 计算当前队列元素个数。
     */
    @Test
    public void sizeTest() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        try {
            System.out.println(queue.size());

            queue.put(1);

            System.out.println(queue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
