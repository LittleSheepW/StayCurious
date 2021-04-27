package com.ww.java.lang.classs;

import org.junit.Test;

import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.function.Function;

/**
 * @author: Sun
 * @create: 2020-12-15 17:23
 * @version: v1.0
 */
public class ClassTest {

    /**
     * 使用给定的字符串名称返回与类或接口关联的Class对象。
     * 调用此方法等效于: Class.forName(className，true，currentLoader)，其中currentLoader表示当前类的定义类加载器。
     * 例如，以下代码片段返回名为java.lang.Thread的类的运行时Class描述符: Class t = Class.forName(“java.lang.Thread”)
     * 调用forName(“X”)会导致名为X的类被初始化。
     */
    @Test
    public void forNameTest() {
        try {
            Class<?> classObject = Class.forName("com.ww.java.lang.classs.ClassTest");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 以String形式返回此Class对象表示的实体名称(类，接口，数组类，原始类型或void)。
     * 如果该类对象表示的引用类型不是数组类型，则返回该类的二进制名称，如Java™语言规范所指定。
     * 如果此类对象表示原始类型或void，则返回的名称是一个String，该字符串等于对应于原始类型或void的Java语言关键字。
     * 如果此类对象表示一类数组，则名称的内部形式由元素类型的名称组成，其后是一个或多个代表数组嵌套深度的'['字符。元素类型名称的编码如下:
     * Element Type          Encoding
     * boolean                  Z
     * byte                     B
     * char                     C
     * class or interface     Lclassname;
     * double                   D
     * float                    F
     * int                      I
     * long                     J
     * short                    S
     */
    @Test
    public void getNameTest() {
        // 如果该类对象表示的引用类型不是数组类型，则返回该类的二进制名称，如Java™语言规范所指定
        System.out.println(String.class.getName());
        System.out.println(ClassTest.class.getName());

        System.out.println();

        // 如果此类对象表示原始类型或void，则返回的名称是一个String，该字符串等于对应于原始类型或void的Java语言关键字
        System.out.println(byte.class.getName());
        System.out.println(void.class.getName());

        System.out.println();

        System.out.println(new Function<ClassTest, String>() {
            @Override
            public String apply(ClassTest classTest) {
                return null;
            }
        }.getClass().getCanonicalName());
        System.out.println(((Function<ClassTest, String>) classTest -> null).getClass().getCanonicalName());

        System.out.println();

        // 如果此类对象表示一类数组，则名称的内部形式由元素类型的名称组成，其后是一个或多个代表数组嵌套深度的'['字符
        System.out.println(Object[].class.getName());
        System.out.println(int[][].class.getName());
        System.out.println(Function[].class.getName());
    }

    /**
     * 返回源代码中给定的基础类的简单名称。如果基础类是匿名的，则返回一个空字符串。
     * 数组的简单名称是组件类型的简单名称，后跟“[]”。特别是其组件类型为匿名的数组的简单名称为“[]”。
     */
    @Test
    public void getSimpleNameTest() {
        System.out.println(String.class.getSimpleName());
        System.out.println(ClassTest.class.getSimpleName());

        System.out.println();

        System.out.println(byte.class.getSimpleName());
        System.out.println(void.class.getSimpleName());

        System.out.println();

        // 如果基础类是匿名的，则返回一个空字符串
        System.out.println(new Function<ClassTest, String>() {
            @Override
            public String apply(ClassTest classTest) {
                return null;
            }
        }.getClass().getSimpleName());
        // 注意: 使用lambda表达匿名类时，是有名字的
        System.out.println(((Function<ClassTest, String>) classTest -> null).getClass().getSimpleName());

        System.out.println();

        // 数组的简单名称是组件类型的简单名称，后跟“[]”。特别是其组件类型为匿名的数组的简单名称为“[]”
        System.out.println(Object[].class.getSimpleName());
        System.out.println(int[][].class.getSimpleName());
        System.out.println(Function[].class.getSimpleName());
    }

    /**
     * 返回Java语言规范定义的基础类的规范名称。如果基础类没有规范名称(即，如果它是本地或匿名类或其组件类型没有规范名称的数组)，则返回null。
     * 注意: getName()和getCanonicalName()都可以产生完整类名，getCanonicalName()除内部类和数组外，对大部分类产生的结果与getName()相同。
     */
    @Test
    public void getCanonicalNameTest() {
        System.out.println(String.class.getCanonicalName());
        System.out.println(ClassTest.class.getCanonicalName());

        System.out.println();

        System.out.println(byte.class.getCanonicalName());
        System.out.println(void.class.getCanonicalName());

        System.out.println();

        System.out.println(new Function<ClassTest, String>() {
            @Override
            public String apply(ClassTest classTest) {
                return null;
            }
        }.getClass().getCanonicalName());
        System.out.println(((Function<ClassTest, String>) classTest -> null).getClass().getCanonicalName());

        System.out.println();

        System.out.println(Object[].class.getCanonicalName());
        System.out.println(int[][].class.getCanonicalName());
        System.out.println(Function[].class.getCanonicalName());
    }

    /**
     * 确定由该对象表示的类或接口实现的接口。
     * 如果此对象表示一个类，则返回值是一个包含对象的数组，这些对象表示该类实现的所有接口。数组中接口对象的顺序与该对象表示的类的声明的Implements
     * 子句中接口名称的顺序相对应。例如，给出声明:
     * class Shimmer implements FloorWax, DessertTopping { ... }
     * 假设s的值是Shimmer的一个实例；表达式的值: s.getClass().getInterfaces()[0]是表示接口FloorWax的Class对象；
     * 表达式的值: s.getClass().getInterfaces()[1]是表示接口DessertTopping的Class对象。
     * 如果此对象表示接口，则数组包含表示该接口扩展的所有接口的对象。数组中接口对象的顺序与该对象表示的接口的声明的extends子句中接口名称的顺序相对应。
     * 如果此对象表示未实现任何接口的类或接口，则该方法返回长度为0的数组。
     * 如果此对象表示原始类型或void，则该方法返回长度为0的数组。
     * 如果此Class对象表示数组类型，则按此顺序返回接口Cloneable和java.io.Serializable。
     */
    @Test
    public void getInterfacesTest() {
        // 如果此对象表示一个类，则返回值是一个包含对象的数组，这些对象表示该类实现的所有接口
        System.out.println(Arrays.toString(ArrayList.class.getInterfaces()));

        System.out.println();

        // 如果此对象表示接口，则数组包含表示该接口扩展的所有接口的对象
        System.out.println(Arrays.toString(List.class.getInterfaces()));

        System.out.println();

        // 如果此对象表示未实现任何接口的类或接口，则该方法返回长度为0的数组
        System.out.println(Arrays.toString(ClassTest.class.getInterfaces()));

        System.out.println();

        // 如果此对象表示原始类型或void，则该方法返回长度为0的数组。
        System.out.println(Arrays.toString(byte.class.getInterfaces()));
        System.out.println(Arrays.toString(void.class.getInterfaces()));

        System.out.println();

        // 如果此Class对象表示数组类型，则按此顺序返回接口Cloneable和java.io.Serializable。
        System.out.println(Arrays.toString(Object[].class.getInterfaces()));
        System.out.println(Arrays.toString(int[][].class.getInterfaces()));
    }

    /**
     * 返回表示该类表示的实体(类，接口，原始类型或void)的超类的Class。如果该Class表示Object类，接口，原始类型或void，则返回null。
     * 如果此对象表示数组类，则返回表示Object类的Class对象。
     */
    @Test
    public void getSuperclassTest() {
        // 返回表示该类表示的实体(类，接口，原始类型或void)的超类的Class
        System.out.println(ArrayList.class.getSuperclass());

        System.out.println();

        // 如果该Class表示Object类，接口，原始类型或void，则返回null
        System.out.println(Object.class.getSuperclass());
        System.out.println(byte.class.getSuperclass());
        System.out.println(void.class.getSuperclass());
        System.out.println(Function.class.getSuperclass());

        System.out.println();

        // 如果此对象表示数组类，则返回表示Object类的Class对象
        System.out.println(Object[].class.getSuperclass());
        System.out.println(int[][].class.getSuperclass());
    }

    /**
     * 确定指定的Class对象是否表示接口类型。
     */
    @Test
    public void isInterfaceTest() {
        // class
        System.out.println(ArrayList.class.isInterface());

        // abstract class
        System.out.println(AbstractList.class.isInterface());

        // interface
        System.out.println(List.class.isInterface());
    }

    /**
     * 创建此Class对象表示的类的新实例。就像通过带有空参数列表的new表达式实例化该类一样。如果尚未初始化该类，则将其初始化。
     * 请注意，此方法传播由nullary构造函数引发的所有异常，包括已检查的异常。使用此方法有效地绕过了编译时异常检查，否则该检查将由编译器执行。
     * Constructor.newInstance方法通过将构造函数抛出的所有异常包装在(选中的)InvocationTargetException中来避免此问题。
     */
    @Test
    public void newInstanceTest() {
        try {
            ClassTest classTest = ClassTest.class.newInstance();
            System.out.println(classTest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 确定指定的对象是否与该类所表示的对象赋值兼容。该方法是Java语言instanceof操作符的动态等效项。如果指定的对象参数是非空的，并且可以转换
     * 为这个类对象所表示的引用类型，而不引发ClassCastException，则该方法返回true。否则返回false。
     * 具体来说，如果这个类对象表示一个声明的类，如果指定的对象参数是被表示的类(或它的任何子类)的实例，则该方法返回true;否则返回false。如果
     * 这个类对象表示一个数组类，如果指定的对象参数可以通过标识转换或扩展引用转换转换为数组类的对象，则该方法返回true;否则返回false。如果这个
     * 类对象表示一个接口，如果指定对象参数的类或任何超类实现了这个接口，则该方法返回true;否则返回false。如果这个类对象表示一个原始类型，这个
     * 方法返回false。
     */
    @Test
    public void isInstanceTest() {
        // 如果这个类对象表示一个声明的类，如果指定的对象参数是被表示的类(或它的任何子类)的实例，则该方法返回true;否则返回false
        System.out.println(Object.class.isInstance(new Object()));
        System.out.println(Object.class.isInstance(new ClassTest()));

        System.out.println();

        // 如果这个类对象表示一个数组类，如果指定的对象参数可以通过标识转换或扩展引用转换转换为数组类的对象，则该方法返回true;否则返回false
        System.out.println(Object[].class.isInstance(new Object[]{}));
        System.out.println(Object[].class.isInstance(new ArrayList<>()));

        System.out.println();

        // 如果这个类对象表示一个接口，如果指定对象参数的类或任何超类实现了这个接口，则该方法返回true;否则返回false
        System.out.println(List.class.isInstance(new ArrayList(){}));

        System.out.println();

        // 如果这个类对象表示一个原始类型，这个方法返回false
        System.out.println(void.class.isInstance(new ClassTest()));
    }

    /**
     * 确定由此Class对象表示的类或接口是否与指定的Class参数表示的类或接口相同，或者是该类或接口的超类或超接口。如果是，则返回true；否则返回false。
     * 如果此Class对象表示原始类型，则如果指定的Class参数正是此Class对象，则此方法返回true；否则返回false。
     * 具体来说，此方法测试是否可以通过标识转换或扩展引用转换将指定Class参数表示的类型转换为此Class对象表示的类型。有关详细信息，请参见Java
     * 语言规范的5.1.1和5.1.4节。
     */
    @Test
    public void isAssignableFromTest() {
        // 确定由此Class对象表示的类或接口是否与指定的Class参数表示的类或接口相同，或者是该类或接口的超类或超接口。如果是，则返回true；否则返回false
        System.out.println(ArrayList.class.isAssignableFrom(ArrayList.class));
        System.out.println(AbstractList.class.isAssignableFrom(ArrayList.class));
        System.out.println(List.class.isAssignableFrom(ArrayList.class));

        System.out.println();

        // 如果此Class对象表示原始类型，则如果指定的Class参数正是此Class对象，则此方法返回true；否则返回false
        System.out.println(boolean.class.isAssignableFrom(boolean.class));
        System.out.println(boolean.class.isAssignableFrom(void.class));
        System.out.println(boolean.class.isAssignableFrom(ArrayList.class));
    }

    /**
     * 返回TypeVariable对象的数组，这些对象按声明顺序表示此GenericDeclaration对象表示的泛型声明所声明的类型变量。如果基础泛型声明
     * 未声明任何类型变量，则返回长度为0的数组。
     */
    @Test
    public void getTypeParametersTest() {
        List<String> list = new ArrayList<>();
        TypeVariable<? extends Class<? extends List>>[] listTypeParameters = list.getClass().getTypeParameters();
        System.out.println(Arrays.toString(listTypeParameters));

        Map<Integer, String> map = new HashMap<>();
        TypeVariable<? extends Class<? extends Map>>[] mapTypeParameters = map.getClass().getTypeParameters();
        System.out.println(Arrays.toString(mapTypeParameters));

        Integer integer = new Integer("1");
        TypeVariable<? extends Class<? extends Integer>>[] integerTypeParameters = integer.getClass().getTypeParameters();
        System.out.println(Arrays.toString(integerTypeParameters));
    }
}
