package com.ww.java.lanuage.annotation;


@TestAnnotation(id = 1, msg = "class")
class UseTestAnnotation {

    @TestAnnotation(id = 2, msg = "field")
    private Integer a;

    @TestAnnotation(id = 3, msg = "method")
    private void useAnnotation() {

    }
}