package com.exadel.dinnerorders.vaadinwindow.guitoolkits;

public class MessageTextParser {
    public static String parseToHTML(String text, int rowLength) {
        StringBuilder source = new StringBuilder(text);
        StringBuilder target = new StringBuilder();
        if (rowLength < 1) {
            throw new IllegalArgumentException("rowLength should be more then 0");
        }

        while (source.length() > rowLength) {
            for (int i = rowLength; i != 0; i--) {
                if (source.charAt(i) == ' ' || source.charAt(i) == '\n') {
                    target.append(source.subSequence(0, i));
                    target.append("<br>");
                    source.delete(0, i + 1);
                    break;
                }
            }
        }

        target.append(source.toString());
        return target.toString();
    }
}
