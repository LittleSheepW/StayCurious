package com.ww.algorithm.recursion;

/**
 * 八皇后问题，是一个古老而著名的问题，是回溯算法的典型案例。该问题是国际西洋棋棋手马克斯·贝瑟尔于1848年提出:在8×8格的国际象棋上摆放八个皇后，
 * 使其不能互相攻击，即：任意两个皇后都不能处于同一行、同一列或同一斜线上，问有多少种摆法。
 * <p>
 * 八皇后问题算法思路分析：
 * 1) 第一个皇后先放第一行第一列
 * 2) 第二个皇后放在第二行第一列、然后判断是否OK，如果不OK，继续放在第二列、第三列、依次把所有列都放完，找到一个合适
 * 3) 继续第三个皇后，还是第一列、第二列......直到第8个皇后也能放在一个不冲突的位置，算是找到了一个正确解
 * 4) 当得到一个正确解时，在栈回退到上一个栈时，就会开始回溯，即将第一个皇后，放到第一列的所有正确解，全部得到
 * 5) 然后回头继续第一个皇后放第二列，后面继续循环执行1,2,3,4的步骤
 * <p>
 * 理论上应该创建一个二维数组来表示棋盘，但是实际上可以通过算法，用一个一维数组即可解决问题：arr[8]={0,4,7,5,2,6,1,3}
 * index表示第index+1个皇后放在index+1行的value+1列
 *
 * @author: Sun
 * @create: 2021-07-02 17:38
 * @version: v1.0
 */
public class Queen8 {

    // 摆法数量
    int count = 0;

    // 定义皇后的个数
    int max = 8;
    // 定义数组array 保存皇后放置位置的结果比如arr={0,4,7,5,2,6,1,3}
    int[] array = new int[max];

    /**
     * 放置皇后
     *
     * @param n
     */
    private void preventQueen(int n) {
        if (n == max) {
            print();
            return;
        }

        // 依次放置皇后，并判断是否冲突
        for (int i = 0; i < max; i++) {
            // 放置皇后
            array[n] = i;
            // 判断冲突，没有冲突时将放置下一个皇后
            if (detectConflicts(n)) {
                preventQueen(n + 1);
            }
        }
    }

    /**
     * 当放置好第n个皇后后，检测第n个皇后与n之前的所有皇后是否冲突(是否在一列、是否在一条斜线上)
     *
     * @param n 第n个皇后
     * @return false为冲突 true为不冲突
     */
    private boolean detectConflicts(int n) {
        for (int i = 0; i < n; i++) {
            /**
             * array[i] == array[n]  判断两个皇后是否在同一列
             * Math.abs(n - i) == Math.abs(array[n] - array[i]) 判断两个皇后是否在同一斜线上
             */
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * 输出皇后摆放的位置
     */
    private void print() {
        count++;

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
        queen8.preventQueen(0);

        System.out.println("摆法数量: " + queen8.count);
    }
}
