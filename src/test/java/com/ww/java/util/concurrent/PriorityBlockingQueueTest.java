package com.ww.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author: Sun
 * @create: 2021-04-13 14:58
 * @version: v1.0
 */
public class PriorityBlockingQueueTest {

    /**
     * 笔记:
     * - PriorityBlockingQueue是使用独占锁实现的带优先级的无界阻塞队列，每次出队都返回优先级最高或者最低的元素。其底层数据结构使用数组实现，
     * 内部是使用平衡二叉树堆实现的，所以直接遍历队列元素不保证有序。默认使用对象的compareTo方法提供比较规则，如果你需要自定义比较规则则可以
     * 自定义comparators。PriorityBlockingQueue内部有一个数组queue，用来存放队列元素，size用来存放队列元素个数。allocationSpinLock
     * 是个自旋锁，其使用CAS操作来保证同时只有一个线程可以扩容队列，状态为0或者1，其中0表示当前没有进行扩容，1表示当前正在扩容。由于这是一个
     * 优先级队列，所以有一个比较器comparator用来比较元素大小。lock独占锁对象用来控制同时只能有一个线程可以进行入队、出队操作。notEmpty条
     * 件变量用来实现take方法阻塞模式。这里没有notFull条件变量是因为这里的put操作是非阻塞的，为啥要设计为非阻塞的，是因为这是无界队列。
     */

    /**
     * Test case: {@link PriorityBlockingQueue#offer(Object)}
     * API描述: offer操作的作用是在队列中插入一个元素，由于是无界队列，所以一直返回true。
     */
    @Test
    public void offerTest() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        queue.offer(2);
        queue.offer(4);
        queue.offer(6);
        queue.offer(1);

        try {
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link PriorityBlockingQueue#poll()}
     * API描述: poll操作的作用是获取队列内部堆树的根节点元素，如果队列为空，则返回null。
     */
    @Test
    public void pollTest() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        queue.offer(2);
        queue.offer(4);
        queue.offer(6);
        queue.offer(8);
        queue.offer(10);
        queue.offer(11);

        queue.poll();
    }

    /**
     * Test case: {@link PriorityBlockingQueue#poll()}
     * API描述: put操作内部调用的是offer操作，由于是无界队列，所以不需要阻塞。
     */
    @Test
    public void putTest() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        queue.put(1);
    }

    /**
     * Test case: {@link PriorityBlockingQueue#take()}
     * API描述: take操作的作用是获取队列内部堆树的根节点元素，如果队列为空则阻塞。
     */
    @Test
    public void takeTest() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        queue.put(1);

        try {
            queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link PriorityBlockingQueue#size()}
     * API描述: 计算队列元素个数。如下代码在返回size前加了锁，以保证在调用size（）方法时不会有其他线程进行入队和出队操作。另外，由于size变量
     * 没有被修饰为volatie的，所以这里加锁也保证了在多线程下size变量的内存可见性。
     */
    @Test
    public void sizeTest() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        System.out.println(queue.size());

        queue.put(1);
        System.out.println(queue.size());
    }
}
