package com.ww.algorithm.sort.exchange;

import org.junit.Test;

import java.util.Arrays;

/**
 * 鸡尾酒排序：冒泡排序的每一个元素都可以像小气泡一样，根据自身大小，一点一点地向着数组的一侧移动。算法的每一轮都是从左至右来比较元素，进行
 * 单向的位置交换。那么鸡尾酒排序做了怎样的优化呢？鸡尾酒排序的元素和交换过程是双向的。
 * 下面举一个例子：有数组[2, 3, 4, 5, 6, 7, 8, 1]，希望对其进行从小到大的排序。如果按照冒泡排序的思想去做的话，元素2, 3, 4, 5, 6, 7, 8
 * 已经是有序的了，只有元素1的位置不对，却还要进行7轮排序！鸡尾酒排序正是要解决这个问题的。
 * <p>
 * 鸡尾酒排序过程：
 * 第1轮：和冒泡排序一样，从左至右依次比较并交换；
 * 第2轮：反过来，从右至左依次比较并交换；
 * 第3轮：同第1轮...
 * 第4轮：同第2轮...
 * 直到某一轮排序中没有元素进行交换，证明已经有序，排序结束。这就是鸡尾酒排序的思路。排序过程就像钟摆一样，从左至右，从右至左，循环往复直至有序。
 * <p>
 * 鸡尾酒排序的优缺点：
 * 优点：在大部分元素已经有序的情况下，可以减少排序的回合数。
 * 缺点：代码量几乎增加了1倍。
 * <p>
 * 稳定排序；
 * 平均时间复杂度是O(n^2)；
 * 最坏时间复杂度为O(n^2)；
 * 空间复杂度为O(1)。
 *
 * @author: Sun
 * @create: 2021-07-20 15:46
 * @version: v1.0
 */
public class CocktailSort {

    /**
     * 鸡尾酒排序第一版🍹
     *
     * @param arr
     */
    public void cocktailSort1(int[] arr) {
        // 比较次数
        int numberOfComparisons = 0;

        for (int i = 0; i < arr.length / 2; i++) {
            boolean flag = true;
            // 从左至右进行比较并交换
            for (int j = i; j < arr.length - i - 1; j++) {
                numberOfComparisons++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    flag = false;
                }
            }
            if (flag) {
                System.out.println("第" + (i + 1) + "趟排序后的结果：" + Arrays.toString(arr));
                break;
            }

            flag = true;
            // 从右至左进行比较并交换
            for (int j = arr.length - i - 1; j > i; j--) {
                numberOfComparisons++;
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;

                    flag = false;
                }
            }
            if (flag) {
                System.out.println("第" + (i + 1) + "趟排序后的结果：" + Arrays.toString(arr));
                break;
            }

            System.out.println("第" + (i + 1) + "趟排序后的结果：" + Arrays.toString(arr));
        }
        System.out.println("总比较次数：" + numberOfComparisons);
    }

    /**
     * 鸡尾酒排序第二版🍹：加入有序区的优化
     *
     * @param arr
     */
    public void cocktailSort2(int[] arr) {
        // 比较次数
        int numberOfComparisons = 0;

        // 从左至右最后一次交换的位置
        int rightExchangeIndex = 0;
        // 无序数列的右边界，每次比较只需要比到这里为止
        int rightBorder = arr.length - 1;
        // 从右至左最后一次交换的位置
        int leftExchangeIndex = arr.length - 1;
        // 无序数列的左边界，每次比较只需要比到这里为止
        int leftBorder = 0;

        for (int i = 0; i < arr.length / 2; i++) {
            boolean flag = true;
            // 从左至右进行比较并交换
            for (int j = leftBorder; j < rightBorder; j++) {
                numberOfComparisons++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    flag = false;
                    rightExchangeIndex = j;
                }
            }
            rightBorder = rightExchangeIndex;
            if (flag) {
                System.out.println("第" + (i + 1) + "趟排序后的结果：" + Arrays.toString(arr));
                break;
            }

            flag = true;
            // 从右至左进行比较并交换
            for (int j = rightBorder; j > leftBorder; j--) {
                numberOfComparisons++;
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;

                    leftExchangeIndex = j;
                    flag = false;
                }
            }
            leftBorder = leftExchangeIndex;
            if (flag) {
                System.out.println("第" + (i + 1) + "趟排序后的结果：" + Arrays.toString(arr));
                break;
            }

            System.out.println("第" + (i + 1) + "趟排序后的结果：" + Arrays.toString(arr));
        }

        System.out.println("总比较次数：" + numberOfComparisons);
    }

    @Test
    public void cocktailSortTest() {
        int[] arr1 = {3, 4, 2, 1, 5, 6, 7, 8};
        System.out.println("鸡尾酒排序第一版排序前：" + Arrays.toString(arr1));
        cocktailSort1(arr1);
        System.out.println("鸡尾酒排序第一版排序后：" + Arrays.toString(arr1));

        System.out.println("---------------");

        int[] arr2 = {3, 4, 2, 1, 5, 6, 7, 8};
        System.out.println("鸡尾酒排序第二版排序前：" + Arrays.toString(arr2));
        cocktailSort2(arr2);
        System.out.println("鸡尾酒排序第二版排序后：" + Arrays.toString(arr2));

        System.out.println("---------------");

    }
}
