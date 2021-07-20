package com.ww.algorithm.sort.select;

import org.junit.Test;

import java.util.Arrays;

/**
 * 简单选择排序：简单选择排序属于内部排序法，简单选择排序是从欲排序的数据中按指定的规则选出某一元素再按规定交换位置后达到排序目的的一种算法。
 * <p>
 * 选择排序思想：第一次从arr[0]~arr[n-1]中选取最小值，与arr[0]交换，第二次从arr[1]~arr[n-1]中选取最小值，与arr[1]交换，第三次从arr[2]
 * ~arr[n-1]中选取最小值，与arr[2]交换，第i次从arr[i-1]~arr[n-1]中选取最小值，与arr[i-1]交换，第n-1次从arr[n-2]~arr[n-1]中选取最
 * 小值，与arr[n-2]交换，总共通过n-1次，得到一个按排序码从小到大排列的有序序列。
 *
 * @author: Sun
 * @create: 2021-07-06 17:06
 * @version: v1.0
 */
public class SimpleSelectSort {

    public void selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int temp = arr[i];
            int tempIndex = i;

            for (int j = i + 1; j < arr.length; j++) {
                if (temp > arr[j]) {
                    tempIndex = j;
                    temp = arr[j];
                }
            }

            if (tempIndex != i) {
                arr[tempIndex] = arr[i];
                arr[i] = temp;
            }

            System.out.println("第" + (i + 1) + "轮排序后：" + Arrays.toString(arr));
        }
    }

    @Test
    public void selectSortTest() {
        int[] arr = {101, 34, 119, 1};

        System.out.println("选择排序前：" + Arrays.toString(arr));
        selectSort(arr);
        System.out.println("选择排序后：" + Arrays.toString(arr));
    }
}
