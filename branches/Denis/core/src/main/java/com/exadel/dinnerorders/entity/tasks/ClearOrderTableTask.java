package com.exadel.dinnerorders.entity.tasks;

import com.exadel.dinnerorders.entity.Operation;
import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.service.OrderService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class ClearOrderTableTask extends Task {
    private long lastLaunch;
    private long returnTime;

    public ClearOrderTableTask(long period, long returnTime, TimeUnit unit, Operation operation) {
        super();
        this.units = unit;
        this.operation = operation;
        this.period = DateUtils.convertToMillis(period, unit);
        this.returnTime = DateUtils.convertToMillis(returnTime, unit);
        lastLaunch = 0;
    }

    @Override
    public Boolean call() {
        Boolean result = true;
        switch (operation) {
            case UPDATE: {
                Timestamp targetDate = new Timestamp(DateUtils.getCurrentMondayTime().getTime() - returnTime);
                Collection<Order> orders = new ArrayList<Order>();
                //Collection<Order> orders = OrderService.findOrderByDate(targetDate);
                for (Order order : orders) {
                    result = result && OrderService.deleteOrder(order);
                }
            }
            case ERASE: {
                //result = MenuService.erase();
            }
        }
        lastLaunch = System.currentTimeMillis();
        return result;
    }

    @Override
    public boolean isTimeToServe() {
        long curTime = System.currentTimeMillis();
        return lastLaunch + period < curTime;
    }

    public void setReturnTime(long returnTime) {
        this.returnTime = returnTime;
    }

    public long getReturnTime() {

        return returnTime;
    }
}