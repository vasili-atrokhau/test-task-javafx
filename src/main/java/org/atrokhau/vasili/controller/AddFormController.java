package org.atrokhau.vasili.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.SneakyThrows;
import org.atrokhau.vasili.Main;
import org.atrokhau.vasili.model.Human;
import org.atrokhau.vasili.service.HumanService;
import org.atrokhau.vasili.util.DialogWindowUtil;

import static org.atrokhau.vasili.util.FieldValidator.getFieldValue;

public class AddFormController {

    @FXML
    private TextField nameField;
    @FXML
    private DatePicker birthdayField;
    @FXML
    private TextField ageField;
    private Stage stage = null;
    private FXMLLoader fxmlLoader;
    private final HumanService humanService = new HumanService();

    @FXML
    public void cancelButton(ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void saveHuman() {
        try {
            Human human = Human.builder()
                    .age(Integer.parseInt(getFieldValue(ageField)))
                    .name(getFieldValue(nameField))
                    .birthday(birthdayField.getValue())
                    .build();

            humanService.saveHuman(human);

            stage.close();
        } catch (NumberFormatException e) {
            DialogWindowUtil.showInformationWindow("Age field error", "Age should be number",
                    Alert.AlertType.ERROR);
        }

    }

    @SneakyThrows
    @FXML
    public void saveButton() {
        saveHuman();
    }

    public void getAddForm(Parent root) {
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setOpacity(1);
        stage.setTitle("Add human");
        stage.setScene(new Scene(root, 450, 450));
        stage.showAndWait();
    }

}
