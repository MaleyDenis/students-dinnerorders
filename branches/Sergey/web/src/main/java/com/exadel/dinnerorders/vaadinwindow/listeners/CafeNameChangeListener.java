package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.service.MenuService;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.AddDishEvent;
import com.exadel.dinnerorders.vaadinwindow.layouts.DishDescriptionRow;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.DayMenuPanel;
import com.exadel.dinnerorders.vaadinwindow.layouts.panels.MenuCreationPanel;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Select;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CafeNameChangeListener implements Component.Listener {
    private MenuCreationPanel panel;
    public CafeNameChangeListener(MenuCreationPanel menuCreationPanel) {
        panel = menuCreationPanel;
    }

    @Override
    public void componentEvent(Component.Event event) {
        String cafename = (String)((Select)event.getSource()).getValue();
        if (cafename != null && panel.getDateBox().getValue() != null) {
            Timestamp menuDate = getMenuDate();
            getMenuByCafeName(cafename, menuDate);
        } else if (cafename == null) {
            panel.flushDayPanels();
        }
    }

    private void getMenuByCafeName(String cafename, Timestamp menuDate) {
        Menu menu = MenuService.findMenuByDateAndCafename(cafename, menuDate);
        if (menu != null) {
            Map<Weekday, List<DishDescriptionRow>> rows = parseByDays(menu);
            postDishesOnScreen(rows);
        } else {
            panel.flushDayPanels();
        }
    }

    private void postDishesOnScreen(Map<Weekday, List<DishDescriptionRow>> rows) {
        int startRow = 1;
        for (int i = 1; i <= DateUtils.DEFAULT_WORK_DAYS; i++) {
            DayMenuPanel dayMenuPanel = (DayMenuPanel)((GridLayout) panel.getContent()).getComponent(0, startRow);
            dayMenuPanel.flush();
            List<DishDescriptionRow> rowList = rows.get(Weekday.getWeekday(i));
            for (DishDescriptionRow dishRow : rowList) {
                dishRow.setParent(dayMenuPanel);
                AddDishEvent addDishEvent = new AddDishEvent(dishRow);
                addDishEvent.setAddedDish(dishRow);
                Application.getInstance().getEventBus().post(addDishEvent);
            }
            startRow += 2;
        }
    }

    private Map<Weekday, List<DishDescriptionRow>> parseByDays(Menu menu) {
        Map<Weekday, List<DishDescriptionRow>> rows = new HashMap<Weekday, List<DishDescriptionRow>>();
        for (List<MenuItem> menuItems:menu.getItems().values()) {
            for (MenuItem menuItem : menuItems) {
                DishDescriptionRow row = new DishDescriptionRow();
                row.getDishName().setValue(menuItem.getDescription());
                row.getCost().setValue(menuItem.getCost().toString());
                List<DishDescriptionRow> weekdayRows = rows.get(menuItem.getWeekday());
                if (weekdayRows == null) {
                    weekdayRows = new ArrayList<DishDescriptionRow>();
                }
                weekdayRows.add(row);
                rows.put(menuItem.getWeekday(), weekdayRows);
            }
        }
        return rows;
    }

    private Timestamp getMenuDate() {
        if (panel.getDateBox().getValue().toString().contains("Current week")) {
            return DateUtils.getCurrentMondayTime();
        } else if (panel.getDateBox().getValue().toString().contains("Next week")) {
            return DateUtils.getNextMondayTime();
        } else return DateUtils.getCurrentTime();
    }
}
