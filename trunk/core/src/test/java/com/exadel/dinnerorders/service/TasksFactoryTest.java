package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.Operation;
import com.exadel.dinnerorders.entity.tasks.ClearMenuTableTask;
import com.exadel.dinnerorders.entity.tasks.ClearOrderTableTask;
import com.exadel.dinnerorders.entity.tasks.Task;
import junit.framework.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TasksFactoryTest {
    @Test(expected = IllegalArgumentException.class)
    public void testCreateTaskForException() {
        TasksFactory.createTask("bad description");
    }

    @Test
    public void testCreateTaskForMenuTask() {
        Task gotTask = TasksFactory.createTask("u menu 28 29 M");
        Assert.assertTrue(gotTask instanceof ClearMenuTableTask);
        ClearMenuTableTask cmTask = (ClearMenuTableTask)gotTask;
        Assert.assertEquals(cmTask.getUnits(), TimeUnit.MINUTES);
        Assert.assertEquals(cmTask.getReturnTime(), 29);
        Assert.assertTrue(cmTask.getOperation() == Operation.UPDATE);
        Assert.assertEquals(cmTask.getPeriod(), 28);
    }

    @Test
    public void testCreateTaskForOrderTask() {
        Task gotTask = TasksFactory.createTask("e order 12 25 D");
        Assert.assertTrue(gotTask instanceof ClearOrderTableTask);
        ClearOrderTableTask cmTask = (ClearOrderTableTask)gotTask;
        Assert.assertEquals(cmTask.getUnits(), TimeUnit.DAYS);
        Assert.assertEquals(cmTask.getReturnTime(), 25);
        Assert.assertTrue(cmTask.getOperation() == Operation.ERASE);
        Assert.assertEquals(cmTask.getPeriod(), 12);
    }
}
