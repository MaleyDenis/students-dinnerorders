package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.SystemResource;
import com.exadel.dinnerorders.entity.tasks.Task;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private final Logger logger = Logger.getLogger(TasksManagerService.class);

    public TasksManagerService() {
        executor = Executors.newSingleThreadScheduledExecutor();
        taskExecutor = Executors.newCachedThreadPool();
        tasks = new ArrayList<Task>();
        tasksExecutionResult = new ArrayList<Future<Boolean>>();
        this.period = Long.parseLong(Configuration.getProperty(SystemResource.DELETION_SERVICE_INTERVAL_DELAY));
        this.unit = TimeUnit.valueOf(Configuration.getProperty(SystemResource.TIME_UNIT));
        loadAllTasks();
    }

    private void loadAllTasks() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream("/tasks.txt"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                tasks.add(TasksFactory.createTask(line));
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            logger.error("TaskManager: can't load tasks", ioe);
        }
    }

    public void start() {
        run();
        sFuture = executor.scheduleAtFixedRate(this, period, period, unit);
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
}
