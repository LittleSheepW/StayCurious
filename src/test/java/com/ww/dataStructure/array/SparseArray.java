package com.ww.dataStructure.array;

import org.junit.Test;

import java.io.*;

/**
 * 稀疏数组：稀疏数组可以看做是压缩版的普通数组，这里说的普通数组是指无效数据量远大于有效数据量的数组。
 * <p>
 * 为什么要对普通数组进行压缩存储呢？
 * - 原数组中存在大量的无效数据，占据了大量的存储空间，真正有用的数据却少之又少。
 * - 压缩存储可以节省存储空间以避免资源不必要的浪费，在数据序列化到磁盘时，压缩存储可以提高IO效率。
 * <p>
 * 稀疏数组存储方式：
 * 第一行存储原始数据总行数，总列数，总的非0数据个数。
 * 接下来每一行都存储非0数所在行、所在列以及具体值。
 *
 * @author: Sun
 * @create: 2021-06-23 11:02
 * @version: v1.0
 */
public class SparseArray {

    /**
     * 二维数组转稀疏数组思路：
     * 1、遍历原始二维数组，得到有效数据个数
     * 2、根据有效数据个数就可以创建稀疏数组
     * 3、再次遍历原始二维数组，将有效数据存入到稀疏数组
     */
    @Test
    public void arrayToSparseArray() {
        // 普通二维数组
        int[][] array = new int[6][7];
        array[0][3] = 22;
        array[0][6] = 15;
        array[1][1] = 11;
        array[1][5] = 17;
        array[2][3] = -6;
        array[3][5] = 39;
        array[4][0] = 91;
        array[5][2] = 28;

        // 打印普通二维数组
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }

        // 得到有效数据个数
        int valueNums = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] != 0) {
                    valueNums++;
                }
            }
        }

        // 创建稀疏数组并初始化第一行
        int[][] sparseArray = new int[valueNums + 1][3];
        sparseArray[0][0] = array.length;
        sparseArray[0][1] = array[0].length;
        sparseArray[0][2] = valueNums;
        // 将二维数组中的有效数据存入到稀疏数组
        int beginIndex = 1;
        for (int j = 0; j < array.length; j++) {
            for (int k = 0; k < array[j].length; k++) {
                if (array[j][k] != 0) {
                    sparseArray[beginIndex][0] = j;
                    sparseArray[beginIndex][1] = k;
                    sparseArray[beginIndex][2] = array[j][k];
                    beginIndex++;
                }
            }
        }

        // 打印稀疏数组
        for (int i = 0; i < sparseArray.length; i++) {
            System.out.print("[" + i + "]" + "\t");

            for (int j = 0; j < sparseArray[i].length; j++) {
                System.out.print(sparseArray[i][j] + "\t");
            }
            System.out.println();
        }

        // 将稀疏数组保存到磁盘上
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/Users/sun/devtools/IdeaWorkspace/StayCurious/src/test/java/com/ww/dataStructure/array/sparseArray.data");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(sparseArray);
            System.out.println("稀疏数组存盘成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 稀疏数组转二维数组思路：
     * 1、读取稀疏数组第一行，根据第一行数据创建原始二维数组
     * 2、读取稀疏数组后续行的数据并赋值给原始二维数组即可
     */
    @Test
    public void sparseArrayToArray() {
        try {
            FileInputStream fileInputStream = new FileInputStream("/Users/sun/devtools/IdeaWorkspace/StayCurious/src/test/java/com/ww/dataStructure/array/sparseArray.data");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            int[][] sparseArray = (int[][]) objectInputStream.readObject();

            // 打印稀疏数组
            for (int i = 0; i < sparseArray.length; i++) {
                System.out.print("[" + i + "]" + "\t");

                for (int j = 0; j < sparseArray[i].length; j++) {
                    System.out.print(sparseArray[i][j] + "\t");
                }
                System.out.println();
            }

            System.out.println();

            int[][] array = new int[sparseArray[0][0]][sparseArray[0][1]];
            for (int i = 1; i < sparseArray.length; i++) {
                array[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
            }
            // 打印普通二维数组
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    System.out.print(array[i][j] + "\t");
                }
                System.out.println();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
