package com.ww.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: Sun
 * @create: 2021-04-13 10:15
 * @version: v1.0
 */
public class LinkedBlockingQueueTest {

    /**
     * 笔记:
     * - LinkedBlockingQueue是使用独占锁实现的有界阻塞队列。底层数据接口使用单向链表实现，有两个Node，分别用来存放首、尾节点，并且还有一个初始
     * 值为0的原子变量count，用来记录队列元素个数。另外还有两个ReentrantLock的实例，分别用来控制元素入队和出队的原子性，其中takeLock用来
     * 控制同时只有一个线程可以从队列头获取元素，其他线程必须等待，putLock控制同时只能有一个线程可以获取锁，在队列尾部添加元素，其他线程必须等待。
     * 另外，notEmpty和notFull是条件变量，它们内部都有一个条件队列用来存放进队和出队时被阻塞的线程，其实这是生产者—消费者模型。
     */

    /**
     * Test case: {@link LinkedBlockingQueue#offer(Object)}
     * API描述: 向队列尾部插入一个元素，如果队列中有空闲则插入成功后返回true，如果队列已满则丢弃当前元素然后返回false。如果e元素为null则
     * 抛出NullPointerException异常。另外，该方法是非阻塞的。
     */
    @Test
    public void offerTest() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.offer(1);
        queue.offer(2);
    }

    /**
     * Test case: {@link LinkedBlockingQueue#put(Object)}
     * API描述: 向队列尾部插入一个元素，如果队列中有空闲则插入后直接返回，如果队列已满则阻塞当前线程，直到队列有空闲插入成功后返回。如果在阻塞
     * 时被其他线程设置了中断标志，则被阻塞线程会抛出InterruptedException异常而返回。另外，如果e元素为null则抛出NullPointerException异常。
     */
    @Test
    public void putTest() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1);
        try {
            queue.put(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread threadA = new Thread(() -> {
            try {
                // 队列满时添加元素，会被阻塞在这里
                System.out.println("线程A尝试添加元素到队列中");

                queue.put(2);
                System.out.println("线程A添加元素到队列成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                // 等3秒再获取队列中的元素
                Thread.sleep(3000);

                System.out.println("线程B从队列中获取元素成功: " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();

        try {
            threadB.join();
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link LinkedBlockingQueue#poll()}
     * API描述: 从队列头部获取并移除一个元素，如果队列为空则返回null，该方法是不阻塞的。
     */
    @Test
    public void pollTest() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        try {
            // 队列为空时直接获取
            System.out.println(queue.poll());

            // 往队列中添加一个元素再次进行获取
            queue.put(1);
            System.out.println(queue.poll());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link LinkedBlockingQueue#take()}
     * API描述: 获取当前队列头部元素并从队列里面移除它。如果队列为空则阻塞当前线程直到队列不为空然后返回元素，如果在阻塞时被其他线程设置了中断
     * 标志，则被阻塞线程会抛出InterruptedException异常而返回。
     */
    @Test
    public void takeTest() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        Thread threadA = new Thread(() -> {
            try {
                // 队列为空时直接获取，会被阻塞在这里
                System.out.println("A线程尝试从队列中获取元素");

                System.out.println("A线程从队列中成功获取到元素: " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                // 等3秒再添加元素到队列中
                Thread.sleep(3000);

                queue.put(1);
                System.out.println("B线程添加元素到队列成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();

        try {
            threadB.join();
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link LinkedBlockingQueue#peek()}
     * API描述: 获取队列头部元素但是不从队列里面移除它，如果队列为空则返回null。该方法是不阻塞的。
     */
    @Test
    public void peekTest() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        try {
            // 队列为空时直接获取
            System.out.println(queue.peek());

            // 往队列中添加一个元素再次进行获取
            queue.put(1);
            System.out.println(queue.peek());
            System.out.println(queue.peek());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link LinkedBlockingQueue#remove(Object)}
     * API描述: 删除队列里面指定的元素，有则删除并返回true，没有则返回false。
     */
    @Test
    public void removeTest() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        try {
            System.out.println(queue.remove(1));

            queue.put(1);
            System.out.println(queue.remove(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test case: {@link LinkedBlockingQueue#size()}
     * API描述: 获取当前队列元素个数。
     */
    @Test
    public void sizeTest() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        try {
            System.out.println(queue.size());

            queue.put(1);
            System.out.println(queue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
