package org.openjfx.view;

import org.openjfx.controller.GameController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.openjfx.model.card.Card;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class View {


    @FXML private Rectangle smallYellowSlideStart;
    @FXML private Rectangle smallYellowSlideMedium;
    @FXML private Rectangle smallYellowSlideMedium1;
    @FXML private Rectangle smallYellowSlideEnd;
    @FXML private Rectangle bigGreenSlideEnd;
    @FXML private Rectangle bigGreenSlideMedium2;
    @FXML private Rectangle bigGreenSlideMedium1;
    @FXML private Rectangle bigGreenSlideMedium;
    @FXML private Rectangle bigGreenSlideStart;
    @FXML private Rectangle smallGreenSlideEnd;
    @FXML private Rectangle smallGreenSlideMedium1;
    @FXML private Rectangle smallGreenSlideMedium;
    @FXML private Rectangle smallGreenSlideStart;
    @FXML private Rectangle bigYellowSlideEnd;
    @FXML private Rectangle bigYellowSlideMedium;
    @FXML private Rectangle bigYellowSlideMedium1;
    @FXML private Rectangle bigYellowSlideMedium2;
    @FXML private Rectangle bigYellowSlideStart;
    @FXML private Rectangle bigBlueSlideEnd;
    @FXML private Rectangle bigBlueSlideMedium2;
    @FXML private Rectangle bigBlueSlideMedium1;
    @FXML private Rectangle bigBlueSlideMedium;
    @FXML private Rectangle bigBlueSlideStart;
    @FXML private Rectangle smallBlueSlideEnd;
    @FXML private Rectangle smallBlueSlideStart;
    @FXML private Rectangle smallBlueSlideMedium;
    @FXML private Rectangle smallBlueSlideMedium1;
    @FXML private Rectangle smallRedSlideStart;
    @FXML private Rectangle smallRedSlideMedium;
    @FXML private Rectangle smallRedSlideMedium1;
    @FXML private Rectangle smallRedSlideEnd;
    @FXML private Rectangle bigRedSlideStart;
    @FXML private Rectangle bigRedSlideMedium;
    @FXML private Rectangle bigRedSlideMedium1;
    @FXML private Rectangle bigRedSlideMedium2;
    @FXML private Rectangle bigRedSlideEnd;
    @FXML private Rectangle SimpleSquare1;
    @FXML private Rectangle SimpleSquare2;
    @FXML private Rectangle SimpleSquare3;
    @FXML private Rectangle SimpleSquare4;
    @FXML private Rectangle SimpleSquare5;
    @FXML private Rectangle SimpleSquare6;
    @FXML private Rectangle SimpleSquare7;
    @FXML private Rectangle SimpleSquare8;
    @FXML private Rectangle SimpleSquare9;
    @FXML private Rectangle SimpleSquare10;
    @FXML private Rectangle SimpleSquare11;
    @FXML private Rectangle SimpleSquare12;
    @FXML private Rectangle SimpleSquare13;
    @FXML private Rectangle SimpleSquare14;
    @FXML private Rectangle SimpleSquare15;
    @FXML private Rectangle SimpleSquare16;
    @FXML private Rectangle SimpleSquare17;
    @FXML private Rectangle SimpleSquare18;
    @FXML private Rectangle SimpleSquare19;
    @FXML private Rectangle SimpleSquare20;
    @FXML private Rectangle SimpleSquare21;
    @FXML private Rectangle SimpleSquare22;
    @FXML private Rectangle SimpleSquare23;
    @FXML private Rectangle SimpleSquare24;
    @FXML private Rectangle redSafetyZone1;
    @FXML private Rectangle redSafetyZone2;
    @FXML private Rectangle redSafetyZone3;
    @FXML private Rectangle redSafetyZone4;
    @FXML private Rectangle redSafetyZone5;
    @FXML private Rectangle yellowSafetyZone1;
    @FXML private Rectangle yellowSafetyZone2;
    @FXML private Rectangle yellowSafetyZone3;
    @FXML private Rectangle yellowSafetyZone4;
    @FXML private Rectangle yellowSafetyZone5;
    @FXML private Rectangle yellowStartSquare;
    @FXML private Rectangle redStartSquare;
    @FXML private Rectangle redHomeSquare;
    @FXML private Rectangle yellowHomeSquare;
    @FXML private Label redPlayerLabel;
    @FXML private Label yellowPlayerLabel;
    @FXML private Circle yellowPawn1;
    @FXML private Circle yellowPawn2;
    @FXML private Circle redPawn1;
    @FXML private Circle redPawn2;
    @FXML private Circle origPositionCircle;
    @FXML private ImageView CurrentCard;
    @FXML private TextArea InfoBox;
    @FXML private TextArea GameLog;
    @FXML private Rectangle yellowHome1, yellowHome2, redHome2, redHome1, redStart2, redStart1, yellowStart1, yellowStart2;
    @FXML private Pane boardPane;
    @FXML private Button drawButton, foldButton;
    @FXML private Group RectangleGroup;
    @FXML private Label victory;

    private Effect effect;
    private GameController controller;


    public void setController(GameController controller) {
        this.controller = controller;
    }

    @FXML
    public void DrawCard() {
       controller.DrawCard();
    }

    @FXML
    public void FoldAction() {
       controller.Fold();
    }

    //HotKeys
    public void setupAccelerators() {
        setAccelerator(foldButton, KeyCode.F);
        setAccelerator(drawButton, KeyCode.D);
    }
    //set up accelerator
    private void setAccelerator(Button button, KeyCode K) {

        assert button != null;
        Scene scene = button.getScene();
        assert scene != null;

        scene.getAccelerators().put(
                new KeyCodeCombination(K, KeyCombination.SHORTCUT_ANY),
                button::fire
        );
    }
    //set victory message
    void setVictory(String mess, Color color) {
        victory.setText(mess);
        victory.setTextFill(color);
        victory.setVisible(true);
    }

    //menu action
    @FXML
    public void NewGame() throws IOException {
        URL url = new File("src/main/java/org/openjfx/view/Login.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene =  new Scene(root, 600, 400);

        Stage window = (Stage) (drawButton.getScene()).getWindow();
        window.setScene(scene);
        window.show();
    }

    //menu action
    @FXML
    public void ExitGame() {
        Platform.exit();
    }

    //initialize players names
    public void initializePlayersView(String playerName1, String playerName2) {
        controller.initializePlayers(playerName1, playerName2);
        redPlayerLabel.setText(playerName1);
        yellowPlayerLabel.setText(playerName2);
    }

    //update info box with current players name and card deck size
    public void updateInfoBox(String name, int size) {
        InfoBox.setText("Turn: " + name + "\n" + "Cards Left: "+ size);
    }

    //update game log box with message
    public void updateGameLogBox(String string) {
        GameLog.appendText("\n" + string);
    }

    //set card images
    public void setCardImage(Card card) {
        System.out.println(card.getCardNumber());
        CurrentCard.setImage(new Image(card.getImage()));
    }

    //initializes square images
    public void setSquareImages() {
        Image img;
        //yellow
        img = new Image("slides/yellowSlideEnd.png");
        squareImage(img, smallYellowSlideEnd, bigYellowSlideEnd, "slides/yellowSlideStart.png", smallYellowSlideStart, bigYellowSlideStart, "slides/yellowSlideMedium.png", smallYellowSlideMedium, smallYellowSlideMedium1, bigYellowSlideMedium, bigYellowSlideMedium1, bigYellowSlideMedium2);
        //red
        img = new Image("slides/redSlideEnd.png");
        squareImage(img, smallRedSlideEnd, bigRedSlideEnd, "slides/redSlideStart.png", smallRedSlideStart, bigRedSlideStart, "slides/redSlideMedium.png", smallRedSlideMedium, smallRedSlideMedium1, bigRedSlideMedium, bigRedSlideMedium1, bigRedSlideMedium2);
        //green
        img = new Image("slides/greenSlideEnd.png");
        squareImage(img, smallGreenSlideEnd, bigGreenSlideEnd, "slides/greenSlideStart.png", smallGreenSlideStart, bigGreenSlideStart, "slides/greenSlideMedium.png", smallGreenSlideMedium, smallGreenSlideMedium1, bigGreenSlideMedium, bigGreenSlideMedium1, bigGreenSlideMedium2);
        //blue
        img = new Image("slides/blueSlideEnd.png");
        squareImage(img, smallBlueSlideEnd, bigBlueSlideEnd, "slides/blueSlideStart.png", smallBlueSlideStart, bigBlueSlideStart, "slides/blueSlideMedium.png", smallBlueSlideMedium, smallBlueSlideMedium1, bigBlueSlideMedium, bigBlueSlideMedium1, bigBlueSlideMedium2);

    }
    //tool
    private void squareImage(Image img, Rectangle smallRedSlideEnd, Rectangle bigRedSlideEnd, String s, Rectangle smallRedSlideStart, Rectangle bigRedSlideStart, String s2, Rectangle smallRedSlideMedium, Rectangle smallRedSlideMedium1, Rectangle bigRedSlideMedium, Rectangle bigRedSlideMedium1, Rectangle bigRedSlideMedium2) {
        smallRedSlideEnd.setFill(new ImagePattern(img));
        bigRedSlideEnd.setFill(new ImagePattern(img));
        img = new Image(s);
        smallRedSlideStart.setFill(new ImagePattern(img));
        bigRedSlideStart.setFill(new ImagePattern(img));
        img = new Image(s2);
        smallRedSlideMedium.setFill(new ImagePattern(img));
        smallRedSlideMedium1.setFill(new ImagePattern(img));
        bigRedSlideMedium.setFill(new ImagePattern(img));
        bigRedSlideMedium1.setFill(new ImagePattern(img));
        bigRedSlideMedium2.setFill(new ImagePattern(img));
    }
    //show the rectangle
    void showValidRectangle(Rectangle validDestination) {
        DropShadow ds = new DropShadow();
        ds.setColor(Color.WHITE);

        if(validDestination != null) {

            effect = validDestination.getEffect();
            if(controller.getDeck().getSquare(validDestination).isHomeSquare() || controller.getDeck().getSquare(validDestination).isSafeZone()) {

                Color c = controller.getDeck().getSquare(validDestination).getColor();
                InnerShadow is = new InnerShadow();
                is.setColor(c);
                is.setChoke(0.64);
                is.setWidth(15);
                is.setHeight(15);
                is.setRadius(7);
                ds.setInput(is);

            }
            validDestination.setEffect(ds);
        }
    }
    //stop showing the rectangle
    void stopShowingValidRectangle(Rectangle validDestination) {
        if(controller.getDeck().getSquare(validDestination).isHomeSquare() || controller.getDeck().getSquare(validDestination).isSafeZone()) {
            if(controller.getDeck().getSquare(validDestination).isHomeSquare()) {
                Color c = controller.getDeck().getSquare(validDestination).getColor();
                InnerShadow is = new InnerShadow();
                is.setColor(c);
                is.setChoke(0.64);
                is.setWidth(15);
                is.setHeight(15);
                is.setRadius(7);
                effect = is;
            }
            validDestination.setEffect(effect);
        }else
            validDestination.setEffect(null);
    }
    //get the game board pane
    Pane getBoardPane() {
        return boardPane;
    }

    public Rectangle getYellowHome1() {
        return yellowHome1;
    }

    public Rectangle getYellowHome2() {
        return yellowHome2;
    }

    public Rectangle getRedHome2() {
        return redHome2;
    }

    public Rectangle getRedHome1() {
        return redHome1;
    }

    public Rectangle getRedStart2() {
        return redStart2;
    }

    public Rectangle getRedStart1() {
        return redStart1;
    }

    public Rectangle getYellowStart1() {
        return yellowStart1;
    }

    public Rectangle getYellowStart2() {
        return yellowStart2;
    }

    Circle getOrigPositionCircle() {
        return origPositionCircle;
    }

    public Circle getYellowPawn1() {
        return yellowPawn1;
    }

    public Circle getYellowPawn2() {
        return yellowPawn2;
    }

    public Circle getRedPawn1() {
        return redPawn1;
    }

    public Circle getRedPawn2() {
        return redPawn2;
    }

    public Group getRectangleGroup() {
        return RectangleGroup;
    }

    public Rectangle getRedStartSquare() {
        return redStartSquare;
    }

    public Rectangle getRedHomeSquare() {
        return redHomeSquare;
    }

    public Rectangle getYellowHomeSquare() {
        return yellowHomeSquare;
    }

    public Rectangle getYellowStartSquare() {
        return yellowStartSquare;
    }

}
