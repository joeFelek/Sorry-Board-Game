package org.openjfx.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.openjfx.controller.GameController;

public class PawnView {

    private Point2D offset;
    private boolean movingPiece;    //the piece is on the move

    private Circle circle;  //the current circle
    private GameController controller; //the current controller
    private View view; //the current view
    private Rectangle dest1; //the first destination the pawn can go
    private Rectangle dest2; //the second destination the pawn can go
    private Rectangle dest3; //the third destination the pawn can go

    /**
     * Constructor
     * @param controller link to current controller
     * @param view link to current view
     * @param circle the pawn
     */
    public PawnView(GameController controller, View view, Circle circle) {
        this.controller = controller;
        this.view = view;
        this.circle = circle;
    }

    /**
     * Set event filters of pawns
     */
    public void InitializePawn() {
        circle.addEventFilter(MouseEvent.MOUSE_PRESSED, this::startMovingPiece);
        circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::movePiece);
        circle.addEventFilter(MouseEvent.MOUSE_RELEASED, this::finishMovingPiece);
    }

    /**
     * On click,
     * save pawn starting position
     * set original position pawn (shadowing of start position effect)
     * show valid positions the pawn can be set
     * @pre game must not have ended and the selected pawn must be one of the current players pawns
     * @param evt mouse click
     */
    private void startMovingPiece(MouseEvent evt) {

        if(canMovePawn() && !controller.GameFinished()) {

            dest1 = controller.getValidDestination(this.circle);
            dest2 = controller.getValidDestination(this.circle);
            dest3 = controller.getValidDestination(this.circle);
            view.showValidRectangle(dest1);
            view.showValidRectangle(dest2);
            view.showValidRectangle(dest3);

            circle.setOpacity(0.4d);
            offset = new Point2D(evt.getX(), evt.getY());

            view.getOrigPositionCircle().setFill(circle.getFill());
            view.getOrigPositionCircle().setOpacity(1.0d);
            view.getOrigPositionCircle().setLayoutX(circle.getLayoutX());
            view.getOrigPositionCircle().setLayoutY(circle.getLayoutY());

            movingPiece = true;
            controller.removeValidDestination(this.circle);
        }
    }

    /**
     * On mouse drag,
     * Move the pawn with the mouse
     * @pre game must not have ended and the selected pawn must be one of the current players pawns
     * @param evt mouse dragged
     */
    private void movePiece(MouseEvent evt) {
        if(canMovePawnStealth() && !controller.GameFinished()) {
            Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
            Point2D mousePoint_s = new Point2D(evt.getSceneX(), evt.getSceneY());
            if (inBoard(mousePoint_s)) {
                return;
            }

            Point2D mousePoint_p = circle.localToParent(mousePoint);
            circle.relocate(mousePoint_p.getX() - offset.getX(), mousePoint_p.getY() - offset.getY());
        }
    }

    /**
     * check if the pawn is in the game board
     * @param pt the point the mouse is located
     * @return true if in board return true else false
     */
    private boolean inBoard(Point2D pt) {
        Point2D panePt = view.getBoardPane().sceneToLocal(pt);
        return !(panePt.getX() - offset.getX() >= 0.0d)
                || !(panePt.getY() - offset.getY() >= 0.0d)
                || !(panePt.getX() <= view.getBoardPane().getWidth())
                || !(panePt.getY() <= view.getBoardPane().getHeight());
    }

    /**
     * On click release,
     * if the mouse released in a valid square position the pawn is set to that position else it's moved back to it's first position
     * @pre game must not have ended and the selected pawn must be one of the current players pawns
     * @param evt mouse released
     */
    private void finishMovingPiece(MouseEvent evt) {
        if(canMovePawnStealth() && !controller.GameFinished()) {
            offset = new Point2D(0.0d, 0.0d);

            Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
            Point2D mousePointScene = circle.localToScene(mousePoint);


            Rectangle r = pickRectangle(mousePointScene.getX(), mousePointScene.getY());
            final Timeline timeline = new Timeline();
            timeline.setCycleCount(1);
            timeline.setAutoReverse(false);

            if (r != null) {

                r = controller.HandleBigSquares(r);

                if(controller.ValidMove(r, this.circle)) { //check if valid
                    controller.Adjust(r, this.circle);
                    r = controller.SlideMove(r, this.circle);

                    Point2D rectScene = r.localToScene(r.getX(), r.getY());
                    Point2D parent = view.getBoardPane().sceneToLocal(rectScene.getX(), rectScene.getY());
                    timeline.getKeyFrames().add(
                            new KeyFrame(Duration.millis(100),
                                    new KeyValue(circle.layoutXProperty(), parent.getX() + 40 / 2),
                                    new KeyValue(circle.layoutYProperty(), parent.getY() + 40 / 2),
                                    new KeyValue(circle.opacityProperty(), 1.0d),
                                    new KeyValue(view.getOrigPositionCircle().opacityProperty(), 0.0d)
                            )
                    );

                    controller.isFinished(this.circle);

                    if(controller.GameFinished())
                        view.setVictory(controller.Victory(), controller.currentPlayer().getColor());
                  else
                    controller.EndTurn();
                }else
                    leaveBoard();
            }else
                leaveBoard();

            timeline.play();
            movingPiece = false;

            if(dest3 != null)
                view.stopShowingValidRectangle(dest3);
            if(dest1 != null)
                view.stopShowingValidRectangle(dest1);
            if(dest2 != null)
                view.stopShowingValidRectangle(dest2);
        }
    }

    /**
     * Get the Rectangle the pawn was dropped
     * @param sceneX the x position in the pane of the rectangle
     * @param sceneY the y position in the pane of the rectangle
     * @return the destination rectangle if the pawn was released in one of the rectangles of the board
     *         else return null
     */
    private Rectangle pickRectangle(double sceneX, double sceneY) {
        Point2D mousePoint = new Point2D(sceneX, sceneY);
        Point2D mpLocal = view.getRectangleGroup().sceneToLocal(mousePoint);
        if( view.getBoardPane().contains(mpLocal) ) {
            for( Node cell : view.getRectangleGroup().getChildrenUnmodifiable() ) {
                Point2D mpLocalCell = cell.sceneToLocal(mousePoint);

                if( cell.contains(mpLocalCell) ) {
                    return (Rectangle)cell;
                }
            }
        }
        return null;
    }

    /**
     * Return the pawn to it's starting position
     */
    private void leaveBoard() {
        if( movingPiece ) {

            final Timeline timeline = new Timeline();

            offset = new Point2D(0.0d, 0.0d);
            movingPiece = false;

            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(200),
                            new KeyValue(circle.layoutXProperty(), view.getOrigPositionCircle().getLayoutX()),
                            new KeyValue(circle.layoutYProperty(), view.getOrigPositionCircle().getLayoutY()),
                            new KeyValue(circle.opacityProperty(), 1.0d),
                            new KeyValue(view.getOrigPositionCircle().opacityProperty(), 0.0d)
                    )
            );
            timeline.play();
        }
    }

    /**
     * Check if the pawn clicked is one of the current player pawns
     * @return true if it is else false
     */
    private boolean canMovePawnStealth() {
        return controller.currentPlayer().getColor() == circle.getFill() && controller.currentPlayer().hasDrawn();
    }

    /**
     * Check if the pawn clicked is one of the current player pawns
     * if not print an alert message at the game log
     * @return true if it is else print alert message and return false
     */
    private boolean canMovePawn() {
        if(controller.currentPlayer().getColor() == circle.getFill()) {
            if(controller.currentPlayer().hasDrawn()) {
                return true;
            }
            view.updateGameLogBox(controller.currentPlayer().getName() + " hasn't drawn a card yet.");
        }
        return false;
    }

    /**
     * Return the current pawn
     * @return the current pawn
     */
    public Circle getPawn() {
        return this.circle;
    }

    /**
     * Move the current pawn to r destination
     * @param r destination
     * @post the current pawn will be placed at r Rectangle
     */
    public void SorryMove(Rectangle r) {
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);

        r = controller.HandleBigSquares(r);
        Point2D rectScene = r.localToScene(r.getX(), r.getY());
        Point2D parent = view.getBoardPane().sceneToLocal(rectScene.getX(), rectScene.getY());
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(100),
                        new KeyValue(circle.layoutXProperty(), parent.getX() + 40 / 2),
                        new KeyValue(circle.layoutYProperty(), parent.getY() + 40 / 2),
                        new KeyValue(circle.opacityProperty(), 1.0d),
                        new KeyValue(view.getOrigPositionCircle().opacityProperty(), 0.0d)
                )
        );
        timeline.play();
    }

}
