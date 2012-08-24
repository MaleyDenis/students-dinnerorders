package com.exadel.dinnerorders.vaadinwindow.guitoolkits;

import com.exadel.dinnerorders.entity.SystemResource;
import com.exadel.dinnerorders.service.Configuration;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUploadService {
    public static final int DEFAULT_BUFFER_SIZE = 1024;
    private static final long MAX_FILE_LENGTH =
            Long.parseLong(Configuration.getProperty(SystemResource.MAX_FILE_LENGTH));
    private static final Logger logger = Logger.getLogger(FileUploadService.class);

    public static boolean uploadFile(File source) {
        try{
            Long date = System.currentTimeMillis();
            File target = new File("C:/content/" + date.toString() + source.getName());
            if (!target.createNewFile()) {
                return false;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(target);
            FileInputStream fileInputStream = new FileInputStream(source);
            if (!upload(fileInputStream, fileOutputStream)) {
                target.delete();
                return false;
            }
        } catch (IOException ioe) {
            logger.error("FileUploadService: couldn't upload file");
            return false;
        }
        return true;
    }

    private static boolean upload(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int read = fileInputStream.read(buffer);
        int overallLength = read;
        while (read > 0) {
            fileOutputStream.write(buffer, 0, read);
            read = fileInputStream.read(buffer);
            overallLength += read;
            if (overallLength > MAX_FILE_LENGTH) {
                fileOutputStream.close();
                return false;
            }
        }
        fileOutputStream.close();
        fileInputStream.close();
        return true;
    }
}
