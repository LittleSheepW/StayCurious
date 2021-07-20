package com.ww.algorithm.sort.exchange;

import org.junit.Test;

import java.util.Arrays;

/**
 * 快速排序：快速排序是从冒泡排序演变而来的算法，但是比冒泡排序要高效的多，所以叫快速排序。快速排序之所以快速，是因为它用了分治法。同冒泡排序一样，
 * 快速排序也属于交换排序，通过元素之间的比较和交换位置来达到排序的目的。不同的是，冒泡排序在每一轮只把一个元素冒泡到数列的一端，而快速排序在每一
 * 轮挑选一个基准元素，并让其他比它大的元素移动到数列一边，比它小的元素移动到数列的另一边，从而把数列拆解成了两个部分。
 *
 * <p>
 * 快速排序的基本思想是：通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对这两
 * 部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
 *
 * @author: Sun
 * @create: 2021-07-08 17:57
 * @version: v1.0
 */
public class QuickSort {

    // ------------------------------------- 挖坑法 BEGIN ------------------------------------- //

    /**
     * 挖坑法：
     * 首先，我们选定基准元素pivot，并记住这个位置index，这个位置相当于一个“坑”。并且设置两个指针left和right，指向数列的最左和最右两个元素：
     * 接下来，从right指针开始，把指针所指向的元素和pivot做比较，如果比pivot大，则right指针向左移动；如果比pivot小，则把right所指向的元
     * 素填入坑中，left指针向右移动，然后right所指向的元素变为新的坑。接下来，我们切换到left指针进行比较。如果left指向的元素小于pivot，则
     * left指针向右移动；如果元素大于pivot，则把left指向的元素填入坑中，right指针向左移动。按照上面的思路继续排序，直到left和right重合在
     * 同一位置，将pivot放到该位置。此时数列左边的元素都小于pivot，数列右边的元素都大于pivot，结束本轮交换。
     */
    public static void diggingMethodQuickSort(int[] arr, int startIndex, int endIndex) {
        // 递归结束条件：startIndex大等于endIndex的时候
        if (startIndex >= endIndex) {
            return;
        }

        // 得到基准元素位置
        int pivotIndex = diggingMethodPartition(arr, startIndex, endIndex);
        // 用分治法递归数列的两部分
        diggingMethodQuickSort(arr, startIndex, pivotIndex - 1);
        diggingMethodQuickSort(arr, pivotIndex + 1, endIndex);
    }

    /**
     * 实现元素的移动，让数列中的元素依据自身大小，分别移动到基准元素的左右两边。
     *
     * @param arr
     * @param startIndex
     * @param endIndex
     * @return
     */
    private static int diggingMethodPartition(int[] arr, int startIndex, int endIndex) {
        int left = startIndex;
        int right = endIndex;

        // 坑的位置，初始等于pivot的位置
        int index = startIndex;
        // 取第一个位置的元素作为基准元素
        int pivot = arr[startIndex];

        // 大循环在左右指针重合或者交错时结束
        while (right > left) {
            // right指针从右向左进行比较
            while (right > left) {
                if (arr[right] < pivot) {
                    // TODO: arr[left]可以换成arr[index]
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
                    // TODO: arr[right]可以换成arr[index]
                    arr[right] = arr[left];
                    index = left;
                    right--;
                    break;
                }
                left++;
            }
        }

        arr[index] = pivot;

        return index;
    }

    @Test
    public void diggingMethodQuickSortTest() {
        // int[] arr = {2, 1, 3};
        int[] arr = {4, 7, 6, 5, 3, 2, 8, 1};

        System.out.println("快速排序前：" + Arrays.toString(arr));
        diggingMethodQuickSort(arr, 0, arr.length - 1);
        System.out.println("快速排序后：" + Arrays.toString(arr));
    }

    // ------------------------------------- 挖坑法 END ------------------------------------- //


    // ------------------------------------- 指针交换法 BEGIN ------------------------------------- //

    /**
     * 指针交换法：
     * 我们首先选定基准元素pivot，并且设置两个指针left和right，指向数列的最左和最右两个元素。接下来是第一次循环，从right指针开始，把指针所
     * 指向的元素和基准元素做比较。如果大于等于pivot，则指针向左移动；如果小于pivot，则right指针停止移动，切换到left指针。轮到left指针行动，
     * 把指针所指向的元素和基准元素做比较。如果小于等于pivot，则指针向右移动；如果大于pivot，则left指针停止移动。这时候，我们让left和right
     * 指向的元素进行交换。按照上面的思路继续排序，当left和right指针重合之时，我们让pivot元素和left与right重合点的元素进行交换。此时数列左
     * 边的元素都小于pivot，数列右边的元素都大于pivot，结束本轮交换。
     */
    public void pointerSwapQuickSort(int[] arr, int startIndex, int endIndex) {
        // 递归结束条件：startIndex大等于endIndex的时候
        if (startIndex >= endIndex) {
            return;
        }

        // 得到基准元素位置
        int pivotIndex = pointerSwapQuickSortPartition(arr, startIndex, endIndex);
        // 用分治法递归数列的两部分
        pointerSwapQuickSort(arr, startIndex, pivotIndex - 1);
        pointerSwapQuickSort(arr, pivotIndex + 1, endIndex);
    }

    public int pointerSwapQuickSortPartition(int[] arr, int startIndex, int endIndex) {
        int left = startIndex;
        int right = endIndex;

        // 取第一个位置的元素作为基准元素
        int pivot = arr[startIndex];

        while (left != right) {
            // 控制right指针比较并左移
            while (left < right && arr[right] > pivot) {
                right--;
            }
            // 控制left指针比较并右移
            while (left < right && arr[left] <= pivot) {
                left++;
            }

            // 交换left和right指向的元素
            if (left < right) {
                int temp = arr[left];
                arr[left] = right;
                arr[right] = temp;
            }
        }

        int temp = arr[left];
        arr[left] = arr[startIndex];
        arr[startIndex] = temp;

        return left;
    }

    @Test
    public void pointerSwapQuickSortTest() {
        int[] arr = {2, 1};
        // int[] arr = {4, 7, 6, 5, 3, 2, 8, 1};

        System.out.println("快速排序前：" + Arrays.toString(arr));
        pointerSwapQuickSort(arr, 0, arr.length - 1);
        System.out.println("快速排序后：" + Arrays.toString(arr));
    }

    // ------------------------------------- 指针交换法 END ------------------------------------- //
}