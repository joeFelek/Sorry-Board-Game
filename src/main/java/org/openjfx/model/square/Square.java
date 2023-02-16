package org.openjfx.model.square;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.openjfx.model.Pawn;





public class Square {

    private final Rectangle square;
    private final Rectangle prev;
    private final Rectangle next;
    private final Rectangle sNext;
    private Pawn pawn;                  //the pawn currently in the square. If there isn't currently a pawn, it's null.
    private final int slideBoost;       //increment of the slide
    private final boolean slideStart;   //if slide square
    private final boolean homeSquare;   //if home square
    private final boolean startSquare;  //if start square
    private final boolean safeZone;     //if safeZone
    private Color color;


    public Square(Rectangle square, Rectangle next, Rectangle prev, Rectangle sNext, Pawn pawn, int slideBoost, boolean slideStart, boolean homeSquare, boolean startSquare, boolean safeZone) {
        this.square = square;
        this.next = next;
        this.sNext = sNext;
        this.prev = prev;
        this.pawn = pawn;
        this.slideBoost = slideBoost;
        this.slideStart = slideStart;
        this.homeSquare = homeSquare;
        this.startSquare = startSquare;
        this.safeZone = safeZone;
    }
    /**
     * Transformer
     * Sets a pawn in this Square.
     * @param pawn the pawn to be placed on the Square.
     * @post The Square will have a pawn placed on it.
     */
    public void setPawn(Pawn pawn) {
            this.pawn = pawn;
    }

    /**
     * Transformer
     * Removes pawn from this Square.
     * @post The Square will no longer hold a pawn.
     */
    public void removePawn() {
        this.pawn = null;
    }

    /**
     * Accessor
     * Get the pawn in this Square, if the Square holds more than one pawn return the last placed.
     * @return pawn in the selected Square.
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * Observer
     * Checks if home square
     * @return true if the square is the home square
     */
    public boolean isHomeSquare() {
        return homeSquare;
    }

    /**
     * Observer
     * Checks if start of slide square
     * @return true if slide start
     */
    public boolean isSlideStart() {
        return slideStart;
    }

    /**
     * Observer
     * Checks if starting square
     * @return true if starting square
     */
    public boolean isStartSquare() {
        return startSquare;
    }

    /**
     * Accessor
     * gets the incrementation of the slide
     * @return the size of the slide.
     */
    public int getSlideBoost() {
        return slideBoost;
    }


    /**
     * Observer
     * Checks if safe zone square
     * @return true if safe zone square
     */
    public boolean isSafeZone() {
        return safeZone;
    }

    public Rectangle getSquare() {
        return square;
    }

    public Rectangle getPrev() {
        return prev;
    }

    public Rectangle getNext() {
        return next;
    }

    public Rectangle getsNext() {
        return sNext;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
