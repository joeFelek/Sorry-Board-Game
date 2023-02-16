package org.openjfx.model.card;

import javafx.scene.shape.Rectangle;
import org.openjfx.model.Deck;
import org.openjfx.model.Pawn;
import org.openjfx.model.card.NumberCard;
import org.openjfx.model.square.Square;

/**
 * This class represents Number 11 Card
 */
public class NumberElevenCard extends NumberCard {

    /**
     * Constructor
     * Creates a number 11 card.
     */
    public NumberElevenCard() {
        super(11);
    }

    @Override
    public String toString() {
        return super.toString() + "Eleven";
    }

    @Override
    public boolean movePawn(Pawn pawn1, Pawn pawn2, Square destination, Deck map) {
        if(move11(pawn1,destination,map))
            return true;
        return swap(pawn1, pawn2, destination);
    }

    private boolean move11(Pawn pawn1, Square destination, Deck deck) {

        if(pawn1.isStarted()) {

            if((destination.isHomeSquare() || destination.isSafeZone()) && !destination.getColor().equals(pawn1.getColor())) //check protected squares
                return false;

            Square s = destination;
            Rectangle r = s.getSquare();
            int jumps = 0;

            while(r != null && s != null && r != pawn1.getPosition() && jumps < 11) { //backtracking moves till starting position is found or 11 jumps where made
                r = s.getPrev();
                s = deck.getSquare(r);
                jumps++;
            }

            return jumps == 11 && r == pawn1.getPosition(); //if the right 11 jumps and backtracked found the starting square
        }
        return false;
    }

    private boolean swap(Pawn pawn1, Pawn pawn2, Square destination) {
        return destination.getPawn() != null && pawn1.isStarted() && destination.getPawn() != pawn2 && !(destination.isHomeSquare() || destination.isSafeZone() || destination.isStartSquare());
    }

}
