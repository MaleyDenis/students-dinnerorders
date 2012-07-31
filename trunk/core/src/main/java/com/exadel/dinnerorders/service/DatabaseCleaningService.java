package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.entity.SystemResource;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.concurrent.*;

public class DatabaseCleaningService {
    public static final int NUMBER_OF_THREADS = 1;
    private long startDelay;
    private long intervalDelay;
    private TimeUnit unit;
    private long elapsedTime;
    private ScheduledExecutorService service;
    private ClearTableTask clearTableTask;
    private final Phaser phaser;

    public DatabaseCleaningService() {
        phaser = new Phaser(NUMBER_OF_THREADS);
        this.startDelay = Long.parseLong(Configuration.getProperty(SystemResource.DELETION_SERVICE_START_DELAY));
        this.intervalDelay = Long.parseLong(Configuration.getProperty(SystemResource.DELETION_SERVICE_INTERVAL_DELAY));
        unit = TimeUnit.valueOf(Configuration.getProperty(SystemResource.TIME_UNIT));
        elapsedTime = Long.parseLong(Configuration.getProperty(SystemResource.ELAPSED_TIME));
        service = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        clearTableTask = new ClearTableTask(phaser);
        service.scheduleAtFixedRate(clearTableTask, startDelay, intervalDelay, unit);
    }

    public void stop() {
        service.shutdown();
    }

    public void setStartDelay(long startDelay) {
        this.startDelay = startDelay;
    }

    private boolean clearMenuTable() {
        Timestamp deletionDate = DateUtils.getCurrentMondayTime();
        deletionDate.setTime(deletionDate.getTime() - elapsedTime);
        Collection<Menu> menus = MenuService.findMenuByDate(deletionDate);
        if (menus == null) {
            return true;
        }
        for (Menu menu : menus) {
            if (!MenuService.delete(menu)) {
                return false;
            }
        }
        return true;
    }

    private boolean clearOrderTable() {
        Timestamp deletionDate = DateUtils.getCurrentMondayTime();
        deletionDate.setTime(deletionDate.getTime() - elapsedTime);
        Order order = OrderService.findOrderByDate(deletionDate);
        return order == null || OrderService.deleteOrder(order);
    }

    public void setIntervalDelay(long intervalDelay) {
        this.intervalDelay = intervalDelay;
        service.shutdownNow();
        clearTableTask = new ClearTableTask(phaser);
        service.scheduleAtFixedRate(clearTableTask, startDelay, intervalDelay, unit);
    }

    public int waitCertainLaunch(int nLaunch) {
        return phaser.awaitAdvance(nLaunch);
    }

    private class ClearTableTask implements Runnable {
        private final Phaser phaser;

        public ClearTableTask(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            clearOrderTable();
            clearMenuTable();
            phaser.arrive();
        }
    }
}
