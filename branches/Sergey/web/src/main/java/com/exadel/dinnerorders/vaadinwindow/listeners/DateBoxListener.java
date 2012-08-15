package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.service.MenuService;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.MenuCreationPanel;
import com.vaadin.ui.Component;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Select;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DateBoxListener implements Component.Listener {
    @Override
    public void componentEvent(Component.Event event) {
        MenuCreationPanel panel = (MenuCreationPanel)event.getComponent().getParent().getParent();
        String value = (String)((NativeSelect)event.getSource()).getValue();
        panel.flushDayPanels();
        List<String> cafeNames = null;
        if (value == null) {
            panel.getCafeName().removeAllItems();
            return;
        } else if (value.contains("Current week")) {
            panel.setDate(DateUtils.getCurrentMondayTime(), DateUtils.getCurrentFridayTime());
            cafeNames = getCafeNameByDate(DateUtils.getCurrentMondayTime());
        } else if (value.contains("Next week")) {
            panel.setDate(DateUtils.getNextMondayTime(), DateUtils.getNextFridayTime());
            cafeNames = getCafeNameByDate(DateUtils.getCurrentMondayTime());
        }
        updateSelect(panel, cafeNames);
    }

    private void updateSelect(MenuCreationPanel panel, List<String> cafeNames) {
        Select select = panel.getCafeName();
        select.removeAllItems();
        for (String cafename :cafeNames) {
            select.addItem(cafename);
        }
    }

    private List<String> getCafeNameByDate(Timestamp date) {
        Collection<Menu> menus;
        if (date == null) {
            menus = MenuService.loadAll();
        } else {
            menus = MenuService.findMenuByDate(date);
        }
        List<String> cafeNames = new ArrayList<String>();
        for (Menu menu : menus) {
            cafeNames.add(menu.getCafeName());
        }
        return cafeNames;
    }
}
