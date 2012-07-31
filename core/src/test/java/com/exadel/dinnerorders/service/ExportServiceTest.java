package com.exadel.dinnerorders.service;

import com.vaadin.terminal.DownloadStream;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * User: Dima Shulgin
 * Date: 31.07.12
 */
public class ExportServiceTest {
    @Test
    public void testGetUsersExcel() throws Exception {
        DownloadStream downloadStream = ExportService.getUsersExcel();
      if( downloadStream.getStream() !=null){
          assertTrue(true);
      }else
          assertTrue(false);
    }
}
