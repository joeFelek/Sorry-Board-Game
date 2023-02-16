package org.openjfx.model.card;

import javafx.scene.shape.Rectangle;
import org.openjfx.model.Deck;
import org.openjfx.model.Pawn;
import org.openjfx.model.square.Square;

/**
 *This class represents a Simple Card
 * Simple Cards are 3,5,8,12
 */
public class SimpleNumberCard extends NumberCard {

    /**
     * Constructor
     * Creates a simple number card
     * @param number the number of the card.
     * @pre the card number must 3,5,8 or 12.
     */
    public SimpleNumberCard(int number) {
        super(number);
    }

    @Override
    public String toString() {
        switch (this.getCardNumber()) {
            case 3:
                return super.toString()+"Three";
            case 5:
                return super.toString()+"Five";
            case 8:
                return super.toString()+"Eight";
            case 12:
                return super.toString()+"Twelve";
            default:
                return super.toString()+ this.getCardNumber();
        }
    }

    @Override
    public boolean movePawn(Pawn pawn1, Pawn pawn2, Square destination, Deck deck) {
        if(pawn1.isStarted()) {

            if((destination.isHomeSquare() || destination.isSafeZone()) && !destination.getColor().equals(pawn1.getColor())) //check protected squares
                return false;

            Square s = destination;
            Rectangle r = s.getSquare();
            int jumps = 0;

            while(r != null && s != null && r != pawn1.getPosition() && jumps < deck.getCurrentCard().getCardNumber()) { //backtracking moves till starting position is found or <card number> jumps where made
                r = s.getPrev();
                s = deck.getSquare(r);
                jumps++;
            }

            return jumps == deck.getCurrentCard().getCardNumber() && r == pawn1.getPosition(); //if the right <card number> jumps and backtracked found the starting square
        }
        return false;
    }

}
