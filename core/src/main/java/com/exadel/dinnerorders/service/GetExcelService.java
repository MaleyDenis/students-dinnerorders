package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.User;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.util.ArrayList;

/**
 * User: Dima Shulgin
 * Date: 31.07.12
 */
public class GetExcelService {
    public static HSSFWorkbook getUsersExcel() {
        ArrayList<User> users = (ArrayList<User>) UserService.loadAllUsersFromDB();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("Page 1");


        HSSFRow row1 = worksheet.createRow(0);

        HSSFCell cellA1 = row1.createCell(0);
        cellA1.setCellValue("Заказчик");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.RED.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellA1.setCellStyle(cellStyle);

        HSSFCell cellB1 = row1.createCell(1);

        cellB1.setCellValue("Полномочия");
        cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.RED.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellB1.setCellStyle(cellStyle);

        for (int i = 0; i < users.size(); ++i) {
            HSSFRow row = worksheet.createRow(i+1);
            HSSFCell namecell = row.createCell(0);
            namecell.setCellValue(users.get(i).getUserName());
            HSSFCell cell = row.createCell(1);
            cell.setCellValue(users.get(i).getRole().name());
        }


        return workbook;

    }
}
