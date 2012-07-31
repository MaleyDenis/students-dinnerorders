package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.User;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * User: Dima Shulgin
 * Date: 31.07.12
 */
public class ExportService {
    private static Logger logger = Logger.getLogger(ExportService.class);

    public static InputStream getUsersExcel() {
        Collection<User> users = UserService.loadAllUsersFromDB();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("Page 1");


        HSSFRow row1 = worksheet.createRow(0);

        HSSFCell cellA1 = row1.createCell(0);
        cellA1.setCellValue("Users");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.RED.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellA1.setCellStyle(cellStyle);

        HSSFCell cellB1 = row1.createCell(1);

        cellB1.setCellValue("Role");
        cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.RED.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellB1.setCellStyle(cellStyle);
        int i = 1;
        for (User user : users) {
            HSSFRow row = worksheet.createRow(i);
            HSSFCell namecell = row.createCell(0);
            namecell.setCellValue(user.getUserName());
            HSSFCell cell = row.createCell(1);
            cell.setCellValue(user.getRole().name());
            ++i;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
            bos.close();
            ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
            return in;
        } catch (IOException e) {
            logger.error("File hasn't been created");
        }
        return null;

    }
}
