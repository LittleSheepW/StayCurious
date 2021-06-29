package com.ww.mbean;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 学习Java MBean
 * <p>
 * 在了解MBean之前首先应该先了解一下JMX；
 * - JMX是什么？
 * JMX是Java Management Extensions，它是一个Java平台的管理和监控接口。
 * - 为什么要搞JMX呢？
 * 1、因为在所有的应用程序中，对运行中的程序进行监控都是非常重要的，Java应用程序也不例外。
 * 2、我们肯定希望知道Java应用程序当前的状态，例如：占用了多少内存，分配了多少内存，当前有多少活动线程，有多少休眠线程等等。为了标准化管理和监控，
 * Java平台使用JMX作为管理和监控的标准接口，任何程序，只要按JMX规范访问这个接口，就可以获取所有管理与监控信息。
 * 3、实际上，常用的运维监控如Zabbix、Nagios等工具对JVM本身的监控都是通过JMX获取的信息。
 * 因为JMX是一个标准接口，不但可以用于管理JVM，还可以管理应用程序自身。下图是JMX的架构：
 *     ┌─────────┐  ┌─────────┐
 *     │jconsole │  │   Web   │
 *     └─────────┘  └─────────┘
 * ┌ ─ ─ ─ ─│─ ─ ─ ─ ─ ─ ┼ ─ ─ ─ ─
 *  JVM     ▼            ▼        │
 * │   ┌─────────┐  ┌─────────┐
 *   ┌─┤Connector├──┤ Adaptor ├─┐ │
 * │ │ └─────────┘  └─────────┘ │
 *   │       MBeanServer        │ │
 * │ │ ┌──────┐┌──────┐┌──────┐ │
 *   └─┤MBean1├┤MBean2├┤MBean3├─┘ │
 * │   └──────┘└──────┘└──────┘
 *  ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ┘
 *
 * JMX把所有被管理的资源都称为MBean（Managed Bean），这些MBean全部由MBeanServer管理，如果要访问MBean，可以通过MBeanServer对外提供的
 * 访问接口，例如通过RMI或HTTP访问。使用JMX不需要安装任何额外组件，也不需要第三方库，因为MBeanServer已经内置在JavaSE标准库中了。JavaSE
 * 还提供了一个jconsole程序，用于通过RMI连接到MBeanServer，这样就可以管理整个Java进程。
 * 除了JVM会把自身的各种资源以MBean注册到JMX中，我们自己的配置、监控信息也可以作为MBean注册到JMX，这样管理程序就可以直接控制我们暴露的MBean。
 * 因此，应用程序使用JMX，只需要两步：
 * 1、编写MBean提供管理接口和监控数据；
 * 2、注册MBean。
 *
 * 我们以实际问题为例，假设我们希望给应用程序添加一个IP黑名单功能，凡是在黑名单中的IP禁止访问，传统的做法是定义一个配置文件，启动的时候读取。
 * 如果要修改黑名单怎么办？修改配置文件，然后重启应用程序。但是每次都重启应用程序实在是太麻烦了，能不能不重启应用程序？可以自己写一个定时读取
 * 配置文件的功能，检测到文件改动时自动重新读取。
 * 上述需求本质上是在应用程序运行期间对参数、配置等进行热更新并要求尽快生效。如果以JMX的方式实现，我们不必自己编写自动重新读取等任何代码，只需
 * 要提供一个符合JMX标准的MBean来存储配置即可。
 *
 * --------------------------------------------------------------------------------------------------------------------
 *
 * 注解@ManagedResource表示这是一个MBean，将要被注册到JMX。objectName指定了这个MBean的名字，通常以company:name=Xxx来分类MBean。
 * 注解@ManagedAttribute指示将给定的bean属性公开为JMX属性，仅当在JavaBean getter或setter上使用时才有效。
 * 注解@ManagedOperation指示将给定方法公开为JMX操作，仅当在不是JavaBean getter或setter的方法上使用时才有效。
 *
 * @author: Sun
 * @create: 2021-05-12 14:20
 * @version: v1.0
 */
@Component
@ManagedResource(objectName = "sample:name=blacklist", description = "Blacklist of IP addresses")
public class BlacklistMBean {
    private Set<String> ips = new HashSet<>();

    @ManagedAttribute(description = "Get IP addresses in blacklist")
    public Set<String> getBlacklist() {
        return ips;
    }

    @ManagedOperation
    @ManagedOperationParameter(name = "ip", description = "Target IP address that will be added to blacklist")
    public void addBlacklist(String ip) {
        ips.add(ip);
    }

    @ManagedOperation
    @ManagedOperationParameter(name = "ip", description = "Target IP address that will be removed from blacklist")
    public void removeBlacklist(String ip) {
        ips.remove(ip);
    }

    public boolean shouldBlock(String ip) {
        return ips.contains(ip);
    }
}
