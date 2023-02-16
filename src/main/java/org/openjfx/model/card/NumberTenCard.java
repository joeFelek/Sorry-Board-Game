package org.openjfx.model.card;

import javafx.scene.shape.Rectangle;
import org.openjfx.model.Deck;
import org.openjfx.model.Pawn;
import org.openjfx.model.card.NumberCard;
import org.openjfx.model.square.Square;

/**
 * This class represents Number 10 Card
 */
public class NumberTenCard extends NumberCard {

    /**
     * Constructor
     * Creates a number 10 card.
     */
    public NumberTenCard() {
        super(10);
    }

    @Override
    public String toString() {
        return super.toString() + "Ten";
    }

    //10 front or 1 back
    @Override
    public boolean movePawn(Pawn pawn1, Pawn pawn2, Square destination, Deck deck) {
        if(move10(pawn1,destination,deck))
            return true;
        return move1Back(pawn1, destination, deck);
    }

    private boolean move10(Pawn pawn1, Square destination, Deck deck) {

        if(pawn1.isStarted()) {

            if((destination.isHomeSquare() || destination.isSafeZone()) && !destination.getColor().equals(pawn1.getColor())) //check protected squares
                return false;

            Square s = destination;
            Rectangle r = s.getSquare();
            int jumps = 0;

            while(r != null && s != null && r != pawn1.getPosition() && jumps < 10) { //backtracking moves till starting position is found or 10 jumps where made
                r = s.getPrev();
                s = deck.getSquare(r);
                jumps++;
            }

            return jumps == 10 && r == pawn1.getPosition(); //if the right 10 jumps and backtracked found the starting square
        }
        return false;
    }

    private boolean move1Back(Pawn pawn1, Square destination, Deck deck) {
        if(pawn1.isStarted()) {

            if(destination.isSafeZone() && !destination.getColor().equals(pawn1.getColor())) //check protected squares
                return false;

            Rectangle r = pawn1.getPosition();
            Square s = deck.getSquare(r);
            int jumps = 0;

            while(r != null && s != null && r != destination.getSquare() && jumps < 1) { //backtracking moves till destination position is found or 1 jumps where made

                r = s.getPrev();
                s = deck.getSquare(r);
                jumps++;
            }

            return jumps == 1 && r == destination.getSquare(); //if the right 1 jumps and backtracked found the destination square
        }
        return false;
    }

}
