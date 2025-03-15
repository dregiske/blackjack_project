/**
 * This is the BJ file that contains
 * the BJ class
 */
import java.util.ArrayList;
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

        this.bettingWallet = initialWallet;

        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();

        this.possibleMoves = DEFAULT_MOVES;   
    }

    public static void main(String[] args) {
        BJ game = new BJ(); // create game instance
        game.startGame(); // start game

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println(PROMPT_PLAY);
            String input = scanner.nextLine().toLowerCase();

            // check if player quits
            if(input.equals(QUIT)){
                System.out.println(THANKS);
                break;
            }

            // check if there are enough cards in deck
            if(game.deckIndex >= 40){
                System.out.println("Shuffling deck. . .");
                game.shuffleDeck();
                game.deckIndex = 0; // reset deck index
            }

            System.out.println(PLACE_BETS);
            input = scanner.nextLine().toLowerCase();
            game.parseBet(input);

            game.initialDeal();
            game.printPlayerHand(game.playerHand);
            game.printDealerHalfHand(game.dealerHand);
            game.playerTurn();
            game.dealerTurn();
            game.determineWinner();
        }
        scanner.close();
    }
    
    public void startGame(){
        System.out.println("Welcome to Blackjack!");
        shuffleDeck();
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
                    // incorporate a betting system for double
                    break;
                }
                else if(move.equals("split")){
                    // incorporate a split system
                    break;
                }
            }
        }
        scanner.close();
    }
    
    public void dealerTurn(){
        printDealerFullHand(dealerHand);
        while(handValue(dealerHand) < 17){
            System.out.println("Dealer hits.");
            hit(dealerHand, deck);
            printDealerFullHand(dealerHand);
        }

    }
    
    public int determineWinner(){
        int playerValue = handValue(playerHand);
        int dealerValue = handValue(dealerHand);

        if(playerValue > dealerValue && playerValue <= 21){
            System.out.println(PLAYER_WIN);
            return 1; // player wins, return pos
        }
        else if(playerValue < dealerValue && dealerValue <= 21){
            if(dealerValue == 21){
                System.out.println(DEALER_21);
            }
            System.out.println(DEALER_WIN);
            return -1; // dealer wins, return neg
        }
        System.out.println(PUSH);
        return 0; // push (nothing happens), return 0
    }
}