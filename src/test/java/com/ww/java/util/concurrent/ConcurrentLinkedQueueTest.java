package com.ww.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author: Sun
 * @create: 2021-04-12 13:56
 * @version: v1.0
 */
public class ConcurrentLinkedQueueTest {

    /**
     * 笔记:
     * - ConcurrentLinkedQueue是线程安全的无界非阻塞队列。其底层数据结构使用单向链表实现，对于入队和出队操作使用CAS来实现线程安全。ConcurrentLinkedQueue
     * 内部的队列使用单向链表方式实现，其中有两个volatile类型的Node节点分别用来存放队列的首、尾节点。从下面的无参构造函数可知，默认头、尾节点
     * 都是指向item为null的哨兵节点。新元素会被插入队列末尾，出队时从队列头部获取一个元素。在Node节点内部则维护一个使用volatile修饰的变量item，
     * 用来存放节点的值；next用来存放链表的下一个节点，从而链接为一个单向无界链表。其内部则使用UNSafe工具类提供的CAS算法来保证出入队时操作链表的原子性。
     */

    /**
     * Test case: {@link ConcurrentLinkedQueue#offer(Object)}
     * API描述: offer操作是在队列末尾添加一个元素，如果传递的参数是null则抛出NPE异常，否则由于ConcurrentLinkedQueue是无界队列，该方法一
     * 直会返回true。另外，由于使用CAS无阻塞算法，因此该方法不会阻塞挂起调用线程。
     */
    @Test
    public void offerTest() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.offer(1);
    }


    /**
     * Test case: {@link ConcurrentLinkedQueue#add(Object)}
     * API描述: add操作是在链表末尾添加一个元素，其实在内部调用的还是offer操作。
     */
    @Test
    public void addTest() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.add(1);
    }

    /**
     * Test case: {@link ConcurrentLinkedQueue#poll()}
     * API描述: poll操作是在队列头部获取并移除一个元素，如果队列为空则返回null。
     */
    @Test
    public void pollTest() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        System.out.println(queue.poll());

        queue.add(1);
        System.out.println(queue.poll());
    }

    /**
     * Test case: {@link ConcurrentLinkedQueue#peek()}
     * API描述: peek操作是获取队列头部一个元素（只获取不移除），如果队列为空则返回null。
     */
    @Test
    public void peekTest() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        System.out.println(queue.peek());

        queue.add(1);
        System.out.println(queue.peek());
    }

    /**
     * Test case: {@link ConcurrentLinkedQueue#size()}
     * API描述: 计算当前队列元素个数，在并发环境下不是很有用，因为CAS没有加锁，所以从调用size函数到返回结果期间有可能增删元素，导致统计的元素
     * 个数不精确。
     */
    @Test
    public void sizeTest() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        System.out.println(queue.size());

        queue.add(1);
        System.out.println(queue.size());
    }

    /**
     * Test case: {@link ConcurrentLinkedQueue#remove()}
     * API描述: 如果队列里面存在该元素则删除该元素，如果存在多个则删除第一个，并返回true，否则返回false。
     */
    @Test
    public void removeTest() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        System.out.println(queue.remove(1));

        queue.add(1);
        System.out.println(queue.remove(1));
    }

    /**
     * Test case: {@link ConcurrentLinkedQueue#contains(Object)}
     * API描述: 判断队列里面是否含有指定对象，由于是遍历整个队列，所以像size操作一样结果也不是那么精确，有可能调用该方法时元素还在队列里面，
     * 但是遍历过程中其他线程才把该元素删除了，那么就会返回false。
     */
    @Test
    public void containsTest() {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        System.out.println(queue.contains(1));

        queue.add(1);
        System.out.println(queue.contains(1));
    }
}
