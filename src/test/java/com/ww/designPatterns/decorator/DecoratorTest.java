package com.ww.designPatterns.decorator;

import org.junit.Test;

/**
 * @author: Sun
 * @create: 2021-03-12 14:57
 * @version: v1.0
 */
public class DecoratorTest {

    /**
     * 使用单个装饰器
     */
    @Test
    public void placeOrderCommandTest() {
        Command cmd = new LoggerDecorator(new PlaceOrderCommand());
        cmd.execute();
    }

    /**
     * 使用多个装饰器
     */
    @Test
    public void paymentCommandTest() {
        Command cmd = new LoggerDecorator(new PerformanceDecorator(new PaymentCommand()));
        cmd.execute();
    }
}
