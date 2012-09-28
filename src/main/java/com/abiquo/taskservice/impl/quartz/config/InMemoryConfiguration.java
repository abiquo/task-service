/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.taskservice.impl.quartz.config;

import java.util.Properties;

import com.abiquo.taskservice.TaskService;

/**
 * In Memory configuration of the {@link TaskService}.
 * <p>
 * This class is used to provide hard-coded configuration in order to avoid users altering task
 * scheduling.
 * 
 * @author ibarrera
 */
public class InMemoryConfiguration implements QuartzConfiguration
{

    @Override
    public Properties getConfiguration()
    {
        Properties prop = new Properties();

        prop.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "1");
        prop.put("org.quartz.scheduler.instanceId", "1");
        prop.put("org.quartz.scheduler.instanceName", "InMemoryTaskService");
        prop.put("org.quartz.scheduler.rmi.export", "false");
        prop.put("org.quartz.scheduler.rmi.proxy", "false");

        return prop;
    }

}
