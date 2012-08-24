package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.exadel.dinnerorders.entity.User;
import com.vaadin.ui.Upload;
import org.apache.log4j.Logger;

import java.io.*;

public class FileUploadReceiver implements Upload.Receiver {
    private final Logger logger = Logger.getLogger(FileUploadReceiver.class);
    private final User user;

    public FileUploadReceiver(User user) {
        this.user = user;
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        FileOutputStream fos;
        try {
            if (!mimeType.contains("image")) {
                throw new IllegalArgumentException();
            }
            File file = new File("C:/content/" + user.getLdapLogin() + filename);
            if (!file.createNewFile()) {
                throw new IllegalArgumentException();
            }
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("FileReceiver: couldn't upload file");
            return null;
        } catch (IOException ioException) {
            logger.error("FileReceiver: couldn't upload file");
            return null;
        }
        return fos;
    }
}

