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

            int bet = game.bettingProcess(scanner);

            game.initialDeal();
            game.printPlayerHand(game.playerHand);
            game.printDealerHalfHand(game.dealerHand);
            game.playerTurn(scanner);
            game.dealerTurn();
        
            if(game.determineWinner() == 1){
                if(game.handValue(game.playerHand) == 21){
                    System.out.println(PLAYER_21);
                }
                else{
                    System.out.println(PLAYER_WIN);
                }
                game.bettingWallet = game.bettingWallet + (bet * 2);
            }
            else if(game.determineWinner() == 0){
                if(game.handValue(game.dealerHand) == 21){
                    System.out.println(DEALER_21);
                }
                else{
                    System.out.println(DEALER_WIN);
                }
                game.bettingWallet = game.bettingWallet - bet;
            }
            else{
                System.out.println(PUSH);
            }
            System.out.println("You have $" + game.bettingWallet + " left.");

            game.resetHand(game.playerHand);
            game.resetHand(game.dealerHand);
        }
        scanner.close();
    }
    
    public void startGame(){
        System.out.println("Welcome to Blackjack!");
        shuffleDeck();
    }

    public void resetHand(List<String> hand){
        while(!hand.isEmpty()){
            hand.remove(0);
        }
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
    
    public void playerTurn(Scanner scanner){
        String move;
        
        while(true){
            System.out.println(PROMPT_MOVE);
            move = scanner.nextLine().toLowerCase();
            if(isValidMove(move)){
                if(move.equals("hit")){
                    hit(playerHand, deck);
                    if(handValue(playerHand) > 21){
                        break;
                    }
                    possibleMoves = POST_HIT;
                    printPlayerHand(playerHand);
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
            else{
                System.out.println(INVALID_INPUT);
            }
        }
    }
    
    public void dealerTurn(){
        printDealerFullHand(dealerHand);
        while(handValue(dealerHand) < 17){
            if(handValue(playerHand) > 21){
                break;
            }
            System.out.println("Dealer hits.");
            hit(dealerHand, deck);
            printDealerFullHand(dealerHand);
        }
    }

    public int bettingProcess(Scanner scanner){
        String bet;
        int returnBet;

        while(true){
            System.out.println(PLACE_BETS);
            bet = scanner.nextLine().toLowerCase();
            if(!isValidBet(parseBet(bet))){
                System.out.println("Invalid bet amount, you have $" + bettingWallet + " to bet with.");
            }
            else{
                returnBet = parseBet(bet);
                break;
            }
        }
        return returnBet;
    }
    
    public int determineWinner(){
        int playerValue = handValue(playerHand);
        int dealerValue = handValue(dealerHand);

        if(playerValue > 21){
            System.out.println(PLAYER_BUST);
            return -1; // player bust, return neg
        }
        else if(dealerValue > 21){
            System.out.println(DELAER_BUST);
            return 1; // dealer bust, return pos
        }
        else if(playerValue > dealerValue){
            if (playerValue == 21){
                System.out.println(PLAYER_21);
            }
            else{
                System.out.println(PLAYER_WIN);
            }
            return 1; // player wins, return pos
        }
        else if(playerValue < dealerValue){
            if(dealerValue == 21){
                System.out.println(DEALER_21);
            }
            else{
                System.out.println(DEALER_WIN);
            }
            return -1; // dealer wins, return neg
        }
        System.out.println(PUSH);
        return 0; // push (nothing happens), return 0
    }
}