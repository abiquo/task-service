/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.taskservice.impl.executor;

import static com.abiquo.taskservice.utils.TaskUtils.getName;
import static com.abiquo.taskservice.utils.TaskUtils.validateTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.taskservice.TaskService;
import com.abiquo.taskservice.exception.TaskServiceException;
import com.abiquo.taskservice.impl.AbstractTaskService;
import com.abiquo.taskservice.model.Task;

/**
 * Executor implementation of the {@link TaskService}.
 * 
 * @author ibarrera
 */
public class ExecutorTaskService extends AbstractTaskService
{
    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorTaskService.class);

    /**
     * The default size of the thread pool used to schedule tasks.
     */
    private static final int DEFAULT_POOL_SIZE = 5;

    /**
     * The task executor.
     */
    private ScheduledExecutorService scheduler;

    /**
     * All scheduled tasks.
     */
    private Map<String, Future< ? >> scheduledTasks = new HashMap<String, Future< ? >>();

    /**
     * Creates a new {@link ExecutorTaskService}.
     */
    public ExecutorTaskService()
    {
        this(DEFAULT_POOL_SIZE);
    }

    /**
     * Creates a new {@link ExecutorTaskService} using a thread pool of the specified size.
     * 
     * @param poolSize The size of the thread pool used to schedule tasks.
     */
    public ExecutorTaskService(final int poolSize)
    {
        super();
        scheduler = Executors.newScheduledThreadPool(poolSize);
        LOGGER.info("{} started", this.getClass().getSimpleName());
    }

    @Override
    public void schedule(final Class< ? > taskClass) throws TaskServiceException
    {
        // Check if is a valid task class
        validateTask(taskClass);

        // Read task configuration
        Task taskConfig = taskClass.getAnnotation(Task.class);
        String taskName = getName(taskClass, taskConfig.name(), "Task");

        LOGGER.info("Adding task {} to {}", taskName, this.getClass().getSimpleName());

        // Schedule task
        ExecutorTask executorTask = new ExecutorTask(taskClass);

        ScheduledFuture< ? > scheduledTask = scheduler.scheduleWithFixedDelay(executorTask,
            taskConfig.startDelay(), taskConfig.interval(), taskConfig.timeUnit());

        scheduledTasks.put(taskName, scheduledTask);
    }

    @Override
    public void unschedule(final Class< ? > taskClass) throws TaskServiceException
    {
        // Check if is a valid task class
        validateTask(taskClass);

        // Read task configuration
        Task taskConfig = taskClass.getAnnotation(Task.class);
        String taskName = getName(taskClass, taskConfig.name(), "Task");

        LOGGER.info("Removing task {} from {}", taskName, this.getClass().getSimpleName());

        // Unschedule task
        ScheduledFuture< ? > scheduledTask = (ScheduledFuture< ? >) scheduledTasks.get(taskName);

        if (scheduledTask == null)
        {
            throw new TaskServiceException("Task " + taskClass.getName() + " is not scheduled");
        }

        scheduledTask.cancel(false);
    }

    @Override
    public void shutdown() throws TaskServiceException
    {
        LOGGER.info("Shutting down {}", this.getClass().getSimpleName());
        scheduler.shutdown();
    }

    @Override
    public void schedule(final Class< ? > taskClass, final int minutes) throws TaskServiceException
    {
        // Check if is a valid task class
        validateTask(taskClass);

        // Read task configuration
        Task taskConfig = taskClass.getAnnotation(Task.class);
        String taskName = getName(taskClass, taskConfig.name(), "Task");

        LOGGER.info("Adding task {} to {}", taskName, this.getClass().getSimpleName());

        // Schedule task
        ExecutorTask executorTask = new ExecutorTask(taskClass);

        ScheduledFuture< ? > scheduledTask = scheduler.scheduleWithFixedDelay(executorTask,
            taskConfig.startDelay(), minutes, taskConfig.timeUnit());

        scheduledTasks.put(taskName, scheduledTask);
    }
}
