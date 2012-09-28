/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.taskservice.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.taskservice.TaskService;
import com.abiquo.taskservice.exception.TaskServiceException;
import com.abiquo.taskservice.model.Task;
import com.abiquo.taskservice.utils.AnnotationUtils;

/**
 * Base class with common {@link TaskService} functionality.
 * 
 * @author ibarrera
 */
public abstract class AbstractTaskService implements TaskService
{
    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTaskService.class);

    /**
     * Packages to scan to find tasks.
     */
    public static final String DEFAULT_SCAN_PACKAGE = "com.abiquo";

    @Override
    public void scheduleAll() throws TaskServiceException
    {
        LOGGER.info("Running Task discovery...");

        // Find all task classes
        Set<Class< ? >> taskClasses = null;
        try
        {
            taskClasses =
                AnnotationUtils.findAnnotatedClasses(Task.class, DEFAULT_SCAN_PACKAGE, true);
        }
        catch (Exception ex)
        {
            throw new TaskServiceException("Could not find task classes", ex);
        }

        // Schedule task classes
        if (taskClasses != null && !taskClasses.isEmpty())
        {
            LOGGER.info("Found {} tasks to be scheduled", taskClasses.size());

            for (Class< ? > taskClass : taskClasses)
            {
                schedule(taskClass);
            }
        }
    }
}
