/**
 * This is the BJ file that contains
 * the BJ class
 */
import java.util.List;
import java.util.Scanner;

/**
 * This is the BJ class that
 * extends the BJAbstract abstract class
 */
public class BJ extends BJAbstract{

    // Messages for the game.
    protected static final String GAME_NOT_IMPLEMENTED =
        "Game not yet implemented.";

    // Constructor
    public BJ(){
        this.deckIndex = 0;
        this.possibleMoves = DEFAULT_MOVES;   
    }

    public static void main(String[] args) {
        BJ game = new BJ(); // create game instance
        game.startGame(); // start game

        while(true){
            if(args[0].equals(QUIT)){
                System.out.println("Thanks for playing!");
                break;
            }
            // TODO
        }
    }
    
    public void startGame(){
        System.out.println("Welcome to Blackjack!");
        shuffleDeck();
        initialDeal();
    }

    public void hit(List<String> hand, List<String> deck){
        if(!deck.isEmpty()){
            String card = deck.remove(0);
            hand.add(card);
            deckIndex++;
        }
        else{
            System.out.println("The deck is empty!");
        }
    }

    public void initialDeal(){
        deckIndex++; // burn first card
        hit(playerHand, deck);
        hit(dealerHand, deck);

        hit(playerHand, deck);
        hit(dealerHand, deck);
    }
    
    public void playerTurn(){
        Scanner scanner = new Scanner(System.in);
        String move;
        boolean isValid = false;

        while(!isValid){
            System.out.print(PROMPT_MOVE);
            move = scanner.nextLine().toLowerCase();
            if(isValidMove(move)){
                isValid = true;
                if(move.equals("hit")){
                    hit(playerHand, deck);
                }
                else if(move.equals("stand")){
                    break;
                }
                else if(move.equals("double")){
                    // TODO
                }
                else if(move.equals("split")){
                    // TODO
                }
            }
        }
    }
    
    public void dealerTurn(){
        if(handValue(dealerHand) < 17){
            hit(dealerHand, deck);
        }
        else{
            // TODO
        }

    }
    
    public int determineWinner(){
        int playerValue = handValue(playerHand);
        int dealerValue = handValue(dealerHand);

        if(playerValue > dealerValue){
            return 1; // player wins, return pos
        }
        else if(playerValue < dealerValue){
            return -1; // dealer wins, return neg
        }
        return 0; // push (nothing happens), return 0
    }
}