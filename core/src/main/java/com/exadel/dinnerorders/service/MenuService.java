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

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * User: Василий Силин
 * Date: 18.7.12
 */

public class MenuService {
    private Logger logger = Logger.getLogger(MenuService.class);

    private static MenuDAO menuDAO = new MenuDAO();

    public static Menu findMenuForNextWeek() {
        return null;
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
        MenuDAO menuDAO = new MenuDAO();
        Collection<Menu> result =  Collections2.filter(menuDAO.loadAll(), predicate);

        if (result.isEmpty() || result.size() > 1) {
            throw new WorkflowException();
        }
        return result.iterator().next();
    }

    public static boolean save(Menu newMenu){
        MenuDAO menuDAO = new MenuDAO();
        menuDAO.create(newMenu);
        MenuItemDAO menuItemDAO = new MenuItemDAO();
        for(List<MenuItem> items : newMenu.getItems().values()){
            for(MenuItem item : items){
                menuItemDAO.create(item);
            }
        }
        return true;
    }
}
