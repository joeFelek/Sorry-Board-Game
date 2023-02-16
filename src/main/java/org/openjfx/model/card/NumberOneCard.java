package org.openjfx.model.card;

import javafx.scene.paint.Color;
import org.openjfx.model.Deck;
import org.openjfx.model.Pawn;
import org.openjfx.model.card.NumberCard;
import org.openjfx.model.square.Square;

/**
 * This class represents Number 1 Card
 */
public class NumberOneCard extends NumberCard {

    /**
     * Constructor
     * Creates a number 1 card.
     */
    public NumberOneCard() {
        super(1);
    }

    @Override
    public String toString() {
        return super.toString() + "One";
    }

    //can start from starting square and move by one square
    @Override
    public boolean movePawn(Pawn pawn1, Pawn pawn2, Square destination, Deck map) {

        if(!pawn1.isStarted()) //at start square
            if(pawn1.getColor().equals(Color.RED) && destination == map.getBoard().get(4))
                return true;
            else
                return pawn1.getColor().equals(Color.YELLOW) && destination == map.getBoard().get(34);
        if((destination.isHomeSquare() || destination.isSafeZone()) && !destination.getColor().equals(pawn1.getColor())) //check protected squares
            return false;
        else
            return pawn1.getPosition() == destination.getPrev(); //return if prev square of destination is equal to position of the moved pawn

    }
}
