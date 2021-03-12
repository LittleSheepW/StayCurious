package com.ww.designPatterns.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * 一个用于记录日志的装饰器
 *
 * @author: Sun
 * @create: 2021-03-12 14:52
 * @version: v1.0
 */
@Slf4j
public class LoggerDecorator implements Command {

    Command cmd;

    public LoggerDecorator(Command cmd) {
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        log.info("begin execute");
        this.cmd.execute();
        log.info("execute end");
    }
}
