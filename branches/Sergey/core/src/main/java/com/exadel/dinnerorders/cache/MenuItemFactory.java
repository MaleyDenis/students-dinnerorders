package com.exadel.dinnerorders.cache;

import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.MenuItem;

import net.sf.ashkay.CreationException;
import net.sf.ashkay.ObjectFactory;

/**
 * User: Василий Силин
 * Date: 1.8.12
 */

class MenuItemFactory implements ObjectFactory {
    private static MenuItemDAO menuItemDAO = new MenuItemDAO();

    @Override
    public Object createObjectFor(Object key, Object data) throws CreationException {
        if (key.getClass() != Long.class) {
            throw new CreationException("Key is not type Long. Enter correct key.");
        }
        MenuItem menuItem = menuItemDAO.load((Long)key);
        if (menuItem == null) {
            throw new CreationException("No menu item with this key.");
        }
        return menuItem;
    }
}
