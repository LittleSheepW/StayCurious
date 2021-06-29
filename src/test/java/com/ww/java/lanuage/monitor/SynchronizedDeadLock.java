package com.ww.java.lanuage.monitor;

public class SynchronizedDeadLock {

    private static Object locka = new Object();
    private static Object lockb = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (locka) {
                try {
                    System.out.println(Thread.currentThread().getName() + " got locka!");
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " waiting to get lockb!");
                synchronized (lockb) {
                    System.out.println(Thread.currentThread().getName() + " got lockb!");
                }
            }
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            synchronized (lockb) {
                try {
                    System.out.println(Thread.currentThread().getName() + " got lockb");
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " waiting to get locka!");
                synchronized (locka) {
                    System.out.println(Thread.currentThread().getName() + " got locka!");
                }
            }
        }, "thread2");

        thread1.start();
        thread2.start();
    }
}