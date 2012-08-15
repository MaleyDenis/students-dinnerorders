package com.exadel.dinnerorders.entity.tasks;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.service.MenuService;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Collection;

public class ClearMenuTableTask extends Task {
    private final Logger logger = Logger.getLogger(ClearMenuTableTask.class);
    public ClearMenuTableTask() {
        super();
    }

    @Override
    public Boolean call() {
        Boolean result = true;
        if (lastExecutionTime != null) {
            Collection<Menu> menus = MenuService.findMenuBeforeDate(lastExecutionTime);
            result = MenuService.deleteAll(menus);
        }
        lastExecutionTime = new Timestamp(System.currentTimeMillis());
        logger.trace("ClearMenuTableTask: task executed at" + lastExecutionTime + ". Result: " + result);
        return result;
    }
}