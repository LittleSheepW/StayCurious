package com.ww.java.util.concurrent;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    @Test
    public void awaitTest() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for (int i = 1; i <= cyclicBarrier.getParties(); i++) {
            new Thread(new EmployeeRunnable(cyclicBarrier), "队友" + i).start();
        }

        Thread.sleep(15000);
        System.out.println("主线程结束");
    }
}

class EmployeeRunnable implements Runnable {
    private CyclicBarrier cyclicBarrier;

    public EmployeeRunnable(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            try {
                Random rand = new Random();
                int randomNum = rand.nextInt(2001) + 1000;
                Thread.sleep(randomNum);
                System.out.println(Thread.currentThread().getName() + ", 通过了第" + i + "个障碍物, 使用了 " + ((double) randomNum / 1000) + "s");
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
