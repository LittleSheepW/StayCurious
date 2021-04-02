package com.ww.java.lombok;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author: Sun
 * @create: 2021-01-26 14:07
 * @version: v1.0
 */
@RequiredArgsConstructor(staticName = "setValue")
public class LombokTest {

    @NonNull
    private final Integer value;
}
