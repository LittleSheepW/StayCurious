package com.ww.java.oom;

import java.util.HashSet;
import java.util.Set;

/**
 * 运行时常量池 内存溢出异常测试
 * VM Args：-Xms6m -Xmx6m
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {
        // 使用Set保持着常量池引用，避免Full GC回收常量池行为
        Set<String> set = new HashSet<String>();
        // 在short范围内足以让6MB的PermSize产生OOM了
        short i = 0;
        while (true) {
            set.add(String.valueOf(i++).intern());
        }
    }
}