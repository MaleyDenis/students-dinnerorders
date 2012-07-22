package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.IdDAO;

/**
 * User: Dima Shulgin
 * Date: 20.07.12
 */
public class IdService {

    private static Long ID;

    synchronized public static Long getUniqueID() {

        IdDAO idDAO = new IdDAO();
        ID = idDAO.getID();
        ++ID;
        return ID;
    }


}
