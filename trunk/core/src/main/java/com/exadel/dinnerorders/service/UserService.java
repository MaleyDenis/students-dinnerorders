package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.dao.UserDAO;
import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.exception.WorkflowException;
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
    private static UserDAO userDAO = new UserDAO();

    public static User create(String ldapUsername)  {
        User user = new User();
        user.setUserName(ldapUsername);
        user.setRole(Role.USER);
        if (!userDAO.create(user)) {
            throw new WorkflowException("User can't be created.");
        }
        return user;
    }

    private static Collection<User> getAllUsers()  {
        return userDAO.loadAll();
    }

    public static Collection<User> loadAllUsersFromLdap()  {
        Collection<String> userNames = new LdapService().loadAll();
        Collection<User> users = new ArrayList<User>();
        for (String userName : userNames) {
            User user = findUserByUserName(userName);
            users.add(user);
        }
        return users;
    }

    public static User findUserByUserName(final String ldapUsername)  {

        Collection<User> users = getAllUsers();
        Iterable<User> iterables = Iterables.filter(users, new Predicate<User>() {
            public boolean apply(User u) {
                return StringUtils.equals(u.getUserName(), ldapUsername);
            }
        });

        if (iterables.iterator().hasNext()) {
            return iterables.iterator().next();
        } else {
            User user = new User();
            user.setId(null);
            user.setUserName(ldapUsername);
            user.setRole(Role.USER);
            return user;
        }
    }

    public static User findUserByID(final Long id)  {
        return userDAO.load(id);

    }

    public static Collection<User> loadAllUsersFromDB()  {
        return userDAO.loadAll();
    }
}
