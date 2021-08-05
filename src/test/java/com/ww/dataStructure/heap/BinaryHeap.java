package com.ww.dataStructure.heap;

import org.junit.Test;

import java.util.Arrays;

/**
 * 二叉堆：二叉堆本质上是一种完全二叉树，它分为两个类型，最大堆和最小堆。二叉堆的根节点叫做堆顶。最大堆和最小堆的特点决定了最大堆的堆顶是整个堆中
 * 的最大元素；最小堆的堆顶是整个堆中的最小元素。二叉堆是实现堆排序及优先队列的基础。
 * - 最大堆：最大堆的堆顶是整个堆中的最大元素。最大堆的任何一个父节点的值，都大于或等于它左、右孩子节点的值。
 * - 最小堆：最小堆的堆顶是整个堆中的最小元素。最小堆的任何一个父节点的值，都小于或等于它左、右孩子节点的值。
 * <p>
 * 对于二叉堆，有如下几种操作：
 * - 插入节点：当二叉堆插入节点时，插入位置是完全二叉树的最后一个位置，插入后依次和父节点进行比较并上浮。
 * - 删除节点：二叉堆删除节点的过程和插入节点的过程整好相反，所删除的是处于堆顶的节点，然后将最后一个节点临时补到原本堆顶的位置，最后让暂处堆顶
 * 位置的节点和它的左右孩子中的某一个进行比较并下沉。
 * - 构建二叉堆：构建二叉堆也就是把一个无序的完全二叉树调整为二叉堆，本质就是让所有非叶子节点依次下沉。
 * 这几种操作都基于堆的自我调整。所谓堆的自我调整，就是把一个不符合堆性质的完全二叉树，调整成一个堆。
 * <p>
 * 二叉堆虽然是一个完全二叉树，但它的存储方式并不是链式存储，而是顺序存储。换句话说，二叉堆的所有节点都存储在数组中。在数组中，没有左、右指针的
 * 情况下，可以通过数组下标来定位一个父节点的左孩子和右孩子。假设父节点的下标是parent，那么它的左孩子下标就是2*parent+1，右孩子下标就是2*parent+2。
 *
 * @author: Sun
 * @create: 2021-07-16 18:12
 * @version: v1.0
 */
public class BinaryHeap {

    /**
     * 最小二叉堆代码实现
     */
    public static class LeastBinaryHeap {

        /**
         * 节点上浮
         *
         * @param arr
         */
        public void upAdjust(int[] arr) {
            // 初始为最后一个插入二叉堆的元素，在上浮的过程中不断变小
            int childIndex = arr.length - 1;
            // 子元素对应的父元素索引
            int parentIndex = (childIndex - 1) / 2;

            // 待上浮的数据
            int tempData = arr[childIndex];
            while (childIndex > 0 && tempData < arr[parentIndex]) {
                // 让原来的父节点下来替代原来的子节点
                arr[childIndex] = arr[parentIndex];
                childIndex = parentIndex;
                parentIndex = (parentIndex - 1) / 2;
            }

            arr[childIndex] = tempData;
        }

        /**
         * 节点下沉
         *
         * @param arr
         * @param parentIndex
         */
        public void downAdjust(int[] arr, int parentIndex, int length) {
            // 待下沉的数据
            int tempData = arr[parentIndex];

            int childIndex = parentIndex * 2 + 1;
            // 先看有没有左子节点
            while (childIndex < length) {
                // 看看有没有右子节点，如果有右子节点，则左右子节点相互比较，找到最小的那个
                if (childIndex + 1 < length && arr[childIndex] > arr[childIndex + 1]) {
                    childIndex++;
                }
                // 将父节点和子节点比较一下，如果父节点比子节点小，则不动，反之则下沉
                if (tempData <= arr[childIndex]) {
                    break;
                }

                arr[parentIndex] = arr[childIndex];
                parentIndex = childIndex;
                childIndex = childIndex * 2 + 1;
            }

            arr[parentIndex] = tempData;
        }

        /**
         * 构建最小二叉堆
         *
         * @param arr
         */
        public void buildBinaryHeap(int[] arr) {
            // 从最后一个非叶子节点开始，依次下沉。     i = (arr.length - 2) / 2得到的结果是最后一个非叶子节点
            for (int i = (arr.length - 2) / 2; i >= 0; i--) {
                downAdjust(arr, i, arr.length);
            }
        }
    }

    /**
     * 最大二叉堆代码实现
     */
    public static class LargestBinaryHeap {

        /**
         * 节点上浮
         *
         * @param arr
         */
        public void upAdjust(int[] arr) {
            // 初始为最后一个插入二叉堆的元素，在上浮的过程中不断变小
            int childIndex = arr.length - 1;
            // 子元素对应的父元素索引
            int parentIndex = (childIndex - 1) / 2;

            // 待上浮的数据
            int tempData = arr[childIndex];
            while (childIndex > 0 && tempData > arr[parentIndex]) {
                // 让原来的父节点下来替代原来的子节点
                arr[childIndex] = arr[parentIndex];
                childIndex = parentIndex;
                parentIndex = (parentIndex - 1) / 2;
            }

            arr[childIndex] = tempData;
        }

        /**
         * 节点下沉
         *
         * @param arr
         * @param parentIndex
         */
        public void downAdjust(int[] arr, int parentIndex, int length) {
            // 待下沉的数据
            int tempData = arr[parentIndex];

            int childIndex = parentIndex * 2 + 1;
            // 先看有没有左子节点
            while (childIndex < length) {
                // 看看有没有右子节点，如果有右子节点，则左右子节点相互比较，找到最大的那个
                if (childIndex + 1 < length && arr[childIndex] < arr[childIndex + 1]) {
                    childIndex++;
                }
                // 将父节点和子节点比较一下，如果父节点比子节点大，则不动，反之则下沉
                if (tempData >= arr[childIndex]) {
                    break;
                }

                arr[parentIndex] = arr[childIndex];
                parentIndex = childIndex;
                childIndex = childIndex * 2 + 1;
            }

            arr[parentIndex] = tempData;
        }

        /**
         * 构建最大二叉堆
         *
         * @param arr
         */
        public void buildBinaryHeap(int[] arr) {
            // 从最后一个非叶子节点开始，依次下沉。     i = (arr.length - 2) / 2得到的结果是最后一个非叶子节点
            for (int i = (arr.length - 2) / 2; i >= 0; i--) {
                downAdjust(arr, i, arr.length);
            }
        }
    }

    /**
     * 测试最小二叉堆
     */
    @Test
    public void leastBinaryHeapTest() {
        LeastBinaryHeap leastBinaryHeap = new LeastBinaryHeap();

        // 节点上浮
        int[] array = new int[]{1, 3, 2, 6, 5, 7, 8, 9, 10, 0};
        leastBinaryHeap.upAdjust(array);
        System.out.println(Arrays.toString(array));

        // 节点下沉
        array = new int[]{7, 1, 3, 10, 5, 2, 8, 9, 6};
        leastBinaryHeap.buildBinaryHeap(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 测试最大二叉堆
     */
    @Test
    public void largestBinaryHeapTest() {
        LargestBinaryHeap largestBinaryHeap = new LargestBinaryHeap();

        // 节点上浮
        int[] array = new int[]{10, 9, 8, 7, 5, 2, 3, 1, 6, 11};
        largestBinaryHeap.upAdjust(array);
        System.out.println(Arrays.toString(array));

        // 节点下沉
        array = new int[]{7, 1, 3, 10, 5, 2, 8, 9, 6};
        largestBinaryHeap.buildBinaryHeap(array);
        System.out.println(Arrays.toString(array));
    }
}