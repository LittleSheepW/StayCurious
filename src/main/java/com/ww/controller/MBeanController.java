package com.ww.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Sun
 * @create: 2021-05-12 14:44
 * @version: v1.0
 */
@RestController
@RequestMapping(value = "/mbean")
public class MBeanController {

    @RequestMapping(value = "/ping")
    public String hello() {
        return "Hello world!";
    }
}
