package com.ww.algorithm.complexity;

/**
 * 基本操作执行次数(时间频度)：设T(n)为程序基本操作执行次数的函数(也可以认为是程序的相对执行时间函数)，n为输入规模。
 * <p>
 * 渐进时间复杂度：表示算法的执行时间随着数据规模增长的变化趋势。是对一个算法运行时间长短的量度。使用大O表示法，记做T(n)=O(f(n))
 * 一般情况下，算法中基本操作重复执行的次数是问题规模n的某个函数，用T(n)表示，若有某个辅助函数f(n),使得当n趋近于无穷大时，T(n)/f(n)的极限值
 * 为不等于零的常数，则称f(n)是T(n)的同数量级函数。记作T(n)=O(f(n)),称O(f(n)) 为算法的渐进时间复杂度，简称时间复杂度。
 * <p>
 * 简单的讲，时间复杂度就是把程序的相对执行时间函数T(n)简化为一个数量级，这个数量级可以是n、n^2、n^3等。
 * 推导时间复杂度时有如下几个原则：
 * - 如果运行时间是常数量级，则用常数1表示；
 * - 只保留时间函数中的最高项阶；
 * - 如果最高项阶存在，则省去最高项前面的系数。
 *
 * @author: Sun
 * @create: 2021-07-13 11:37
 * @version: v1.0
 */
public class TimeComplexity {

    /**
     * 常数阶：
     * T(n) = 2
     * T(n) = O(1)
     *
     * @param n
     */
    public void constant(int n) {
        System.out.println("等1分钟");
        System.out.println("吃1个鸡腿");
    }

    /**
     * 对数阶：
     * T(n) = 5logn
     * T(n) = O(logn)
     *
     * @param n
     */
    public void logarithm(int n) {
        for (int i = n; i > 1; i /= 2) {
            System.out.println("等1分钟");
            System.out.println("等1分钟");
            System.out.println("等1分钟");
            System.out.println("等1分钟");
            System.out.println("吃一半面包");
        }
    }

    /**
     * 线性阶：
     * T(n) = 3n
     * T(n) = O(n)
     *
     * @param n
     */
    public void linear(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println("等1分钟");
            System.out.println("等1分钟");
            System.out.println("吃1cm面包");
        }
    }

    /**
     * 平方阶：
     * T(n) = 0.5n^2 + 0.5n
     * T(n) = O(n)
     *
     * @param n
     */
    public void polynomial(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                System.out.println("等1分钟");
            }
            System.out.println("吃1cm面包");
        }
    }
}
