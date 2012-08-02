package com.exadel.dinnerorders.stategies;

import com.exadel.dinnerorders.entity.ExportStrategy;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.service.ExportService;
import com.exadel.dinnerorders.service.UserService;
import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * User: Dima Shulgin
 * Date: 02.08.12
 */
public class UserStrategy implements ExportStrategy {
    private static Logger logger = Logger.getLogger(ExportService.class);

    public String getFileName() {
        return "users.xls";
    }

    public Collection getCollection() {
        return UserService.loadAllFromDB();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Class getClazz() {
        return User.class;
    }


}

