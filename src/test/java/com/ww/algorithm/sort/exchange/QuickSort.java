package com.ww.algorithm.sort.exchange;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 快速排序：快速排序是从冒泡排序演变而来的算法，但是比冒泡排序要高效的多，所以叫快速排序。快速排序之所以快速，是因为它用了分治法。同冒泡排序一样，
 * 快速排序也属于交换排序，通过元素之间的比较和交换位置来达到排序的目的。不同的是，冒泡排序在每一轮只把一个元素冒泡到数列的一端，而快速排序在每一
 * 轮挑选一个基准元素，并让其他比它大的元素移动到数列一边，比它小的元素移动到数列的另一边，从而把数列拆解成了两个部分。
 * <p>
 * 快速排序的基本思想是：通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对这两
 * 部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
 * <p>
 * 快速排序的平均时间复杂度是O(nlogn)，最坏时间复杂度为O(n^2)。
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
     * 分治(挖坑法)
     *
     * @param arr        待交换的数组
     * @param startIndex 起始下标
     * @param endIndex   结束下标
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

        return index;
    }

    @Test
    public void diggingMethodQuickSortTest() {
        int[] arr = {4, 7, 6, 5, 3, 2, 8, 1};

        System.out.println("挖坑法快速排序前：" + Arrays.toString(arr));
        diggingMethodQuickSort(arr, 0, arr.length - 1);
        System.out.println("挖坑法快速排序后：" + Arrays.toString(arr));
    }

    // ------------------------------------- 挖坑法 END ------------------------------------- //


    // ------------------------------------- 单边循环法 BEGIN ------------------------------------- //

    /**
     * 单边循环法：我们首先选定基准元素pivot，同时设置一个mark指针指向数列起始位置，这个mark指针代表小于基准元素的区域边界。接下来，从基准元
     * 素的下一个位置开始遍历数组。如果遍历到的元素大于基准元素，就继续往后遍历。如果遍历到的元素小于基准元素，则需要做两件事：第一，将mark指针
     * 右移一位，因为小于pivot的区域边界增大了1；第二，让最新遍历到的元素和mark指针所在位置的元素交换位置，因为最新遍历的元素归属于小于pivot
     * 的区域。按照上面的思路继续遍历直到数组最后，最终将mark指针的元素和基准元素进行交换。此时数列左边的元素都小于pivot，数列右边的元素都大于
     * pivot，结束本轮交换。
     */
    public void unilateralLoopQuickSort(int[] arr, int startIndex, int endIndex) {
        // 递归结束条件：startIndex大等于endIndex的时候
        if (startIndex >= endIndex) {
            return;
        }

        // 得到基准元素位置
        int pivotIndex = unilateralLoopQuickSortPartition(arr, startIndex, endIndex);
        // 用分治法递归数列的两部分
        unilateralLoopQuickSort(arr, startIndex, pivotIndex - 1);
        unilateralLoopQuickSort(arr, pivotIndex + 1, endIndex);
    }

    /**
     * 分治(单边循环法)
     *
     * @param arr        待交换的数组
     * @param startIndex 起始下标
     * @param endIndex   结束下标
     * @return
     */
    public int unilateralLoopQuickSortPartition(int[] arr, int startIndex, int endIndex) {
        int mark = startIndex;

        // 取第一个位置的元素作为基准元素(也可与选择随机位置的元素作为基准元素)
        int pivot = arr[startIndex];

        for (int i = startIndex + 1; i <= endIndex; i++) {
            if (arr[i] < pivot) {
                mark++;

                int temp = arr[mark];
                arr[mark] = arr[i];
                arr[i] = temp;
            }
        }

        arr[startIndex] = arr[mark];
        arr[mark] = pivot;

        return mark;
    }

    @Test
    public void unilateralLoopQuickSortTest() {
        int[] arr = {3, 2, 1, 4, 5};

        System.out.println("单边循环法快速排序前：" + Arrays.toString(arr));
        unilateralLoopQuickSort(arr, 0, arr.length - 1);
        System.out.println("单边循环法快速排序后：" + Arrays.toString(arr));
    }

    // ------------------------------------- 单边循环法 END ------------------------------------- //


    // ------------------------------------- 双边循环法 BEGIN ------------------------------------- //

    /**
     * 双边循环法：
     * 我们首先选定基准元素pivot，并且设置两个指针left和right，指向数列的最左和最右两个元素。接下来是第一次循环，从right指针开始，把指针所
     * 指向的元素和基准元素做比较。如果大于等于pivot，则指针向左移动；如果小于pivot，则right指针停止移动，切换到left指针。轮到left指针行动，
     * 把指针所指向的元素和基准元素做比较。如果小于等于pivot，则指针向右移动；如果大于pivot，则left指针停止移动。这时候，我们让left和right
     * 指向的元素进行交换。按照上面的思路继续排序，当left和right指针重合之时，我们让pivot元素和left与right重合点的元素进行交换。此时数列左
     * 边的元素都小于pivot，数列右边的元素都大于pivot，结束本轮交换。
     */
    public void bilateralRoundRobinQuickSort(int[] arr, int startIndex, int endIndex) {
        // 递归结束条件：startIndex大等于endIndex的时候
        if (startIndex >= endIndex) {
            return;
        }

        // 得到基准元素位置
        int pivotIndex = bilateralRoundRobinQuickSortPartition(arr, startIndex, endIndex);
        // 用分治法递归数列的两部分
        bilateralRoundRobinQuickSort(arr, startIndex, pivotIndex - 1);
        bilateralRoundRobinQuickSort(arr, pivotIndex + 1, endIndex);
    }

    /**
     * 分治(双边循环法)
     *
     * @param arr        待交换的数组
     * @param startIndex 起始下标
     * @param endIndex   结束下标
     * @return
     */
    public int bilateralRoundRobinQuickSortPartition(int[] arr, int startIndex, int endIndex) {
        int left = startIndex;
        int right = endIndex;

        // 取第一个位置的元素作为基准元素(也可与选择随机位置的元素作为基准元素)
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
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }

        arr[startIndex] = arr[left];
        arr[left] = pivot;

        return left;
    }

    @Test
    public void bilateralRoundRobinQuickSortTest() {
        int[] arr = {4, 7, 6, 5, 3, 2, 8, 1};

        System.out.println("双边循环法快速排序前：" + Arrays.toString(arr));
        bilateralRoundRobinQuickSort(arr, 0, arr.length - 1);
        System.out.println("双边循环法快速排序后：" + Arrays.toString(arr));
    }

    // ------------------------------------- 双边循环法 END ------------------------------------- //


    // ------------------------------------- 非递归方式(使用栈) BEGIN------------------------------------- //
    public void stackQuickSort(int[] arr, int startIndex, int endIndex) {
        // 用一个集合栈来代替递归的函数栈
        Stack<Map<String, Integer>> stack = new Stack<>();

        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("startIndex", startIndex);
        paramMap.put("endIndex", endIndex);
        stack.push(paramMap);

        // 循环结束条件：栈为空时结束
        while (!stack.isEmpty()) {
            // 栈顶元素出栈，得到起止下标
            Map<String, Integer> tempParamMap = stack.pop();

            // 得到基准元素位置
            Integer tempStartIndex = tempParamMap.get("startIndex");
            Integer tempEndIndex = tempParamMap.get("endIndex");
            int pivotIndex = unilateralLoopQuickSortPartition(arr, tempStartIndex, tempEndIndex);

            // 根据基准元素分成两部分, 把每一部分的起止下标入栈
            if (tempStartIndex < pivotIndex - 1) {
                paramMap = new HashMap<>();
                paramMap.put("startIndex", tempStartIndex);
                paramMap.put("endIndex", pivotIndex - 1);
                stack.push(paramMap);
            }
            if (pivotIndex + 1 < tempEndIndex) {
                paramMap = new HashMap<>();
                paramMap.put("startIndex", pivotIndex + 1);
                paramMap.put("endIndex", tempEndIndex);
                stack.push(paramMap);
            }
        }
    }

    @Test
    public void stackQuickSortTest() {
        int[] arr = {4, 7, 6, 5, 3, 2, 8, 1};

        System.out.println("非递归方式快速排序前：" + Arrays.toString(arr));
        stackQuickSort(arr, 0, arr.length - 1);
        System.out.println("非递归方式快速排序后：" + Arrays.toString(arr));
    }

    // ------------------------------------- 非递归方式(使用栈) END------------------------------------- //
}