package com.exadel.dinnerorders.entity.tasks;

import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.service.OrderService;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Collection;

public class ClearOrderTableTask extends Task {
    private final Logger logger = Logger.getLogger(ClearOrderTableTask.class);

    public ClearOrderTableTask() {
        super();
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
}