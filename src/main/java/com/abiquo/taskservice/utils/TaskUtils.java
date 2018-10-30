/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.taskservice.utils;

import java.lang.reflect.Method;

import com.abiquo.taskservice.exception.TaskServiceException;
import com.abiquo.taskservice.model.Task;
import com.abiquo.taskservice.model.TaskMethod;

/**
 * Utility method to work with periodical tasks.
 * 
 * @author ibarrera
 */
public class TaskUtils
{
    /**
     * Validates if a class can be scheduled.
     * 
     * @param taskClass The class to validate.
     * @throws TaskServiceException If the class cannot be scheduled.
     */
    public static void validateTask(final Class< ? > taskClass) throws TaskServiceException
    {
        // Find task annotation and
        if (!taskClass.isAnnotationPresent(Task.class))
        {
            throw new TaskServiceException("Only classes with Task annotation can be proxied");
        }

        int methodCount = 0;
        for (Method method : taskClass.getMethods())
        {
            if (method.isAnnotationPresent(TaskMethod.class))
            {
                methodCount++;
            }
        }

        if (methodCount != 1)
        {
            throw new TaskServiceException(
                "Task class must have one (and only one) method annotated with TaskMethod annotation");
        }
    }

    /**
     * Gets the method to execute.
     * 
     * @param taskClass The class containing the task method.
     * @return The task method to execute.
     * @throws TaskServiceException If task method is not found.
     */
    public static Method getTaskMethod(final Class< ? > taskClass) throws TaskServiceException
    {
        for (Method method : taskClass.getMethods())
        {
            if (method.isAnnotationPresent(TaskMethod.class))
            {
                return method;
            }
        }

        throw new TaskServiceException(
            "Task class must have one (and only one) method annotated with TaskMethod annotation");
    }

    /**
     * Gets the descriptive name for the task.
     * 
     * @param taskConfig The task configuration.
     * @param defaultName The default name.
     * @param suffix The suffix.
     */
    public static String getName(final Class< ? > taskClass, final String defaultName,
        final String suffix)
    {
        if ("".equals(defaultName))
        {
            return taskClass.getSimpleName() + suffix;
        }
        else
        {
            return defaultName;
        }
    }
}
