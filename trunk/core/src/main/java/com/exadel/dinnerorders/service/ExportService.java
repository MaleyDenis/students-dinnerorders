package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.Export;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * User: Dima Shulgin
 * Date: 31.07.12
 */
public class ExportService<E> {
    private static Logger logger = Logger.getLogger(ExportService.class);

    public static InputStream getUsersExcel(Class clazz) throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        ArrayList<String> fieldNames = new ArrayList<String>();
        int rowsCount = 0;
        int cellIndex = 0;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("Page 1");
        HSSFRow row1 = worksheet.createRow(rowsCount);
        ++rowsCount;

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(Export.class);
            if (annotation instanceof Export) {
                Export export = (Export) annotation;
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

        Collection users = UserService.loadAllUsersFromDB();

        Iterator collectionIt = users.iterator();
        cellIndex = 0;

        while (collectionIt.hasNext()) {
            Object item = collectionIt.next();
            HSSFRow row = worksheet.createRow(rowsCount);
            for (String fieldName : fieldNames) {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                HSSFCell namecell = row.createCell(cellIndex);
                namecell.setCellValue(field.get(item).toString());
                ++cellIndex;
            }

            cellIndex = 0;
            ++rowsCount;

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
