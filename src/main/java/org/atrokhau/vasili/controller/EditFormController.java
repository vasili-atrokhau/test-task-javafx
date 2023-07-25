package org.atrokhau.vasili.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.atrokhau.vasili.model.Human;
import org.atrokhau.vasili.service.HumanService;
import org.atrokhau.vasili.util.DialogWindowUtil;

import static org.atrokhau.vasili.util.FieldValidator.getFieldValue;

public class EditFormController {

    @FXML
    public Button saveButton;
    @FXML
    public Button cancelButton;
    @FXML
    private TextField nameField;
    @FXML
    private DatePicker birthdayField;
    @FXML
    private TextField ageField;
    private Stage stage = null;
    private final HumanService humanService = new HumanService();

    @FXML
    public void closeButton(ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    private void updateHuman() {

        try {
            Human selectedHuman = (Human) stage.getUserData();
            selectedHuman.setAge(Integer.parseInt(getFieldValue(ageField)));
            selectedHuman.setName(getFieldValue(nameField));
            selectedHuman.setBirthday(birthdayField.getValue());

            humanService.updateHuman(selectedHuman);
            stage.close();
        } catch (NumberFormatException e) {
            DialogWindowUtil.showInformationWindow("Age field error", "Age should be number",
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void saveButtonAction() {
        updateHuman();
    }

    public void getEditForm(TreeTableView<Human> humansTable, Parent root) {
        stage = new Stage();

        if (humansTable.getSelectionModel().getSelectedItem() != null) {
            TreeItem<Human> selectedItem = humansTable.getSelectionModel().getSelectedItem();
            Human selectedHuman = Human.builder()
                    .id(selectedItem.getValue().getId())
                    .name(selectedItem.getValue().getName())
                    .age(selectedItem.getValue().getAge())
                    .birthday(selectedItem.getValue().getBirthday())
                    .build();

            stage.setUserData(selectedHuman);
            nameField.setText(selectedHuman.getName());
            ageField.setText(selectedHuman.getAge().toString());
            birthdayField.setValue(selectedHuman.getBirthday());

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Edit human");
            stage.setScene(new Scene(root, 450, 450));
            stage.showAndWait();

        } else {
            DialogWindowUtil.showInformationWindow("Message", "User was not selected",
                    Alert.AlertType.ERROR);
        }
    }
}
