Task Service
============

Simple project to define scheduled tasks.

### Rationale ###

In Abiquo platform, there are many tasks that need to be executed periodically. Tasks such as sanity checks are important to guarantee platform integrity, and must be executed in background at a given time. To accomplish this, and to have a unified way to define and schedule this kind of tasks, Abiquo provides a *Task Service* with the basic functionality needed to manage them.

### Task Configuration ###

Two annotations are provided to configure periodical tasks:

**@Task** - Is used at class level to configure that class to be a periodical task. It has the following attributes used to configure task scheduling:
* **name** (optional) - The name of the task.
* **interval** - The execution interval.
* **timeUnit** (default is MINUTES) - The time unit in which the interval is expressed (Currently only MINUTES and SECONDS are supported).
* **startDelay** (default is 0) - The delay between task loading and task first execution.
* **cron** (only supported in _Quartz_ implementations) - The cron expression that configures the task.

**@TaskMethod** \- Is used to annotate the method of the class that will be executed by the task. There must be one and only one method annotated as @TaskMethod in a @Task annotated class.

The following are a few examples of Task configurations:

    // Task execution each 10 minutes
    @Task(interval = 10, timeUnit = TimeUnit.MINUTES)
    @Task(cron = "0 0/10 * * * ?")

    // Task execution each 15 seconds and delayed 10 seconds
    @Task(name = "DummyTask", interval = 15, timeUnit = TimeUnit.SECONDS, startDelay = 10)

### Service usage ###

To use the service, to schedule a task, two steps are needed.

First, an instance of the service must be obtained. There are two implementations of the service, one using _Quartz_ as the backend scheduler, and the other one using the Java _ExecutorService_. To ge the appropiate implementation of the service, the TaskServiceFactory must be used. It will check if _Quartz_ library is present in classpath and return the _Quartz_ implementation or the _ExecutorService_ one based on this check. To get an instance of the service, the factory can be invoked like:

    // Let the factory give us an appropiate implementation of the service
    TaskService  taskService = TaskServiceFactory.getService();

Once the instance of the service has been obtained, tasks can be scheduled via annotation discovering or scheduling each task manually as follows:

    // This will discover all @Task annotated classes and add them to the Task Service
    taskService.scheduleAll();

    // This will add DummyClass to the Task Service
    taskService.schedule(DummyTask.class);

### External Resources ###

[Quartz official homepage](http://www.quartz-scheduler.org/)

[Quartz cron configuration](http://www.quartz-scheduler.org/docs/tutorials/crontrigger.html)
