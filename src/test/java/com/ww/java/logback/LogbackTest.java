package com.ww.java.logback;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Sun
 * @create: 2020-12-15 14:38
 * @version: v1.0
 */
public class LogbackTest {

    private final Logger logger = LoggerFactory.getLogger(LogbackTest.class);

    @Test
    public void printLogTest() {
        logger.trace("=====trace=====");
        logger.debug("=====debug=====");
        logger.info("=====info=====");
        logger.warn("=====warn=====");
        logger.error("=====error=====");
    }
}