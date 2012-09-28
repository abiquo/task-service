/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.taskservice.exception;

import com.abiquo.taskservice.TaskService;

/**
 * Generic exception for the {@link TaskService}.
 * 
 * @author ibarrera
 */
public class TaskServiceException extends Exception
{
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@link TaskService} with the specified message and cause.
     * 
     * @param message The exception message
     * @param cause The exception cause.
     */
    public TaskServiceException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Creates a new {@link TaskService} with the specified message.
     * 
     * @param message The exception message
     */
    public TaskServiceException(final String message)
    {
        super(message);
    }

    /**
     * Creates a new {@link TaskService} with the specified cause.
     * 
     * @param cause The exception cause.
     */
    public TaskServiceException(final Throwable cause)
    {
        super(cause);
    }

}
