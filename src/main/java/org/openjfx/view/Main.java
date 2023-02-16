package org.openjfx.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL url = new File("src/main/java/org/openjfx/view/Login.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage.setTitle("Sorry!");
        stage.setScene(new Scene(root, 600,400));
        stage.show();
    }

    /**
     * main method
     */
    public static void main(String[] args) {
        launch(args);
    }
}
