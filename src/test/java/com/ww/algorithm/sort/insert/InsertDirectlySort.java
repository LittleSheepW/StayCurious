package com.ww.algorithm.sort.insert;

import org.junit.Test;

import java.util.Arrays;

/**
 * 直接插入排序：直接插入排序属于内部排序法，是对于欲排序的元素查找适当位置并进行插入，最终达到排序的目的。
 * <p>
 * 直接插入排序基本思想：把n个待排序的元素看成为一个有序表和一个无序表，开始时有序表中只包含一个元素，无序表中包含有n-1个元素，排序过程中每次从无序
 * 表中取出第一个元素，把它的排序码依次与有序表元素的排序码进行比较，将它插入到有序表中的适当位置，使之成为新的有序表。
 *
 * @author: Sun
 * @create: 2021-07-06 18:45
 * @version: v1.0
 */
public class InsertDirectlySort {

    public void insertSort(int[] arr) {
        int insertVal = -1;
        int insertIndex = -1;

        for (int i = 1; i < arr.length; i++) {
            insertVal = arr[i];
            insertIndex = i - 1;

            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }

            arr[insertIndex + 1] = insertVal;

            System.out.println("第" + i + "轮排序后：" + Arrays.toString(arr));
        }
    }

    @Test
    public void insertSortTest() {
        int[] arr = {101, 34, 119, 1};

        System.out.println("直接插入排序前：" + Arrays.toString(arr));
        insertSort(arr);
        System.out.println("直接插入排序后：" + Arrays.toString(arr));
    }
}
