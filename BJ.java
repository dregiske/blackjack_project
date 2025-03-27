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

        this.handCount = 1;

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
            
            game.bet = game.bettingProcess(scanner);

            game.initialDeal();
            game.printPlayerHand(game.playerHand);
            game.printDealerHalfHand(game.dealerHand);
            game.playerTurn(scanner, game.playerHand);
            game.dealerTurn();

            int playerValue = game.handValue(game.playerHand);
            int dealerValue = game.handValue(game.dealerHand);
            int outcome = game.determineWinner();
        
            if(outcome == 1){
                if(playerValue == 21){
                    System.out.println(PLAYER_21);
                    
                    game.bettingWallet = game.bettingWallet + (game.bet * 2);
                    // if only 2 cards, pay 3/2 odds
                }
                else{
                    System.out.println(PLAYER_WIN);
                    game.bettingWallet = game.bettingWallet + (game.bet);
                }
            }
            else if(outcome == -1){
                if(dealerValue == 21){
                    System.out.println(DEALER_21);
                }
                else{
                    System.out.println(DEALER_WIN);
                }
                game.bettingWallet = game.bettingWallet - game.bet;
            }
            else{
                System.out.println(PUSH);
            }
            System.out.println("You have $" + game.bettingWallet + " left.");

            game.resetGame();
        }
        scanner.close();
    }
    
    public void startGame(){
        System.out.println("Welcome to Blackjack!");
        shuffleDeck();
    }

    public void resetGame(){
        resetHand(playerHand);
        resetHand(playerHand_1);
        resetHand(playerHand_2);
        resetHand(playerHand_3);
        resetHand(dealerHand);
        handCount = 1;
    }

    public void fullResetGame(){
        resetHand(playerHand);
        resetHand(playerHand_1);
        resetHand(playerHand_2);
        resetHand(playerHand_3);
        resetHand(dealerHand);
        bettingWallet = 200;
        shuffleDeck();
        deckIndex = 0;
        handCount = 1;
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
        deck.remove(0); // burn first card

        hit(playerHand, deck);
        hit(dealerHand, deck);
        hit(playerHand, deck);
        hit(dealerHand, deck);
    }
    
    public void playerTurn(Scanner scanner, List<String> hand){
        String card_1 = hand.get(0);
        String card_2 = hand.get(1);
        if(card_1.charAt(0) == card_2.charAt(0)){
            possibleMoves = SPLIT_OPTION;
        }
        else{
            possibleMoves = DEFAULT_MOVES;
        }

        String move;
        
        while(true){
            System.out.println(PROMPT_MOVE);
            move = scanner.nextLine().toLowerCase();
            if(isValidMove(move)){
                if(move.equals("hit")){
                    hit(hand, deck);
                    possibleMoves = POST_HIT;
                    printPlayerHand(hand);
                    if(handValue(hand) > 21){
                        break;
                    }
                }

                else if(move.equals("stand")){
                    break;
                }

                else if(move.equals("double")){
                    doubleBet(hand);
                    printPlayerHand(hand);
                    break;
                }

                else if(move.equals("split")){
                    split(hand, scanner);
                }
            }
            else{
                System.out.println(INVALID_INPUT);
            }
        }
    }
    
    public void doubleBet(List<String> hand){
        bet = bet * 2;
        hit(hand, deck);
        possibleMoves = POST_DOUBLE;
    }

    public void split(List<String> hand, Scanner scanner){
        List<String> splitHand = null;

        if(handCount == 1){
            splitHand = playerHand_1;
        }
        else if(handCount == 2){
            splitHand = playerHand_2;
        }
        else if(handCount == 3){
            splitHand = playerHand_3;
        }

        if(splitHand == null){
            System.out.println("Invalid hand index.");
            return;
        }

        String splitCard = hand.remove(1);
        splitHand.add(splitCard);
        handCount++;

        hit(hand, deck);
        playerTurn(scanner, hand);

        hit(splitHand, deck);
        playerTurn(scanner, splitHand);
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
        // check if soft 17, then hit, otherwise continue
    }

    public int bettingProcess(Scanner scanner){
        if(bettingWallet == 0){
            System.out.println("No more funds to play! Resetting game.");
            fullResetGame();
        }

        String input;

        while(true){
            System.out.println(PLACE_BETS);
            input = scanner.nextLine().toLowerCase();
            if(!isValidBet(parseBet(input))){
                System.out.println("Invalid bet amount, you have $" + bettingWallet + " to bet with.");
            }
            else{
                bet = parseBet(input);
                break;
            }
        }
        return bet;
    }
    
    public int determineWinner(){
        int playerValue = handValue(playerHand);
        int dealerValue = handValue(dealerHand);

        if(playerValue > 21){
            return -1; // player bust, return neg
        }
        else if(dealerValue > 21){
            return 1; // dealer bust, return pos
        }
        else if(playerValue > dealerValue){
            return 1; // player wins, return pos
        }
        else if(playerValue < dealerValue){
            return -1; // dealer wins, return neg
        }
        return 0; // push (nothing happens), return 0
    }

    public int countCards(List<String> hand){
        int count = 0;
        for(int i = 0; hand.get(i) != null; i++){
            count++;
        }
        return count;
    }
}