package com.ww.designPatterns.templateMethod;

import lombok.extern.slf4j.Slf4j;

/**
 * 设计模式: 模板方法
 * <p>
 * 在父类中写通用代码，只留一个口子(抽象方法)让子类实现。
 * 缺点在于父类会定义一切，要执行哪些代码，以什么顺序执行等等。子类只能无条件接受，没有反抗的余地。
 *
 * @author: Sun
 * @create: 2021-03-12 15:30
 * @version: v1.0
 */
@Slf4j
public abstract class BaseCommand {

    public void execute() {
        log.info("[execute] [begin execute]");

        long beginTime = System.currentTimeMillis();

        log.info("[execute] [开启事务]");
        doBusiness();
        log.info("[execute] [提交事务]");

        long endTime = System.currentTimeMillis();
        log.info("[execute] [本次执行时间] [time:{}]", endTime - beginTime);

        log.info("[execute] [execute end]");
    }

    public abstract void doBusiness();
}

@Slf4j
class PlaceOrderCommand extends BaseCommand {

    @Override
    public void doBusiness() {
        log.info("[doBusiness] [下单]");
    }
}

@Slf4j
class PaymentCommand extends BaseCommand {

    @Override
    public void doBusiness() {
        log.info("[doBusiness] [支付]");
    }
}