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
 * Exposes {@link TaskService} configuration strategies.
 * 
 * @author ibarrera
 */
public interface QuartzConfiguration
{
    /**
     * Gets the configuration for the Task Service.
     * 
     * @return The Task Service Configuration.
     */
    public Properties getConfiguration();
}
