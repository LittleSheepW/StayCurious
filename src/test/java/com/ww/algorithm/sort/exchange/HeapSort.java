package com.ww.algorithm.sort.exchange;

import com.ww.dataStructure.heap.BinaryHeap;
import org.junit.Test;

import java.util.Arrays;

/**
 * 堆排序：二叉堆的构建、删除、自我调整等基本操作，是实现堆排序的基础。
 * <p>
 * 堆排序算法步骤：
 * 1、把无序数组构建成二叉堆。如果需要从小到大排序，则构建成最大堆；如果需要从大到小排序，则构建成最小堆。
 * 2、循环删除堆顶元素，替换到二叉堆末尾，调整堆产生新的堆顶。
 * <p>
 * 不稳定排序；
 * 平均时间复杂度O(nlogn)；第一步把无序数组构建成二叉堆的时间复杂度为O(n)，第二步需要进行n-1次循环，每次循环调用一次downAdjust()方法，所以
 * 第二步的计算规模是(n-1)*logn，时间复杂度为o(nlogn)，两个步骤时并列关系，所以整体的时间复杂度为O(nlogn)。
 * 最坏时间复杂度O(nlogn)；
 * 空间复杂度O(1)。
 *
 * @author: Sun
 * @create: 2021-07-26 14:28
 * @version: v1.0
 */
public class HeapSort {

    /**
     * 升序堆排序
     */
    @Test
    public void ascendingHeapSortTest() {
        BinaryHeap.LargestBinaryHeap largestBinaryHeap = new BinaryHeap.LargestBinaryHeap();

        // 1、将无序数组构建成最大堆
        int[] array = new int[]{1, 3, 2, 6, 5, 7, 8, 9, 10, 0};
        largestBinaryHeap.buildBinaryHeap(array);
        System.out.println("堆排序前的数组：" + Arrays.toString(array));

        // 2、循环删除堆顶元素，移到集合尾部，调整堆产生新的堆顶
        for (int i = array.length - 1; i > 0; i--) {
            // 最后1个元素跟第一个元素进行交换
            int temp = array[i];
            array[i] = array[0];
            array[0] = temp;
            // 调整最大堆
            largestBinaryHeap.downAdjust(array, 0, i);
        }
        System.out.println("堆排序后的数组：" + Arrays.toString(array));
    }

    /**
     * 降序堆排序
     */
    @Test
    public void descendingHeapSortTest() {
        BinaryHeap.LeastBinaryHeap leastBinaryHeap = new BinaryHeap.LeastBinaryHeap();

        // 1、将无序数组构建成最小堆
        int[] array = new int[]{1, 3, 2, 6, 5, 7, 8, 9, 10, 0};
        leastBinaryHeap.buildBinaryHeap(array);
        System.out.println("堆排序前的数组：" + Arrays.toString(array));

        // 2、循环删除堆顶元素，移到集合尾部，调整堆产生新的堆顶
        for (int i = array.length - 1; i > 0; i--) {
            // 最后1个元素跟第一个元素进行交换
            int temp = array[i];
            array[i] = array[0];
            array[0] = temp;
            // 调整最小堆
            leastBinaryHeap.downAdjust(array, 0, i);
        }
        System.out.println("堆排序后的数组：" + Arrays.toString(array));
    }
}
