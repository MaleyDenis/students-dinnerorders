package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.entity.SystemResource;

import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DatabaseCleaningService {
    private long startDelay;
    private long intervalDelay;
    private TimeUnit unit;
    private long elapsedTime;
    private ScheduledExecutorService service;

    public DatabaseCleaningService() {
        this.startDelay = Long.parseLong(Configuration.getProperty(SystemResource.DELETION_SERVICE_START_DELAY));
        this.intervalDelay = Long.parseLong(Configuration.getProperty(SystemResource.DELETION_SERVICE_INTERVAL_DELAY));
        unit = TimeUnit.valueOf(Configuration.getProperty(SystemResource.TIME_UNIT));
        elapsedTime = Long.parseLong(Configuration.getProperty(SystemResource.ELAPSED_TIME));
        service = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        service.scheduleAtFixedRate(new ClearTableTask(), startDelay, intervalDelay, unit);
    }

    private class ClearTableTask implements Runnable {
        @Override
        public void run() {
            clearOrderTable();
            clearMenuTable();
        }
    }

    private void clearMenuTable() {
        Timestamp deletionDate = DateUtils.getCurrentMondayTime();
        deletionDate.setTime(deletionDate.getTime() - elapsedTime);
        Menu menu = MenuService.findMenuByDate(deletionDate);
        if (menu != null) {
            MenuService.delete(menu);
        }
    }

    private void clearOrderTable() {
        Timestamp deletionDate = DateUtils.getCurrentMondayTime();
        deletionDate.setTime(deletionDate.getTime() - elapsedTime);
        Order order = OrderService.findOrderByDate(deletionDate);
        if (order != null) {
            OrderService.deleteOrder(order);
        }
    }
}
