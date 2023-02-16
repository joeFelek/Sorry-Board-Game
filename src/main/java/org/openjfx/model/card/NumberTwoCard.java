package org.openjfx.model.card;

import javafx.scene.paint.Color;
import org.openjfx.model.Deck;
import org.openjfx.model.Pawn;
import org.openjfx.model.card.NumberCard;
import org.openjfx.model.square.Square;

/**
 * This class represents a Number 2 Card
 */
public class NumberTwoCard extends NumberCard {

    /**
     * Constructor
     * Creates a number 2 card.
     */
    public NumberTwoCard() {
        super(2);
    }

    @Override
    public String toString() {
        return super.toString() + "Two";
    }

    @Override
    public boolean movePawn(Pawn pawn1, Pawn pawn2, Square destination, Deck game) {

        if (!pawn1.isStarted())  //at start square
            if (pawn1.getColor().equals(Color.RED) && destination == game.getBoard().get(5))
                return true;
            else
                return pawn1.getColor().equals(Color.YELLOW) && destination == game.getBoard().get(35);
        if((destination.isHomeSquare() || destination.isSafeZone()) && !destination.getColor().equals(pawn1.getColor())) //check protected squares
            return false;
        else {
            Square prev = game.getSquare(destination.getPrev()); //previous square of destination
            return prev != null && pawn1.getPosition() == prev.getPrev(); //previous square of previous of destination equals the position of the pawn moved
        }

    }
}
