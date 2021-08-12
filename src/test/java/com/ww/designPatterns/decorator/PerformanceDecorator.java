package com.ww.designPatterns.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * 一个用于性能统计的装饰器
 *
 * @author: Sun
 * @create: 2021-03-12 14:54
 * @version: v1.0
 */
@Slf4j
public class PerformanceDecorator implements Command {

    Command cmd;

    public PerformanceDecorator(Command cmd) {
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        long beginTime = System.currentTimeMillis();
        this.cmd.execute();
        long endTime = System.currentTimeMillis();

        log.info("[execute] [消耗的时间:{}]", endTime - beginTime);
    }
}
