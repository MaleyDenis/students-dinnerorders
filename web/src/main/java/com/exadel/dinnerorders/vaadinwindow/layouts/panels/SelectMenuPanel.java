package com.exadel.dinnerorders.vaadinwindow.layouts.panels;

import com.exadel.dinnerorders.dao.MenuItemDAO;
import com.exadel.dinnerorders.entity.*;
import com.exadel.dinnerorders.service.DateUtils;
import com.exadel.dinnerorders.service.OrderService;
import com.exadel.dinnerorders.service.UserService;
import com.exadel.dinnerorders.vaadinwindow.application.Application;
import com.exadel.dinnerorders.vaadinwindow.events.ShowOrderUserEvent;
import com.exadel.dinnerorders.vaadinwindow.listeners.UserOrderButtonListener;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SelectMenuPanel extends Panel {

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
    private List<DayMenusPanel>currentWeekMenusPanels;
    private String dateStartWeek;
    private String dateEndWeek;
    private final Application application;


    public SelectMenuPanel(Collection<Menu> menuCollection, String dateStartWeek, String dateEndWeek, Application application){
        this.menuCollection = menuCollection;
        this.dateStartWeek = dateStartWeek;
        this.dateEndWeek = dateEndWeek;
        this.application = application;
        tabSheet = new TabSheet();
        currentWeekMenusPanels = new ArrayList<DayMenusPanel>();
        locateComponents();

        this.application.getEventBus().register(this);


    }

    public void initComponents(){
        gridLayout = new GridLayout(DEFAULT_COLUMNS,DEFAULT_ROWS);
        gridLayout.setWidth(100, UNITS_PERCENTAGE);
        gridLayout.setHeight(100, UNITS_PERCENTAGE);

    }

    public void locateComponents(){
        sendOrderButton = new Button("Make order");
        for (Menu menu: menuCollection) {
            tabSheet.addTab(addPanelMenu(menu), menu.getCafeName());
        }

        addComponent(new Label("Monday: "+dateStartWeek));
        addComponent(new Label("Friday: "+dateEndWeek));
        addComponent(tabSheet);
        addComponent(sendOrderButton);
        sendOrderButton.addListener(new UserOrderButtonListener(application));
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
        panel.setStyleName("");
        initComponents();
        for(int i=1; i<=NUMBER_OF_SERVICE_DAYS; i++){
            weekdayLabel = new Label(Weekday.getWeekday(i).toString());
            gridLayout.addComponent(locateLayoutWeek(weekdayLabel));
            for(MenuItem menuItem: menu.getItems().get(Weekday.getWeekday(i))){
                DayMenusPanel showDayMenusPanel = new DayMenusPanel(menuItem.getDescription(),menuItem.getCost());
                showDayMenusPanel.setId(menuItem.getId());
                showDayMenusPanel.setWeekday(menuItem.getWeekday());
                currentWeekMenusPanels.add(showDayMenusPanel);
                gridLayout.addComponent(showDayMenusPanel);
                panel.addComponent(gridLayout);
            }
        }
        return panel;
    }

    public MenuItem checkIDMenuItem(Long id){
        MenuItemDAO menuDAO = new MenuItemDAO();
        Collection<MenuItem>menuItemCollection = menuDAO.loadAll();
        for(MenuItem menuItem:menuItemCollection){
            if(menuItem.getId().equals(id)){
               return menuItem;
            }
        }
        return  null;
    }

    @Subscribe
    public void saveMenuUser(ShowOrderUserEvent showOrderUserEvent){
        if (this.getContent() != showOrderUserEvent.getParent()) {
             return;
        }
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        for (DayMenusPanel menusPanel : currentWeekMenusPanels){
            if(menusPanel.isSelected()){
                menuItems.add(checkIDMenuItem(menusPanel.getId()));
            }
        }
        Timestamp date = DateUtils.getCurrentTime();
        Double cost = OrderService.getCostOrder(menuItems);
        User user = UserService.findUserByUserName(application.getUser().getUserName());
        Order order = new Order(null, user.getId(), cost, date, date);
        order.setMenuItemList(menuItems);
        application.setOrder(order);

    }
}
