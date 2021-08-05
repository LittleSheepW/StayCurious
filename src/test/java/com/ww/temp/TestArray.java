package com.ww.temp;

import org.junit.Test;

/**
 * @author: Sun
 * @create: 2021-06-23 10:40
 * @version: v1.0
 */
public class TestArray {

    @Test
    public void test() {
        int[] a1 = {1, 2, 3, 4, 5};
        int[][] a2 = {{1,2}, {3,4}, {5,6}, {7,8}, {9,10}};

        for (int i : a1) {
            System.out.print(i + "\t");
        }

        System.out.println("\n--------------");

        for (int i = 0; i < a2.length; i++) {
            for (int i1 = 0; i1 < a2[i].length; i1++) {
                System.out.print("a2[" + i + "][" + i1 + "]\t");
            }
            System.out.println();
        }
    }
}
