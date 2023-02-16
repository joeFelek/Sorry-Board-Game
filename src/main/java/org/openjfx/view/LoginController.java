package org.openjfx.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.openjfx.controller.GameController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public ComboBox<String> resolution;
    @FXML private TextField redPlayerName;
    @FXML private TextField yellowPlayerName;
    @FXML private Label nullInputErrorMessage;

    private final ObservableList<String> resList = FXCollections.
            observableArrayList("1920 x 1080", "1366 x 768");


    /**
     * Start button
     * Link controller with view and view with controller
     * Initialize controller and set players names
     * Open the game window
     * @pre the two players name mustn't be null
     */
    public void startButton(ActionEvent event) throws IOException {

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if(!redPlayerName.getText().equals("") && !yellowPlayerName.getText().equals("")) {

            FXMLLoader loader = new FXMLLoader();
            Scene scene2;

            //choose resolution
            if(resolution.getValue().equals("1920 x 1080")) {
                System.out.println(1);
                URL url = new File("src/main/java/org/openjfx/view/View.fxml").toURI().toURL();
                loader.setLocation(url);
                Parent root2 = loader.load();
                scene2 =  new Scene(root2, 1920, 1080);
            }
            else {
                System.out.println(2);
                URL url = new File("src/main/java/org/openjfx/view/ViewSmall.fxml").toURI().toURL();
                loader.setLocation(url);
                Parent root2 = loader.load();
                scene2 =  new Scene(root2, 1366, 768);
            }


            View view = loader.getController();
            GameController controller = new GameController();

            controller.setView(view);  //link view to controller
            view.setController(controller); //link controller to view

            controller.initialize();
            view.initializePlayersView(redPlayerName.getText(), yellowPlayerName.getText());
            view.setupAccelerators();

            window.setScene(scene2);
            window.setX(Screen.getPrimary().getVisualBounds().getMinX());
            window.setY(Screen.getPrimary().getVisualBounds().getMinY());
            window.show();
        }else {
            nullInputErrorMessage.setVisible(true);
        }
    }

    /**
     * Initialize Combobox for resolutions
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resolution.setValue("default");
        resolution.setItems(resList);
    }
}
