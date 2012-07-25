package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.Role;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.service.UserService;
import junit.framework.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import static org.apache.log4j.helpers.LogLog.error;

/**
 * User: Dima Shulgin
 * Date: 17.07.12
 */
public class UserDAOTest {
    UserDAO userDAO = new UserDAO()  ;

    @Test
    public void testCreate() throws Exception {
        long id = 1;
        User user = new User(id, "testLogin1", "testName1", Role.USER);
        userDAO.create(user);
        User user1 = UserService.findUserByUserName(user.getUserName());
        if (user1.getLdapLogin().equals(user.getLdapLogin())) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testUpdate() throws Exception {

        long id = 0;
        User user = new User(id, "testLogin2", "testName2", Role.USER);

        userDAO.create(user);
        user.setLdapLogin("updateLogin");
        user.setUserName("updateName");
        userDAO.update(user);
        User user1 = UserService.findUserByUserName("updateName");

        if (user.getUserName().equals(user1.getUserName())) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testDelete() throws Exception {
        long id = 0;

        User user = new User(id, "testLogin3", "testName3", Role.USER);

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

        long id = 0;
        User user = new User(id, "testLogin4", "testName4", Role.USER);

        userDAO.create(user);
        User user1 = userDAO.load(user.getId());
        if (user1.getUserName().equals(user.getUserName())) {

            Assert.assertTrue(true);

        } else {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testLoadAll() throws Exception {

        User user = new User(null, "testLogin5", "testName5", Role.USER);
        new UserDAO(){
              public void deleteRows() {
                    Connection connection = connection(this.getClass());

                    PreparedStatement preparedStatement = null;
                    try {
                        preparedStatement = connection.prepareStatement(("DELETE  FROM user WHERE ID > 0;"));
                        preparedStatement.execute();

                    } catch (SQLException e) {
                        error("Error in the function deleteRows", e);

                    }
                }
          } .deleteRows();

        int quantity1 = 45;

        while (quantity1 != 0) {
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
