/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.taskservice.impl.executor;

import static com.abiquo.taskservice.utils.TaskUtils.getTaskMethod;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.taskservice.exception.TaskServiceException;

/**
 * A generic executor task.
 * 
 * @author ibarrera
 */
public class ExecutorTask implements Runnable
{
    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorTask.class);

    /**
     * The target task to execute.
     */
    private Class< ? > targetClass;

    /**
     * The target method to execute.
     */
    private Method targetMethod;

    /**
     * Creates a new {@link ExecutorTask} for the given task class.
     * 
     * @throws TaskServiceException If task cannot be created.
     */
    public ExecutorTask(final Class< ? > taskClass) throws TaskServiceException
    {
        super();

        // Get task class and task method to execute
        this.targetClass = taskClass;
        this.targetMethod = getTaskMethod(taskClass);

        // Ensure that Task class can be instantiated
        try
        {
            this.targetClass.newInstance();
        }
        catch (Exception ex)
        {
            throw new TaskServiceException(
                "Could not instantiate task class: " + taskClass.getName(), ex);
        }
    }

    @Override
    public void run()
    {
        try
        {
            Object targetTask = targetClass.newInstance();
            targetMethod.invoke(targetTask);
        }
        catch (Exception ex)
        {
            // TODO: Task exception handling
            LOGGER.error("An error occured while executing task {}", targetClass.getSimpleName());
        }
    }

}
