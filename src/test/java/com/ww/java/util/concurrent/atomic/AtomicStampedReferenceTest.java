package com.ww.java.util.concurrent.atomic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author: Sun
 * @create: 2021-03-31 15:04
 * @version: v1.0
 */
@Slf4j
public class AtomicStampedReferenceTest {

    AtomicStampedReference<Node> atomicStampedReference = null;

    @Before
    public void init() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        node1.next = node2;
        node2.next = node3;

        atomicStampedReference = new AtomicStampedReference<>(node1, 1);
    }

    /**
     * Test case: {@link AtomicStampedReference#get(int[])}
     * API描述: 同时获取到引用和数据戳，可以将int类型的数组作为参数传到get()方法，该方法将返回引用，同时数据戳会存储在int类型数组的第一个元素。
     */
    @Test
    public void getTest() {
        int[] stampHolder = new int[1];
        Node node = atomicStampedReference.get(stampHolder);
        log.info("[getTest] [Node:{}] [Stamp:{}]", node, stampHolder[0]);
    }

    /**
     * Test case: {@link AtomicStampedReference#getReference()}
     * API描述: 返回引用的当前值。
     */
    @Test
    public void getReferenceTest() {
        Node node = atomicStampedReference.getReference();
        log.info("[getReferenceTest] [Node:{}]", node);
    }

    /**
     * Test case: {@link AtomicStampedReference#getStamp()}
     * API描述: 返回stamp的当前值。
     */
    @Test
    public void getStampTest() {
        int stamp = atomicStampedReference.getStamp();
        log.info("[getStampTest] [Stamp:{}]", stamp);
    }

    /**
     * Test Case: {@link AtomicStampedReference#attemptStamp(Object, int)}
     * API描述: 如果当前引用等于预期引用, 将更新新的版本戳到内存。
     */
    @Test
    public void attemptStampTest() {
        int[] stampHolder = new int[1];
        Node node = atomicStampedReference.get(stampHolder);
        log.info("[attemptStampTest] [Before update...] [Node:{}] [Stamp:{}]", node, stampHolder[0]);

        atomicStampedReference.attemptStamp(node, stampHolder[0] + 1);
        node = atomicStampedReference.get(stampHolder);
        log.info("[attemptStampTest] [Before after...] [Node:{}] [Stamp:{}]", node, stampHolder[0]);
    }

    /**
     * Test case: {@link AtomicStampedReference#compareAndSet(Object, Object, int, int)}
     * 测试目的: 使用AtomicStampedReference.compareAndSet()解决ABA问题。
     */
    @Test
    public void abaTest() {
        log.info("[abaTest] [链表初始状态] [Node:{}] [Stamp:{}]", atomicStampedReference.getReference(),
                atomicStampedReference.getStamp());

        // A线程删除头节点
        Thread threadA = new Thread(() -> {
            this.deleteHeadNode(true);
            log.info("[abaTest] [THREAD-A删除A节点结束] [Node:{}] [Stamp:{}]", atomicStampedReference.getReference(),
                    atomicStampedReference.getStamp());
        }, "THREAD-A");

        // B线程删除A节点，再删除B节点，再把A节点加到队首
        Thread threadB = new Thread(() -> {
            // 删除A节点
            Node oldHeadNodeA = this.deleteHeadNode(false);
            log.info("[abaTest] [THREAD-B删除A节点结束] [Node:{}] [Stamp:{}]", atomicStampedReference.getReference(),
                    atomicStampedReference.getStamp());

            // 删除B节点
            Node oldHeadNodeB = this.deleteHeadNode(false);
            log.info("[abaTest] [THREAD-B删除B节点结束] [Node:{}] [Stamp:{}]", atomicStampedReference.getReference(),
                    atomicStampedReference.getStamp());

            // 再把A节点放到队首
            addHeadNode(oldHeadNodeA);
            log.info("[abaTest] [THREAD-B再把A节点放到队首结束] [Node:{}] [Stamp:{}]", atomicStampedReference.getReference(),
                    atomicStampedReference.getStamp());
        }, "THREAD-B");

        try {
            threadA.start();
            Thread.sleep(500);
            threadB.start();

            threadB.join();
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------- PRIVATE METHOD ---------------------------------------- //

    private Node addHeadNode(Node newHeadNode) {
        while (true) {
            Node oldHeadNode = atomicStampedReference.getReference();
            int stamp = atomicStampedReference.getStamp();

            if (oldHeadNode == null) {
                if (atomicStampedReference.compareAndSet(oldHeadNode, newHeadNode, stamp, stamp + 1)) {
                    return oldHeadNode;
                }
            }

            newHeadNode.next = oldHeadNode;
            if (atomicStampedReference.compareAndSet(oldHeadNode, newHeadNode, stamp, stamp + 1)) {
                return oldHeadNode;
            }

            log.warn("[addHeadNode] [ThreadName:{}] [执行失败，再次执行] [Stamp:{}]", Thread.currentThread().getName(), stamp);
        }
    }

    private Node deleteHeadNode(boolean sleepFlag) {
        while (true) {
            Node oldHeadNode = atomicStampedReference.getReference();
            int stamp = atomicStampedReference.getStamp();

            if (oldHeadNode == null) {
                log.warn("[deleteHeadNode] [节点为空，无法再次删除]");
                return null;
            }

            Node newHeadNode = oldHeadNode.next;
            if (sleepFlag) {
                try {
                    // 模拟线程时间片用完，切换线程
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (atomicStampedReference.compareAndSet(oldHeadNode, newHeadNode, stamp, stamp + 1)) {
                oldHeadNode.next = null;
                return oldHeadNode;
            }

            log.warn("[deleteHeadNode] [ThreadName:{}] [执行失败，再次执行] [Stamp:{}]", Thread.currentThread().getName(), stamp);
        }
    }

    @Data
    private static class Node {
        Object data;
        Node next;

        Node(Object data) {
            this.data = data;
        }
    }
}
