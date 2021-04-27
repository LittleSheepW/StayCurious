package com.ww.temp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

/**
 * @author: Sun
 * @create: 2021-04-27 15:20
 * @version: v1.0
 */
public class CloneTest {

    @Test
    public void test() {
        Student student1 = new Student(1, "sun");


        try {
            Student student2 = student1.clone();

            System.out.println(student1 == student2);
            System.out.println(student1.getId() == student2.getId());
            System.out.println(student1.getName() == student2.getName());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Student implements Cloneable {

    private Integer id;
    private String name;

    @Override
    protected Student clone() throws CloneNotSupportedException {
        return (Student) super.clone();
    }
}
