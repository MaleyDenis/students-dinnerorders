package com.exadel.dinnerorders.entity.tasks;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.Operation;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.service.MenuService;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class ClearMenuTableTask extends Task {
    private long lastLaunch;
    private long returnTime;

    public ClearMenuTableTask(long period, long returnTime, TimeUnit unit, Operation operation) {
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
                Collection<Menu> menus = MenuService.findMenuByDate(targetDate);
                for (Menu menu : menus) {
                    result = result && MenuService.delete(menu);
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

    public long getReturnTime() {
        return returnTime;
    }
}