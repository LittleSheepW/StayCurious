package com.ww.java.util.concurrent;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author: Sun
 * @create: 2021-05-31 15:47
 * @version: v1.0
 */
public class CountDownLatchTest {

    @Test
    public void awaitTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 1; i <= latch.getCount(); i++) {
            new Thread(new PlayerThread(latch), "player" + i).start();
        }
        System.out.println("正在等待所有玩家准备好...");
        latch.await();
        System.out.println("开始游戏");
    }
}

class PlayerThread implements Runnable {
    private CountDownLatch countDownLatch;

    public PlayerThread(CountDownLatch latch) {
        this.countDownLatch = latch;
    }

    @Override
    public void run() {
        try {
            Random rand = new Random();
            int randomNum = rand.nextInt(2001) + 1000;
            Thread.sleep(randomNum);
            System.out.println(Thread.currentThread().getName() + " 已经准备好了, 所使用的时间为 " + ((double) randomNum / 1000) + "s");
            countDownLatch.countDown();
            // System.out.println(Thread.currentThread().getName() + " count down!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
