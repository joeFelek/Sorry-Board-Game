package org.openjfx.model.card;

import org.openjfx.model.Deck;
import org.openjfx.model.Pawn;
import org.openjfx.model.square.Square;

/**
 * Interface of all Cards in the game.
 */
public abstract class Card {

    private final int cardNumber;   //number of the card

    public Card(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Accessor
     * Get the card number.
     * @return card value.
     */
    public int getCardNumber() {
            return cardNumber;
    }

    /**
     * Accessor
     * Get the String name of the Card.
     * @return String name of the Card syntax:"Number <cardNumber> card".
     */
    public abstract String toString();

    /**
     * Observer and Transformer.
     * Moves the Pawn on the Board depending on the selected card effects.
     * @param pawn1 the pawn to be moved.
     * @param pawn2 the second pawn to be moved if there is one, only for the case the selected card is 11 or Sorry card
     * @param deck current deck
     * @post the pawn will be moved to the appropriate position on the board.
     * @return 1 if the move was completed successfully, 0 if not.
     */
    public abstract boolean movePawn(Pawn pawn1, Pawn pawn2, Square destination, Deck deck);

    /**
     * Get the string image path
     * @return image path
     */
    public abstract String getImage();

    public boolean numberSevenSplitMove() {
        return false;
    }
}
