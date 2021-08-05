package com.ww.algorithm.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * 计数排序：假设数组中有20个随机整数，取值范围为0~10，要求用最快的速度把这20个整数从小到大进行排序。如何给这些无序的随机整数进行排序呢？
 * 1、考虑到这些整数只能够在0-10这11个数中取值，取值范围有限。所以可以根据这有限的范围，建立一个长度为11的新数组。数组下标从0到10，元素初始值全为0。
 * 2、遍历无序数组，每遍历到一个整数，便将新数组对应下标的元素进行加1操作。
 * 3、遍历新数组，输出数组元素的下标值，元素的值是几，就输出几次。
 * 这就是计数排序的基本过程，它适用于一定范围内的整数排序。在取值范围不是很大的情况下，它的性能甚至快过那些时间复杂度为O(nlogn)的排序。
 * <p>
 * 如果原始数列的规模是n，最大和最小整数的差值是m，计数排序的时间复杂度和空间复杂度分别是多少?
 * 优化后的计数排序代码第1、2、4步都涉及遍历原始数组，运算量都是n，第3步遍历统计数列，运算量是m，所以总体运算量是3n+m，去掉系数，时间复杂度为O(n+m)。
 * 至于空间复杂度，如果不考虑结果数组，只考虑统计数组大小的话，空间复杂度是O(m)。
 * <p>
 * 计数排序虽然很强大，但是很少被大家使用，因为计数排序有它的局限性，主要表现为如下两点：
 * 1、当数列最大和最小值的差距过大时，并不适用计数排序。
 * 例如给出20个随机整数，范围在0-1亿之间，这时如果使用计数排序，需要创建长度为1亿的数组。不但严重浪费空间，而且时间复杂度也会随之升高。
 * 2、当数列元素不是整数时，并不适用计数排序。
 * 如果数组中的元素都是小数，则无法创建对应的统计数组。这样显然无法进行计数排序。
 * 对于以上这些局限性，另一种线性时间排序算法做了弥补，这种排序算法叫做桶排序。
 * <p>
 * 稳定排序；
 * 平均时间复杂度O(n+m)， 优化后的计数排序代码第1、2、4都涉及遍历原始数列，运算量都是n，第3步是遍历统计数列，运算量是m，所以总体运算量是3n+m，
 * 去掉系数，时间复杂度为O(n+m)；
 * 最坏时间复杂度O(n+m)；
 * 空间复杂度O(m)，不考虑结果数组，只考虑统计数组大小，空间复杂度为O(m)。
 *
 * @author: Sun
 * @create: 2021-07-26 15:18
 * @version: v1.0
 */
public class CountSort {

    /**
     * 普通版的计数排序
     *
     * @param arr
     * @return
     */
    public int[] countSort1(int[] arr) {
        // 1、得到最大值
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // 2、构建统计数组并统计对应元素的个数
        int[] countArr = new int[max + 1];
        for (int i = 0; i < arr.length; i++) {
            countArr[arr[i]]++;
        }

        // 3、得到排序后的结果
        int index = 0;
        int[] sortedArr = new int[arr.length];
        for (int i = 0; i < countArr.length; i++) {
            for (int j = 0; j < countArr[i]; j++) {
                sortedArr[index++] = i;
            }
        }

        return sortedArr;
    }

    /**
     * 优化后的计数排序：普通版的计数排序虽然可以实现排序，但也存在一些问题：
     * 1、以最大值来决定统计数组的长度。如果有一个数组[99, 93, 98, 95, 90]，该数组最大值是99，最小值是90，一共有5个数，却会创建长度为99+1的
     * 数组，0~89位置的数组空间全都浪费了。
     * 2、只是简单的按照统计数组的下标输出元素值，并没有真正给原始数列进行排序。如果只是单纯给整数排序，没什么问题。如果在现实业务里，给出一个学生
     * 成绩表，要求按照成绩从低到高进行排序，如果成绩相同，则遵循原表固有顺序，在该场景下遇到相同的分数就会分不清谁是谁。
     *
     * @param arr
     * @return
     */
    public int[] countSort2(int[] arr) {
        // 1、得到最大最小值，并算出差值
        int max = arr[0];
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        int d = max - min;

        // 2、构建统计数组并统计对应元素个数
        int[] countArr = new int[d + 1];
        for (int i = 0; i < arr.length; i++) {
            countArr[arr[i] - min]++;
        }

        /**
         * 3、统计数组做变形，后面的元素等于前面元素之和
         * 为什么要相加呢？相加的目的是让统计数组存储的元素值，等于相应整数的最终排序位置的序号。例如下标是9的元素值为5，代表原始数列的整数9，最终
         * 的排序在第5位。
         */
        for (int i = 1; i < countArr.length; i++) {
            countArr[i] += countArr[i - 1];
        }

        // 4、倒序遍历原始数列，从统计数组找到正确位置，输出到结果数组
        int[] sortedArr = new int[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            // 例如：countArr数组中下标为2的元素值为4，原始数列2+90的整数最终排序在第4位
            sortedArr[countArr[arr[i] - min] - 1] = arr[i];
            countArr[arr[i] - min]--;
        }

        return sortedArr;
    }

    @Test
    public void countSort1Test() {
        int[] array = new int[]{4, 4, 6, 5, 3, 2, 8, 1, 7, 5, 6, 0, 10};
        int[] sortedArray = countSort1(array);
        System.out.println(Arrays.toString(sortedArray));
    }

    @Test
    public void countSort2Test() {
        int[] array = new int[]{95, 94, 91, 98, 99, 90, 99, 93, 91, 92};
        int[] sortedArray = countSort2(array);
        System.out.println(Arrays.toString(sortedArray));
    }
}
