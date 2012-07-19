package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.UserDAO;
import com.exadel.dinnerorders.entity.User;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * User: Dima Shulgin
 * Date: 19.07.12
 */
public class UserService {
    private static Logger logger = Logger.getLogger(UserService.class);


    private static ArrayList<User> getAllUsers() {
        UserDAO userDAO = new UserDAO();
        return (ArrayList<User>) userDAO.loadAll();
    }


    public static User findUserbyUserName(final String username) {

        ArrayList<User> users = getAllUsers();
        Iterable<User> iterables = Iterables.filter(users, new Predicate<User>() {
            public boolean apply(User u) {

                return StringUtils.equals(u.getUserName(), username);
            }
        });


        if (iterables.iterator().hasNext()) {
            return iterables.iterator().next();
        } else
            return null;

    }
}
