package org.openjfx.model.card;

import org.openjfx.model.Deck;
import org.openjfx.model.Pawn;
import org.openjfx.model.square.Square;

/**
 * This class represents a Sorry Card
 */
public class SorryCard extends Card {

    /**
     * Constructor
     * Make a Sorry card
     * Sorry card value is set to 13 for comparison with other cards.
     */
    public SorryCard() {
        super(13);
    }

    @Override
    public String toString() {
        return "Sorry Card";
    }

    @Override
    public boolean movePawn(Pawn pawn1, Pawn pawn2, Square destination, Deck map) {
        return destination.getPawn() != null && !pawn1.isStarted() && destination.getPawn() != pawn2 && !(destination.isHomeSquare() || destination.isSafeZone() || destination.isStartSquare());
    }

    @Override
    public String getImage() {
        return "cards/cardSorry.png";
    }

}
