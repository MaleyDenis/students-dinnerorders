package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.Export;
import com.exadel.dinnerorders.entity.ExportStrategy;
import com.exadel.dinnerorders.entity.User;
import com.exadel.dinnerorders.exception.WorkflowException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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

    public static InputStream getExcel(ExportStrategy exportStrategy) {

        Class mainClass = exportStrategy.getClazz();
        ArrayList<String> fieldNames = new ArrayList<String>();
        int rowsCount = 0;
        int cellIndex = 0;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("Page 1");
        HSSFRow row1 = worksheet.createRow(rowsCount);
        ++rowsCount;

        Field[] fields = mainClass.getDeclaredFields();
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(Export.class);
            if (annotation != null) {

                fieldNames.add(field.getName());

                //create cell
                HSSFCell cell = row1.createCell(cellIndex);
                cell.setCellValue(((Export) annotation).column());

                //create style of cell
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setFillForegroundColor(HSSFColor.RED.index);
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                cell.setCellStyle(cellStyle);
                ++cellIndex;
            }
        }

        try {


            Collection collection = exportStrategy.getCollection();


            cellIndex = 0;

            for (Object entity : collection) {
                HSSFRow row = worksheet.createRow(rowsCount);
                for (String fieldName : fieldNames) {
                    HSSFCell namecell = row.createCell(cellIndex);
                    namecell.setCellValue(PropertyUtils.getProperty(entity, fieldName).toString());
                    ++cellIndex;
                }

                cellIndex = 0;
                ++rowsCount;

            }

        } catch (NoSuchMethodException e) {
            logger.error("Method has not been found");
            throw new WorkflowException(e);
        } catch (InvocationTargetException e) {
            logger.error("Some Error");
            throw new WorkflowException(e);
        } catch (IllegalAccessException e) {
            logger.error("Some Error");
            throw new WorkflowException(e);
        }


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
            bos.close();
            return new ByteArrayInputStream(bos.toByteArray());
        } catch (IOException e) {
            logger.error("File hasn't been created");
        }
        return null;

    }
}
