package com.ww.algorithm.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author: Sun
 * @create: 2021-07-08 19:46
 * @version: v1.0
 */
public class Temp {

    public static void diggingMethodQuickSort(int[] arr, int startIndex, int endIndex) {
        // 递归结束条件：startIndex大等于endIndex的时候
        if (startIndex >= endIndex) {
            return;
        }

        // 取第一个位置的元素作为基准元素
        int pivot = arr[startIndex];
        int left = startIndex;
        int right = endIndex;
        // 坑的位置，初始等于pivot的位置
        int index = startIndex;
        // 大循环在左右指针重合或者交错时结束
        while (right > left) {
            // right指针从右向左进行比较
            while (right > left) {
                if (arr[right] < pivot) {
                    arr[left] = arr[right];
                    index = right;
                    left++;
                    break;
                }
                right--;
            }

            // left指针从左向右进行比较
            while (right > left) {
                if (arr[left] > pivot) {
                    arr[right] = arr[left];
                    index = left;
                    right--;
                    break;
                }
                left++;
            }

        }

        arr[index] = pivot;

        // 用分治法递归数列的两部分
        diggingMethodQuickSort(arr, startIndex, index - 1);
        diggingMethodQuickSort(arr, index + 1, endIndex);
    }

    @Test
    public void diggingMethodQuickSortTest() {
        int[] arr = {4, 7, 6, 5, 3, 2, 8, 1};

        System.out.println("快速排序前：" + Arrays.toString(arr));
        diggingMethodQuickSort(arr, 0, arr.length - 1);
        System.out.println("快速排序后：" + Arrays.toString(arr));
    }
}