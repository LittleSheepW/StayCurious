package com.ww.java.lanuage.generic;

/**
 * @author: Sun
 * @create: 2021-03-03 11:48
 * @version: v1.0
 */
public class Replace {
}

class Father {

    public Object run(String s) {
        return null;
    }
}

class Son extends Father {

    /**
     * 重写方法签名必须相同，访问修饰符不能比父类更严格，返回类型是父类的返回类型或父类返回类型的子类，异常不能比父类抛出的更大。
     * 重载方法名相同，参数列表、返回类型、访问修饰符、异常不同都可以。
     *
     * 重载父类的方法时，返回类型可以更窄，
     * @return
     */
    public String run(Object o) {
        return null;
    }
}
