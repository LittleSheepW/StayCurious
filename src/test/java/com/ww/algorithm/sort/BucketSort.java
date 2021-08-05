package com.ww.algorithm.sort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * 桶排序：同样是一种线性时间的排序算法。类似于计数排序所创建的统计数组，桶排序需要创建若干个桶来协助排序。那么桶排序中所谓的"桶"，又是什么呢？
 * 每一个桶(bucket)代表一个区间范围，里面可以承载一个或多个元素。
 * 桶排序的工作原理：
 * 1、创建桶，并确定每一个桶的区间范围(具体需要建立多少个桶，如何确定桶的区间范围，有很多种不同的方式)
 * 2、遍历原始数列，把元素对号入座放到各个桶中
 * 3、对每个桶内部的元素分别进行排序
 * 4、遍历所有的桶，输出所有元素
 * <p>
 * 桶排序的时间复杂度是多少呢？
 * 假设原始数列有n个元素，分为n个桶。
 * 第一步：求数列最大、最小值，运算量为n
 * 第二步：创建空桶，运算量为n
 * 第三步：把原始数列的元素分配到各个桶中，运算量为n
 * 第四步：在每个桶内部做排序，在元素分布相对均匀的情况下，所有桶的运算量之和为n
 * 第五步：输出排序数列，运算量为n
 * <p>
 * 稳定排序；
 * 平均时间复杂度为O(n)；
 * 最坏时间复杂度为O(nlogn)，桶排序的性能并非绝对稳定。如果元素的分布极不均衡，在极端情况下，第一个桶中有n-1个元素，最后一个桶中有1个元素。
 * 此时的时间复杂度将退化为O(nlogn)，而且还白白创建了许多空桶。；
 * 空间复杂度也同样为O(n)。
 *
 * @author: Sun
 * @create: 2021-08-04 14:35
 * @version: v1.0
 */
public class BucketSort {

    /**
     * 桶排序
     *
     * @param arr
     * @return
     */
    public double[] bucketSort(double[] arr) {
        // 1、得到最大最小值并算出差值，初始化桶
        double max = arr[0];
        double min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        double d = max - min;

        int bucketNum = arr.length;
        ArrayList<LinkedList<Double>> bucketList = new ArrayList<>(bucketNum);
        for (int i = 0; i < bucketNum; i++) {
            bucketList.add(new LinkedList<>());
        }

        // 2、遍历原始数列，把元素对号入座放到各个桶中
        for (int i = 0; i < arr.length; i++) {
            // 区间跨度 = (最大值 - 最小值) / (桶的数量 - 1)
            int num = (int) ((arr[i] - min) * (bucketNum - 1) / d);
            bucketList.get(num).add(arr[i]);
        }

        // 3、对每个桶内部的元素分别进行排序
        for (int i = 0; i < bucketList.size(); i++) {
            Collections.sort(bucketList.get(i));
        }

        // 4、遍历所有的桶，输出所有元素
        int index = 0;
        double[] sortedArr = new double[arr.length];
        for (LinkedList<Double> list : bucketList) {
            for (Double element : list) {
                sortedArr[index++] = element;
            }
        }

        return sortedArr;
    }

    @Test
    public void bucketSortTest() {
        double[] array = new double[]{4.12, 6.421, 0.0023, 3.0, 2.123, 8.122, 4.12, 10.09};
        double[] sortedArray = bucketSort(array);
        System.out.println(Arrays.toString(sortedArray));
    }
}