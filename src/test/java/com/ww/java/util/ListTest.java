package com.ww.java.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Sun
 * @create: 2021-01-08 12:19
 * @version: v1.0
 */
public class ListTest {

    /**
     * 从该列表中删除所有未包含在指定集合中的元素。
     */
    @Test
    public void retainAllTest() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);
        list1.add(5);

        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(3);
        list2.add(6);

        list1.retainAll(list2);

        System.out.println(list1);
        System.out.println(list2);
    }
}
