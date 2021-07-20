package com.ww.algorithm.sort.exchange;

import org.junit.Test;

import java.util.Arrays;

/**
 * 冒泡排序(Bubble sort)
 * <p>
 * 冒泡排序基本思想：对待排序序列从前向后，依次比较相邻元素的值，发现逆序则进行交换，使值较大的元素逐渐从前移向后部，就象水底下的气泡一样逐渐向上冒。
 *
 * @author: Sun
 * @create: 2021-07-06 16:06
 * @version: v1.0
 */
public class BubbleSort {

    /**
     * 冒泡排序第一版：最简单的冒泡排序实现
     *
     * @param arr
     */
    public void bubbleSort1(int[] arr) {
        // 比较次数
        int numberOfComparisons = 0;

        // 比较多少趟
        for (int i = 0; i < arr.length - 1; i++) {
            // 每一趟比较的元素的个数
            for (int j = 0; j < arr.length - 1 - i; j++) {
                numberOfComparisons++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            System.out.println("第" + (i + 1) + "趟排序后的结果：" + Arrays.toString(arr));
        }

        System.out.println("总比较次数：" + numberOfComparisons);
    }

    /**
     * 冒泡排序第二版：有的时候经过几轮排序后数组已经完全有序，无需再进行后面几轮排序，但冒泡排序第一还是会进行后面几轮排序，例如：[5, 1, 2, 3, 4]，
     * 经过第一轮排序后，该数组就已经完全有序，无需再进行后面几轮排序。针对这个点，冒泡排序第二版做了优化。
     *
     * @param arr
     */
    public void bubbleSort2(int[] arr) {
        // 比较次数
        int numberOfComparisons = 0;

        // 比较多少趟
        for (int i = 0; i < arr.length - 1; i++) {
            // 数组是否完全有序
            boolean flag = true;
            // 每一趟比较的元素的个数
            for (int j = 0; j < arr.length - 1 - i; j++) {
                numberOfComparisons++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    // 如果发生了交换，证明数组还没有完全有序
                    flag = false;
                }
            }
            if (flag) {
                break;
            }

            System.out.println("第" + (i + 1) + "趟排序后的结果：" + Arrays.toString(arr));
        }

        System.out.println("总比较次数：" + numberOfComparisons);
    }


    /**
     * 冒泡排序第三版：有数组[3, 4, 2, 1, 5, 6, 7, 8]，该数组的特点是前半部分的元素无序(3, 4, 2, 1)，后半部分的元素有序(5, 6, 7, 8)，
     * 而且后半部分元素中的最小值也大于前半部分元素的最大值。这样的数字在排序时有一个问题，就是后面的许多元素已经是有序的了，可是每一轮还是白白
     * 比较了很多次。
     * 这个问题的关键点在于对数列有序区的界定。按照冒泡排序的逻辑，有序区的长度和排序的轮数是相等的。例如第1轮排序过后的有序区长度是1，第2轮排
     * 序过后的有序区长度是2...实际上数组真正的有序区可能会大于轮数。那么该如何避免这种情况呢？我们可以在每一轮排序后，记录下来最后一次元素交换
     * 的位置，该位置即为无序数列的边界，再往后就是有序区了。
     *
     * @param arr
     */
    public void bubbleSort3(int[] arr) {
        // 比较次数
        int numberOfComparisons = 0;

        // 最后一次交换的位置
        int lastExchangeIndex = 0;
        // 无序数列的边界，每次比较只需要比到这里为止
        int sortBorder = arr.length - 1;

        // 比较多少趟
        for (int i = 0; i < arr.length - 1; i++) {
            // 数组是否完全有序
            boolean flag = true;
            // 每一趟比较的元素的个数
            for (int j = 0; j < sortBorder; j++) {
                numberOfComparisons++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    // 如果发生了交换，证明数组还没有完全有序
                    flag = false;
                    // 更新最后一次交换的位置
                    lastExchangeIndex = j;
                }
            }

            sortBorder = lastExchangeIndex;
            if (flag) {
                break;
            }

            System.out.println("第" + (i + 1) + "趟排序后的结果：" + Arrays.toString(arr));
        }

        System.out.println("总比较次数：" + numberOfComparisons);
    }

    @Test
    public void bubbleSortTest() {
        int[] arr = {3, 4, 2, 1, 5, 6, 7, 8};
        System.out.println("冒泡排序第一版排序前：" + Arrays.toString(arr));
        bubbleSort1(arr);
        System.out.println("冒泡排序第一版排序后：" + Arrays.toString(arr));

        System.out.println("---------------");

        int[] arr2 = {3, 4, 2, 1, 5, 6, 7, 8};
        System.out.println("冒泡排序第二版排序前：" + Arrays.toString(arr2));
        bubbleSort2(arr2);
        System.out.println("冒泡排序第二版排序后：" + Arrays.toString(arr2));

        System.out.println("---------------");

        int[] arr3 = {3, 4, 2, 1, 5, 6, 7, 8};
        System.out.println("冒泡排序第三版排序前：" + Arrays.toString(arr3));
        bubbleSort3(arr3);
        System.out.println("冒泡排序第三版排序后：" + Arrays.toString(arr3));
    }
}
