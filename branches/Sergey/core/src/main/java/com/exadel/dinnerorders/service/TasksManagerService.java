package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.SystemResource;
import com.exadel.dinnerorders.entity.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TasksManagerService implements Runnable {
    private List<Task> tasks;
    private List<Future<Boolean>> tasksExecutionResult;
    private ScheduledExecutorService executor;
    private ExecutorService taskExecutor;
    private ScheduledFuture sFuture;
    private long period;
    private TimeUnit unit;

    public TasksManagerService() {
        executor = Executors.newSingleThreadScheduledExecutor();
        taskExecutor = Executors.newCachedThreadPool();
        tasks = new ArrayList<Task>();
        tasksExecutionResult = new ArrayList<Future<Boolean>>();
        this.period = Long.parseLong(Configuration.getProperty(SystemResource.DELETION_SERVICE_INTERVAL_DELAY));
        this.unit = TimeUnit.valueOf(Configuration.getProperty(SystemResource.TIME_UNIT));
        loadDefaultTasks();
    }

    private void loadDefaultTasks() {
        addTask(TasksFactory.createTask("1 * 2 8 * com.exadel.dinnerorders.entity.tasks.ClearMenuTableTask"));
        addTask(TasksFactory.createTask("2 * 2 8 * com.exadel.dinnerorders.entity.tasks.ClearOrderTableTask"));
    }

    public void start() {
        sFuture = executor.scheduleAtFixedRate(this, 0, period, unit);
    }

    @Override
    public void run() {
        tasksExecutionResult.clear();
        for (Task task : tasks) {
            if (task.isTimeToServe()) {
                tasksExecutionResult.add(taskExecutor.submit(task));
            }
        }
    }

    public void stop() {
        sFuture.cancel(true);
        executor.shutdownNow();
    }

    public Boolean getLastExecutionResult() {
        Boolean result = true;
        try{
            for (Future<Boolean> fResult : tasksExecutionResult) {
                result = result && fResult.get();
            }
        } catch (ExecutionException eException) {
            result = false;
        } catch (InterruptedException iException) {
            result = false;
        }
        return result;
    }

    public void addTask(Task task) {
        if (task != null) {
            tasks.add(task);
        }
    }

    public List<Task> getTasksList() {
        return tasks;
    }
}
