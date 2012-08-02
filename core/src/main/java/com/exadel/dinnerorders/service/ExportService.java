package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.Entity;
import com.exadel.dinnerorders.entity.Export;
import com.exadel.dinnerorders.entity.ExportStrategy;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

        try {


            Collection<Entity> collection = exportStrategy.getCollection();


            cellIndex = 0;

            for (Entity entity : collection) {
                Object item = entity;
                HSSFRow row = worksheet.createRow(rowsCount);
                for (String fieldName : fieldNames) {
                    HSSFCell namecell = row.createCell(cellIndex);
                    namecell.setCellValue(PropertyUtils.getProperty(item, fieldName).toString());
                    ++cellIndex;
                }

                cellIndex = 0;
                ++rowsCount;

            }

        } catch (NoSuchMethodException e) {
            logger.error("Method has not been found", e);
        } catch (InvocationTargetException e) {
            logger.error("Some Error", e);
        }   catch (IllegalAccessException e) {
            logger.error("Some Error", e);
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
