/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.taskservice.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Configures the class to be a periodical task.
 * 
 * @author ibarrera
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Task
{
    /**
     * The name of the task
     */
    String name() default "";

    /**
     * Cron expression configuring task execution.
     */
    String cron() default "";

    /**
     * Task execution interval.
     */
    int interval() default 0;

    /**
     * Task start delay.
     */
    int startDelay() default 0;

    /**
     * Task time units (minutes or seconds).
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;

}
