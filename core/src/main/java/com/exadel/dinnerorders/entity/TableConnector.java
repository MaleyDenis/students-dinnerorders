package com.exadel.dinnerorders.entity;

import org.apache.solr.client.solrj.beans.Field;

public class TableConnector {
    @Field
    private String id;
    @Field
    private String menu_id;
    @Field
    private String menuitem_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenuitem_id() {
        return menuitem_id;
    }

    public void setMenuitem_id(String menuitem_id) {
        this.menuitem_id = menuitem_id;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
               "Menu_ID: " + menu_id + "\n" +
               "MenuItem_ID: " + menuitem_id;
    }
}
