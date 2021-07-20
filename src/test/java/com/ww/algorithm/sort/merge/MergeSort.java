package com.ww.algorithm.sort.merge;

import org.junit.Test;

import java.util.Arrays;

/**
 * 归并排序：归并排序是利用归并的思想实现的排序方法，该算法采用经典的分治(divide-and-conquer)策略，分治法将问题分成一些小的问题然后递归求解，
 * 治的阶段将分阶段得到的各答案"修补"在一起，即分而治之。
 * <p>
 * 归并排序的思想：首先将集合进行逐层的折半分组，一直到每组只有一个元素为止；之后各小组间进行比较和排序并合并成一个大组；大组之间继续比较和排序，
 * 再合并成更大的组；最终所有元素合并成了一个有序的集合。
 *
 * @author: Sun
 * @create: 2021-07-12 14:05
 * @version: v1.0
 */
public class MergeSort {

    public void mergeSort(int[] arr, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            // 折半成两个小集合，分别进行递归
            mergeSort(arr, start, mid);
            mergeSort(arr, mid + 1, end);
            // 把两个有序小集合，归并成一个大集合
            merge(arr, start, mid, end);
        }
    }

    /**
     * 归并数组，首先创建一个临时大集合用于存储归并结果，长度是两个小集合之和。（p1，p2，p是三个辅助指针，用于记录当前操作的位置）。从左到右
     * 逐一比较两个小集合中的元素，把较小的元素优先放入大集合。从另一个还有剩余元素的集合中，把剩余元素按顺序复制到大集合尾部。这样一来，两个有
     * 序的小集合就归并成了一个有序的大集合。最终再将临时大集合中的元素填充到原数组中。
     *
     * @param arr
     * @param start
     * @param mid
     * @param end
     */
    public void merge(int[] arr, int start, int mid, int end) {
        // 初始化指针
        int p = 0;
        int p1 = start;
        int p2 = mid + 1;
        // 开辟额外大集合
        int[] tempArr = new int[end - start + 1];

        // 比较两个小集合的元素，依次放入大集合
        while (p1 <= mid && p2 <= end) {
            if (arr[p1] <= arr[p2]) {
                tempArr[p++] = arr[p1++];
            } else {
                tempArr[p++] = arr[p2++];
            }
        }

        // 左侧小集合还有剩余，依次放入大集合尾部
        while (p1 <= mid) {
            tempArr[p++] = arr[p1++];
        }
        // 右侧小集合还有剩余，依次放入大集合尾部
        while (p2 <= end) {
            tempArr[p++] = arr[p2++];
        }
        // 把大集合的元素复制回原数组中
        for (int i = 0; i < tempArr.length; i++) {
            arr[i + start] = tempArr[i];
        }
    }

    @Test
    public void mergeSortTest() {
        // int[] arr = {2, 1, 3};
        int[] arr = {4, 7, 6, 5, 3, 2, 8, 1};

        System.out.println("快速排序前：" + Arrays.toString(arr));
        mergeSort(arr, 0, arr.length - 1);
        System.out.println("快速排序后：" + Arrays.toString(arr));
    }
}
