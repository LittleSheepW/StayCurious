package com.ww.temp;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author: Sun
 * @create: 2021-04-21 11:38
 * @version: v1.0
 */
public class Impl implements InterfaceA, InterfaceB{

    @Override
    public void add(int a, int b) {

        try {
            throw new OutOfMemoryError();
        } catch (Error outOfMemoryError) {
            outOfMemoryError.printStackTrace();
        }

    }
}
