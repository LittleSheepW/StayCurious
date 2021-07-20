package com.ww.algorithm.complexity;

/**
 * 空间复杂度：表示算法的存储空间随着数据规模增长的变化趋势。是对一个算法在运行过程中临时占用存储空间大小的量度。使用大O表示法，记做S(n)=O(f(n))
 *
 * @author: Sun
 * @create: 2021-07-13 16:22
 * @version: v1.0
 */
public class SpaceComplexity {

    /**
     * 常量空间：当算法的存储空间大小固定，和输入规模没有直接的关系时，空间复杂度记做O(1)
     *
     * @param n
     */
    public void constant(int n) {
        int var = 3;
    }

    /**
     * 线性空间：当算法分配的空间是一个线性的集合(如数组)，并且集合大小和输入规模n成正比时，空间复杂度记做O(n)
     *
     * @param n
     */
    public void linear(int n) {
        int[] arr = new int[n];
    }

    /**
     * 二维空间：当算法分配的空间是一个二维数组集合，并且集合的长度和宽度都与输入规模n成正比时，空间复杂度记做O(n^2)
     *
     * @param n
     */
    public void twoDimensional(int n) {
        int var = 3;
    }

    /**
     * 递归空间：递归是一个比较特殊的场景。虽然递归代码中并没有显示地声明变量或集合，但是计算机在执行程序时，会专门分配一块内存，用来存储方法
     * 调用栈。执行递归操作所需要的内存空间和递归的深度成正比。纯粹的递归操作的空间复杂度也是线性的，如果递归的深度是n，那么空间复杂度就是O(n)。
     *
     * @param n
     */
    public void recursion(int n) {
        if (n <= 1) {
            return;
        }

        recursion(n - 1);
    }
}
