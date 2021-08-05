package com.ww.java.oom;

/**
 * Java栈 堆栈溢出错误测试
 * 使用-Xss参数减少栈内存容量，StackOverflowError异常出现时输出的堆栈深度相应缩小
 * VM Args：-Xss160k
 * <p>
 * 测试结果：在无法动态扩展栈容量大小的虚拟机上，无论是由于栈帧太大还是虚拟机栈容量太小，当新的栈帧内存无法分配的时候，HotSpot虚拟机抛出的都是
 * StackOverflowError异常。
 */
public class JavaVMStackSOF1 {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        JavaVMStackSOF1 oom = new JavaVMStackSOF1();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}