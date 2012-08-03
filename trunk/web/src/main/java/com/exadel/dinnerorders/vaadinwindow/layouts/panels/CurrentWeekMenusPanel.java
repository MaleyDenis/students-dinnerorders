package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.MenuItem;
import com.exadel.dinnerorders.entity.Weekday;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowMadeOrderEvent;
import com.exadel.dinnerorders.vaadinwindow.listeners.SendOrderButtonListener;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CurrentWeekMenusPanel extends Panel {

    public static final int NUMBER_OF_SERVICE_DAYS = 5;
    public static final int DEFAULT_COLUMNS = 1;
    public static final int DEFAULT_ROWS = 12;
    private TabSheet tabSheet;
    private Panel panel;
    private GridLayout gridLayout;
    private Collection<Menu> menuCollection;
    private Label weekdayLabel;
    private Button sendOrderButton;
    private GridLayout layoutWeek;
    private List<ShowDayMenusPanel>currentWeekMenusPanels;


    public CurrentWeekMenusPanel(Collection<Menu> menuCollection){
        this.menuCollection = menuCollection;
        tabSheet = new TabSheet();
        currentWeekMenusPanels = new ArrayList<ShowDayMenusPanel>();
        locateComponents();

        Application.getInstance().getEventBus().register(this);

    }

    public void initComponents(){
        gridLayout = new GridLayout(DEFAULT_COLUMNS,DEFAULT_ROWS);
        gridLayout.setWidth(100, UNITS_PERCENTAGE);
        gridLayout.setHeight(100, UNITS_PERCENTAGE);

    }

    public void locateComponents(){
        sendOrderButton = new Button("Make order");
        for (Menu menu: menuCollection){
            tabSheet.addTab(addPanelMenu(menu),menu.getCafeName());
        }
        addComponent(tabSheet);
        addComponent(sendOrderButton);
        sendOrderButton.addListener(new SendOrderButtonListener());
    }

    private GridLayout locateLayoutWeek(Label label){
        layoutWeek = new GridLayout();
        label.setWidth(40, UNITS_PIXELS);
        layoutWeek.setWidth(100, UNITS_PERCENTAGE);
        layoutWeek.setHeight(40, UNITS_PIXELS);
        layoutWeek.setComponentAlignment(label,Alignment.MIDDLE_CENTER);
        layoutWeek.addComponent(label);
        return layoutWeek;
    }

    private Panel addPanelMenu(Menu menu){
        panel = new Panel();
        initComponents();
        for(int i=1; i<=NUMBER_OF_SERVICE_DAYS; i++){
            weekdayLabel = new Label(Weekday.getWeekday(i).toString());
            gridLayout.addComponent(locateLayoutWeek(weekdayLabel));
            for(MenuItem menuItem: menu.getItems().get(Weekday.getWeekday(i))){
                ShowDayMenusPanel showDayMenusPanel = new ShowDayMenusPanel(menuItem.getDescription(),menuItem.getCost());
                showDayMenusPanel.setId(menuItem.getId());
                showDayMenusPanel.setWeekday(menuItem.getWeekday());
                currentWeekMenusPanels.add(showDayMenusPanel);
                gridLayout.addComponent(showDayMenusPanel);
                panel.addComponent(gridLayout);
            }
        }
        return panel;
    }

    private MenuItem checkIDMenuItem (Long id){
        MenuItemDAO menuDAO = new MenuItemDAO();
        Collection<MenuItem>menuItemCollection = menuDAO.loadAll();
        for(MenuItem menuItem:menuItemCollection){
            if(menuItem.getId() == id){
               return menuItem;
            }
        }
        return  null;
    }

    @Subscribe
    public void saveOrder(ShowMadeOrderEvent showMadeOrderEvent){
        int i=1;
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        List <MenuItem> menuItems1 = new ArrayList<MenuItem>();
        for (ShowDayMenusPanel menusPanel:currentWeekMenusPanels){
            if(menusPanel.isSelected()){

            }
        }
    }


}
