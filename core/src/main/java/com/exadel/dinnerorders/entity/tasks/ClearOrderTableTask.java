package com.exadel.dinnerorders.entity.tasks;

import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.service.OrderService;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;

public class ClearOrderTableTask extends Task {
    private final Logger logger = Logger.getLogger(ClearOrderTableTask.class);

    public ClearOrderTableTask() {
    }

    @Override
    public Boolean call() {
        Boolean result = true;
        if (lastExecutionTime != null) {
            Collection<Order> orders = OrderService.findOrderBeforeDate(lastExecutionTime);
            result = OrderService.deleteAll(orders);
        }
        lastExecutionTime = new Timestamp(System.currentTimeMillis());
        logger.trace("ClearOrderTableTask: task executed at" + lastExecutionTime + ". Result: " + result);
        return result;
    }

    @Override
    public boolean isTimeToServe() {
        return isEveryTime() || isAppropriateTime();
    }

    private boolean isAppropriateTime() {
        boolean answer;
        answer = minutes == -1 || DateUtils.getMinutes() == minutes;
        answer = answer && (hours == -1 || DateUtils.getHours() == hours);
        answer = answer && (dayOfMonth == -1 || DateUtils.getDayOfMonth() == dayOfMonth);
        answer = answer && (month == -1 || DateUtils.getMonth() == month);
        answer = answer && (dayOfWeek == -1 || DateUtils.getDayOfWeek() == dayOfWeek);
        return answer && !isAlreadyExecuted();
    }

    private boolean isAlreadyExecuted() {
        if (getLastExecutionTime() == null) {
            return false;
        }
        Calendar lastExecutionCalendar = Calendar.getInstance();
        lastExecutionCalendar.setTime(lastExecutionTime);
        boolean answer;
        answer = DateUtils.getMinutes() == lastExecutionCalendar.get(Calendar.MINUTE);
        answer = answer && DateUtils.getHours() == lastExecutionCalendar.get(Calendar.HOUR);
        answer = answer && DateUtils.getDayOfMonth() == lastExecutionCalendar.get(Calendar.DAY_OF_MONTH);
        answer = answer && DateUtils.getMonth() == lastExecutionCalendar.get(Calendar.MONTH) + 1;
        answer = answer && DateUtils.getDayOfWeek() == lastExecutionCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        return answer;
    }

    private boolean isEveryTime() {
        boolean answer;
        answer = minutes == -1;
        answer = answer && hours == -1;
        answer = answer && dayOfMonth == -1;
        answer = answer && month == -1;
        answer = answer && dayOfWeek == -1;
        return answer;
    }
}