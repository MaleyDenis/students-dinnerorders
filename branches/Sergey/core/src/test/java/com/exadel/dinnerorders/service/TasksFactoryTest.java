package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.tasks.ClearMenuTableTask;
import com.exadel.dinnerorders.entity.tasks.ClearOrderTableTask;
import com.exadel.dinnerorders.entity.tasks.Task;
import junit.framework.Assert;
import org.junit.Test;

public class TasksFactoryTest {
    @Test(expected = IllegalArgumentException.class)
    public void testCreateTaskForException() {
        TasksFactory.createTask("bad description");
    }

    @Test
    public void testCreateTaskForMenuTask() {
        Task gotTask = TasksFactory.createTask("59 23 31 12 7 com.exadel.dinnerorders.entity.tasks.ClearMenuTableTask");
        Assert.assertTrue(gotTask instanceof ClearMenuTableTask);
        ClearMenuTableTask cmTask = (ClearMenuTableTask)gotTask;
        Assert.assertEquals(cmTask.getMinutes(), 59);
    }

    @Test
    public void testCreateTaskForOrderTask() {
        Task gotTask = TasksFactory.createTask("59 23 31 12 7 com.exadel.dinnerorders.entity.tasks.ClearOrderTableTask");
        Assert.assertTrue(gotTask instanceof ClearOrderTableTask);
        ClearOrderTableTask coTask = (ClearOrderTableTask)gotTask;
        Assert.assertEquals(coTask.getMinutes(), 59);
    }

    @Test
    public void testCreateTaskForOrderTaskForTemplate() {
        Task gotTask = TasksFactory.createTask("* * 31 2 * com.exadel.dinnerorders.entity.tasks.ClearOrderTableTask");
        Assert.assertTrue(gotTask instanceof ClearOrderTableTask);
        ClearOrderTableTask coTask = (ClearOrderTableTask)gotTask;
        coTask.isTimeToServe();
        coTask.call();
        Assert.assertEquals(coTask.getMinutes(), -1);
        Assert.assertEquals(coTask.getHours(), -1);
        Assert.assertEquals(coTask.getDayOfWeek(), -1);
    }
}
