package com.ww.algorithm.recursion;

import org.junit.Test;

/**
 * 使用递归解决迷宫问题
 *
 * @author: Sun
 * @create: 2021-07-02 14:24
 * @version: v1.0
 */
public class Maze {

    /**
     * 使用某种固定策略寻找出路
     * <p>
     * 如果小球能到map[6][5]位置，则说明通路找到
     * 约定：当map[i][j]为0表示该点没有走过当为1表示墙；2表示通路可以走；3表示该点已经走过但是走不通
     * 在走迷宫时，需要确定一个策略(方法)下->右->上->左，如果该点走不通，再进行回溯
     *
     * @param maze 迷宫
     * @param i    表示从迷宫的哪个位置开始出发
     * @param j    表示从迷宫的哪个位置开始出发
     * @return
     */
    public boolean findAWayOut(int[][] maze, int i, int j) {
        // maze[6][5]为迷宫的终点
        if (maze[6][5] == 2) {
            return true;
        } else {
            if (maze[i][j] == 0) {
                // 往下走
                maze[i][j] = 2;
                if (findAWayOut(maze, i + 1, j)) {
                    return true;
                } else if (maze[i][j + 1] == 0) {
                    // 往右走
                    return true;
                } else if (maze[i - 1][j] == 0) {
                    // 往上走
                    return true;
                } else if (maze[i][j - 1] == 0) {
                    // 往左走
                    return true;
                } else {
                    // 上下左右都走不通
                    maze[i][j] = 3;
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    @Test
    public void findAWayOutTest() {
        /**
         * 初始化迷宫：
         * 1、创建一个二维数组，模拟迷宫
         * 2、设置迷宫的上下墙
         * 3、设置迷宫的左右墙
         * 4、设置挡板
         */
        int[][] maze = new int[8][7];
        for (int i = 0; i < 7; i++) {
            maze[0][i] = 1;
            maze[7][i] = 1;
        }
        for (int i = 0; i < 7; i++) {
            maze[i][0] = 1;
            maze[i][6] = 1;
        }
        maze[3][1] = 1;
        maze[3][2] = 1;

        /**
         * 回溯思路分析：
         * 第三个栈：maze[3][1] != 0 return false
         * 第二个栈：maze[2][1] = 2   findAWayOut(maze, 3, 1)  return false
         * 第一个栈：maze[1][1] = 2   findAWayOut(maze, 2, 1)  return false
         */
        // maze[1][2] = 1;
        // maze[2][2] = 1;


        // 打印初始化好的迷宫
        System.out.println("初始化迷宫完毕：");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(maze[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("---------");

        findAWayOut(maze, 1, 1);

        System.out.println("寻找出路完毕：");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(maze[i][j] + "\t");
            }
            System.out.println();
        }
    }


    /**
     * TODO：求出最短路径  这样也太费劲了吧？
     * 怎么把策略组合到一起呢？
     * 上 下 左 右
     * 上 下 右 左
     * 上 左 下 右
     * 上 左 右 下
     * 上 右 下 左
     * 上 右 左 下
     */

}
