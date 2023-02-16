package org.openjfx.model.card;

import javafx.scene.shape.Rectangle;
import org.openjfx.model.Deck;
import org.openjfx.model.Pawn;
import org.openjfx.model.card.NumberCard;
import org.openjfx.model.square.Square;

/**
 * This class represents Number 4 Card
 */
public class NumberFourCard extends NumberCard {

    /**
     * Constructor
     * Creates a number 4 card.
     */
    public NumberFourCard() {
        super(4);
    }

    @Override
    public String toString() {
        return super.toString() + "Four";
    }

    @Override
    public boolean movePawn(Pawn pawn1, Pawn pawn2, Square destination, Deck deck) {

        if(pawn1.isStarted()) {

            if(destination.isSafeZone() && !destination.getColor().equals(pawn1.getColor())) //check protected squares
                return false;

            Rectangle r = pawn1.getPosition();
            Square s = deck.getSquare(r);
            int jumps = 0;

            while(r != null && s != null && r != destination.getSquare() && jumps < 4) { //backtracking moves till destination position is found or 4 jumps where made

                r = s.getPrev();
                s = deck.getSquare(r);
                jumps++;
            }

            return jumps == 4 && r == destination.getSquare(); //if the right 4 jumps and backtracked found the destination square
        }
        return false;
    }


}
