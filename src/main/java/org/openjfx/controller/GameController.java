package org.openjfx.controller;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.openjfx.model.Deck;
import org.openjfx.model.Pawn;
import org.openjfx.model.Player;
import org.openjfx.model.card.NumberSevenCard;
import org.openjfx.model.square.Square;
import org.openjfx.view.PawnView;
import org.openjfx.view.View;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameController {


    private Player player1, player2;                //two players
    private Deck deck;                              //board and card deck
    private View view;                              //interface
    private PawnView Pawn1, Pawn2, Pawn3, Pawn4;    //pawn movement interface


    /**
     * Link controller and view
     * @param view interface
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Get the deck
     * @return deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Initialize controller :
     *  deck, interface pawns, board.
     */
    public void initialize() {
        deck = new Deck();
        InitializePawns();
        deck.setBoard(initBoard());
        deck.initBoard();
        view.setSquareImages();
    }

    /**
     * Initialize interface pawns
     * @post event Filters will be set for each pawn
     */
    private void InitializePawns() {
        Pawn1 = new PawnView(this, view, view.getRedPawn1());
        Pawn1.InitializePawn();
        Pawn2 = new PawnView(this, view, view.getRedPawn2());
        Pawn2.InitializePawn();
        Pawn3 = new PawnView(this, view, view.getYellowPawn1());
        Pawn3.InitializePawn();
        Pawn4 = new PawnView(this, view, view.getYellowPawn2());
        Pawn4.InitializePawn();
    }

    /**
     * Initializes players,
     * setting their name from user input from the first scene controller <LoginController>
     * @param playerName1 player1 name
     * @param playerName2 player2 name
     * @post the players will have a color, a name and pawns, that are placed at start model position.
     */
    public void initializePlayers(String playerName1, String playerName2) {

        player1 = new Player(Color.RED, playerName1, new Pawn(view.getRedPawn1()), new Pawn(view.getRedPawn2()) );
        player2 = new Player(Color.YELLOW, playerName2, new Pawn(view.getYellowPawn1()), new Pawn(view.getYellowPawn2()));
        player1.getPawn1().setPosition(view.getRedStart1());
        player1.getPawn2().setPosition(view.getRedStart2());
        player2.getPawn1().setPosition(view.getYellowStart1());
        player2.getPawn2().setPosition(view.getYellowStart2());
        deck.getSquare(player1.getPawn1().getPosition()).setPawn(player1.getPawn1());
        deck.getSquare(player1.getPawn2().getPosition()).setPawn(player1.getPawn2());
        deck.getSquare(player2.getPawn1().getPosition()).setPawn(player2.getPawn1());
        deck.getSquare(player2.getPawn2().getPosition()).setPawn(player2.getPawn2());
        view.updateInfoBox(currentPlayer().getName(), deck.getCardDeck().size());
    }

    /**
     * Return the board map of the game, filled in order (based on the GAME MAP printed in console output)
     * @return a rectangle ArrayList of the board map
     */
    private ArrayList<Rectangle> initBoard() {
        ArrayList<Rectangle> board = new ArrayList<>();
        int i = 0;
        for(Node node : view.getRectangleGroup().getChildren()) {
            board.add((Rectangle) node);
            System.out.println(i + "  " + node.getId());
            i++;
        }
        return board;
    }

    /**
     * Choose a random player to start.
     * @pre the game hasn't been started.
     * @post one of the two players turn will be set to true
     * @return the player to start
     */
    private Player chooseStartingPlayer() {
        ArrayList<Player> list = new ArrayList<>();
        list.add(player1);
        list.add(player2);
        Player random = list.get(new Random().nextInt(2));
        random.setHisTurn(true);
        return random;
    }

    /**
     * Get the player that has to play this turn, if there isn't a current player choose one by random
     * @return current player
     */
    public Player currentPlayer() {

        if(player1.isHisTurn())
            return player1;
        else if(player2.isHisTurn())
            return player2;
        else
            return chooseStartingPlayer();
    }

    /**
     * Draw a card from deck of cards. Set currentCard to card drawn. Update the view to show card drawn
     * @pre the currentPlayer mustn't have already drawn a card
     * @post the currentCard will be updated and the drawnCard will be removed from the deck.
     */
    public void DrawCard() {
    if(!currentPlayer().hasDrawn()) {
        deck.drawFromDeck();
            view.setCardImage(deck.getCurrentCard());
            view.updateGameLogBox(currentPlayer().getName()+ " Drew " + deck.getCurrentCard());
            view.updateInfoBox(currentPlayer().getName(), deck.getCardDeck().size());
            currentPlayer().setHasDrawn(true);
      }
  }

    /**
     * Fold this round
     * @pre current player must have drawn a card and their isn't any valid moves to play
     * @post the round will end and the current player will change
     */
    public void Fold() {
        if(currentPlayer().hasDrawn() && ((noValidMove(currentPlayer().getPawn1().getPawn()) && noValidMove(currentPlayer().getPawn2().getPawn())) || deck.getCurrentCard().getCardNumber() == 11)) {
          view.updateGameLogBox(currentPlayer().getName() + " has Folded.");
            endTurn();
    }
    }

    /**
     * Set pawn has finished (arrived at home square position)
     * @param pawn current pawn that moved
     * @pre pawn must be at one of the two home positions
     */
    public void isFinished(Circle pawn) {
        Pawn curr = GetPawnMoved(pawn);

        if(curr.getColor().equals(Color.RED)) {
            if(curr.getPosition().equals(view.getRedHome1()) || curr.getPosition().equals(view.getRedHome2()))
                curr.setFinished(true);
        }else
            if(curr.getPosition().equals(view.getYellowHome1()) || curr.getPosition().equals(view.getYellowHome2()))
                curr.setFinished(true);
    }

    /**
     * End the current round
     * @post if current card is 2 current player replay else change current player
     */
    public void EndTurn() {
        if(!deck.getCurrentCard().numberSevenSplitMove())
            if(deck.getCurrentCard().getCardNumber() == 2)
                currentPlayer().setHasDrawn(false);
             else
                endTurn();
    }

    /**
     * Change current player and update Info Box
     */
    private void endTurn() {

        if(currentPlayer() == player1) {
            player1.setHisTurn(false);
            player2.setHisTurn(true);
            player2.setHasDrawn(false);
        }else {
            player2.setHisTurn(false);
            player1.setHisTurn(true);
            player1.setHasDrawn(false);
        }
        view.updateInfoBox(currentPlayer().getName(), deck.getCardDeck().size());
    }

    /**
     * Get current pawn that moved
     * @param c the interface pawn that got clicked
     * @return model current pawn
     */
    private Pawn GetPawnMoved(Circle c) {
        if(c == currentPlayer().getPawn1().getPawn())
            return currentPlayer().getPawn1();
        else
            return currentPlayer().getPawn2();
    }

    /**
     * Check if the interface pawn move is Valid based on the card he has drawn
     * @param destination the destination rectangle the interface pawn got dropped
     * @param pawn the interface pawn moved
     * @return true if valid move else false
     */
    public boolean ValidMove(Rectangle destination, Circle pawn) {

        Pawn currentPawn = GetPawnMoved(pawn);
        Square s = deck.getSquare(destination);

        if(currentPawn!=null) {

           if(destination == currentPawn.getPosition())
                return false;
           else if(deck.getCurrentCard().movePawn(currentPawn, friendlyPawn(currentPawn), s, deck)) { //check valid card move

                if(friendlyPawn(currentPawn).getPosition() == destination) //if friendly pawn on destination square
                   return false;
                if(deck.getCurrentCard().getCardNumber() == 11 && (deck.getSquare(currentPawn.getPosition()).isSafeZone() || deck.getSquare(currentPawn.getPosition()).isHomeSquare())) //handle card eleven move when pawn is at safety zone
                    return false;
                if(deck.getCurrentCard().getCardNumber() == 4 && deck.getSquare(currentPawn.getPosition()).isHomeSquare()) //handle card number 4 move when pawn is at home square
                    return false;
                if(deck.getCurrentCard().getCardNumber() == 7) //handle card number 7
                    return handleCardNumberSeven(destination, currentPawn);

                return true;
            }
        }
        return false;
    }

    /**
     * Check if card number 7 move made is valid
     * @param destination the destination of the pawn moved
     * @param currentPawn the pawn moved
     * @return true if valid else false
     */
    private boolean handleCardNumberSeven(Rectangle destination, Pawn currentPawn) {
        NumberSevenCard seven = (NumberSevenCard) deck.getCurrentCard();

        if(!seven.isSecondMove()) {  //if first move
            if(seven.getNumberOfSquaresMoved() == 0) //if pawn moved seven squares
                return true;
            //if split move
            if(!checkValidNumberSevenMove(destination, currentPawn, friendlyPawn(currentPawn))) { //check if the second move can be possible if not return false
                seven.reset();
                return false;
            }
        }else
            seven.reset();

        return true;
    }

    /**
     * Check if friendly pawn can do a valid move when doing a split number seven move
     * @param destination the destination of the first pawn that moved
     * @param currentPawn the first pawn
     * @param friendlyPawn the second pawn
     * @return true if there is a valid move else false
     */
    private boolean checkValidNumberSevenMove(Rectangle destination, Pawn currentPawn, Pawn friendlyPawn) {

        NumberSevenCard seven = (NumberSevenCard) deck.getCurrentCard();
        Square dest = deck.getSquare(destination);

        //check if first pawn lands on a slide start and his friendly pawn is in the slide causing it to go back to home
        if(dest.isSlideStart() && currentPawn.getColor() != dest.getColor()) {
            Square s1 = dest;
            for(int i=0; i < dest.getSlideBoost()+1; i++) {
                if(friendlyPawn.getPosition() == s1.getSquare())
                    return false;
                s1 = deck.getSquare(s1.getNext());
            }
        }
        //check all squares to find if friendly pawn has at least one valid move
        for(int i=0; i<77; i++) {
            Square s = deck.getBoard().get(i);
            if(seven.secondMove(friendlyPawn,s,deck) && destination != s.getSquare()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adjust model pawn to the move of the interface pawn,
     *  set model pawn position to destination and set model destination pawn to current pawn , do sorry move or swap move
     * @param destination the rectangle the pawn got dropped
     * @param pawn the interface pawn moved
     */
    public void Adjust(Rectangle destination, Circle pawn) {
        Pawn currentPawn = GetPawnMoved(pawn);

        Square s = deck.getSquare(destination);

        if(s.getPawn()!=null) { //if sorry or swap move (enemy pawn on destination square or sorry card or card number 11)
            if (deck.getCurrentCard().getCardNumber() == 11) {
                swapMove(s, currentPawn);
            }
            else {
                SorryMove(s, currentPawn);
                deck.getSquare(currentPawn.getPosition()).removePawn();
            }
        }else
            deck.getSquare(currentPawn.getPosition()).removePawn();

        s.setPawn(currentPawn);
        currentPawn.setPosition(destination);

        if(deck.getCurrentCard().getCardNumber() == 1 || deck.getCurrentCard().getCardNumber() == 2 || deck.getCurrentCard().getCardNumber() == 13) {
            currentPawn.setStarted(true);
        }
    }

    /**
     * Check if there is a valid move to be played with current pawn (checks all rectangles)
     * @param pawn pawn moved
     * @return true if there isn't a valid move else false
     */
    private boolean noValidMove(Circle pawn) {
        if(deck.getCurrentCard().getCardNumber() != 7) {
            Pawn currentPawn = GetPawnMoved(pawn);

            Square s = deck.getSquare(currentPawn.getPosition());

            for (int i = 0; i < 77; i++) {
                if (ValidMove(s.getSquare(), currentPawn.getPawn()))
                    return false;
                s = deck.getBoard().get(i);
            }
        }
        return true;
    }

    /**
     * Return the rectangle that the pawn can move to (checks all rectangles)
     * @param pawn pawn moved
     * @return valid rectangle
     */
    public Rectangle getValidDestination(Circle pawn) {
       if(deck.getCurrentCard().getCardNumber() != 7) {
            Pawn currentPawn = GetPawnMoved(pawn);

            Square s = deck.getSquare(currentPawn.getPosition());

            for (int i = 0; i < 77; i++) {
                if (ValidMove(s.getSquare(), currentPawn.getPawn()) && (s.getSquare() != currentPawn.getDest1() && s.getSquare() != currentPawn.getDest2())) {

                    if (currentPawn.getDest1() == null)
                        currentPawn.setDest1(s.getSquare());
                    else if (currentPawn.getDest2() == null)
                        currentPawn.setDest2(s.getSquare());

                    if (s.isHomeSquare())
                        if (s.getSquare() == view.getRedHome1() || s.getSquare() == view.getRedHome2())
                            return view.getRedHomeSquare();
                        else if (s.getSquare() == view.getYellowHome1() || s.getSquare() == view.getYellowHome2())
                            return view.getYellowHomeSquare();

                    return s.getSquare();
                }
                s = deck.getBoard().get(i);
            }

       }
        return null;
    }

    /**
     * Remove all valid destination Rectangles from the model pawn
     * @param pawn pawn moved
     */
    public void removeValidDestination(Circle pawn) {
        Pawn currentPawn = GetPawnMoved(pawn);
        currentPawn.setDest1(null);
        currentPawn.setDest2(null);
    }

    /**
     * Return the start rectangle the sorry pawn can move to
     * @param sorryPawn the pawn that got owned
     * @return one of the two start rectangle
     */
    private Rectangle getSorryRectangle(Pawn sorryPawn) {

        if(sorryPawn.getColor().equals(Color.RED)) {
            if (deck.getBoard().get(64).getPawn() == null) {
                return deck.getBoard().get(64).getSquare();
            }else if(deck.getBoard().get(65).getPawn() == null) {
                return deck.getBoard().get(65).getSquare();
            }
        }else {
            if (deck.getBoard().get(66).getPawn() == null) {
                return deck.getBoard().get(66).getSquare();
            }else if(deck.getBoard().get(67).getPawn() == null) {
                return deck.getBoard().get(67).getSquare();
            }
        }
        return null;
    }

    /**
     * Get the interface pawn that equals model pawn SorryPawn
     * @param SorryPawn model pawn
     * @return interface pawn
     */
    private PawnView getSorryPawn(Pawn SorryPawn) {
        if(Pawn1.getPawn().equals(SorryPawn.getPawn()))
            return Pawn1;
        else if(Pawn2.getPawn().equals(SorryPawn.getPawn()))
            return Pawn2;
        else if(Pawn3.getPawn().equals(SorryPawn.getPawn()))
            return Pawn3;
        return Pawn4;
    }

    /**
     * Do a sorry move (the destination pawn will go to the start position and the moved pawn will take it's place)
     * update the view
     * @param dest the destination the pawn moved will go
     * @param currentPawn the pawn moved
     */
    private void SorryMove(Square dest, Pawn currentPawn) {
        if(!currentPawn.isStarted())
            currentPawn.setStarted(true);
        if(dest.getPawn().isStarted())
            dest.getPawn().setStarted(false);
        PawnView pv = getSorryPawn(dest.getPawn());
        Rectangle start = getSorryRectangle(dest.getPawn());
        pv.SorryMove(start);
        dest.getPawn().setPosition(start);
        deck.getSquare(start).setPawn(dest.getPawn());
    }

    /**
     * do a swap move(card 11 move),
     * destination pawn will be set to pawn moved position and pawn moved will be set to destination,
     * update the view
     * @param dest the destination the pawn moved was placed
     * @param currentPawn the pawn moved
     */
    private void swapMove(Square dest, Pawn currentPawn) {

        Square s = deck.getSquare(currentPawn.getPosition()); //position of current pawn
        Pawn d = dest.getPawn(); //pawn in destination square
        PawnView pv = getSorryPawn(dest.getPawn()); //view of pawn in destination square

        s.setPawn(d);
        d.setPosition(currentPawn.getPosition());
        pv.SorryMove(currentPawn.getPosition());
    }

    /**
     * Return the second pawn of the current player (the pawn that hasn't been moved)
     * @param currentPawn the pawn moved
     * @return the pawn that hasn't been moved
     */
    private Pawn friendlyPawn(Pawn currentPawn) {
        if(currentPlayer().getPawn1() == currentPawn)
            return currentPlayer().getPawn2();
        else
            return currentPlayer().getPawn1();
    }

    /**
     * Check if the game has finished
     * @return true if one of the two players have won else false
     */
    public boolean GameFinished() {
        return player1.playerHasFinished() || player2.playerHasFinished();
    }

    /**
     * Get the player that won
     * @return the players name and a victory text.
     */
    public String Victory() {
        if(player1.playerHasFinished())
            return player1.getName().toUpperCase() + " WON!";
        else
            return player2.getName().toUpperCase() + " WON!";
    }

    /**
     * Handle Big squares
     * If pawn got placed in Home or Start square return the one of the two position it can go
     * @param r rectangle the pawn moved got placed
     * @return the rectangle the pawn moved can be placed
     */
    public Rectangle HandleBigSquares(Rectangle r) {
        if (r == view.getRedHomeSquare()) {
            if(deck.getBoard().get(60).getPawn() == null)
                return view.getRedHome1();
            else if(deck.getBoard().get(61).getPawn() == null)
                return view.getRedHome2();
        }else if (r == view.getYellowHomeSquare()) {
            if(deck.getBoard().get(62).getPawn() == null)
                return view.getYellowHome1();
            if(deck.getBoard().get(63).getPawn() == null)
                return view.getYellowHome2();
        }else if(r == view.getRedStartSquare()) {
            if(deck.getBoard().get(64).getPawn() == null)
                return view.getRedStart1();
            else if(deck.getBoard().get(65).getPawn() == null)
                return view.getRedStart2();
        }else if (r == view.getYellowStartSquare()) {
            if(deck.getBoard().get(66).getPawn() == null)
                return view.getYellowStart1();
            else if(deck.getBoard().get(67).getPawn() == null)
                return view.getYellowStart2();
        }
        return r;
    }

    /**
     * Check if r is a slide start square
     * @param r Rectangle to be checked
     * @return true if slide start else false
     */
    private boolean isSlideStart(Rectangle r) {
        Square s = deck.getSquare(r);
        return s.isSlideStart();
    }

    /**
     * Check if there is a pawn placed inside a slide
     * @param r the slide start
     * @param pawn the current pawn moved at slide start
     * @return true if a pawn was found else false
     */
    private boolean checkPawnAtSlide(Rectangle r, Pawn pawn) {
        Square s = deck.getSquare(r);
        int end = s.getSlideBoost();

        while(end > 0) {
            s = deck.getSquare(s.getNext());

            if(s.getPawn() != null && s.getPawn() != pawn)
                return true;
            end--;
        }
        return false;
    }

    /**
     * Get the pawn that is inside the slide
     * @param r the slide start
     * @return pawn inside slide or null if there isn't any
     */
    private Pawn getPawnAtSlide(Rectangle r) {
        Square s = deck.getSquare(r);
        int end = s.getSlideBoost();

        while(end > 0) {
            s = deck.getSquare(s.getNext());
            if(s.getPawn() != null) {
                return s.getPawn();
            }
            end--;
        }
        return null;
    }

    /**
     * Return the end of the slide
     * @param r slide start
     * @return slide end
     */
    private Rectangle getSlideEnd(Rectangle r) {
        Square s = deck.getSquare(r);
        int end = s.getSlideBoost();

        while(s!=null && end>0) {
            s = deck.getSquare(s.getNext());
            end--;
        }

        assert s != null;
        return s.getSquare();
    }

    /**
     * Do a slide move
     * @param r pawn moved destination
     * @param currentPawn the pawn to slide
     * @return the end position if r is a slide start else return r unchanged
     */
    public Rectangle SlideMove(Rectangle r, Circle currentPawn) {

        Pawn pawn = GetPawnMoved(currentPawn);
        Square s = deck.getSquare(r);

        if(isSlideStart(r) && pawn.getColor() != s.getColor()) {
            while(checkPawnAtSlide(r, pawn)) {
                s = deck.getSquare(Objects.requireNonNull(getPawnAtSlide(r)).getPosition());
                SorryMove(s, pawn);
                s.removePawn();
            }

            deck.getSquare(r).removePawn();
            Square dest = deck.getSquare(getSlideEnd(r));
            dest.setPawn(pawn);
            pawn.setPosition(dest.getSquare());
            assert r!=null;
            r = getSlideEnd(r);
        }
        return r;
    }

}
