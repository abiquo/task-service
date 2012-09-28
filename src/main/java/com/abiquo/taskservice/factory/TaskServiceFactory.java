/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.taskservice.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.taskservice.TaskService;
import com.abiquo.taskservice.exception.TaskServiceException;
import com.abiquo.taskservice.impl.executor.ExecutorTaskService;
import com.abiquo.taskservice.impl.quartz.QuartzTaskService;

/**
 * Factory to access the {@link TaskService}.
 * <p>
 * If Quartz library is found in classpath, this factory tries to load by a
 * {@link QuartzTaskService} implementation. Otherwise, a {@link ExecutorTaskService} implementation
 * is returned.
 * 
 * @author ibarrera
 */
public class TaskServiceFactory
{
    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceFactory.class);

    /**
     * The singleton instance of the {@link TaskService}.
     */
    private static TaskService service;

    /**
     * Gets the {@link TaskService}.
     * <p>
     * If Quartz library is found in classpath, this factory tries to load by a
     * {@link QuartzTaskService} implementation. Otherwise, a {@link ExecutorTaskService}
     * implementation is returned.
     * 
     * @return The <code>TaskService</code>.
     * @throws TaskServiceException If the service cannot be initialized.
     */
    public static TaskService getService() throws TaskServiceException
    {
        if (service == null)
        {
            try
            {
                // Check if Quartz library is in classpath
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                cl.loadClass("org.quartz.Scheduler");

                LOGGER.info("Loading Task Service: {}", QuartzTaskService.class.getName());
                service = new QuartzTaskService();
            }
            catch (ClassNotFoundException ex)
            {
                // Quartz library not found
                LOGGER.info("Loading Task Service: {}", ExecutorTaskService.class.getName());
                service = new ExecutorTaskService();
            }
        }

        return service;
    }
}
