package com.ww.dataStructure.queue;

import java.util.Arrays;

/**
 * 优先队列：优先队列不再遵循先入先出的原则，而是分为两种情况。
 * - 最大优先队列：无论入队顺序如何，都是当前最大的元素优先出队。
 * - 最小优先队列：无论入队顺序如何，都是当前最小的元素优先出队。
 * 要实现以上需求，利用线性数据结构并非不能实现，但是时间复杂度较高。使用二叉堆实现优先队列，时间复杂度会低一些。优先队列入队和出队的时间复杂度
 * 和二叉堆节点上浮、下沉的时间复杂度相同，都是O(logn)。
 * <p>
 * 以下代码实现为最大优先队列
 *
 * @author: Sun
 * @create: 2021-07-19 14:42
 * @version: v1.0
 */
public class PriorityQueue {

    private int size;
    private int[] array;

    public PriorityQueue() {
        this.array = new int[32];
    }

    /**
     * 元素入队
     *
     * @param key
     */
    public void enQueue(int key) {
        if (size >= array.length) {
            resize();
        }

        this.array[size++] = key;
        upAdjust();
    }

    /**
     * 元素出队
     *
     * @return
     */
    public int deQueue() {
        if (size <= 0) {
            throw new RuntimeException("The queue is empty!");
        }

        int head = array[0];
        array[0] = array[--size];
        downAdjust();

        return head;
    }

    /**
     * 元素上浮
     */
    public void upAdjust() {
        int childIndex = size - 1;
        int parentIndex = (childIndex - 1) / 2;

        int temp = array[childIndex];
        while (childIndex > 0 && temp > array[parentIndex]) {
            array[childIndex] = array[parentIndex];
            childIndex = parentIndex;
            parentIndex = (parentIndex - 1) / 2;
        }
        array[childIndex] = temp;
    }

    /**
     * 元素下沉
     */
    public void downAdjust() {
        int parentIndex = 0;
        int childIndex = parentIndex * 2 + 1;
        int temp = array[parentIndex];

        while (childIndex < size) {
            if (childIndex + 1 < size && array[childIndex] < array[childIndex + 1]) {
                childIndex++;
            }
            if (temp >= array[childIndex]) {
                break;
            }

            array[parentIndex] = array[childIndex];
            parentIndex = childIndex;
            childIndex = childIndex * 2 + 1;
        }
        array[parentIndex] = temp;
    }

    /**
     * 队列扩容
     */
    public void resize() {
        Arrays.copyOf(this.array, this.array.length * 2);
    }


    public static void main(String[] args) throws Exception {
        PriorityQueue priorityQueue = new PriorityQueue();
        priorityQueue.enQueue(3);
        priorityQueue.enQueue(5);
        priorityQueue.enQueue(10);
        priorityQueue.enQueue(2);
        priorityQueue.enQueue(7);
        System.out.println("出队元素：" + priorityQueue.deQueue());
        System.out.println("出队元素：" + priorityQueue.deQueue());
        System.out.println("出队元素：" + priorityQueue.deQueue());
        System.out.println("出队元素：" + priorityQueue.deQueue());
        System.out.println("出队元素：" + priorityQueue.deQueue());
        // System.out.println("出队元素：" + priorityQueue.deQueue());
    }
}
