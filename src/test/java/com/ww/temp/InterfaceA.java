package com.ww.temp;

/**
 * @author: Sun
 * @create: 2021-04-21 11:36
 * @version: v1.0
 */
public interface InterfaceA {

    /**
     * 默认方法
     * @param a
     * @param b
     */
    default void add(int a, int b) {
        System.out.print("Answer by Default method = ");
        System.out.println(a + b);
    }

    /**
     * 静态方法
     * @param a
     * @param b
     */
    static void mul(int a, int b) {
        System.out.print("Answer by Static method = ");
        System.out.println(a * b);
    }
}
