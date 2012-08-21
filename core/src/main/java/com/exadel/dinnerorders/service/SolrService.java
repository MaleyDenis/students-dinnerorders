package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.*;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.sun.mail.iap.ConnectionException;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import javax.annotation.Nullable;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SolrService {
    public static final long DEFAULT_SERVER_START_TIME = 5500;
    public static final int DEFAULT_SET_CONNECTION_ATTEMPTS = 5;
    private static SolrServer server;
    private final static Logger logger = Logger.getLogger(SolrService.class);

    private synchronized static SolrServer getConnection() {
        if (server == null) {
            try {
                String url = Configuration.getProperty(SystemResource.SOLR_HOST);
                url += ":" + Configuration.getProperty(SystemResource.SOLR_PORT) + "/solr";
                setConnection(url);
                server = new HttpSolrServer(url);
            } catch (ConnectionException cException) {
                logger.error("SolrServer: connection refused", cException);
            }
        }
        return server;
    }

    private static void setConnection(String url) throws ConnectionException {
        try{
            Runtime cmdExecutor = Runtime.getRuntime();
            cmdExecutor.exec("cmd /c start C:\\start_server.bat");
            boolean noConnection = true;
            for (int i = 0; i < DEFAULT_SET_CONNECTION_ATTEMPTS && noConnection; i++) {
                Thread.sleep(DEFAULT_SERVER_START_TIME);
                try {
                    noConnection = false;
                    SolrServer server = new HttpSolrServer(url);
                    server.commit();
                } catch (SolrServerException ssException) {
                    logger.error("SolrService: Couldn't set connection", ssException);
                    if (i == DEFAULT_SET_CONNECTION_ATTEMPTS - 1) {
                        throw new ConnectionException();
                    }
                    noConnection = true;
                }
            }
        } catch (IOException ioException) {
            logger.error("SolrService: Couldn't startup server", ioException);
        } catch (InterruptedException intException) {
            logger.error("SolrService: Couldn't await server's launch", intException);
        }
    }

    public static boolean reimportDatabase() {
        getConnection();
        deleteAll();
        boolean result = false;
        try {
            SolrRequest request = new UpdateRequest("/dataimport?command=full-import");
            server.request(request);
            server.commit();
            result = true;
        } catch (SolrServerException ssException) {
            ssException.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }

    public static boolean deleteAll() {
        getConnection();
        boolean result = false;
        try {
            server.deleteByQuery("*:*");
            server.commit();
            result = true;
        } catch (Exception e) {
            logger.error("SolrService: Couldn't deleteAll");
        }
        return result;
    }

    public static Collection<Menu> loadAll() {
        getConnection();
        Collection<ProxyMenu> proxyMenus = readMenus();
        List<Menu> menus = new ArrayList<Menu>();
        for (ProxyMenu proxyMenu : proxyMenus ){
            menus.add(proxyMenu.convertToSimpleMenu());
        }
        return menus;
    }

    private static Collection<ProxyMenu> readMenus() {
        SolrQuery getMenusQuery = new SolrQuery("cafename:*");
        Collection<ProxyMenu> loadedProxyMenus = null;
        try {
            QueryResponse queryResult = server.query(getMenusQuery);
            loadedProxyMenus = queryResult.getBeans(ProxyMenu.class);
        } catch (SolrServerException ssException) {
            ssException.printStackTrace();
        }
        if (loadedProxyMenus != null) {
            loadMenusItems(loadedProxyMenus);
        }
        return loadedProxyMenus;
    }

    private static void loadMenusItems(Collection<ProxyMenu> loadedProxyMenus) {
        try {
            for (ProxyMenu proxyMenu : loadedProxyMenus) {
                SolrQuery getItemsIDQuery = new SolrQuery("menu_id:" + proxyMenu.getId());
                QueryResponse response = server.query(getItemsIDQuery);
                Collection<TableConnector> menuItemIds = response.getBeans(TableConnector.class);
                readMenuItemsEntity(menuItemIds, proxyMenu);
            }
        } catch (SolrServerException ssException) {
            ssException.printStackTrace();
        }
    }

    private static void readMenuItemsEntity(Collection<TableConnector> menuItemIds, ProxyMenu proxyMenu) {
        try {
            List<ProxyMenuItem> proxyMenuItems = new ArrayList<ProxyMenuItem>();
            for (TableConnector tableConnector : menuItemIds) {
                SolrQuery query = new SolrQuery("id:" + tableConnector.getMenuitem_id());
                QueryResponse response = server.query(query);
                proxyMenuItems.addAll(response.getBeans(ProxyMenuItem.class));
                for (ProxyMenuItem proxyMenuItem : proxyMenuItems) {
                    proxyMenu.addItem(proxyMenuItem);
                }
                proxyMenuItems.clear();
            }
        } catch (SolrServerException ssException) {
            ssException.printStackTrace();
        }
    }

    public static Menu loadMenuById(Long id) {
        getConnection();
        SolrQuery query = new SolrQuery("id:" + id.toString());
        Menu menu = null;
        try {
            QueryResponse response = server.query(query);
            List<ProxyMenu> loaded = response.getBeans(ProxyMenu.class);
            if (!loaded.get(0).isBadData()) {
                loadMenusItems(loaded);
                menu = loaded.get(0).convertToSimpleMenu();
            }
        } catch (SolrServerException ssException) {
            logger.error("SolrService: can't execute query at loadMenuById");
        }
        return menu;
    }

    public static MenuItem loadMenuItemById(Long id) {
        getConnection();
        SolrQuery query = new SolrQuery("id:" + id.toString());
        MenuItem menuItem = null;
        try {
            QueryResponse response = server.query(query);
            ProxyMenuItem loadedMenuItem = response.getBeans(ProxyMenuItem.class).get(0);
            if (!loadedMenuItem.isBadData()) {
                menuItem = loadedMenuItem.createSimpleMenuItem();
            }
        } catch (SolrServerException ssException) {
            logger.error("SolrService: can't execute query at loadMenuItemById");
        }
        return menuItem;
    }

    public static Collection<Menu> findMenuForNextWeek() {
        Timestamp date = DateUtils.getNextMondayTime();
        return findMenuByDate(date);
    }

    public static Collection<Menu> findMenuForCurrentWeek(){
        Timestamp date = DateUtils.getCurrentMondayTime();
        return findMenuByDate(date);
    }

    public static Collection<Menu> findMenuBeforeDate(final Timestamp date){
        Predicate<Menu> predicate = new Predicate<Menu>() {
            public boolean apply(@Nullable Menu o) {
                return o != null && ((o.getDateStart().before(date) || (o.getDateStart().equals(date) )));
            }
        };
        return Collections2.filter(loadAll(), predicate);
    }

    public static Collection<Menu> findMenuByDate(final Timestamp date){
        Predicate<Menu> predicate = new Predicate<Menu>() {
            public boolean apply(@Nullable Menu o) {
                return o != null && ((o.getDateStart().before(date) && o.getDateEnd().after(date)) ||
                        (o.getDateStart().equals(date) || o.getDateEnd().equals(date)));
            }
        };
        Collection<Menu> result =  Collections2.filter(loadAll(), predicate);
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    /**
     * Checks all loaded ProxyMenuItems for correct data and deletes bad of them
     * @param loaded all loaded ProxyMenuItems instances
     */
    private static void checkForCorrectData(List<ProxyMenu> loaded) {
        for (int i = 0; i < loaded.size(); ) {
            if (loaded.get(i).isBadData()) {
                loaded.remove(i);
            } else {
                i++;
            }
        }
    }

    public static List<String> loadCafeNames() {
        getConnection();
        List<String> names = new ArrayList<String>();
        try {
            SolrQuery getNamesQuery = new SolrQuery("cafename:*");
            getNamesQuery.setFields("cafename");
            QueryResponse response = server.query(getNamesQuery);
            for (SolrDocument document : response.getResults())  {
                names.add(document.get("cafename").toString());
            }
        } catch (SolrServerException ssException) {
            logger.error("SolrService: can't load cafe names", ssException);
        }
        return names;
    }

    public static Menu findMenuByDateAndCafename(String cafename, Timestamp menuDate) {
        Collection<Menu> menus = findMenuByDate(menuDate);
        for (Menu menu:menus) {
            if (menu.getCafeName().equals(cafename)) {
                return menu;
            }
        }
        return null;
    }
}
