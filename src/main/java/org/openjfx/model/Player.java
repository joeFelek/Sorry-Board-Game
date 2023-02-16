package org.openjfx.model;

import javafx.scene.paint.Color;


public class Player {

    private Color color;        //player's unique color.
    private final String name;          //player's unique name.
    private final Pawn pawn1, pawn2;    //player's two pawns.
    private boolean hisTurn;            //is this players turn checker.
    private boolean hasDrawn;

    /**
     * Constructor
     * Creates a new player.
     * @param color player's color.
     * @param name  player's name.
     */
    public Player(Color color, String name, Pawn pawn1, Pawn pawn2) {
        this.pawn1 = pawn1;
        this.pawn2 = pawn2;
        this.color = color;
        this.name = name;
        this.hisTurn = false;
        this.hasDrawn = false;
    }

    /**
     * Accessor
     * Get the player's first pawn.
     * @pre the player's pawns must be initialized.
     * @return the first pawn.
     */
    public Pawn getPawn1() {
        return pawn1;
    }

    /**
     * Accessor
     * Get the player's second pawn.
     * @pre the player's pawns must be initialized.
     * @return the second pawn.
     */
    public Pawn getPawn2() {
        return pawn2;
    }

    /**
     * Accessor
     * Get the player's color.
     * @return player's color.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Accessor
     * Get the player's name.
     * @return player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Observer
     * Check if it's the players turn.
     * @pre the game must have started.
     * @return true if player's turn else false.
     */
    public boolean isHisTurn() {
        return hisTurn;
    }

    /**
     * Transformer
     * Set player to play or wait this turn.
     * @param hisTurn true if player will play this turn else false.
     */
    public void setHisTurn(boolean hisTurn) {
        this.hisTurn = hisTurn;
    }

    /**
     * Observer
     * Checks if the player has finished the game.
     * @return true if both pawns are at the home square else false.
     */
    public boolean playerHasFinished() {
        return pawn1.isFinished() && pawn2.isFinished();
    }

    /**
     * Accessor
     * Get a string with players info <name> and <color>
     * @return players info.
     */
    @Override
    public String toString() {
        if(color.toString().equals(Color.RED.toString()))
            return "Player " +name+ " with colour red";
        return "Player " +name+ " with colour yellow";
    }

    public boolean hasDrawn() {
        return hasDrawn;
    }

    public void setHasDrawn(boolean hasDrawn) {
        this.hasDrawn = hasDrawn;
    }

}
