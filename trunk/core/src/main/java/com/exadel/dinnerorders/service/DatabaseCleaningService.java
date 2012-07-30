package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.entity.SystemResource;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DatabaseCleaningService {
    private long startDelay;
    private long intervalDelay;
    private TimeUnit unit;
    private long elapsedTime;
    private ScheduledExecutorService service;
    private int initialDelay;
    private ClearTableTask clearTableTask;

    public DatabaseCleaningService() {
        this.startDelay = Long.parseLong(Configuration.getProperty(SystemResource.DELETION_SERVICE_START_DELAY));
        this.intervalDelay = Long.parseLong(Configuration.getProperty(SystemResource.DELETION_SERVICE_INTERVAL_DELAY));
        unit = TimeUnit.valueOf(Configuration.getProperty(SystemResource.TIME_UNIT));
        elapsedTime = Long.parseLong(Configuration.getProperty(SystemResource.ELAPSED_TIME));
        service = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        clearTableTask = new ClearTableTask();
        service.scheduleAtFixedRate(clearTableTask, startDelay, intervalDelay, unit);
    }

    public int getInitialDelay() {
        return initialDelay;
    }

    public void stop() {
        service.shutdown();
    }

    public void setStartDelay(long startDelay) {
        this.startDelay = startDelay;
    }

    private class ClearTableTask implements Runnable {
        private boolean alreadyOnceCleared = false;

        @Override
        public void run() {
            clearOrderTable();
            clearMenuTable();
            alreadyOnceCleared = true;
        }

        public boolean isAlreadyOnceCleared() {
            return alreadyOnceCleared;
        }
    }

    private void clearMenuTable() {
        Timestamp deletionDate = DateUtils.getCurrentMondayTime();
        deletionDate.setTime(deletionDate.getTime() - elapsedTime);
        Collection<Menu> menus = MenuService.findMenuByDate(deletionDate);
        if (menus != null) {
            for (Menu menu : menus)
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

    public void setIntervalDelay(long intervalDelay) {
        this.intervalDelay = intervalDelay;
        service.shutdownNow();
        clearTableTask = new ClearTableTask();
        service.scheduleAtFixedRate(clearTableTask, startDelay, intervalDelay, unit);
    }

    public boolean isAlreadyOnceCleared(){
        return clearTableTask.isAlreadyOnceCleared();
    }
}
