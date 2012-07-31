package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.MenuDAO;
import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import org.apache.log4j.Logger;

import javax.annotation.Nullable;

import java.sql.Timestamp;

import java.util.Collection;
import java.util.List;

/**
 * User: Василий Силин
 * Date: 18.7.12
 */

public class MenuService {
    private Logger logger = Logger.getLogger(MenuService.class);

    private static MenuDAO menuDAO = new MenuDAO();
    private static MenuItemDAO menuItemDAO = new MenuItemDAO();

    public static Collection<Menu> findMenuForNextWeek() {
        Timestamp date = DateUtils.getNextMondayTime();
        return findMenuByDate(date);
    }

    public static Collection<Menu> findMenuForCurrentWeek(){
        Timestamp date = DateUtils.getCurrentMondayTime();
        return findMenuByDate(date);
    }

    public static Collection<Menu> findMenuByDate(final Timestamp date){
        Predicate<Menu> predicate = new Predicate<Menu>() {
            public boolean apply(@Nullable Menu o) {
                return o != null && o.getDateStart().before(date) && o.getDateEnd().after(date);
            }
        };
        MenuDAO menuDAO = new MenuDAO();
        Collection<Menu> result =  Collections2.filter(menuDAO.loadAll(), predicate);

        if (result.isEmpty()) {
            return null;
        }

        return result;
    }

    public static boolean save(Menu newMenu){
        for(List<MenuItem> items : newMenu.getItems().values()){
            for(MenuItem item : items){
                if(!menuItemDAO.create(item)){
                    return false;
                }
            }
        }
        return menuDAO.create(newMenu);
    }

    public static boolean delete(Menu menu) {
        for(List<MenuItem> items : menu.getItems().values()){
            for(MenuItem item : items){
                if(!menuItemDAO.delete(item)){
                    return false;
                }
            }
        }
        return menuDAO.delete(menu);
    }

    public static boolean update(Menu newMenu){
        return menuDAO.update(newMenu);
    }
}
