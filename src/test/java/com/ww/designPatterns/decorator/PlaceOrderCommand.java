package com.ww.designPatterns.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * 业务逻辑: 下单
 *
 * @author: Sun
 * @create: 2021-03-12 14:56
 * @version: v1.0
 */
@Slf4j
public class PlaceOrderCommand implements Command {

    @Override
    public void execute() {
        log.info("[execute] [下单]");
    }
}
