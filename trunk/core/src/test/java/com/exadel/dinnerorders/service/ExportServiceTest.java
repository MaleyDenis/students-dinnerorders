package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.ExportStrategy;
import com.exadel.dinnerorders.stategies.UserStrategy;
import junit.framework.Assert;
import org.apache.poi.util.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * User: Dima Shulgin
 * Date: 31.07.12
 */
public class ExportServiceTest {
    @Test
    public void testGetUsersExcel() throws Exception {
        ExportStrategy exportStrategy = new UserStrategy();
        InputStream inputStream = ExportService.getExcel(exportStrategy);
        if (inputStream != null) {

            File f = new File("ExportService-test.xls");
            FileOutputStream fileOut = new FileOutputStream(f);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            fileOut.write(bytes);


            if (f.exists()) {
                Assert.assertTrue(true);
            } else {
                Assert.assertTrue(false);
            }
        } else {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testgetTxt() throws Exception {
        ExportStrategy exportStrategy = new UserStrategy();
          InputStream inputStream = ExportService.getTxt(exportStrategy);
          if (inputStream != null) {

              File f = new File("ExportService-test.txt");

              FileOutputStream fileOut = new FileOutputStream(f);
              byte[] bytes = IOUtils.toByteArray(inputStream);
              fileOut.write(bytes);


              if (f.exists()) {
                  Assert.assertTrue(true);
              } else {
                  Assert.assertTrue(false);
              }
          } else {
              Assert.assertTrue(false);
          }
    }
}
