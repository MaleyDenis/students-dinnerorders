package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.ParameterContainer;
import com.exadel.dinnerorders.entity.tasks.Task;
import org.apache.log4j.Logger;

import java.util.Scanner;
import java.util.regex.Pattern;

public class TasksFactory {
    private static Logger logger = Logger.getLogger(TasksFactory.class);

    public static Task createTask(String taskDescription) {
        if (!checkString(taskDescription)) {
            throw new IllegalArgumentException();
        } else {
            Scanner scanner  = new Scanner(taskDescription);
            ParameterContainer parameterContainer = new ParameterContainer(scanner);
            return createAccordingTask(parameterContainer);
        }
    }

    private static Task createAccordingTask(ParameterContainer parameterContainer) {
        Task task = null;
        try {
            task = (Task)TasksFactory.class.getClassLoader().loadClass(parameterContainer.getClassName()).newInstance();
            task.setMinutes(parameterContainer.getMinutes());
            task.setHours(parameterContainer.getHours());
            task.setDayOfMonth(parameterContainer.getDaysOfMonth());
            task.setDayOfWeek(parameterContainer.getDayOfWeek());
            task.setMonth(parameterContainer.getMonth());
        } catch (ClassNotFoundException cnfException) {
            logger.error("TasksFactory: Class was not found", cnfException);
        } catch (IllegalAccessException iaException) {
            logger.error("TasksFactory: Illegal access", iaException);
        } catch (InstantiationException iException) {
            logger.error("TasksFactory: Instantiation exception", iException);
        }
        return task;
    }

    private static boolean checkString(String taskDescription) {
        boolean result;
        try {
            Scanner scanner = new Scanner(taskDescription);
            result = Pattern.matches("\\*|\\d|[1-5][0-9]", scanner.next());
            result = result && Pattern.matches("\\*|\\d|1\\d|2[0-3]", scanner.next());
            result = result && Pattern.matches("\\*|[1-9]|[1-2]\\d|3[0-1]", scanner.next());
            result = result && Pattern.matches("\\*|\\d|1[0-2]", scanner.next());
            result = result && Pattern.matches("\\*|[1-7]", scanner.next());
            result = result && Pattern.matches("[\\S]{1,}", scanner.next());
        } catch (Exception exception) {
            return false;
        }
        return result;
    }
}
