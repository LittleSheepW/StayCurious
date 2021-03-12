package com.ww.java.lanuage.generic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Fruit {
}

class Orange extends Fruit {
}

class Apple extends Fruit {
}

class RedApple extends Apple {
}

/**
 * 逆变与协变用来描述类型转换（type transformation）后的继承关系，其定义：如果𝐴、𝐵表示类型，𝑓(⋅)表示类型转换，≤表示继承关系（比如，𝐴≤𝐵表示𝐴是由𝐵派生出来的子类）；
 * 𝑓(⋅)是协变（covariant）的，当𝐴≤𝐵时有𝑓(𝐴)≤𝑓(𝐵)成立；
 * 𝑓(⋅)是逆变（contravariant）的，当𝐴≤𝐵时有𝑓(𝐵)≤𝑓(𝐴)成立；
 * 𝑓(⋅)是不变（invariant）的，当𝐴≤𝐵时上述两个式子均不成立，即𝑓(𝐴)与𝑓(𝐵)相互之间没有继承关系。
 *
 * Java中的常见类型转换的协变性、逆变性或不变性。
 * 泛型：
 * 令f(A)=ArrayList<A>，那么𝑓(⋅)时逆变、协变还是不变的呢？
 * 如果是逆变，则ArrayList<Integer>是ArrayList<Number>的父类型；
 * 如果是协变，则ArrayList<Integer>是ArrayList<Number>的子类型；
 * 如果是不变，二者没有相互继承关系。
 * ArrayList<Number> list = new ArrayList<Integer>(); 该语句在编译时发生错误 说明泛型是不变的。
 *
 * 数组：
 * 令f(A)=A[]，容易证明数组是协变的：Number[] numbers = new Integer[3];
 *
 *
 * PECS法则：生产者（Producer）使用extends，消费者（Consumer）使用super
 * - 生产者：如果你需要一个提供E类型元素的集合，使用泛型通配符<? extends E>。它好比一个生产者，可以提供数据。
 * - 消费者：如果你需要一个只能装入E类型元素的集合，使用泛型通配符<? super E>。它好比一个消费者，可以消费你提供的数据。
 * - 既是生产者也是消费者：别使用泛型通配符
 */
public class GenericTest {

    /**
     * 协变<? extends T>，类型的上界是 T，参数化类型可能是 T 或 T 的子类
     * <p>
     * List<? extends Fruit> fruitsList1 = new ArrayList<>()
     * - 写入：因为集合fruitsList1中装的元素类型为Fruit或Fruit子类，直觉告诉我们，往fruitsList1中添加一个Fruit类型对象或其子类对象是可行的
     * fruitsList1.add(new Fruit());   // 编译不通过
     * fruitsList1.add(new Apple());   // 编译不通过
     * 结果是编译都不通过，为什么？因为<? extends Fruit>只是告诉编译器集合中元素的类型上限，但它具体是什么类型编译器是不知道的，
     * List<? extends Fruit> fruitsList1可以指向ArrayList<Fruit>，也可以指向ArrayList<Apple>、ArrayList<Orange>，也就是说
     * 它的类型是不确定的，既然是不确定的，为了类型安全，编译器只能阻止添加元素了。举个例子，当你添加一个Apple时，但fruitsList1此时指向
     * ArrayList<Banana>，显然类型就不兼容了。当然null除外，因为它可以表示任何类型。
     * <p>
     * - 读取：无论fruitsList1指向什么，编译器都可以确定获取的元素是Fruit类型，所有读取集合中的元素是允许的
     * Fruit fruit = appleList1.get(0);    // 编译通过
     * 补充：<?>是<? extends Object>的简写
     */
    @Test
    public void covariantTest() {
        List<? extends Fruit> fruitsList1 = new ArrayList<>();
        List<? extends Fruit> fruitsList2 = new ArrayList<Fruit>();
        List<? extends Fruit> fruitsList3 = new ArrayList<Apple>();

        /*List<? extends Fruit> fruitsList4 = new ArrayList<Object>();

        fruitsList1.add(new Fruit());
        fruitsList1.add(new Apple());

        fruitsList2.add(new Fruit());
        fruitsList2.add(new Apple());

        fruitsList3.add(new Fruit());
        fruitsList3.add(new Apple());*/

        Fruit fruit1 = fruitsList1.get(0);
        Fruit fruit2 = fruitsList2.get(0);
        Fruit fruit3 = fruitsList3.get(0);
    }


    /**
     * 逆变<? super T>，表示类型的下界是 T，参数化类型可以是 T 或 T 的超类。
     * <p>
     * List<? super Apple> appleList1 = new ArrayList<>();
     * - 写入：因为appleList1中装的元素是Apple或Apple的某个父类，我们无法确定是哪个具体类型，但是可以确定的是Apple和Apple的子类是和
     * 这个“不确定的类”兼容的，因为它肯定是这个“不确定类型”的子类，也就是说我们可以往集合中添加Apple或者Apple子类的对象，所以对于下面的
     * 添加是允许的：
     * appleList1.add(new Apple());
     * appleList1.add(new RedApple());
     * 它无法添加Apple的任何父类对象，举个例子，当你往appleList1中添加一个Fruit类型对象时，但此时appleList1指向ArrayList<Apple>，
     * 显然类型就不兼容了。
     * appleList1.add(new Fruit());    // 编译不通过
     * <p>
     * - 读取：编译器允许从appleList1中获取元素的，但是无法确定的获取的元素具体是什么类型，只能确定一定是Object类型的子类，因此我们想
     * 获得存储进去的对应类型的元素就只能进行强制类型转换了。
     * Object object1 = appleList1.get(0);    // 获取的元素为Object类型
     */
    @Test
    public void inverterTest() {
        // Apple extends Fruit   f(Fruit) extends f(Apple)
        List<? super Apple> appleList1 = new ArrayList<>();
        List<? super Apple> appleList2 = new ArrayList<Apple>();
        List<? super Apple> appleList3 = new ArrayList<Fruit>();
        List<? super Apple> appleList4 = new ArrayList<Object>();

        /*List<? super Apple> appleList4 = new ArrayList<RedApple>();

        appleList1.add(new Fruit());

        appleList2.add(new Fruit());

        appleList3.add(new Fruit());

        appleList4.add(new Fruit());*/

        appleList1.add(new Apple());
        appleList1.add(new RedApple());

        appleList2.add(new Apple());
        appleList2.add(new RedApple());

        appleList3.add(new Apple());
        appleList3.add(new RedApple());

        appleList4.add(new Apple());
        appleList4.add(new RedApple());

        Object object1 = appleList1.get(0);
        Object object2 = appleList2.get(0);
        Object object3 = appleList3.get(0);
        Object object4 = appleList4.get(0);
    }
}