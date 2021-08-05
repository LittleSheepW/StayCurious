package com.ww.temp;

/**
 * @author: Sun
 * @create: 2021-05-26 14:28
 * @version: v1.0
 */
public class SynchronizedDemo {

    public void synchronizedCodeBlock() {
        synchronized (this) {
            System.out.println("synchronized 代码块");
        }
    }

    public synchronized void synchronizedMethod() {
        System.out.println("synchronized 方法");
    }
}
