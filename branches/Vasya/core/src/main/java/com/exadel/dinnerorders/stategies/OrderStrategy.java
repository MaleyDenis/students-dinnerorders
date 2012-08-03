package com.exadel.dinnerorders.stategies;

import com.exadel.dinnerorders.entity.ExportStrategy;
import com.exadel.dinnerorders.entity.Order;
import com.exadel.dinnerorders.service.ExportService;
import com.exadel.dinnerorders.service.OrderService;
import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * User: Dima Shulgin
 * Date: 02.08.12
 */
public class OrderStrategy implements ExportStrategy {
    private static Logger logger = Logger.getLogger(ExportService.class);

    public String getFileName() {
        return "orders.xls";
    }

    public Collection getCollection() {
        return OrderService.getAllSortedOrders();
    }

    public Class getClazz() {
        return Order.class;
    }


}

