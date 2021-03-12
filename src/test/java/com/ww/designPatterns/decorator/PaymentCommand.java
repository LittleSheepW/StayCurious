package com.ww.designPatterns.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * 业务逻辑：支付
 *
 * @author: Sun
 * @create: 2021-03-12 15:04
 * @version: v1.0
 */
@Slf4j
public class PaymentCommand implements Command {

    @Override
    public void execute() {
        log.info("[execute] [支付]");
    }
}
