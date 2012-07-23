package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.UserDAO;
import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: Dima Shulgin
 * Date: 19.07.12
 */

public class UserService {


    private static Collection<User> getAllUsers() {
        UserDAO userDAO = new UserDAO();
        return userDAO.loadAll();
    }

    public static Collection<User> loadAllUsersFromLdap() {
        Collection<String> userNames = new LdapService().loadAll();
        Collection<User> users = new ArrayList<User>();
        for (String userName : userNames) {
            User user = findUserByUserName(userName);
            if (user == null) {
                user = new User();
                user.setId(null);
                user.setUserName(userName);
                user.setRole(Role.USER);
            }
            users.add(user);
        }
        return users;
    }

    public static User findUserByUserName(final String username) {

        Collection<User> users = getAllUsers();
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


    public static User findUserByID(final Long id) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.load(id);
        return user;

    }
}
