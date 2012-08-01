package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.cache.MenuItemCache;
import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.MenuItem;

import org.apache.log4j.Logger;

/**
 * User: Василий Силин
 * Date: 1.8.12
 */

public class MenuItemService {
    private Logger logger = Logger.getLogger(MenuItemService.class);
    private static MenuItemDAO menuItemDAO = new MenuItemDAO();

    public static boolean save(MenuItem newMenuItem){
        return menuItemDAO.create(newMenuItem);
    }

    public static MenuItem load(Long id){
        return MenuItemCache.getInstance().get(id);
    }

    public static boolean delete(MenuItem menu) {
        return menuItemDAO.delete(menu);
    }

    public static boolean update(MenuItem newMenuItem){
        return menuItemDAO.update(newMenuItem);
    }
}
