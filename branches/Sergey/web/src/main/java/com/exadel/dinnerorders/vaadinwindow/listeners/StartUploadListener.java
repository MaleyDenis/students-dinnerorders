package com.exadel.dinnerorders.vaadinwindow.listeners;

import com.vaadin.ui.Button;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

public class StartUploadListener implements Upload.StartedListener {
    private void disableSendButton(Upload.StartedEvent event) {
        Button send = (Button)((VerticalLayout)event.getComponent().getParent()).getComponent(0);
        send.setEnabled(false);
    }

    @Override
    public void uploadStarted(Upload.StartedEvent event) {
        disableSendButton(event);
        if (!event.getMIMEType().contains("image")) {
            event.getUpload().interruptUpload();
        }
    }
}
