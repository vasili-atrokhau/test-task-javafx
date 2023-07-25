package org.atrokhau.vasili.util;

import javafx.scene.control.Alert;

public class DialogWindowUtil {

    private DialogWindowUtil() {

    }

    public static void showInformationWindow(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
