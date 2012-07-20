package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.service.UserService;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 * User: Dima Shulgin
 * Date: 17.07.12
 */
public class UserDAOTest {
    UserDAO userDAO = new UserDAO();

    @Test
    public void testCreate() throws Exception {
        long id = 1;
        User user = new User(id, "testLogin1", "testName1", Role.USER);
        userDAO.create(user);
        User user1 = UserService.findUserbyUserName(user.getUserName());
        if (user1.getLdapLogin().equals(user.getLdapLogin())) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }

    }

    @Test
    public void testUpdate() throws Exception {

        long id = userDAO.getMaxIndex() + 1;
        User user = new User(id, "testLogin2", "testName2", Role.USER);
        userDAO.dropTable();
        userDAO.create(user);
        user.setLdapLogin("updateLogin");
        user.setUserName("updateName");
        userDAO.update(user);
        User user1 = UserService.findUserbyUserName("updateName");

        if (user.getUserName().equals(user1.getUserName())) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }


    }

    @Test
    public void testDelete() throws Exception {

        long id = userDAO.getMaxIndex() + 1;
        User user = new User(id, "testLogin3", "testName3", Role.USER);
        userDAO.dropTable();
        userDAO.create(user);

        Collection collection = userDAO.loadAll();
        int quantity = collection.size();
        collection = userDAO.loadAll();
        userDAO.delete(user);
        userDAO.create(user);
        if (quantity == collection.size()) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }


    }

    @Test
    public void testLoad() throws Exception {

        long id = userDAO.getMaxIndex() + 1;
        User user = new User(id, "testLogin4", "testName4", Role.USER);

        userDAO.dropTable();
        userDAO.create(user);
        User user1 = userDAO.load(id);
        if (user1.getUserName().equals(user.getUserName())) {

            Assert.assertTrue(true);

        } else {
            Assert.assertTrue(false);
        }


    }

    @Test
    public void testLoadAll() throws Exception {
        long id = userDAO.getMaxIndex() + 1;
        User user = new User(id, "testLogin5", "testName5", Role.USER);
        userDAO.dropTable();
        int quantity1 = 45;
        while (quantity1 != 0) {
            id = id + 1;
            userDAO.create(user);
            --quantity1;
        }


        int quantity2 = userDAO.loadAll().size();

        if (quantity2 == 45) {

            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }

    }
}
