package com.exadel.dinnerorders.dao;

import com.exadel.dinnerorders.entity.User;
import org.junit.Test;

/**
 * User: Dima Shulgin
 * Date: 17.07.12
 */
public class UserDAOTest {
    UserDAO userDAO = new UserDAO();

    @Test
    public void testCreate() throws Exception {
        User user = new User(13, "test", "test");

        userDAO.create(user);
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testLoad() throws Exception {

    }

    @Test
    public void testLoadAll() throws Exception {

    }
}
