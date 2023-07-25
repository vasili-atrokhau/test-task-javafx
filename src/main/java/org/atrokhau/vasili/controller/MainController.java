package org.atrokhau.vasili.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import lombok.SneakyThrows;
import org.atrokhau.vasili.model.Human;
import org.atrokhau.vasili.service.HumanService;
import org.atrokhau.vasili.util.DialogWindowUtil;
import org.atrokhau.vasili.util.FieldValidator;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.atrokhau.vasili.util.DialogWindowUtil.showInformationWindow;

public class MainController {

    @FXML
    private TreeTableColumn<Human, Long> id;
    @FXML
    private TreeTableColumn<Human, String> name;
    @FXML
    private TreeTableColumn<Human, Integer> age;
    @FXML
    private TreeTableColumn<Human, Date> birthday;
    @FXML
    private TreeTableView<Human> humansTable;
    private FXMLLoader fxmlLoader = null;
    private Parent root = null;
    private final HumanService humanService = new HumanService();

    private void initData() {
        ObservableList<Human> humans;

        humans = humanService.buildData();

        id.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        name.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        age.setCellValueFactory(new TreeItemPropertyValueFactory<>("age"));
        birthday.setCellValueFactory(new TreeItemPropertyValueFactory<>("birthday"));

        List<TreeItem<Human>> humanItems = humans.stream()
                .map(TreeItem::new)
                .collect(Collectors.toList());

        TreeItem<Human> tableRoot = new TreeItem<>(Human.builder().name("Manager").age(19).build());
        tableRoot.getChildren().addAll(humanItems);
        humansTable.setRoot(tableRoot);
        tableRoot.setExpanded(true);

        humansTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreeItem<Human> humanTreeItem = humansTable.getSelectionModel().getSelectedItem();
                if (FieldValidator.isBirthday(humanTreeItem.getValue().getBirthday())) {
                    DialogWindowUtil.showInformationWindow("Message","Today is birthday",
                            Alert.AlertType.INFORMATION);
                }
            }
        });

    }

    @SneakyThrows
    @FXML
    public void editHumanAction() {
        fxmlLoader = new FXMLLoader(getClass().getResource("/EditFormController.fxml"));
        root = fxmlLoader.load();
        EditFormController controller = fxmlLoader.getController();
        controller.getEditForm(humansTable, root);
        initData();
    }

    @SneakyThrows
    @FXML
    public void addHumanAction() {
        fxmlLoader = new FXMLLoader(getClass().getResource("/AddFormController.fxml"));
        root = fxmlLoader.load();
        AddFormController controller = fxmlLoader.getController();
        controller.getAddForm(root);
        initData();
    }

    @FXML
    public void initialize() {
        initData();
    }

    @FXML
    public void deleteHumanAction() {
        if (humansTable.getSelectionModel().getSelectedItem() != null) {
            TreeItem<Human> selectedItem = humansTable.getSelectionModel().getSelectedItem();
            Human selectedHuman = Human.builder()
                    .id(selectedItem.getValue().getId())
                    .name(selectedItem.getValue().getName())
                    .age(selectedItem.getValue().getAge())
                    .birthday(selectedItem.getValue().getBirthday())
                    .build();

            humanService.deleteHuman(selectedHuman.getId());
            initData();
            showInformationWindow("Message", "User was deleted", Alert.AlertType.INFORMATION);
        } else {
            showInformationWindow("Message", "User was not selected", Alert.AlertType.ERROR);
        }

        humansTable.refresh();
    }


}
