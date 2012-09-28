/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.taskservice;

import com.abiquo.taskservice.exception.TaskServiceException;

/**
 * The TaskService.
 * <p>
 * Provides generic functionality to schedule and manage periodical tasks.
 * 
 * @author ibarrera
 */
public interface TaskService
{
    /**
     * Schedules a new task.
     * 
     * @param taskClass The task to schedule.
     * @throws TaskServiceException If task cannot be scheduled.
     */
    public void schedule(final Class< ? > taskClass) throws TaskServiceException;

    /**
     * Schedule a new task that overrides the minutes of its annotation. This is useful when we need
     * to establish a schedule from a System Property.
     * 
     * @param taskClass The task to schedule.
     * @param minutes Minutes to schedule the task.
     * @throws TaskServiceException If task cannot be scheduled.
     */
    public void schedule(final Class< ? > taskClass, int minutes) throws TaskServiceException;

    /**
     * Finds and schedules all task classes.
     * <p>
     * This method scans all classes annotated with {@link Task} annotation and schedules them.
     * 
     * @throws TaskServiceException If tasks cannot be found and scheduled.
     */
    public void scheduleAll() throws TaskServiceException;

    /**
     * Removes a task from the {@link TaskService}.
     * 
     * @param taskClass The task to unschedule.
     * @throws TaskServiceException If task cannot be unscheduled.
     */
    public void unschedule(final Class< ? > taskClass) throws TaskServiceException;

    /**
     * Shuts down the service and stops all tasks.
     * 
     * @throws TaskServiceException If an error occurs qhile shutting down the service.
     */
    public void shutdown() throws TaskServiceException;
}
