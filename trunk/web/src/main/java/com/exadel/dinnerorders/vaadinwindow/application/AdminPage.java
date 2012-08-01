package com.exadel.dinnerorders.vaadinwindow.application;

import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.service.ExportService;
import com.exadel.dinnerorders.service.UserService;
import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Window;

import java.io.File;
import java.util.ArrayList;

/**
 * User: Dima Shulgin
 * Date: 23.07.12
 */
public class AdminPage extends com.vaadin.Application  {
    private Window mainWindow;

    private PagedTable table;
    private IndexedContainer container;


    @Override
    public void init() {

        createPagedTable();
        createWindow();

        //    Main m = new Main(new File("D:/hello.xls"), AdminPage.this);
        //   m.getStream();
        //   AdminPage.this.getMainWindow().open(m);
        Button button = new Button("Download Excel");
        button.addListener(new Button.ClickListener() {

            public void buttonClick(Button.ClickEvent event) {
                final FileResource stream = new FileResource(new File(""),
                        AdminPage.this) {
                    @Override
                    public DownloadStream getStream() {
                        DownloadStream downloadStream = null;
                        try {
                            downloadStream = new DownloadStream(ExportService.getUsersExcel(User.class),"application/vnd.ms-excel","users.xls");
                        } catch (NoSuchFieldException e) {

                        } catch (IllegalAccessException e) {

                        } catch (InstantiationException e) {

                        }
                        return downloadStream;
                    }
                };

                mainWindow.open(stream);

            }

        });
        mainWindow.addComponent(button);


    }

    private void createPagedTable() {
        ArrayList<User> users = (ArrayList<User>) UserService.loadAllUsersFromDB();
        final Role[] roles = Role.values();
        NativeSelect nativeSelect;

        int size = users.size();
        container = new IndexedContainer();
        container.addContainerProperty("ID", Long.class, null);
        container.addContainerProperty("Name", String.class, null);
        container.addContainerProperty("Role", NativeSelect.class, null);

        for (int i = 0; i < size; ++i) {
            Item item = container.addItem(i);
            item.getItemProperty("ID").setValue(users.get(i).getId());
            item.getItemProperty("Name").setValue(users.get(i).getUserName());
            nativeSelect = new NativeSelect("role");
            for (int j = 0; j < roles.length; ++j) {
                nativeSelect.addItem(roles[j]);
            }
            nativeSelect.setNullSelectionAllowed(false);
            nativeSelect.setValue(users.get(i).getRole());
            nativeSelect.setImmediate(true);
            item.getItemProperty("Role").setValue(nativeSelect);

        }
        table = new PagedTable("Users");
        table.setContainerDataSource(container);
        table.setPageLength(15);
        table.setWidth("550");

    }

    private void createWindow() {

        mainWindow = new Window("Users");

        mainWindow.addComponent(table);
        mainWindow.addComponent(table.createControls());
        setMainWindow(mainWindow);

    }
}
