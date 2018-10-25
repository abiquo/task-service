/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.taskservice.impl.quartz;

import static com.abiquo.taskservice.utils.TaskUtils.getTaskMethod;

import java.lang.reflect.Method;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.abiquo.taskservice.exception.TaskServiceException;

/**
 * Generic implementation of the {@link Job} interface to allow executing any class method as a
 * periodical task.
 * 
 * @author ibarrera
 */
public class QuartzTask implements Job
{
    /**
     * Attibute used to store target task class name.
     */
    public static final String TASK_CLASS_ATTRIBUTE = "taskClass";

    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException
    {
        // Get the target task class
        Class< ? > taskClass =
            (Class< ? >) context.getJobDetail().getJobDataMap().get(TASK_CLASS_ATTRIBUTE);

        Object targetTask = null;
        Method targetMethod = null;

        // Find task method to execute
        try
        {
            targetMethod = getTaskMethod(taskClass);
        }
        catch (TaskServiceException ex)
        {
            throw new JobExecutionException("Could not get task method", ex);
        }

        // Instantiate task object
        try
        {
            targetTask = taskClass.newInstance();
        }
        catch (Exception ex)
        {
            throw new JobExecutionException(
                "Could not instantiate task class: " + taskClass.getName(), ex);
        }

        // Execute task method
        try
        {
            targetMethod.invoke(targetTask);
        }
        catch (Exception ex)
        {
            throw new JobExecutionException(
                "Could not execute task: " + targetTask.getClass().getName(), ex);
        }
    }

}
