package org.openjfx.model;



import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.openjfx.model.card.*;
import org.openjfx.model.square.Square;


import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cardDeck = new ArrayList<>(44); //array that contains all game Cards.
    private ArrayList<Rectangle> board = new ArrayList<>();  //array that contains all game squares.
    private ArrayList<Square> modelBoard = new ArrayList<>(); //Double Linked List map of the game.
    private Card currentCard;                                  //the current card that has been drawn.


    public Deck () {
        initDeck();
    }

    /**
     * Initialize the game card deck.
     * @post fill the @array deck with the 44 game cards.
     */
    private void initDeck() {
        for(int i=0; i<44; i++)
            if(i>=40)
                cardDeck.add(new SorryCard());
            else if(i>=36)
                cardDeck.add(new SimpleNumberCard(12));
            else if(i>=32)
                cardDeck.add(new NumberElevenCard());
            else if(i>=28)
                cardDeck.add(new NumberTenCard());
            else if(i>=24)
                cardDeck.add(new SimpleNumberCard(8));
            else if(i>=20)
                cardDeck.add(new NumberSevenCard());
            else if(i>=16)
                cardDeck.add(new SimpleNumberCard(5));
            else if(i>=12)
                cardDeck.add(new NumberFourCard());
            else if(i>=8)
                cardDeck.add(new SimpleNumberCard(3));
            else if(i>=4)
                cardDeck.add(new NumberTwoCard());
            else
                cardDeck.add(new NumberOneCard());
        Collections.shuffle(cardDeck);
    }

    /**
     * Initialize the game board. Every object has a Next square and Prev square based on the clockwise direction of the game.
     * Squares that can have two directions use the sNext(second Next) else it's null.
     * @pre the @deck.board must be initialized
     * @post fill the @ArrayList modelBoard with the appropriate square blocks based on the map order printed in console.
     */
    public void initBoard() {
        for(int i = 0; i < 82; i++) {
            if(i == 0)  //first
                modelBoard.add(new Square(board.get(i), board.get(i+1), board.get(59), null, null, 0, false, false, false,false));
            else if(i==1 || i==16 || i==31 || i==46) //small slide starts
                modelBoard.add(new Square(board.get(i), board.get(i+1), board.get(i-1), null, null, 3, true, false, false,false));
            else if(i==9 || i==24 || i==39 || i==54) //big slide starts
                modelBoard.add(new Square(board.get(i), board.get(i+1), board.get(i-1), null, null, 4, true, false, false,false));
            else if(i == 2) //entrance to red safe zones
                modelBoard.add(new Square(board.get(i), board.get(i+1), board.get(i-1), board.get(68), null, 0, false, false, false,false));
            else if(i == 32) //entrance to yellow safe zones
                modelBoard.add(new Square(board.get(i), board.get(i+1), board.get(i-1), board.get(73), null, 0, false, false, false,false));
            else if(i == 59)  //last
                modelBoard.add(new Square(board.get(i), board.get(0), board.get(i-1), null, null, 0, false, false, false,false));
            else if(i < 60) //simple squares
                modelBoard.add(new Square(board.get(i), board.get(i+1), board.get(i-1), null, null, 0, false, false, false,false));
            else if(i < 62) //red home
                modelBoard.add(new Square(board.get(i), null, board.get(72), null, null, 0, false, true, false,false));
            else if(i < 64) //yellow home
                modelBoard.add(new Square(board.get(i), null, board.get(77), null, null, 0, false, true, false,false));
            else if(i < 66) //red start
                modelBoard.add(new Square(board.get(i), board.get(4), null, null, null, 0, false, false, true,false));
            else if(i < 68) //yellow start
                modelBoard.add(new Square(board.get(i), board.get(34), null, null, null, 0, false, false, true,false));
            else if(i == 68) //first red safe zone
                modelBoard.add(new Square(board.get(i), board.get(i+1), board.get(2), null, null, 0, false, false, false,true));
            else if(i < 72) //red safe zone
                modelBoard.add(new Square(board.get(i), board.get(i+1), board.get(i-1), null, null, 0, false, false, false,true));
            else if(i == 72) //entrance to red home
                modelBoard.add(new Square(board.get(i), board.get(60), board.get(i-1), board.get(61), null, 0, false, false, false,true));
            else if(i == 73) //first yellow safe zone
                modelBoard.add(new Square(board.get(i), board.get(i+1), board.get(32), null, null, 0, false, false, false,true));
            else if(i < 77) //yellow safe zone
                modelBoard.add(new Square(board.get(i), board.get(i+1), board.get(i-1), null, null, 0, false, false, false,true));
            else if(i == 77)//entrance to yellow home
                modelBoard.add(new Square(board.get(i), board.get(62), board.get(i-1), board.get(63), null, 0, false, false, false,true));
            else if(i < 80)
                modelBoard.add(new Square(board.get(i), null, null, null, null, 0, false, false, true,false));
            else
                modelBoard.add(new Square(board.get(i), null, null, null, null, 0, false, true, false,false));

            //Color initialize
            if(containsColor(i, "red")) {
                modelBoard.get(i).setColor(Color.RED);
            }else if(containsColor(i, "yellow")) {
                modelBoard.get(i).setColor(Color.YELLOW);
            }else if(containsColor(i, "green")) {
                modelBoard.get(i).setColor(Color.GREEN);
            }else if(containsColor(i, "blue")) {
                modelBoard.get(i).setColor(Color.BLUE);
            }else {
                modelBoard.get(i).setColor(Color.WHITE);
            }
        }

    }

    /**
     * Transformer
     * Change the game Rectangle board with a new one.
     * @param board the new board.
     * @post the Rectangle board will be @param board.
     */
    public void setBoard(ArrayList<Rectangle> board) {
        this.board = board;
    }

    /**
     * Accessor
     * Get the current DLL Square board.
     * @return map board.
     */
    public ArrayList<Square> getBoard() {
        return modelBoard;
    }

    /**
     * Accessor
     * Get the card Deck.
     * @return card Deck.
     */
    public ArrayList<Card> getCardDeck() {
        return cardDeck;
    }

    /**
     * Accessor
     * Get the current card drawn from the card deck.
     * @return the last card drawn from the deck.
     */
    public Card getCurrentCard() {
        return currentCard;
    }

    /**
     * Draw the last card from the card deck.
     * @pre if the card deck is empty initializes a new one.
     * @post @currentCard will be set to the last card drawn and the card will be removed from the card deck.
     */
    public void drawFromDeck() {
        if(cardDeck.size() == 0) initDeck();        //if the deck is out of cards get a new deck
        currentCard = cardDeck.get(cardDeck.size()-1);  //draw the last card from the deck and set it as the current Card drawn
        cardDeck.remove(cardDeck.size()-1);       //remove the card drawn from the deck
    }

    /**
     * Search the Square ArrayList and return the Square that has a Rectangle r as value
     * @param r the Rectangle to be looked for in the Square list
     * @pre if the r Rectangle isn't in the ArrayList return null.
     * @return the Square that has the r Rectangle
     */
    public Square getSquare(Rectangle r) {
        for(Square s : modelBoard) {
            if(s.getSquare() == r)
                return s;
        }
        return null;
    }

    //Tool Function.
    private boolean containsColor(int i, String color) {
        return modelBoard.get(i).getSquare().getId().toLowerCase().contains(color);
    }

}
