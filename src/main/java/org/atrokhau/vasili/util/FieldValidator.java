package org.atrokhau.vasili.util;

import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.Month;

public class FieldValidator {

    private FieldValidator() {

    }

    public static String getFieldValue(TextField field) {
        String value = "";
        if (field.getText() != null) {
            value = field.getText();
        }

        return value;
    }

    public static boolean isBirthday(LocalDate date) {
        LocalDate now = LocalDate.now();
        Month month = date.getMonth();
        int dayOfMonth = date.getDayOfMonth();
        return now.getMonth().equals(month) && now.getDayOfMonth() == dayOfMonth;
    }
}
