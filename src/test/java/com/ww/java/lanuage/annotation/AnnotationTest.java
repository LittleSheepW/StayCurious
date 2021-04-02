package com.ww.java.lanuage.annotation;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author: Sun
 * @create: 2021-01-25 14:08
 * @version: v1.0
 */
public class AnnotationTest {

    /**
     * 如果此元素上存在指定类型的注释，则返回true，否则返回false。 设计此方法主要是为了方便访问标记注释。
     * 此方法返回的真值等效于: getAnnotation(annotationClass) != null
     * 默认方法的主体指定为上面的代码。
     */
    @Test
    public void isAnnotationPresentTest() {
        System.out.println(Runnable.class.isAnnotationPresent(FunctionalInterface.class));
        System.out.println(Runnable.class.isAnnotationPresent(TestAnnotation.class));
    }

    /**
     * 如果存在指定类型的注释，则返回该元素的注释，否则为空。
     */
    @Test
    public void getAnnotationTest() {
        FunctionalInterface annotation1 = UseTestAnnotation.class.getAnnotation(FunctionalInterface.class);
        if (annotation1 != null) {
            System.out.println(annotation1);
        } else {
            System.out.println("UseTestAnnotation类上没有获取到FunctionalInterface对象");
        }


        TestAnnotation annotation2 = UseTestAnnotation.class.getAnnotation(TestAnnotation.class);
        if (annotation2 != null) {
            // 如果获取到的 Annotation 如果不为 null，则就可以调用它们的属性方法了
            System.out.println(annotation2.id());
            System.out.println(annotation2.msg());

            try {
                Field field = UseTestAnnotation.class.getDeclaredField("a");
                field.setAccessible(true);
                TestAnnotation annotation3 = field.getAnnotation(TestAnnotation.class);
                if (annotation3 != null) {
                    System.out.println(annotation3.id());
                    System.out.println(annotation3.msg());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Method method = UseTestAnnotation.class.getDeclaredMethod("useAnnotation");
                method.setAccessible(true);
                TestAnnotation annotation4 = method.getAnnotation(TestAnnotation.class);
                if (annotation4 != null) {
                    System.out.println(annotation4.id());
                    System.out.println(annotation4.msg());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("UseTestAnnotation类上没有获取到UseTestAnnotation对象");
        }
    }

    /**
     * 返回该元素上的注释。如果该元素上没有注释，则返回值是一个长度为0的数组。该方法的调用者可以自由地修改返回的数组；它不会影响返回给其他调用者的数组。
     */
    @Test
    public void getAnnotationsTest() {
        Annotation[] annotations = Runnable.class.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }
}