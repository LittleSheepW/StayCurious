package com.ww.java.classLoader;

import org.junit.Test;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author: Sun
 * @create: 2021-03-10 15:33
 * @version: v1.0
 */
public class ClassLoaderTest {

    @Test
    public void classLoadPathTest() {
        System.out.println("启动类加载器加载路径: ");
        URL[] bootstrapUrls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url : bootstrapUrls) {
            System.out.println(url);
        }
        System.out.println("---------------------------------------");
        System.out.println("扩展类加载器加载路径: ");
        URL[] extUrls = ((URLClassLoader) ClassLoader.getSystemClassLoader().getParent()).getURLs();
        for (URL url : extUrls) {
            System.out.println(url);
        }
        System.out.println("---------------------------------------");
        System.out.println("应用类加载器加载路径: ");
        URL[] urls = ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs();
        for (URL url : urls) {
            System.out.println(url);
        }
    }
}
