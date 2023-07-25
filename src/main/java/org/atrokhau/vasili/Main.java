package org.atrokhau.vasili;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainController.fxml"));
        Scene scene = new Scene(loader.load(), 460, 450);
        stage.setScene(scene);
        stage.setTitle("Human management application");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}