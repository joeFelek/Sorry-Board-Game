package org.openjfx.model.card;

import javafx.scene.shape.Rectangle;
import org.openjfx.model.Deck;
import org.openjfx.model.Pawn;
import org.openjfx.model.square.Square;

/**
 * This class represents Number 7 Card
 */
public class NumberSevenCard extends NumberCard {

    private int numberOfSquaresMoved;
    private boolean needsToPlayAgain;
    private boolean secondMove;
    private Pawn pawnMovedInFirstMove;

    /**
     * Constructor
     * Creates a number 7 card.
     */
    public NumberSevenCard() {
        super(7);
        numberOfSquaresMoved = 0;
        needsToPlayAgain = false;
        pawnMovedInFirstMove = null;
        secondMove = false;
    }

    @Override
    public String toString() {
        return super.toString()+"Seven";
    }

    @Override
    public boolean movePawn(Pawn pawn1, Pawn pawn2, Square destination, Deck deck) {
        if(needsToPlayAgain) {
            secondMove = true;
            return secondMove(pawn1, destination, deck);
        }else
            return firstMove(pawn1, destination, deck);

    }

    //Check if first card number seven move is valid
    public boolean firstMove(Pawn pawn1, Square destination, Deck deck) {
        if(pawn1.isStarted()) {

            if((destination.isHomeSquare() || destination.isSafeZone()) && !destination.getColor().equals(pawn1.getColor())) //check protected squares
                return false;

            Square s = destination;
            Rectangle r = s.getSquare();
            int jumps = 0;

            while(r != null && s != null && r != pawn1.getPosition() && jumps < 7) { //backtracking moves till starting position is found or 7 jumps where made
                r = s.getPrev();
                s = deck.getSquare(r);
                jumps++;
            }

            if(r != pawn1.getPosition()) return false;

            if(jumps < 7 ) {
                needsToPlayAgain = true;
                numberOfSquaresMoved = jumps;
                pawnMovedInFirstMove = pawn1;
            }
            return true;
        }
        return false;
    }

    //Check if split card number seven move is valid
    public boolean secondMove(Pawn pawn1, Square destination, Deck deck) {
        if(pawn1.isStarted() && pawn1 != pawnMovedInFirstMove) {

            if((destination.isHomeSquare() || destination.isSafeZone()) && !destination.getColor().equals(pawn1.getColor())) //check protected squares
                return false;

            Square s = destination;
            Rectangle r = s.getSquare();
            int jumps = 0;

            while(r != null && s != null && r != pawn1.getPosition() && jumps < 7) { //backtracking moves till starting position is found or 7 jumps where made
                r = s.getPrev();
                s = deck.getSquare(r);
                jumps++;
            }

            if(r != pawn1.getPosition()) return false;

            return jumps + numberOfSquaresMoved == 7;
        }
        return false;
    }

    public void reset() {
        numberOfSquaresMoved = 0;
        needsToPlayAgain = false;
        pawnMovedInFirstMove = null;
        secondMove = false;
    }

    public boolean isSecondMove() {
        return secondMove;
    }

    public int getNumberOfSquaresMoved() {
        return numberOfSquaresMoved;
    }

    @Override
    public boolean numberSevenSplitMove() {
        return needsToPlayAgain;
    }
}
