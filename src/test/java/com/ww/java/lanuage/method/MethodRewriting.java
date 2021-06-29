package com.ww.java.lanuage.method;

/**
 * 方法重写规则：
 * 重写方法签名必须相同，访问修饰符不能比父类更严格，返回类型是父类的返回类型或父类返回类型的子类，异常不能比父类抛出的更大。
 *
 * @author: Sun
 * @create: 2021-03-03 11:48
 * @version: v1.0
 */
public class MethodRewriting {

    class Father {

        protected Object run(String s) {
            return s;
        }
    }

    class Son extends Father {

        @Override
        public String run(String s) {
            return s;
        }
    }
}