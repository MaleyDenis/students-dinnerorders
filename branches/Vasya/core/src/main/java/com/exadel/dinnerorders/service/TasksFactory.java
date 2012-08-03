package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.ParameterContainer;
import com.exadel.dinnerorders.entity.tasks.ClearMenuTableTask;
import com.exadel.dinnerorders.entity.tasks.ClearOrderTableTask;
import com.exadel.dinnerorders.entity.tasks.Task;

import java.util.Scanner;
import java.util.regex.Pattern;

public class TasksFactory {

    public static Task createTask(String taskDescription) {
        Pattern pattern = Pattern.compile("[e, u] [\\D|[0-9]]{1,50} [0-9]{1,50} [0-9]{1,50} [D|H|M|S|m|N]");
        if (!pattern.matcher(taskDescription).matches()) {
            throw new IllegalArgumentException();
        } else {
            Scanner scanner  = new Scanner(taskDescription);
            ParameterContainer parameterContainer = new ParameterContainer(scanner);
            return createAccordingTask(parameterContainer);
        }
    }

    private static Task createAccordingTask(ParameterContainer parameterContainer) {
        if (parameterContainer.getTableName().equals("order")) {
            return new ClearOrderTableTask(
                    parameterContainer.getPeriod(),
                    parameterContainer.getReturnTime(),
                    parameterContainer.getUnit(),
                    parameterContainer.getOperation());
        } else if (parameterContainer.getTableName().equals("menu")) {
            return new ClearMenuTableTask(
                    parameterContainer.getPeriod(),
                    parameterContainer.getReturnTime(),
                    parameterContainer.getUnit(),
                    parameterContainer.getOperation());
        }
        return null;
    }
}
