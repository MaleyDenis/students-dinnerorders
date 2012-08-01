package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.User;
import junit.framework.Assert;
import org.junit.Test;

import java.io.InputStream;

/**
 * User: Dima Shulgin
 * Date: 31.07.12
 */
public class ExportServiceTest {
    @Test
    public void testGetUsersExcel() throws Exception {
        InputStream inputStream = ExportService.getUsersExcel(User.class);
        if (inputStream != null) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }
    }
}
