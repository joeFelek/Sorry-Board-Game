package org.openjfx.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class Pawn {

    private Color color;
    private Circle pawn;
    private Rectangle position;   //the current square the pawn is positioned.
    private boolean started;      //@var started checks if the pawn has left the start position.
    private boolean finished;     //@var finished checks if the pawn has reached the home position.
    private Rectangle dest1;
    private Rectangle dest2;

    /**
     * Constructor
     * Creates a new pawn.
     * @param pawn the circle id of the pawn
     *
     */
    public Pawn(Circle pawn) {
        this.pawn = pawn;
        this.color = (Color) pawn.getFill();
        this.started = false;
        this.finished = false;
        dest1 = null;
        dest2 = null;
    }

    public Circle getPawn() {
        return pawn;
    }

    /**
     * Accessor
     * Get the current position of the pawn in the board.
     * @return pawn's current position.
     */
    public Rectangle getPosition() {
        return position;
    }

    /**
     * Accessor
     * Get the color of the pawn.
     * @return pawn's color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Observer
     * Checks if the pawn reached the Home square(has reached the end).
     * @return true if the pawn has reached it's final destination/Home square else false.
     */
    boolean isFinished() {
        return finished;
    }

    /**
     * Observer
     * Checks if the pawn got out of the Starting square
     * @return true if the pawn left the starting square else false.
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Transformer
     * Set a new position for the pawn.
     * @param position the new position the pawn will be.
     */
    public void setPosition(Rectangle position) {
        this.position = position;
    }

    /**
     * Transformer
     * Set if the pawn has reached the finish square
     * @param finished true if is at home square else false
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Transformer
     * Set if the pawn has left the starting square
     * @param started true if out else false.
     */
    public void setStarted(boolean started) {
        this.started = started;
    }


    public Rectangle getDest1() {
        return dest1;
    }

    public void setDest1(Rectangle dest1) {
        this.dest1 = dest1;
    }

    public Rectangle getDest2() {
        return dest2;
    }

    public void setDest2(Rectangle dest2) {
        this.dest2 = dest2;
    }
}
