package com.ww.designPatterns.decorator;

/**
 * 设计模式: 装饰者模式
 * <p>
 * 装饰者模式比模板方法更加灵活，可以使用任意数量的装饰器，还可以以任意次序执行。
 *
 * @author: Sun
 * @create: 2021-03-12 14:52
 * @version: v1.0
 */
public interface Command {

    void execute();
}
