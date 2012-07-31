package com.exadel.dinnerorders.service;

import org.junit.Test;

import java.io.InputStream;

import static junit.framework.Assert.assertTrue;

/**
 * User: Dima Shulgin
 * Date: 31.07.12
 */
public class ExportServiceTest {
    @Test
    public void testGetUsersExcel() throws Exception {
        InputStream inputStream = ExportService.getUsersExcel();
        if (inputStream != null) {
            assertTrue(true);
        } else
            assertTrue(false);
    }
}
