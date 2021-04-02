package com.ww.java.util.concurrent.atomic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: Sun
 * @create: 2021-03-31 14:08
 * @version: v1.0
 */
@Slf4j
public class AtomicReferenceTest {

    AtomicReference<Node> atomicReference = new AtomicReference<>();

    @Before
    public void init() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        node1.next = node2;
        node2.next = node3;

        atomicReference.set(node1);
    }

    /**
     * Test case: {@link AtomicReference#compareAndSet(Object, Object)}
     * 测试目的: 模拟ABA问题。
     * ABA问题: 假设有两个线程，线程1读到内存的数值为A，然后时间片到期，撤出CPU。线程2运行，线程2也读到了A，把它改成了B，然后又把B改成原来的值A，
     * 简单地说，修改的次序是A→B→A。接着线程1开始运行，它发现内存的值还是A，完全不知道内存被操作过。如果只是简单的数字，那没什么影响；如果使用
     * AtomicReference，并且操作的是复杂的数据结构，就可能会出问题了。例如下列代码: A线程最终指向了一个错误的节点
     */
    @Test
    public void abaTest() {
        log.info("[abaTest] [链表初始状态:{}]", atomicReference.get());

        // A线程删除头节点
        Thread threadA = new Thread(() -> {
            this.deleteHeadNode(true);
            log.info("[abaTest] [THREAD-A删除A节点结束] [Node:{}]", atomicReference.get());
        }, "THREAD-A");

        // B线程删除A节点，再删除B节点，再把A节点加到队首
        Thread threadB = new Thread(() -> {
            // 删除A节点
            Node oldHeadNodeA = this.deleteHeadNode(false);
            log.info("[abaTest] [THREAD-B删除A节点结束] [Node:{}]", atomicReference.get());

            // 删除B节点
            Node oldHeadNodeB = this.deleteHeadNode(false);
            log.info("[abaTest] [THREAD-B删除B节点结束] [Node:{}]", atomicReference.get());

            // 再把A节点放到队首
            addHeadNode(oldHeadNodeA);
            log.info("[abaTest] [THREAD-B再把A节点放到队首结束] [Node:{}]", atomicReference.get());
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
            Node oldHeadNode = atomicReference.get();

            if (oldHeadNode == null) {
                if (atomicReference.compareAndSet(oldHeadNode, newHeadNode)) {
                    return oldHeadNode;
                }
            }

            newHeadNode.next = oldHeadNode;
            if (atomicReference.compareAndSet(oldHeadNode, newHeadNode)) {
                return oldHeadNode;
            }

            log.warn("[addHeadNode] [ThreadName:{}] [执行失败，再次执行]", Thread.currentThread().getName());
        }
    }

    private Node deleteHeadNode(boolean sleepFlag) {
        while (true) {
            Node oldHeadNode = atomicReference.get();
            if (oldHeadNode == null) {
                log.warn("[deleteHeadNode] [节点为空，无法再次删除]");
                return null;
            }

            Node newHeadNode = oldHeadNode.next;
            if (sleepFlag) {
                try {
                    // 模拟线程时间片用完，切换线程
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (atomicReference.compareAndSet(oldHeadNode, newHeadNode)) {
                oldHeadNode.next = null;
                return oldHeadNode;
            }

            log.warn("[deleteHeadNode] [ThreadName:{}] [执行失败，再次执行]", Thread.currentThread().getName());
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
