package com.exadel.dinnerorders.cache;

import com.exadel.dinnerorders.dao.MenuDAO;
import com.exadel.dinnerorders.entity.Menu;

import net.sf.ashkay.CreationException;
import net.sf.ashkay.ObjectFactory;

/**
 * User: Василий Силин
 * Date: 31.7.12
 */

class MenuFactory implements ObjectFactory {
    private static MenuDAO menuDAO = new MenuDAO();

    @Override
    public Object createObjectFor(Object key, Object data) throws CreationException {
        if (key.getClass() != Long.class) {
            throw new CreationException("Key is not type Long. Enter correct key.");
        }
        Menu menu = menuDAO.load((Long)key);
        if (menu == null) {
            throw new CreationException("No menu with this key.");
        }
        return menu;
    }
}
