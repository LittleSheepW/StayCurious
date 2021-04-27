package com.ww.temp;

import org.junit.Test;

/**
 * @author: Sun
 * @create: 2021-04-21 11:51
 * @version: v1.0
 */
public class Memory {

    Object object = new Object();

    int a = 1;

    @Test
    public void test() {
        Impl o = new Impl();
        Impl o1 = new Impl();
        Impl o2 = new Impl();

        System.out.println(o.hashCode());
        System.out.println(o1.hashCode());
        System.out.println(o2.hashCode());
    }
}
