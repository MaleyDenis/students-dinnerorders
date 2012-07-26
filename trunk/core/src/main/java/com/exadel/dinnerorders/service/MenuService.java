package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.MenuDAO;
import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.exception.WorkflowException;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.apache.log4j.Logger;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MenuService {
    private Logger logger = Logger.getLogger(MenuService.class);

    private static MenuDAO menuDAO = new MenuDAO();

    public static Menu findMenuForNextWeek() {
        final Timestamp nextMondayDate = DateUtils.getNextMondayTime();
        return findMenuByDate(nextMondayDate);
    }

    public static Menu findMenuForCurrentWeek(){
        final Date date = new Date();
        return findMenuByDate(date);
    }

    public static Menu findMenuByDate(final Date date){
        Predicate<Menu> predicate = new Predicate<Menu>() {
            public boolean apply(@Nullable Menu o) {
                return o != null && o.getDateStart().before(date) && o.getDateEnd().after(date);
            }
        };
        Collection<Menu> result =  Collections2.filter(menuDAO.loadAll(), predicate);

        if (result.size() > 1) {
            throw new WorkflowException();
        }

        if (result.isEmpty()) {
            return null;
        }

        return result.iterator().next();
    }

    public static boolean save(Menu newMenu){
        MenuDAO menuDAO = new MenuDAO();
        MenuItemDAO menuItemDAO = new MenuItemDAO();
        for(List<MenuItem> items : newMenu.getItems().values()){
            for(MenuItem item : items){
                menuItemDAO.create(item);
            }
        }
        menuDAO.create(newMenu);    //create after for-cycle
        return true;
    }
}