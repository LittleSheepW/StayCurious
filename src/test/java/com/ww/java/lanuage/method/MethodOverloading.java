package com.ww.java.lanuage.method;

/**
 * 方法重重载规则：
 * 重载时方法名必须相同，参数类型、个数、顺序，方法返回值和访问修饰符可以不同。
 *
 * @author: Sun
 * @create: 2021-05-14 17:56
 * @version: v1.0
 */
public class MethodOverloading {

    protected String run(String s) {
        return s;
    }

    private Integer run(Integer i) {
        return i;
    }
}

