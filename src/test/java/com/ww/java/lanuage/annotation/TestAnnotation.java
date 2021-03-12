package com.ww.java.lanuage.annotation;

import java.lang.annotation.*;

/**
 * 注解是一系列元数据，它提供数据用来解释程序代码，但是注解并非是所解释的代码本身的一部分。注解对于代码的运行效果没有直接影响。要想正确检阅注解，
 * 离不开一个手段，那就是反射。
 * 注解有许多用处，主要如下：
 * - 提供信息给编译器：编译器可以利用注解来探测错误和警告信息
 * - 编译阶段时的处理：软件工具可以用来利用注解信息来生成代码、Html文档或者做其它相应处理。
 * - 运行时的处理：某些注解可以在程序运行的时候接受代码的提取
 *
 * @Retention Retention 的英文意为保留期的意思。当 @Retention 应用到一个注解上的时候，它解释说明了这个注解的的存活时间。
 * 它的取值如下：
 * RetentionPolicy.SOURCE 注解只在源码阶段保留，在编译器进行编译时它将被丢弃忽视。
 * RetentionPolicy.CLASS 注解只被保留到编译进行的时候，它并不会被加载到 JVM 中。
 * RetentionPolicy.RUNTIME 注解可以保留到程序运行的时候，它会被加载进入到 JVM 中，所以在程序运行时可以获取到它们。
 * <p>
 * @Documented 顾名思义，这个元注解肯定是和文档有关。它的作用是能够将注解中的元素包含到 Javadoc 中去。
 * <p>
 * @Target Target 是目标的意思，@Target 指定了注解运用的地方。当一个注解被 @Target 注解时，这个注解就被限定了运用的场景。
 * 它的取值如下：
 * ElementType.ANNOTATION_TYPE 可以给一个注解进行注解
 * ElementType.CONSTRUCTOR 可以给构造方法进行注解
 * ElementType.FIELD 可以给属性进行注解
 * ElementType.LOCAL_VARIABLE 可以给局部变量进行注解
 * ElementType.METHOD 可以给方法进行注解
 * ElementType.PACKAGE 可以给一个包进行注解
 * ElementType.PARAMETER 可以给一个方法内的参数进行注解
 * ElementType.TYPE 可以给一个类型进行注解，比如类、接口、枚举
 * <p>
 * @Inherited Inherited 是继承的意思，但是它并不是说注解本身可以继承，而是说如果一个超类被 @Inherited 注解过的注解进行注解的话，
 * 那么如果它的子类没有应用任何注解的话，那么这个子类就继承了超类的注解。
 */
@Documented
@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
//@Repeatable()
public @interface TestAnnotation {

    /**
     * 注解的属性：
     * 注解的属性也叫做成员变量。注解只有成员变量，没有方法。注解的成员变量在注解的定义中以“无形参的方法”形式来声明，其方法名定义了该
     * 成员变量的名字，其返回值定义了该成员变量的类型。注解中属性可以有默认值，默认值需要用 default 关键值指定。
     * <p>
     * 注意：
     * 1、在注解中定义属性时它的类型必须是 8 种基本数据类型外加 类、接口、注解及它们的数组。
     * 2、注解中属性可以有默认值，默认值需要用 default 关键值指定。
     * 3、如果一个注解内仅仅只有一个名字为 value 的属性时，应用这个注解时可以直接接属性值填写到括号内。
     */

    int id() default 0;

    String msg() default "default";
}