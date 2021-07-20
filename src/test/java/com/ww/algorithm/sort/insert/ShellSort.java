package com.ww.algorithm.sort.insert;

import org.junit.Test;

import java.util.Arrays;

/**
 * 希尔排序：希尔排序也是一种插入排序，它是简单插入排序经过改进之后的一个更高效的版本，也称为缩小增量排序。
 * <p>
 * 插入排序的平均时间复杂度是O（n^2）。这个排序算法并不复杂，但显然并不是一个高效的排序算法。那么，怎样可以对插入排序算法做出优化呢？我们不妨
 * 从插入排序的两个特点入手：
 * 1.在大多数元素已经有序的情况下，插入排序的工作量较小。这个结论很明显，如果一个数组大部分元素都有序，那么数组中的元素自然不需要频繁地进行
 * 比较和交换。
 * 2.在元素数量较少的情况下，插入排序的工作量较小。这个结论更加显而易见，插入排序的工作量和n的平方成正比，如果n比较小，那么排序的工作量自然要
 * 小得多。
 * 如何让待排序的数组大部分元素有序或者说元素数量减少呢？
 * 可以对原始数组进行预处理，使得原始数组的大部分元素变得有序。如何对原始数组进行预处理呢？聪明的科学家想到了一种分组排序的方法，以此对数组进行
 * 一定的“粗略调整”。
 * <p>
 * 希尔排序法基本思想：先将要排序的一组数按某个增量d分成若干组，每组中记录的下标相差d，对每组中全部元素进行排序，然后再用一个较小的增量对它进行
 * 分组，在每组中再进行排序。当增量减到1时，整个要排序的数被分成一组，排序完成。一般的初次取序列的一半为增量，以后每次减半，直到增量为1。
 * <p>
 * 给定实例的shell排序的排序过程：
 * 假设待排序文件有10个记录，其关键字分别是：
 * 49，38，65，97，76，13，27，49，55，04。
 * 增量序列的取值依次为：
 * 5，2，1
 * <p>
 * 在某些极端情况下，希尔排序的最坏时间复杂度仍然是O(n^2)，甚至比直接插入排序更慢，例如：{2, 1, 5, 3, 7, 6, 9, 8}
 * 看看上面这个数组，如果我们照搬之前的分组思路，无论是以4为增量，还是以2为增量，每组内部的元素都没有任何交换。一直到我们把增量缩减为1，数组才
 * 会按照直接插入排序的方式进行调整。对于这样的数组，希尔排序不但没有减少直接插入排序的工作量，反而白白增加了分组操作的成本。
 * 每一轮希尔增量之间是等比的，这就导致了希尔增量存在盲区。为了避免这样的极端情况，聪明的科学家们发明了许多更为严谨的增量方式。为了保证分组粗调
 * 没有盲区，每一轮的增量需要彼此“互质”，也就是没有除1之外的公约数。于是，人们相继提出了很多种增量方式，其中最具代表性的是Hibbard增量和Sedgewick增量。
 * Hibbard的增量序列如下：1，3，7，15...   通项公式 2^k-1，利用此种增量方式的希尔排序，最坏时间复杂度是O（n^(3/2)）。
 * Sedgewick的增量序列如下：1, 5, 19, 41, 109... 通项公式 9*4^k - 9*2^k + 1 或者 4^k - 3*2^k + 1 利用此种增量方式的希尔排序，最坏
 * 时间复杂度是O（n^(4/3)）。
 *
 * @author: Sun
 * @create: 2021-07-07 14:30
 * @version: v1.0
 */
public class ShellSort {

    // ------------------------------------- 交换式希尔排序 BEGIN ------------------------------------- //

    /**
     * 交换式希尔排序：效率极低
     * 第一轮大循环：
     * {8, 9, 1, 7, 2, 3, 5, 4, 6, 0}
     * gap = 5
     * 第一轮大循环中的第一轮小循环：
     * i = 5   j = 0 arr[0] > arr[5] true  arr[0]=3  arr[5]=8  {3, 9, 1, 7, 2, 8, 5, 4, 6, 0}
     * 第一轮大循环中的第二轮小循环：
     * i = 6   j = 1 arr[1] > arr[6] true  arr[1]=3  arr[6]=8  {3, 5, 1, 7, 2, 8, 9, 4, 6, 0}
     * 第一轮大循环中的第三轮小循环：
     * i = 7   j = 2 arr[2] > arr[7] false  {3, 5, 1, 7, 2, 8, 9, 4, 6, 0}
     * 第一轮大循环中的第四轮小循环：
     * i = 8   j = 3 arr[3] > arr[8] true  arr[3]=6  arr[6]=7  {3, 5, 1, 6, 2, 8, 9, 4, 7, 0}
     * 第一轮大循环中的第五轮小循环：
     * i = 9   j = 4 arr[4] > arr[9] true  arr[4]=0  arr[9]=2  {3, 5, 1, 6, 0, 8, 9, 4, 7, 2}
     * <p>
     * 第二轮大循环：
     * {3, 5, 1, 6, 0, 8, 9, 4, 7, 2}
     * gap = 2
     * 第二轮大循环中的第一轮小循环：
     * i = 2   j = 0 arr[0] > arr[2]
     * 第二轮大循环中的第二轮小循环：
     * i = 3   j = 1 arr[1] > arr[3]
     * 第二轮大循环中的第三轮小循环：
     * i = 4   j = 2 arr[2] > arr[4]  j = 0 arr[0] > arr[2]
     * 第二轮大循环中的第四轮小循环：
     * i = 5   j = 3 arr[3] > arr[5]  j = 1 arr[1] > arr[3]
     * 第二轮大循环中的第五轮小循环：
     * i = 6   j = 4 arr[4] > arr[6]  j = 2 arr[2] > arr[4]  j = 0 arr[0] > arr[2]
     * 第二轮大循环中的第六轮小循环：
     * i = 7   j = 5 arr[5] > arr[7]  j = 3 arr[3] > arr[5]  j = 1 arr[1] > arr[3]
     * 第二轮大循环中的第七轮小循环：
     * i = 8   j = 6 arr[6] > arr[8]  j = 4 arr[4] > arr[6]  j = 2 arr[2] > arr[4]  j = 0 arr[0] > arr[2]
     * 第二轮大循环中的第八轮小循环：
     * i = 9   j = 7 arr[7] > arr[9]  j = 5 arr[5] > arr[7]  j = 3 arr[3] > arr[5]  j = 1 arr[1] > arr[3]
     *
     * @param arr
     */
    public void exchangeShellSort(int[] arr) {
        int count = 1;

        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // 对每组中的元素进行排序(交换)
            for (int i = gap; i < arr.length; i++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        int temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }

            System.out.println("第" + count + "次排序后：" + Arrays.toString(arr));
            count++;
        }
    }

    @Test
    public void exchangeShellSortTest() {
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};

        System.out.println("插入排序前：" + Arrays.toString(arr));
        exchangeShellSort(arr);
        System.out.println("插入排序后：" + Arrays.toString(arr));
    }

    // ------------------------------------- 交换式希尔排序 END ------------------------------------- //


    // ------------------------------------- 移位式希尔排序 BEGIN ------------------------------------- //

    /**
     * 移位式希尔排序：
     * 普通的插入排序是将无序表中的某个元素和有序表中的元素逐个对比，然后找到适当的位置并插入。
     * 希尔排序实际上在gap=1之前的几轮对比中就是将某个元素与前面的元素跳着对比，然后找到适当的位置并插入。
     * <p>
     * 第一轮大循环：
     * 第一轮大循环中的第一轮小循环：
     * {8, 9, 1, 7, 2, 3, 5, 4, 6, 0}
     * gap = 5  i = 5  insertVal = 3   insertIndex = 5
     * if (arr[5]  < arr[0]) true
     * while {
     * 5 - 5 >= 0 && 3 < arr[0] true
     * arr[5] = arr[0] {8, 9, 1, 7, 2, 8, 5, 4, 6, 0}
     * insertIndex = 0
     * 0 - 5 >= 0 && 3 < arr[-5] false
     * }
     * arr[0] = 3   {3, 9, 1, 7, 2, 8, 5, 4, 6, 0}
     * <p>
     * 第一轮大循环中的第二轮小循环：
     * {3, 9, 1, 7, 2, 8, 5, 4, 6, 0}
     * gap = 5  i = 6  insertVal = 5   insertIndex = 6
     * if (arr[6]  < arr[1]) true
     * while {
     * 6 - 5 >= 0 && 5 < arr[1] true
     * arr[6] = arr[1] {3, 9, 1, 7, 2, 8, 9, 4, 6, 0}
     * insertIndex = 1
     * 1 - 5 >= 0 && 5 < arr[-4] false
     * }
     * arr[1] = 5   {3, 5, 1, 7, 2, 8, 9, 4, 6, 0}
     * <p>
     * 第一轮大循环中的第三轮小循环：
     * {3, 5, 1, 7, 2, 8, 9, 4, 6, 0}
     * gap = 5  i = 7  insertVal = 4   insertIndex = 7
     * if (arr[7]  < arr[2]) false
     * <p>
     * 第一轮大循环中的第四轮小循环：
     * {3, 5, 1, 7, 2, 8, 9, 4, 6, 0}
     * gap = 5  i = 8  insertVal = 6   insertIndex = 8
     * if (arr[8]  < arr[3]) true
     * while {
     * 8 - 5 >= 0 && 6 < arr[3] true
     * arr[8] = arr[3] {3, 5, 1, 7, 2, 8, 9, 4, 7, 0}
     * insertIndex = 3
     * 3 - 5 >= 0 && 6 < arr[-2] false
     * }
     * arr[3] = 6   {3, 5, 1, 6, 2, 8, 9, 4, 7, 0}
     * <p>
     * 第一轮大循环中的第五轮小循环：
     * {3, 5, 1, 6, 2, 8, 9, 4, 7, 0}
     * gap = 5  i = 9  insertVal = 0   insertIndex = 9
     * if (arr[9]  < arr[4]) true
     * while {
     * 9 - 5 >= 0 && 0 < arr[4] true
     * arr[9] = arr[4] {3, 5, 1, 6, 2, 8, 9, 4, 7, 2}
     * insertIndex = 4
     * 4 - 5 >= 0 && 6 < arr[-1] false
     * }
     * arr[4] = 0   {3, 5, 1, 6, 0, 8, 9, 4, 7, 2}
     * <p>
     * 第二轮大循环：
     * 第二轮大循环中的第一轮小循环：
     * gap = 2  i = 2  insertVal = x   insertIndex = 2
     * 第二轮大循环中的第二轮小循环：
     * gap = 2  i = 3  insertVal = x   insertIndex = 3
     * 第二轮大循环中的第三轮小循环：
     * gap = 2  i = 4  insertVal = x   insertIndex = 4
     * 第二轮大循环中的第四轮小循环：
     * gap = 2  i = 5  insertVal = x   insertIndex = 5
     * 第二轮大循环中的第五轮小循环：
     * gap = 2  i = 6  insertVal = x   insertIndex = 6
     * 第二轮大循环中的第六轮小循环：
     * gap = 2  i = 7  insertVal = x   insertIndex = 7
     * 第二轮大循环中的第七轮小循环：
     * gap = 2  i = 8  insertVal = x   insertIndex = 8
     * 第二轮大循环中的第八轮小循环：
     * gap = 2  i = 9  insertVal = x   insertIndex = 9
     * <p>
     * 第三轮大循环：
     * 第三轮大循环中的第一轮小循环：
     * gap = 1  i = 1  insertVal = x   insertIndex = 1
     * 第三轮大循环中的第二轮小循环：
     * gap = 1  i = 2  insertVal = x   insertIndex = 2
     * 第三轮大循环中的第三轮小循环：
     * gap = 1  i = 3  insertVal = x   insertIndex = 3
     * 第三轮大循环中的第四轮小循环：
     * gap = 1  i = 4  insertVal = x   insertIndex = 4
     * 第三轮大循环中的第五轮小循环：
     * gap = 1  i = 5  insertVal = x   insertIndex = 5
     * 第三轮大循环中的第六轮小循环：
     * gap = 1  i = 6  insertVal = x   insertIndex = 6
     * 第三轮大循环中的第七轮小循环：
     * gap = 1  i = 7  insertVal = x   insertIndex = 7
     * 第三轮大循环中的第八轮小循环：
     * gap = 1  i = 8  insertVal = x   insertIndex = 8
     * 第三轮大循环中的第九轮小循环：
     * gap = 1  i = 9  insertVal = x   insertIndex = 9
     *
     * @param arr
     */
    public void shiftedShellSort(int[] arr) {
        int count = 1;

        // 从第gap个元素，逐个对其所在的组进行直接插入排序
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int insertIndex = i;
                int insertVal = arr[insertIndex];

                if (arr[insertIndex] < arr[insertIndex - gap]) {
                    while (insertIndex - gap >= 0 && insertVal < arr[insertIndex - gap]) {
                        arr[insertIndex] = arr[insertIndex - gap];
                        insertIndex -= gap;
                    }

                    arr[insertIndex] = insertVal;
                }
            }

            System.out.println("第" + count + "次排序后：" + Arrays.toString(arr));
            count++;
        }
    }

    @Test
    public void shiftedShellSortTest() {
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};

        System.out.println("插入排序前：" + Arrays.toString(arr));
        shiftedShellSort(arr);
        System.out.println("插入排序后：" + Arrays.toString(arr));
    }

    // ------------------------------------- 移位式希尔排序 END ------------------------------------- //
}
