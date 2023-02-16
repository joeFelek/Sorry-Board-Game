package org.openjfx.model.card;

import org.openjfx.model.Deck;
import org.openjfx.model.Pawn;
import org.openjfx.model.square.Square;

/**
 * This abstract class represents the Number Cards
 * abstract functions: movePawn.
 */
abstract class NumberCard extends Card {

    /**
     * Constructor
     * Creates a Number Card.
     * @param number number of the Card.
     */
    NumberCard(int number) {
        super(number);
    }

    @Override
    public String toString() {
        return "Card Number ";
    }

    @Override
    public abstract boolean movePawn(Pawn pawn1, Pawn pawn2, Square destination, Deck deck);

    @Override
    public String getImage() {
        switch (super.getCardNumber()) {
            case 1:
                return "cards/card1.png";
            case 2:
                return "cards/card2.png";
            case 3:
                return "cards/card3.png";
            case 4:
                return "cards/card4.png";
            case 5:
                return "cards/card5.png";
            case 7:
                return "cards/card7.png";
            case 8:
                return "cards/card8.png";
            case 10:
                return "cards/card10.png";
            case 11:
                return "cards/card11.png";
            case 12:
                return "cards/card12.png";
            default:
                return "";
        }
    }
}
