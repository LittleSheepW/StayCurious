package com.ww.designPatterns.templateMethod;

import org.junit.Test;

/**
 * @author: Sun
 * @create: 2021-03-12 15:34
 * @version: v1.0
 */
public class TemplateMethodTest {

    @Test
    public void paymentCommandTest() {
        BaseCommand cmd = new PlaceOrderCommand();
        cmd.execute();
    }

    @Test
    public void placeOrderCommandTest() {
        BaseCommand cmd = new PaymentCommand();
        cmd.execute();
    }
}
