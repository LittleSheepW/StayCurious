package com.ww.temp;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;

/**
 * JVM基本内存分配原则
 * -XX：+PrintGCDetails这个收集器日志参数，告诉虚拟机在发生垃圾收集行为时打印内存回收日志，并且在进程退出的时候输出当前的内存各区域分配情况。
 *
 * @author: Sun
 * @create: 2021-05-25 14:31
 * @version: v1.0
 */
public class JvmBasicMemoryAllocationPrinciple {

    public static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        // JvmBasicMemoryAllocationPrinciple.objectsAreAllocatedInEdenFirst();
        JvmBasicMemoryAllocationPrinciple.bigObjectsGoDirectlyIntoTheOldAge();
    }

    /**
     * 对象优先在Eden分配
     * JVM参数：-verbose:gc -Xms25M -Xmx25M -Xmn15M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    public static void objectsAreAllocatedInEdenFirst() {
        byte[] all1, all2, all3;

        all1 = new byte[1 * _1MB];
        all2 = new byte[8 * _1MB];
        all3 = new byte[8 * _1MB];      // Minor GC
    }

    public JvmBasicMemoryAllocationPrinciple() {
        System.out.println("Constructor...");
    }

    /**
     * 大对象直接进入老年代  TODO：为啥加了参数也不再老年区直接分配呢？？
     * JVM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
     */
    public static void bigObjectsGoDirectlyIntoTheOldAge() {
        byte[] all1,all2,all3;
        all1 = new byte[4 * _1MB];
        all2 = new byte[4 * _1MB];
        all3 = new byte[4 * _1MB];

        /*for (MemoryPoolMXBean memoryPoolMXBean : ManagementFactory.getMemoryPoolMXBeans()) {

            System.out.println(memoryPoolMXBean.getName() + "  总量:" + memoryPoolMXBean.getUsage().getCommitted() + "   使用的内存:" + memoryPoolMXBean.getUsage().getUsed());
        }*/

    }
}
